package com.example.cinemaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinemaapp.model.Repository
import com.example.cinemaapp.model.RepositoryImpl
import com.example.cinemaapp.viewmodel.AppState.Success
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve

    fun getCinemaFromLocalSource() = getDataFromLocalSource()

    fun getCinemaFromRemoteSource(id:Int) = getCinemaDetailsFromLocalSource(id)


    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(repositoryImpl.getCinemasFromServer()?.let {
                Success(
                    it
                )
            })
        }.start()
    }

    private fun getCinemaDetailsFromLocalSource(id: Int){
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