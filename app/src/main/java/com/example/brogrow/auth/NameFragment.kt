package com.example.brogrow.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentNameBinding
import com.example.brogrow.model.UserResponse
import com.example.brogrow.network.ServiceBuilder
import com.example.brogrow.repo.DATASTORE_NAME
import com.example.brogrow.repo.Datastore
import com.example.brogrow.view.dashboard.DashBoardActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NameFragment : Fragment() {

    private var _binding:FragmentNameBinding? = null
    private val binding get() = _binding!!
    lateinit var datastore: Datastore
    private lateinit var phoneNumber:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        datastore = Datastore(requireContext())
        lifecycleScope.launch {
            phoneNumber = datastore.getUserDetails(Datastore.PHONE_NUMBER).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater,container,false)


        binding.nextBtn.setOnClickListener {
            binding.progressBar2.visibility = View.VISIBLE
            val name = binding.nameEt.text.toString()
            if(!name.isNullOrEmpty()){

                lifecycleScope.launch {
                    datastore.changeLoginState(true)
                    datastore.saveToDatastore(Datastore.NAME_KEY,name,requireContext())
                    createUser(phoneNumber,name)
                }
            }
            else{
                binding.nameLayout.helperText = "Enter valid name"
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun createUser(phoneNumber: String, name: String) {
        val request = ServiceBuilder.buildService()
        val call = request.createUser(name,phoneNumber)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                when {
                    response.isSuccessful -> {
                        binding.progressBar2.visibility = View.GONE
                        findNavController().navigate(R.id.action_nameFragment_to_firstFragment)
                    }
                    else -> Toast.makeText(requireContext(), "Failed!!! Try again", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onResume() {
        super.onResume()
        binding.nameEt.requestFocus()
        binding.nameEt.showKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}