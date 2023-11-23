package com.example.subway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.subway.databinding.ActivityMainBinding
import com.example.subway.search.SearchActivity
import com.github.chrisbanes.photoview.PhotoView

class MainActivity : AppCompatActivity() {

    // 네비게이션 버튼 관련
    private lateinit var noticeBtn: ImageButton
    private lateinit var complaintBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 네비게이션 버튼 관련
        noticeBtn = binding.noticeBtn
        complaintBtn = binding.complaintBtn

        // 노선도 확대 축소
        val photoView: PhotoView = binding.stationMap
        photoView.setImageResource(R.drawable.station_map)
        photoView.setZoomable(true)

        binding.searchBtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun toggleAdditionalButtonsVisibility() {
        // 공지사항과 민원신고 버튼의 가시성을 토글
        noticeBtn.isVisible = !noticeBtn.isVisible
        complaintBtn.isVisible = !complaintBtn.isVisible
    }

    fun onMoreButtonClick(view: View) {
        // 더보기 버튼을 클릭했을 때
        toggleAdditionalButtonsVisibility()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}