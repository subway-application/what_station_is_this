package com.example.subway.PathsDirections

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.subway.MainActivity
import com.example.subway.R


//상단바 알림에 필요한 클래스
class NotificationHelper(base: Context?) : ContextWrapper(base) {

    //채널 변수 만들기
    val channelID: String = "1"
    val channelNm: String = "channelName"

    init {
        //안드로이드 버전이 오레오보다 크면
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //채널 생성
            createChannel()
        }
    }

    //채널 생성 함수
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(){

        //객체 생성
        val channel: NotificationChannel = NotificationChannel(channelID, channelNm, NotificationManager.IMPORTANCE_DEFAULT)

        //설정
        channel.enableLights(true) //빛
        channel.enableVibration(true) //진동
        channel.lightColor = Color.RED
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        // 채널 생성
        getManager().createNotificationChannel(channel)
    }

    //NotificationManager 생성
    fun getManager(): NotificationManager {
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    //Notification 설정
    fun getChannelNotification(): NotificationCompat.Builder{

        val pre_station = "000"
        val now_station = "999"
        val next_station = "111"

        // 클릭 시 MainActivity로 이동하는 PendingIntent 설정
//        val intent = Intent(this, RouteGuideActivity::class.java). apply {
//            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        }
        //val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        println("어어어기")

        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("현재 역") //제목
            .setContentText("${pre_station}     <     ${now_station}     >     ${next_station}")//내용
            .setSmallIcon(R.drawable.alarm_icon) //아이콘
            .setOngoing(true)
            //.setContentIntent(pendingIntent) // 클릭 이벤트 설정
    }
}