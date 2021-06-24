package com.example.cinemaapp.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinemaapp.R
import com.example.cinemaapp.databinding.DetailFragmentBinding
import com.example.cinemaapp.databinding.MainFragmentBinding
import com.example.cinemaapp.model.Cinema

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
       arguments?.getParcelable<Cinema>(BUNDLE_EXTRA).let {
           nameDetails.text = it?.name
           descriptionDetails.text = it?.description
           releaseDateDetails.text = it?.releaseDate
           revenueDetails.text = it?.revenue
           durationDetails.text = it?.duration
           ratingDetails.text = it?.rating
           typeDetails.text = it?.type
           descriptionDetails.movementMethod = ScrollingMovementMethod()
       }

    }
    

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}