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
        val cinema = arguments?.getParcelable<Cinema>(BUNDLE_EXTRA)
        if(cinema != null){
            nameDetails.text = cinema.name
            descriptionDetails.text = cinema.description
            releaseDateDetails.text = cinema.releaseDate
            revenueDetails.text = cinema.revenue
            durationDetails.text = cinema.duration
            ratingDetails.text = cinema.rating
            typeDetails.text = cinema.type
            descriptionDetails.movementMethod = ScrollingMovementMethod()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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