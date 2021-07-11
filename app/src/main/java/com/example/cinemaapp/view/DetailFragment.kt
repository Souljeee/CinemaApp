package com.example.cinemaapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinemaapp.R
import com.example.cinemaapp.databinding.DetailFragmentBinding
import com.example.cinemaapp.databinding.MainFragmentBinding
import com.example.cinemaapp.model.Cinema
import com.squareup.picasso.Picasso

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_CINEMA_EXTRA = "CINEMA"
const val DETAILS_FEELS_LIKE_EXTRA = "FEELS LIKE"
private const val TEMP_INVALID = -100
private const val FEELS_LIKE_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Cinema>(BUNDLE_EXTRA).let {
            nameDetails.text = it?.name
            descriptionDetails.text = it?.description
            releaseDateDetails.text = it?.releaseDate
            revenueDetails.text = it?.revenue.toString()
            durationDetails.text = it?.duration
            ratingDetails.text = it?.rating
            typeDetails.text = it?.type
            Picasso.get().load("https://image.tmdb.org/t/p/w500${it?.poster}").fit().into(detailPoster)
            descriptionDetails.movementMethod = ScrollingMovementMethod()
        }
    }


    companion object {
        private const val API_KEY = "37a33c74592d522dfdc42d090c29c4bf"
        const val BUNDLE_EXTRA = "cinema"

        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}