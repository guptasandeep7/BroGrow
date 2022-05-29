package com.example.brogrow.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.brogrow.R
import com.example.brogrow.databinding.FragmentComparisonPageBinding
import com.example.brogrow.utill.makeSlug
import com.example.brogrow.viewmodel.ComparisionPageViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.json.JSONException

class ComparisonPage : Fragment() {

    private var _binding: FragmentComparisonPageBinding? = null
    private val binding get() = _binding!!
    companion object{
        lateinit var selectedCategory: String
        lateinit var selectedPincode: String
        lateinit var selectedCategory2: String
        lateinit var selectedPincode2: String
    }
    lateinit var comparisionPageViewModel: ComparisionPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comparisionPageViewModel = ViewModelProvider(this)[ComparisionPageViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComparisonPageBinding.inflate(inflater, container, false)

        binding.categoryBtn.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            val dialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.fragment_bottom_fashion, null)
            bottomSheet.setContentView(dialogView)
            bottomSheet.show()
            var listview = dialogView.findViewById<ListView>(R.id.listview)
            var edittext = dialogView.findViewById<EditText>(R.id.edit_text)
            var arrayList = arrayListOf(
                "Hospitality",
                "Logistics",
                "Banks",
                "Telecom",
                "Paper",
                "Media & Entertainment",
                "Ship Building",
                "Automobile & Ancillaries",
                "Electricals",
                "Metals & Mining",
                "Footwear",
                "Software & IT Services",
                "Insurance",
                "Aviation",
                "Infrastructure",
                "Containers & Packaging",
                "Real Estate",
                "Construction Materials",
                "Photographic Products",
                "Diversified",
                "ETF",
                "Finance",
                "Alcohol",
                "Consumer Durables",
                "Capital Goods",
                "Textiles",
                "Miscellaneous",
                "Trading",
                "FMCG",
                "Plastic Products",
                "Healthcare",
                "Industrial Gases & Fuels",
                "Agri",
                "Oil & Gas",
                "Diamond  &  Jewellery",
                "Chemicals",
                "Manufacturing",
                "Power",
                "Retailing"
            )
            var adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayList)
            listview.adapter = adapter
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.getFilter().filter(s);
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

            listview.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    selectedCategory = arrayList[p2]
                    binding.categoryBtn.text = selectedCategory
                    binding.categoryBtn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    bottomSheet.cancel()
                }
        }

        binding.category2Btn.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            val dialogView =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.fragment_bottom_fashion, null)
            bottomSheet.setContentView(dialogView)
            bottomSheet.show()
            var listview = dialogView.findViewById<ListView>(R.id.listview)
            var edittext = dialogView.findViewById<EditText>(R.id.edit_text)
            var arrayList = arrayListOf(
                "Hospitality",
                "Logistics",
                "Banks",
                "Telecom",
                "Paper",
                "Media & Entertainment",
                "Ship Building",
                "Automobile & Ancillaries",
                "Electricals",
                "Metals & Mining",
                "Footwear",
                "Software & IT Services",
                "Insurance",
                "Aviation",
                "Infrastructure",
                "Containers & Packaging",
                "Real Estate",
                "Construction Materials",
                "Photographic Products",
                "Diversified",
                "ETF",
                "Finance",
                "Alcohol",
                "Consumer Durables",
                "Capital Goods",
                "Textiles",
                "Miscellaneous",
                "Trading",
                "FMCG",
                "Plastic Products",
                "Healthcare",
                "Industrial Gases & Fuels",
                "Agri",
                "Oil & Gas",
                "Diamond  &  Jewellery",
                "Chemicals",
                "Manufacturing",
                "Power",
                "Retailing"
            )
            var adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayList)
            listview.adapter = adapter
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.getFilter().filter(s);
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

            listview.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    selectedCategory2 = arrayList[p2]
                    binding.category2Btn.text = selectedCategory2
                    binding.category2Btn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    bottomSheet.cancel()
                }
        }


        binding.compareBtn.setOnClickListener {
            selectedPincode = binding.pincodeEt.text.toString()
            selectedPincode2 = binding.pincode2Et.text.toString()

            startActivity(Intent(requireActivity(), CompareActivity::class.java))

        }
        return binding.root
    }



}