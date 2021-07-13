package com.example.cinemaapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.cinemaapp.R
import com.example.cinemaapp.databinding.DetailFragmentBinding
import com.example.cinemaapp.databinding.OptionsFragmentBinding
const val ADULT_CONTENT_EXTRA = "ADULT_CONTENT"
class OptionsFragment : Fragment() {


    private lateinit var binding : OptionsFragmentBinding
    private var isAdultContent = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =OptionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            adultContentSwitch.isChecked  = it.getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_CONTENT_EXTRA,false)
        }
        adultContentSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isAdultContent = true
                activity?.let {
                    it.getPreferences(Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(ADULT_CONTENT_EXTRA,isAdultContent)
                        .apply()
                }
            }else{
                isAdultContent = false
                activity?.let {
                    it.getPreferences(Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(ADULT_CONTENT_EXTRA,isAdultContent)
                        .apply()
                }
            }
        }
    }
    companion object {
        fun newInstance() = OptionsFragment()
    }

}