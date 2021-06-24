package com.example.cinemaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.R
import com.example.cinemaapp.model.Cinema
import com.example.cinemaapp.view.MainFragment


class MainScreenAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainScreenAdapter.ViewHolder>() {

    private lateinit var cinema : List<Cinema>
    fun setCinema(cinema: List<Cinema>){
        this.cinema = cinema
        notifyDataSetChanged()
    }

    private lateinit var holder: ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainScreenAdapter.ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cinema_item_layout, parent, false)
        holder = ViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: MainScreenAdapter.ViewHolder, position: Int) {
        holder.bind(cinema[position])
    }

    override fun getItemCount(): Int {
        return 3
    }
    fun removeListener() {
        onItemViewClickListener = null
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView
        private var year: TextView
        private var rating: TextView
        private var poster: ImageView

        init {
            name = itemView.findViewById(R.id.cinema_name)
            year = itemView.findViewById(R.id.cinema_year)
            rating = itemView.findViewById(R.id.cinema_rating)
            poster = itemView.findViewById(R.id.cinema_poster)
        }

        fun bind(cinema: Cinema) {
            name.text = cinema.name
            year.text = cinema.releaseDate
            rating.text = cinema.rating
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(cinema)
            }
        }
    }
}