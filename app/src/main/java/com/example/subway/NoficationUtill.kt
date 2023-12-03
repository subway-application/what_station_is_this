//package com.example.subway
//
//import android.Manifest
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Build
//import androidx.core.app.ActivityCompat
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.example.subway.R  // 앱의 R 클래스에 따라 수정
//
//// NotificationUtil.kt
//object NotificationUtil {
//
//    private const val CHANNEL_ID = "my_channel"
//    private const val NOTIFICATION_ID = 1
//
//    fun createNotificationChannel(context: Context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "My Channel"
//            val descriptionText = "My Notification Channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    fun showNotification(context: Context, title: String, content: String) {
//        val intent = Intent(context, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//            //.setSmallIcon(R.drawable.ic_notification)  // 알림 아이콘 (앱의 R 클래스에 따라 수정)
//            .setContentTitle(title)
//            .setContentText(content)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(context)) {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            notify(NOTIFICATION_ID, builder.build())
//        }
//    }
//}