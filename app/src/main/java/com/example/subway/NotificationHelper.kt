package com.example.subway

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.subway.R


//상단바 알림에 필요한 클래스
class NotificationHelper(base: Context?) : ContextWrapper(base) {

    //채널 변수 만들기
    val channelID: String = "channelID"
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
//        val channel: NotificationChannel = NotificationChannel(channelID, channelNm, NotificationManager.IMPORTANCE_DEFAULT)
//
//        //설정
//        channel.enableLights(true) //빛
//        channel.enableVibration(true) //진동
//        channel.lightColor = Color.RED
//        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//
//        //생성
//        getManager().createNotificationChannel(channel)

        // 이미 생성된 채널이 없는 경우에만 채널 생성
        val channel = NotificationChannel(
            channelID,
            channelNm,
            NotificationManager.IMPORTANCE_NONE
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        // 채널 설정
        channel.enableLights(true) // 빛
        channel.enableVibration(true) // 진동
        channel.lightColor = Color.RED
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        // 채널 생성

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


        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("현재 역") //제목
            .setContentText("${pre_station}     <     ${now_station}     >     ${next_station}")//내용
            .setSmallIcon(R.drawable.alarm_icon) //아이콘
            .setOngoing(true)
    }
}