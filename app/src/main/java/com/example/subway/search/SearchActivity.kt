package com.example.subway.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.subway.MainActivity
import com.example.subway.R

class SearchActivity : AppCompatActivity() {

    // 변수 선언
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: StationAdapter
    private lateinit var stationList: List<StationData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // XML 레이아웃에서 정의한 뷰의 ID를 사용하여 실제 뷰 객체를 참조
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        // CSV 파일에서 StationData 읽어오기
        // CsvParser의 parseStationData 메서드를 호출하여 역 정보를 담은 리스트를 생성
        // stationList 변수에 저장
        stationList = CsvParser.parseStationData(this)

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StationAdapter(stationList) { selectedStation ->
            // 아이템 클릭 시 처리 (예: 메인 페이지로 이동)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("latitude", selectedStation.latitude)
            intent.putExtra("longitude", selectedStation.longitude)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // SearchView 설정
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}