package com.example.cinemaapp.services

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cinemaapp.model.Cinema
import com.example.cinemaapp.model.CinemaDTO
import com.example.cinemaapp.view.*
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID_EXTRA = "ID"

class DetailsService(name: String = "DetailsService") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(p0: Intent?) {
        if (p0 == null) {
            onEmptyIntent()
        } else {
            val id = p0.getIntExtra(ID_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadCinema(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadCinema(id: Int?){
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/$id?api_key=37a33c74592d522dfdc42d090c29c4bf&language=ru")
            lateinit var urlConnection: HttpsURLConnection
            try{
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.addRequestProperty("api_key","37a33c74592d522dfdc42d090c29c4bf")
                urlConnection.readTimeout = 10000

                val bufferReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                    getLinesForOld(bufferReader)
                }else {
                    getLines(bufferReader)
                }
                val cinemaDTO: CinemaDTO =  GsonBuilder().create().fromJson(lines, CinemaDTO::class.java)
                onResponse(cinemaDTO)
            }catch (e: Exception){
                onErrorRequest(e.message ?: "Empty error")
            }finally {
                urlConnection.disconnect()
            }
        }catch (e: MalformedURLException){
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun getLinesForOld(reader: BufferedReader) : String
    {
        val rawData = StringBuilder(1024)
        var tempVariable : String?

        while(reader.readLine().also { tempVariable = it } != null){
            rawData.append(tempVariable).append("\n")
        }
        return rawData.toString()
    }

    private fun onResponse(cinemaDTO: CinemaDTO) {
        if (cinemaDTO == null) {
            onEmptyResponse()
        } else {
            val cinema = cinemaDTOToCinema(cinemaDTO)
            onSuccessResponse(cinema)
        }
    }

    private fun cinemaDTOToCinema(dto: CinemaDTO) : Cinema {
        return Cinema(
            id = dto.id,
            name = dto.title,
            releaseDate = dto.release_date,
            revenue = "${dto.revenue}$",
            rating =  "${dto.vote_average}(${dto.vote_count})",
            duration = "${dto.runtime} мин",
            description = dto.overview,
        )
    }


    private fun onSuccessResponse(cinema: Cinema) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_CINEMA_EXTRA, cinema)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }

}