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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : MainScreenAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater,container,false)

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
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getCinemaFromLocalSource() }
                    .show()
            }
        }
    }


    private fun initRecyclerView() = with(binding){
        mainRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        mainRecyclerView.layoutManager = layoutManager

        adapter  = MainScreenAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(cinema: Cinema) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailFragment.BUNDLE_EXTRA, cinema)
                    manager.beginTransaction()
                        .add(R.id.container, DetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }
        })
        mainRecyclerView.adapter = adapter

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