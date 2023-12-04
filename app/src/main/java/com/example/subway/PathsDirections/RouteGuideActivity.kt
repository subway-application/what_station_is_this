package com.example.subway.PathsDirections

//import ForegroundService
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.subway.R


class RouteGuideActivity: AppCompatActivity() {
    private lateinit var notificationHelper: NotificationHelper
    private val favoriteRoutesSet = mutableSetOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_guidance)
        // 출발, 도착역 정보

        var routeData_st: Int = intent.getStringExtra("start")!!.toInt()
        var routeData_ed: Int = intent.getStringExtra("end")!!.toInt()
        println(routeData_st)

        var data_min_time: PathInfo? = routeData_ed?.let { dijkstraTime(this, routeData_st, it) }
        var data_min_transfer: PathInfo? =
            routeData_ed?.let { minTransfers(this, routeData_st, it) }
        var data_min_cost: PathInfo? = routeData_ed?.let { dijkstraCost(this, routeData_st, it) }


        //경로 3개 버튼 클릭시
        val min_time_btn_clicked = findViewById<ImageButton>(R.id.min_timeBtn_full)
        val min_tsf_btn_clicked = findViewById<ImageButton>(R.id.min_transferBtn_full)
        val min_cost_btn_clicked = findViewById<ImageButton>(R.id.min_costBtn_full)


        //최단 시간 텍스트 설정
        val min_time_btn = findViewById<ImageButton>(R.id.min_timeBtn)
        min_time_btn.setOnClickListener {
            //소요 시간
            val min_time_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data_min_time?.totalTime?.div(3600)
            val minutes = (data_min_time?.totalTime?.rem(3600))?.div(60)
            val seconds = data_min_time?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_time_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0) {
                        min_time_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_time_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_time_transfer = findViewById<TextView>(R.id.transfer)
            min_time_transfer.text = "환승 ${data_min_time?.numTransfers.toString()}번"

            //금액
            val min_time_cost = findViewById<TextView>(R.id.cost)
            min_time_cost.text = "${data_min_time?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.VISIBLE
            min_tsf_btn_clicked.visibility = View.GONE
            min_cost_btn_clicked.visibility = View.GONE
        }


        //최소 환승 텍스트 설정
        val min_tsf_btn = findViewById<ImageButton>(R.id.min_transferBtn)
        min_tsf_btn.setOnClickListener {
            //소요 시간
            val min_tsf_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data_min_transfer?.totalTime?.div(3600)
            val minutes = (data_min_transfer?.totalTime?.rem(3600))?.div(60)
            val seconds = data_min_transfer?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_tsf_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0) {
                        min_tsf_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_tsf_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_tsf_transfer = findViewById<TextView>(R.id.transfer)
            min_tsf_transfer.text = "환승 ${data_min_transfer?.numTransfers.toString()}번"

            //금액
            val min_tsf_cost = findViewById<TextView>(R.id.cost)
            min_tsf_cost.text = "${data_min_transfer?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.GONE
            min_tsf_btn_clicked.visibility = View.VISIBLE
            min_cost_btn_clicked.visibility = View.GONE
        }


        //최소 금액 텍스트 설정
        val min_cost_btn = findViewById<ImageButton>(R.id.min_costBtn)
        min_cost_btn.setOnClickListener {
            //소요 시간
            val min_cost_time = findViewById<TextView>(R.id.time)
            // 시간 출력
            val hours = data_min_cost?.totalTime?.div(3600)
            val minutes = (data_min_cost?.totalTime?.rem(3600))?.div(60)
            val seconds = data_min_cost?.totalTime?.rem(60)
            if (hours != null) {
                if (hours > 0) {
                    min_cost_time.text = " ${hours}시간 ${minutes}분 ${seconds}초"
                } else if (minutes != null) {
                    if (minutes > 0) {
                        min_cost_time.text = "${minutes}분 ${seconds}초"
                    } else {
                        min_cost_time.text = "${seconds}초"
                    }
                }
            }

            //환승 횟수
            val min_cost_transfer = findViewById<TextView>(R.id.transfer)
            min_cost_transfer.text = "환승 ${data_min_cost?.numTransfers.toString()}번"

            //금액
            val min_cost_cost = findViewById<TextView>(R.id.cost)
            min_cost_cost.text = "${data_min_cost?.totalCost.toString()}원"

            min_time_btn_clicked.visibility = View.GONE
            min_tsf_btn_clicked.visibility = View.GONE
            min_cost_btn_clicked.visibility = View.VISIBLE
        }

        //일단 환승 먼저 하려고 최소환승으로 함. 나머지도 나중에 추가해야됨.
        //최소 환승 경로 받아옴
        val path_min_transfer: List<Node>? = data_min_transfer?.minTransfersPath

        val prevAndCurtAndNext: MutableList<List<Node?>> = mutableListOf()

        //경로를 for문으로 돌며 현재역을 하나씩 앞으로 옮겨감
        for (i in 0 until path_min_transfer!!.size) {
            println("경로 받아와짐? ${path_min_transfer}")
            val (p, n) = findPrevAndNextTsf(
                path_min_transfer,
                data_min_transfer!!.startStation,
                data_min_transfer!!.endStation,
                path_min_transfer!![i]
            )
            var stList: List<Node?> = listOf(p, path_min_transfer[i], n)
            prevAndCurtAndNext.add(stList)
            // p가 null이면 n만
            if (p == null) {
                println(n)
                // n이 null이면 p만
            } else if (n == null) {
                println(p)
                // 둘 다 아니면 n,p출력
            } else {
                println("${p}, ${n}")
            }
            //시간 지나면 현재역 바뀌는 거 하려고 함
            //Thread.sleep(path_min_transfer[i].edges[i].time.toLong()*1000)
            // 시간 함수(초 단위)(path[i].edge.time)로 시간만큼 지연
            // 출발역부터 하차 역의 전 역까지 걸리는 시간 += path[i].edge.time

            //하차알림 해야됨 일단 보류
//            var time_sum: Int = 0
//            path_min_transfer[i].edges[i].time.toInt()
//            if (i == path_min_transfer.size-1) {
//            // 출발역부터 하차 역의 전 역까지 걸리는 시간 print
//                println(time_sum)
//            }
        }

        println("경로 받아와짐? ${path_min_transfer}")

        notificationHelper = NotificationHelper(this, "fix")
        findViewById<ImageButton>(R.id.bellBtn).setOnClickListener {

            showNotification(prevAndCurtAndNext, "fix")

            // 현재 경로를 즐겨찾기에 추가하고 저장
            // 사용자에게 메시지 표시
            findViewById<ImageButton>(R.id.bookmarkstarBtn).setOnClickListener {
                addRouteToFavorites(routeData_st, routeData_ed)
                Toast.makeText(this, "경로가 즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }
    //알림 호출
    fun showNotification(prevAndCurtAndNext: List<List<Node?>>, ID: String) {
        val notificationManager = notificationHelper.getManager()

        // Notification을 생성하고 표시하는 부분 수정
        for (stList in 0 until prevAndCurtAndNext.size) {
            if (stList == prevAndCurtAndNext.size - 1) {
                val nb1: NotificationCompat.Builder = notificationHelper.getChannelNotification(
                    "alarm",
                    prevAndCurtAndNext[stList]
                )
                val notification = nb1.build()
                notificationManager.notify(1, notification)
            } else {
                val nb2: NotificationCompat.Builder =
                    notificationHelper.getChannelNotification("fix", prevAndCurtAndNext[stList])
                val notification = nb2.build()
                notificationManager.notify(2, notification)
            }

            // 일정 시간 대기 (예: 3초)
            Thread.sleep(3000)
        }

        // 추가된 경로를 저장
        saveFavorites()
    }
    fun loadFavorites() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("FavoriteRoutes", Context.MODE_PRIVATE)

        // 기존에 저장된 즐겨찾기를 로드하여 favoriteRoutesSet에 업데이트
        val savedRoutes = sharedPreferences.getStringSet("routes", setOf())
        favoriteRoutesSet.addAll(savedRoutes.orEmpty())
    }

    fun saveFavorites() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("FavoriteRoutes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // favoriteRoutesSet을 저장
        editor.putStringSet("routes", favoriteRoutesSet)
        editor.apply()
    }
}