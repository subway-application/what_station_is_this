package com.example.subway.setting.notice


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R
import java.sql.Timestamp

class BaseAdapter(private val dataList: LinkedList<Base>) : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_base_station_notice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList.getDataNode(position)
        holder.titleTextView.text = data?.title
        holder.contentTextView.text = data?.content
        holder.timestampTextView.text = data?.timestamp.toString()
    }

    override fun getItemCount(): Int {
        return dataList.return_size()
    }
}