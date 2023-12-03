package com.example.subway.routeguide

import com.example.subway.NotificationHelper
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.subway.R

class RouteGuideActivity: AppCompatActivity() {
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var startSttName: String
    private lateinit var endSttName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_guidance)

        notificationHelper = NotificationHelper(this)

        // 이전 액티비티로부터 데이터를 가져옴
        startSttName = intent.getStringExtra("startSttName") ?: ""
        endSttName = intent.getStringExtra("endSttName") ?: ""

        findViewById<ImageButton>(R.id.bellBtn).setOnClickListener {
            //알림 호출
            showNotification()
        }

        //북마크 버튼 리스너
        findViewById<ImageButton>(R.id.bookmarkstarBtn).setOnClickListener {
            // 현재 경로 정보를 BookmarkManager에 저장
//            val currentBookmark = MainStation(startSttName = startSttName, endSttName = endSttName)
//            BookmarkManager.addBookmark(currentBookmark)

            // BookmarkActivity로 이동
            navigateToBookmarkActivity()
        }
    }

    //알림 호출
    private fun showNotification() {
        val nb: NotificationCompat.Builder = notificationHelper.getChannelNotification()
        nb.setOngoing(true)
        notificationHelper.getManager().notify(1, nb.build())
    }

    // BookmarkActivity 호출
    private fun navigateToBookmarkActivity() {
//        val intent = Intent(this, BookmarkActivity::class.java)
        startActivity(intent)
    }
}
