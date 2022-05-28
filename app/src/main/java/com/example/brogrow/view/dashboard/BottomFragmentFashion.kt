package com.example.brogrow.view.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentBottomFashionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomFragmentFashion :BottomSheetDialogFragment(){
lateinit var binding:FragmentBottomFashionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_bottom_fashion, container, false)
        var arrayList=ArrayList<String>()
        arrayList.add("Kunal Is God")
        arrayList.add("kUNAL IS GOOD")
        arrayList.add("Kunal is best")
        arrayList.add("kunal is nice")
        arrayList.add("good")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayList)
        binding.listview.adapter=adapter

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.getFilter().filter(s);
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return binding.root
    }


}