package com.example.cinemaapp.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.R
import com.example.cinemaapp.adapters.*
import com.example.cinemaapp.viewmodel.AppState
import com.example.cinemaapp.viewmodel.MainViewModel
import com.example.cinemaapp.databinding.MainFragmentBinding
import com.example.cinemaapp.model.Cinema
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var refreshKey:Boolean = false
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var cinemaData: MutableList<MutableList<Cinema>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getCinemaFromLocalSource()
    }


    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                cinemaData = appState.cinemaData
                loadingLayout.visibility = View.GONE
                initNowPlayingRecyclerView()
                nowPlayingAdapter.setCinema(isAdultContent(cinemaData[2]))
                initPopularRecyclerView()
                popularAdapter.setCinema(isAdultContent(cinemaData[0]))
                initTopRatedRecyclerView()
                topRatedAdapter.setCinema(isAdultContent(cinemaData[3]))
                initUpcomingRecyclerView()
                upcomingAdapter.setCinema(isAdultContent(cinemaData[1]))
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessDetails -> {
                var cinemaDetails = appState.cinema
                loadingLayout.visibility = View.GONE
                activity?.supportFragmentManager?.apply {
                    this.beginTransaction()
                        .add(R.id.container, DetailFragment.newInstance(Bundle().apply {
                            putParcelable(DetailFragment.BUNDLE_EXTRA, cinemaDetails)
                        }))
                        .addToBackStack("")
                        .commit()
                }

            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                mainFragmentRoot.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getCinemaFromLocalSource() })
            }
        }
    }

    private fun isAdultContent(cinemaData: MutableList<Cinema>) : MutableList<Cinema> {
        activity?.let {
            if(!(it.getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_CONTENT_EXTRA,false))){
                for(i in 0 until cinemaData.size){
                    if(cinemaData[i].adult == true){
                        cinemaData.removeAt(i)
                    }
                }
            }
        }
        return cinemaData
    }


    private fun initPopularRecyclerView() = with(binding) {
        popularRv.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularRv.layoutManager = layoutManager

        popularAdapter = PopularAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                saveCinemaToHistory(cinema)
                getCinemaDetailsFromServer(cinema)
            }
        })
        popularRv.adapter = popularAdapter
    }

    private fun initTopRatedRecyclerView() = with(binding) {
        topRatedRv.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topRatedRv.layoutManager = layoutManager

        topRatedAdapter = TopRatedAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                saveCinemaToHistory(cinema)
                getCinemaDetailsFromServer(cinema)
            }
        })
        topRatedRv.adapter = topRatedAdapter
    }

    private fun initNowPlayingRecyclerView() = with(binding) {
        nowPlayingRv.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        nowPlayingRv.layoutManager = layoutManager

        nowPlayingAdapter = NowPlayingAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                saveCinemaToHistory(cinema)
                getCinemaDetailsFromServer(cinema)
            }
        })
        nowPlayingRv.adapter = nowPlayingAdapter
    }

    private fun initUpcomingRecyclerView() = with(binding) {
        upcomingRv.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        upcomingRv.layoutManager = layoutManager

        upcomingAdapter = UpcomingAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                saveCinemaToHistory(cinema)
                getCinemaDetailsFromServer(cinema)
            }
        })
        upcomingRv.adapter = upcomingAdapter
    }

    private fun saveCinemaToHistory(cinema: Cinema) {
        viewModel.saveCityToDB(cinema)
    }

    fun getCinemaDetailsFromServer(cinema : Cinema) {
        val id = cinema.id
        id?.let { viewModel.getCinemaFromRemoteSource(it) }
    }

    override fun onDestroy() {
        popularAdapter.removeListener()
        nowPlayingAdapter.removeListener()
        topRatedAdapter.removeListener()
        upcomingAdapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(cinema: Cinema)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}

private fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}