package com.example.cinemaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.R
import com.example.cinemaapp.model.Cinema


class MainScreenAdapter(var cinema: Cinema) : RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {
    private lateinit var holder : ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenAdapter.ViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.cinema_item_layout,parent,false)
        holder = ViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: MainScreenAdapter.ViewHolder, position: Int) {
        holder.setData(cinema)
    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name : TextView
        private var year  : TextView
        private var rating :TextView
        private var poster :ImageView
        init{
            name  =  itemView.findViewById(R.id.cinema_name)
            year  =  itemView.findViewById(R.id.cinema_year)
            rating = itemView.findViewById(R.id.cinema_rating)
            poster = itemView.findViewById(R.id.cinema_poster)
        }


        fun setData(cinema: Cinema) {
            name.text = cinema.name
            year.text = cinema.year
            rating.text = cinema.rating
        }
    }
}