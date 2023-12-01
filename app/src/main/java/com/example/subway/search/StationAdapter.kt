package com.example.subway.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R

class StationAdapter (
    private val originalStationList: List<StationData>,
    private val itemClickListener: (StationData) -> Unit
    ) : RecyclerView.Adapter<StationAdapter.StationViewHolder>(), Filterable {

        private var filteredStationList: List<StationData> = originalStationList

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subway_station_search, parent, false)
            return StationViewHolder(view)
        }

        override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
            val station = filteredStationList[position]
            holder.bind(station)
            holder.itemView.setOnClickListener { itemClickListener(station) }
        }

        override fun getItemCount(): Int = filteredStationList.size

        inner class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameTextView: TextView = itemView.findViewById(R.id.stationDetailTextView)
            private val latitudeTextView: TextView = itemView.findViewById(R.id.stationMap)
            private val longitudeTextView: TextView = itemView.findViewById(R.id.stationMap)

            init {
                // 각 TextView가 null인지 확인 후 처리
                if (nameTextView == null || latitudeTextView == null || longitudeTextView == null) {
                    // 처리: 예를 들어 Log 출력 또는 예외 처리
                    Log.e("StationViewHolder", "TextView initialization failed.")
                }
            }

            fun bind(station: StationData) {
                nameTextView.text = station.name
                latitudeTextView.text = "Latitude: ${station.latitude}"
                longitudeTextView.text = "Longitude: ${station.longitude}"
            }
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val filterResults = FilterResults()
                    val query = constraint?.toString()?.toLowerCase()

                    filteredStationList = if (query.isNullOrBlank()) {
                        originalStationList
                    } else {
                        originalStationList.filter { it.name.toLowerCase().contains(query) }
                    }

                    filterResults.values = filteredStationList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    notifyDataSetChanged()
                }
            }
        }

    }
