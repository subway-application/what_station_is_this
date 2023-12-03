package com.example.subway.bookmark

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.R

class BookmarkActivity: AppCompatActivity() {
    private val favoriteRoutes = FavoriteRoutesManager.getFavoriteRoutes().toMutableList()
    private val favoritesRecyclerView: RecyclerView by lazy { findViewById(R.id.favoritesRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

//        favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
//        val favoritesAdapter = BookmarkAdapter(
//            favoriteRoutes,
////            onDeleteClickListener = { position -> onDeleteFavoriteRoute(position) }
////        ) { selectedRoute -> navigateToRouteDisplay(selectedRoute) }
////
////        favoritesRecyclerView.adapter = favoritesAdapter
//    }
//
//    private fun onDeleteFavoriteRoute(position: Int) {
//        // 즐겨찾기 목록에서 아이템 삭제
//        favoriteRoutes.removeAt(position)
//
//        // 어댑터에 아이템 삭제 알림
//        (favoritesRecyclerView.adapter as BookmarkAdapter).removeItem(position)
//
//        // UI 업데이트
//        Toast.makeText(this, "경로가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
//    }
//
//    // 저장된 경로를 클릭하면 다시 경로 표시 페이지로 이동
////    private fun navigateToRouteDisplay(route: Route) {
////        // 경로 페이지로 다시 이동하기 때문에 경로 페이지 파일이 있어야 합니다.
////        val intent = Intent(this, RouteDisplayActivity::class.java)
////        intent.putExtra("startStation", route.startStation)
////        intent.putExtra("endStation", route.endStation)
////        startActivity(intent)
////    }
    }
}