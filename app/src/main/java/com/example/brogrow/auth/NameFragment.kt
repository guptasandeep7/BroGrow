package com.example.brogrow.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brogrow.databinding.FragmentNameBinding
import com.example.brogrow.repo.DATASTORE_NAME
import com.example.brogrow.repo.Datastore
import com.example.brogrow.view.dashboard.DashBoardActivity
import kotlinx.coroutines.launch

class NameFragment : Fragment() {

    private var _binding:FragmentNameBinding? = null
    private val binding get() = _binding!!
    lateinit var datastore: Datastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        datastore = Datastore(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater,container,false)


        binding.nextBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            if(!name.isNullOrEmpty()){
                lifecycleScope.launch {
                    datastore.changeLoginState(true)
                    datastore.saveToDatastore(Datastore.NAME_KEY,name,requireContext())
                }
                // navigate to dashboard
                startActivity(Intent(activity,DashBoardActivity::class.java))
                activity?.finish()
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