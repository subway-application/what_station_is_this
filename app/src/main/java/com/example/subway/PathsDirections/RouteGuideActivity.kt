package com.example.subway.PathsDirections

//import ForegroundService
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.subway.R


class RouteGuideActivity: AppCompatActivity() {
    private lateinit var notificationHelper: NotificationHelper
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_guidance)
        // 출발, 도착역 정보

        var routeData_st: Int = intent.getStringExtra("start")!!.toInt()
        var routeData_ed: Int? = intent.getStringExtra("end")!!.toInt()
        println(routeData_st)

        var data: PathInfo? = routeData_ed?.let { minTransfers(this, routeData_st, it) }

        if (data != null) {
            println(data)
        }

        //경로 3개 버튼 클릭시
        val min_time_btn_clicked = findViewById<ImageButton>(R.id.min_timeBtn_full)
        val min_tsf_btn_clicked = findViewById<ImageButton>(R.id.min_transferBtn_full)
        val min_cost_btn_clicked = findViewById<ImageButton>(R.id.min_costBtn_full)


        //최단 시간 텍스트 설정
        //이따 수정
        val min_time_btn = findViewById<ImageButton>(R.id.min_timeBtn)
        min_time_btn.setOnClickListener{
            //소요 시간
            val min_time_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data?.totalTime?.div(3600)
            val minutes = (data?.totalTime?.rem(3600))?.div(60)
            val seconds = data?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_time_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0){
                        min_time_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_time_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_time_transfer = findViewById<TextView>(R.id.transfer)
            min_time_transfer.text = "환승 ${data?.numTransfers.toString()}번"

            //금액
            val min_time_cost = findViewById<TextView>(R.id.cost)
            min_time_cost.text = "${data?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.VISIBLE
            min_tsf_btn_clicked.visibility = View.GONE
            min_cost_btn_clicked.visibility = View.GONE
        }


        //최소 환승 텍스트 설정
        val min_tsf_btn = findViewById<ImageButton>(R.id.min_transferBtn)
        min_tsf_btn.setOnClickListener{
            //소요 시간
            val min_tsf_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data?.totalTime?.div(3600)
            val minutes = (data?.totalTime?.rem(3600))?.div(60)
            val seconds = data?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_tsf_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0){
                        min_tsf_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_tsf_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_tsf_transfer = findViewById<TextView>(R.id.transfer)
            min_tsf_transfer.text = "환승 ${data?.numTransfers.toString()}번"

            //금액
            val min_tsf_cost = findViewById<TextView>(R.id.cost)
            min_tsf_cost.text = "${data?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.GONE
            min_tsf_btn_clicked.visibility = View.VISIBLE
            min_cost_btn_clicked.visibility = View.GONE
        }


        //최소 금액 텍스트 설정
        //이따 수정
        val min_cost_btn = findViewById<ImageButton>(R.id.min_costBtn)
        min_cost_btn.setOnClickListener{
            //소요 시간
            val min_cost_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data?.totalTime?.div(3600)
            val minutes = (data?.totalTime?.rem(3600))?.div(60)
            val seconds = data?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_cost_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0){
                        min_cost_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_cost_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_cost_transfer = findViewById<TextView>(R.id.transfer)
            min_cost_transfer.text = "환승 ${data?.numTransfers.toString()}번"

            //금액
            val min_cost_cost = findViewById<TextView>(R.id.cost)
            min_cost_cost.text = "${data?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.GONE
            min_tsf_btn_clicked.visibility = View.GONE
            min_cost_btn_clicked.visibility = View.VISIBLE
        }




        notificationHelper = NotificationHelper(this)
        findViewById<ImageButton>(R.id.bellBtn).setOnClickListener {
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
        // Notification 클릭 시 MainActivity로 이동하는 PendingIntent 설정
//        val intent = Intent(this, RouteGuideActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        nb.setContentIntent(pendingIntent)

        // Notification을 생성하고 표시하는 부분 수정
        val notificationManager = notificationHelper.getManager()
        val notification = nb.build()
        notificationManager.notify(1, notification)

    }
}
