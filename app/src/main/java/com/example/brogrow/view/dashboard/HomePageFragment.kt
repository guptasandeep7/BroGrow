package com.example.brogrow.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.brogrow.R
import com.example.brogrow.databinding.ActivityDashBoardBinding
import com.example.brogrow.databinding.FragmentHomePageBinding


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

        return binding.root
    }


}