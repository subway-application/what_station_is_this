package com.example.subway.bookmark

// RecyclerView 어댑터를 작성하여 데이터를 바인딩하고 뷰를 생성

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R


// MutableList 클래스의 멤버 함수로, 해당 리스트에서 특정 인덱스의 요소를 제거하는 역할
class BookmarkAdapter(private val favoriteRoutes: MutableList<Route>, private val onDeleteClickListener: (Int) -> Unit,
                      private val onRouteClickListener: (Route) -> Unit) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val routeTextView: TextView = view.findViewById(R.id.routeTextView)
//        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton) // 이것은 레이아웃 파일에 맞게 수정하세요
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subway_station_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = favoriteRoutes[position]
        holder.routeTextView.text = "${route.startStation} to ${route.endStation}"

        // 아이템 클릭 시 이벤트 처리
        holder.itemView.setOnClickListener {
            onRouteClickListener.invoke(route)
        }

        // 삭제 버튼 클릭 시 이벤트 처리
//        holder.deleteButton.setOnClickListener {
//            onDeleteClickListener.invoke(position)
//        }
    }

    override fun getItemCount(): Int {
        return favoriteRoutes.size
    }

    // 아이템 삭제 메서드 추가
    fun removeItem(position: Int) {
        favoriteRoutes.removeAt(position)
        notifyItemRemoved(position)
    }
}