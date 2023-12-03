package com.example.subway.PathsDirections

//import ForegroundService
import android.content.Intent
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
            showNotification()
        }

        var data: PathInfo? = minTransfers(this, 208, 120)

        if (data != null) {
            println(data)
        }

        //북마크 버튼 리스너
        findViewById<ImageButton>(R.id.bookmarkstarBtn).setOnClickListener {
        //여기에 코드 작성
    }

        //var data: PathInfo? = main1(this, 101, 102) // main1의 return type이 PathInfo가 아니어서 생긴 오류

    }
    //알림 호출
    private fun showNotification() {
        val nb: NotificationCompat.Builder = notificationHelper.getChannelNotification()
        //startService(Intent(this, ForegroundService::class.java))
        notificationHelper.getManager().notify(1, nb.build())
    }
}
