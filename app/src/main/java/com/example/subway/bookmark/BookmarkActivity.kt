package com.example.subway.bookmark

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.PathsDirections.RouteGuideActivity
import com.example.subway.R

class BookmarkActivity : AppCompatActivity() {
    private lateinit var favoriteRoutesAdapter: FavoriteRoutesAdapter
    private lateinit var favoritesList: MutableList<String>  // List로 변경
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        sharedPreferences = getSharedPreferences("FavoriteRoutes", Context.MODE_PRIVATE)

        // 리스트 초기화
        favoritesList = mutableListOf()

        // 리사이클러뷰 초기화
        val recyclerView: RecyclerView = findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 저장된 즐겨찾기 불러오기
        val favoriteRoutesList = loadFavorites()

        // 어댑터 초기화
        favoriteRoutesAdapter = FavoriteRoutesAdapter(favoriteRoutesList) { route ->
            // 아이템을 클릭했을 때 해당 경로 페이지로 이동
            val routeData = route.split("-")
            val startStation = routeData[0].toInt()
            val endStation = routeData[1].toInt()
            navigateToRouteGuide(startStation, endStation)
        }

        // 리사이클러뷰에 어댑터 설정
        recyclerView.adapter = favoriteRoutesAdapter
    }

    private fun loadFavorites(): List<String> {
        // SharedPreferences에서 데이터를 읽어옵니다.
        return sharedPreferences.getStringSet("routes", setOf())?.toList() ?: emptyList()
    }

    private fun saveFavorites() {
        // SharedPreferences 에서 에디터를 가져옵니다.
        val editor = sharedPreferences.edit()

        // List 형태의 데이터를 Set 형태로 변환하여 저장합니다.
        val favoritesSet = favoritesList.toSet()
        editor.putStringSet("routes", favoritesSet)

        // 변경사항을 저장합니다.
        editor.apply()
    }

    private fun navigateToRouteGuide(start: Int, end: Int) {
        // 경로 페이지로 이동하는 코드
        val intent = Intent(this, RouteGuideActivity::class.java)
        intent.putExtra("start", start.toString())
        intent.putExtra("end", end.toString())
        startActivity(intent)
    }

    private fun addRouteToFavorites(start: Int, end: Int) {
        // 새로운 경로를 리스트에 추가
        val routeString = "$start-$end"
        favoritesList.add(routeString)

        // 업데이트된 목록을 저장
        saveFavorites()

        // 어댑터에 데이터 변경 알림
        favoriteRoutesAdapter.updateData(favoritesList)
    }
}