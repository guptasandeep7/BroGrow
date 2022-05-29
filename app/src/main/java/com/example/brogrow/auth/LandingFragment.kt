package com.example.brogrow.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brogrow.R
import com.google.android.material.button.MaterialButton

class LandingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing, container, false)

        val startButton = view.findViewById<MaterialButton>(R.id.start_btn)

        startButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_firstFragment)
        }

        return view
    }
}