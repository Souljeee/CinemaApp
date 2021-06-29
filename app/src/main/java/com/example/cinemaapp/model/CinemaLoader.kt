package com.example.cinemaapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object CinemaLoader {
    fun loadCinemas(): CinemaListDTO? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/popular?api_key=37a33c74592d522dfdc42d090c29c4bf&language=ru")
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
                return GsonBuilder().create().fromJson(lines,CinemaListDTO::class.java)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                urlConnection.disconnect()
            }
        }catch (e:MalformedURLException){
            e.printStackTrace()
        }
        return null
    }


    fun loadCinema(id: Int?): CinemaDTO? {
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
                return GsonBuilder().create().fromJson(lines,CinemaDTO::class.java)
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                urlConnection.disconnect()
            }
        }catch (e:MalformedURLException){
            e.printStackTrace()
        }
        return null
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

}