package com.example.subway.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R

class StationAdapter (
    private val originalStationList: List<StationData>,
    private val itemClickListener: (StationData) -> Unit
    ) : RecyclerView.Adapter<StationAdapter.ViewHolder>(), Filterable {

    private var filteredStationList: List<StationData> = originalStationList


        inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

            private val stationNameTextView: TextView = itemView.findViewById(R.id.stationName)

            fun blid(stationData: StationData){
                // 아이템에 대한 화면 표시 로직 추가 (예: 역 이름 표시)
                stationNameTextView.text = stationData.name

                itemView.setOnClickListener { itemClickListener(stationData) }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subway_station_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.blid(filteredStationList[position])
    }


    override fun getItemCount(): Int {
        return filteredStationList.size
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint.isNullOrBlank()) {
                    // 검색어가 없을 경우 전체 역 리스트를 보여줍니다.
                    filterResults.values = originalStationList
                } else {
                    // 검색어가 포함된 역만 필터링하여 보여줍니다.
                    val filteredList = originalStationList.filter {
                        it.name.contains(constraint.toString(), ignoreCase = true)
                    }
                    filterResults.values = filteredList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredStationList = results?.values as List<StationData>
                notifyDataSetChanged()
            }
        }
    }
}
