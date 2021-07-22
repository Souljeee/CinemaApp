package com.example.cinemaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemaapp.app.App.Companion.getHistoryDao
import com.example.cinemaapp.model.*
import com.example.cinemaapp.viewmodel.AppState.Success
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())

) : ViewModel() {
    fun getLiveData() = liveDataToObserve

    fun getCinemaFromLocalSource() = getDataFromLocalSource()

    fun getCinemaFromRemoteSource(id: Int) = getCinemaDetailsFromLocalSource(id)

    fun saveCityToDB(cinema: Cinema) {
        historyRepository.saveEntity(cinema)
    }


    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(repositoryImpl.getCinemas()?.let {
                Success(
                    it as MutableList<MutableList<Cinema>>
                )
            })
        }.start()
    }

    private fun getCinemaDetailsFromLocalSource(id: Int) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(repositoryImpl.getCinemaFromServer(id).let {
                AppState.SuccessDetails(
                    it
                )
            })
        }.start()
    }


}