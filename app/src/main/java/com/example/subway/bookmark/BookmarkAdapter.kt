package com.example.subway.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R

class FavoriteRoutesAdapter(
    private var favoriteRoutes: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FavoriteRoutesAdapter.FavoriteRouteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRouteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subway_station_bookmark, parent, false)
        return FavoriteRouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteRouteViewHolder, position: Int) {
        val route = favoriteRoutes[position]
        holder.bind(route)
        holder.itemView.setOnClickListener { onItemClick(route) }
    }

    override fun getItemCount(): Int = favoriteRoutes.size

    fun updateData(newData: List<String>) {
        // 중복된 아이템 제거하고 새로운 데이터를 추가
        favoriteRoutes = (favoriteRoutes + newData).distinct()

        // RecyclerView 갱신
        notifyDataSetChanged()
    }

    class FavoriteRouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routeTextView: TextView = itemView.findViewById(R.id.textViewRoute)

        fun bind(route: String) {
            routeTextView.text = route
        }
    }
}