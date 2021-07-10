package com.example.cinemaapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemaapp.adapters.HistoryAdapter
import com.example.cinemaapp.databinding.HistoryFragmentBinding
import com.example.cinemaapp.viewmodel.AppState
import com.example.cinemaapp.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {



    private lateinit var viewModel: HistoryViewModel

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                initRecyclerView()
                adapter.setCinema(appState.cinemaData)
            }
        }

    }

    private fun initRecyclerView() = with(binding) {
        historyRcView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        historyRcView.layoutManager = layoutManager

        adapter = HistoryAdapter()
        historyRcView.adapter = adapter

    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

}