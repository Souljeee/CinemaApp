package com.example.cinemaapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemaapp.R
import com.example.cinemaapp.adapters.MainScreenAdapter
import com.example.cinemaapp.viewmodel.AppState
import com.example.cinemaapp.viewmodel.MainViewModel
import com.example.cinemaapp.databinding.MainFragmentBinding
import com.example.cinemaapp.model.Cinema
import com.example.cinemaapp.model.CinemaLoader
import com.example.cinemaapp.model.RepositoryImpl
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainScreenAdapter

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
                val cinemaData = appState.cinemaData
                loadingLayout.visibility = View.GONE
                initRecyclerView()
                adapter.setCinema(cinemaData)
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


    private fun initRecyclerView() = with(binding) {
        mainRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        mainRecyclerView.layoutManager = layoutManager

        adapter = MainScreenAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                getCinemaDetailsFromServer(cinema)
            }
        })
        mainRecyclerView.adapter = adapter

    }

    fun getCinemaDetailsFromServer(cinema : Cinema) {
        val id = cinema.id

        id?.let { viewModel.getCinemaFromRemoteSource(it) }

    }

    override fun onDestroy() {
        adapter.removeListener()
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