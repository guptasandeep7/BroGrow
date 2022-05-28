package com.example.brogrow.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentPhoneNumberBinding

class PhoneNumberFragment : Fragment() {
    private var _binding: FragmentPhoneNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)

        binding.otpBtn.setOnClickListener {
            val phone = binding.phoneEt.text
            if(phone.toString().length == 10)
            findNavController().navigate(R.id.action_phoneNumberFragment_to_otpFragment)
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
        binding.phoneEt.requestFocus()
        binding.phoneEt.showKeyboard()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}