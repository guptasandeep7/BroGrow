package com.example.brogrow.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brogrow.R
import com.example.brogrow.repo.Datastore
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class LandingFragment : Fragment() {
    lateinit var datastore: Datastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing, container, false)

        datastore = Datastore(requireContext())

        val startButton = view.findViewById<MaterialButton>(R.id.start_btn)

        startButton.setOnClickListener {
            lifecycleScope.launch {
                if (datastore.isLogin()) {
                    findNavController().navigate(R.id.action_landingFragment_to_firstFragment)
                } else {
                    findNavController().navigate(R.id.action_landingFragment_to_phoneNumberFragment)
                }
            }
        }

        return view
    }
}