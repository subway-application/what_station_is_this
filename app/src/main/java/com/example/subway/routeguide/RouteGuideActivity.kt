package com.example.subway.routeguide

import com.example.subway.NotificationHelper
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.subway.R

class RouteGuideActivity: AppCompatActivity() {
    private lateinit var notificationHelper: NotificationHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_guidance)

        notificationHelper = NotificationHelper(this)

        findViewById<ImageButton>(R.id.bellBtn).setOnClickListener {
            //알림 호출
            showNotification()
        }

        //북마크 버튼 리스너
        findViewById<ImageButton>(R.id.bookmarkstarBtn).setOnClickListener {
            //여기에 코드 작성
        }
    }
    //알림 호출
    private fun showNotification() {
        val nb: NotificationCompat.Builder = notificationHelper.getChannelNotification()
        nb.setOngoing(true)
        notificationHelper.getManager().notify(1, nb.build())
    }
}
