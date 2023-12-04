package com.example.subway.PathsDirections

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
class NotificationHelper(base: Context?, val ID: String) : ContextWrapper(base) {

    //채널 변수 만들기
    val channelID: String = ID
    val channelNm: String = "channelName"

    init {
        //안드로이드 버전이 오레오보다 크면
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //채널 생성
            createChannel(ID)
        }
    }

    //채널 생성 함수
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(ID: String){

        //객체 생성
        val channel: NotificationChannel = NotificationChannel(ID, channelNm, NotificationManager.IMPORTANCE_DEFAULT)

        //설정
        channel.enableLights(true) //빛
        channel.enableVibration(true) //진동
        channel.lightColor = Color.RED
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        // 채널 생성
        getManager().createNotificationChannel(channel)

        if (ID == "alarm") {
            val channel2: NotificationChannel = NotificationChannel("alarm_11", "alarm", NotificationManager.IMPORTANCE_DEFAULT)
            channel2.enableLights(true)
            channel2.enableVibration(true)
            channel2.lightColor = Color.BLUE
            channel2.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            getManager().createNotificationChannel(channel2)
        }
    }

    //NotificationManager 생성
    fun getManager(): NotificationManager {
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    //Notification 설정
    fun getChannelNotification(ID: String, prevAndCurtAndNext: List<Node?>): NotificationCompat.Builder{

        if (ID == "fix") {
            var pre_station = "pre"
            var now_station = "now"
            var next_station = "next"

            // prevAndCurtAndNext 리스트에 대한 예외 처리
            if (prevAndCurtAndNext.isNotEmpty() && prevAndCurtAndNext.size >= 2) {
                if (prevAndCurtAndNext[0] == null) {
                    pre_station = "출발"
                    now_station = prevAndCurtAndNext[1].toString()
                    next_station = prevAndCurtAndNext[2].toString()
                } else if (prevAndCurtAndNext[2] == null) {
                    pre_station = prevAndCurtAndNext[0].toString()
                    now_station = prevAndCurtAndNext[1].toString()
                    next_station = "도착"
                } else {
                    pre_station = prevAndCurtAndNext[0].toString()
                    now_station = prevAndCurtAndNext[1].toString()
                    next_station = prevAndCurtAndNext[2].toString()
                }
            }

            return NotificationCompat.Builder(applicationContext, channelID)
                .setContentTitle("현재 역") //제목
                .setContentText("${pre_station}     <     ${now_station}     >     ${next_station}")//내용
                .setSmallIcon(R.drawable.alarm_icon) //아이콘
                .setOngoing(true)
        } else {

            return NotificationCompat.Builder(applicationContext, channelID)
                .setContentTitle("하차 알림") //제목
                .setContentText("다음 역에서 하차 해야 합니다.")//내용
                .setSmallIcon(R.drawable.alarm_icon) //아이콘
                .setOngoing(true)
        }
    }
}