package com.example.brogrow.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentHomePageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomePageFragment : Fragment() {

    lateinit var  binding:FragmentHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home_page, container, false)
        binding.Location.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialodView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_pincode, null)
                bottomSheet.setContentView(dialodView)
                bottomSheet.show()
            }
        })
        binding.Fashion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialodView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.fragment_bottom_fashion, null)
                bottomSheet.setContentView(dialodView)
                bottomSheet.show()
            }
        })
        return binding.root
    }


}