package com.example.subway.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.subway.MainActivity
import com.example.subway.R
import java.io.BufferedReader // BufferedReader를 import 추가
import java.io.InputStreamReader
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    // 변수 선언
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var stationAdapter: StationAdapter


    private val stationList = mutableListOf<StationData>() // 역 정보 리스트
    private val filteredStationList = mutableListOf<StationData>() // 검색 결과를 담을 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // RecyclerView 및 SearchView 초기화
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)


        // 역 정보 로드
        loadStationData()

        // 어댑터 초기화(변수명 수정, itemClickListener 추가)
        stationAdapter = StationAdapter(filteredStationList) { station ->
            // 아이템 클릭 시 메인페이지로 이동
            navigateToStationLocation(station)
        }

        recyclerView.adapter = stationAdapter // 변수명 수정
        recyclerView.layoutManager = LinearLayoutManager(this)

        // SearchView 이벤트 처리
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 어댑터를 업데이트하는 로직 추가
                filterStations(newText)
                return true
            }
        })
    }

    private fun loadStationData() {
        // 역 정보를 res/raw/stationlocation에서 읽어와 stationList에 추가하는 로직 추가
        try {
            val inputStream = resources.openRawResource(R.raw.stationlocation)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val lines: List<String> = reader.readLines()

            for (line in lines) {
                val parts = line.split(' ')
                val name = parts[0]
                val x = parts[3].toFloat() // Float로 수정
                val y = parts[6].toFloat() // Float로 수정
                stationList.add(StationData(name, x, y))
            }

            // 초기에 전체 역 리스트를 보여줍니다.
            filteredStationList.addAll(stationList)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun filterStations(query: String?) {
        // 검색어에 따라 filteredStationList를 업데이트하는 로직 추가
        filteredStationList.clear()

        if (query.isNullOrBlank()) {
            // 검색어가 없을 경우 전체 역 리스트를 보여줍니다.
            filteredStationList.addAll(stationList)
        } else {
            // 검색어가 포함된 역만 필터링하여 보여줍니다.
            val filteredStations = stationList.filter {
                it.name.contains(query, ignoreCase = true)
            }
            filteredStationList.addAll(filteredStations)
        }

        stationAdapter.notifyDataSetChanged()
    }

    private fun navigateToStationLocation(station: StationData) {
        // 메인페이지로 이동 및 해당 역의 좌표로 이동하는 로직 추가
        // 예: 역 좌표 정보를 다른 액티비티로 전달하거나 해당 역으로 지도 이동
        // 아래는 예시 코드로 Log로 좌표를 출력하고 있습니다.
        val intent = Intent(this, MainActivity::class.java)
        Log.d("StationClick", "Name: ${station.name}, X: ${station.x}, Y: ${station.y}")

        intent.putExtra("stationName", station.name)
        intent.putExtra("stationX", station.x)
        intent.putExtra("stationY", station.y)

        // SharedPreferences에 값을 저장하는 코드
        val sharedPreferences: SharedPreferences = getSharedPreferences("deperture", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // "Start"와 "End" 값을 저장합니다.
        editor.putString("Start", station.name)
        editor.putString("End", "")

        editor.apply()

        // MainActivity로 이동
        startActivity(intent)
    }

}