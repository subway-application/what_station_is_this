package com.example.subway.PathsDirections

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.example.subway.R


class RouteGuideActivity : AppCompatActivity() {
    private lateinit var notificationHelper: NotificationHelper

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

            // 환승 횟수, 출발역, 도착역, 환승역, 노선
            addRouteGuide(
                data_min_time?.numTransfers,
                data_min_time?.startStation,
                data_min_time?.endStation,
                data_min_time?.transferStations,
                data_min_time?.linesList
            )
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

            // 환승 횟수, 출발역, 도착역, 환승역, 노선
            addRouteGuide(
                data_min_transfer?.numTransfers,
                data_min_transfer?.startStation,
                data_min_transfer?.endStation,
                data_min_transfer?.transferStations,
                data_min_transfer?.linesList
            )
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

            // 환승 횟수, 출발역, 도착역, 환승역, 노선
            addRouteGuide(
                data_min_cost?.numTransfers,
                data_min_cost?.startStation,
                data_min_cost?.endStation,
                data_min_cost?.transferStations,
                data_min_cost?.linesList
            )
        }

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

    // 환승 횟수, 출발역, 도착역, 환승역, 노선
    fun addRouteGuide(
        numTransfers: Int?,
        startStation: Node?,
        endStation: Node?,
        transferStations: List<String>?,
        linesList: List<Set<Int>>?
    ) {
        val parentLayout: FrameLayout = findViewById(R.id.route_main)

        // 스크롤뷰를 부모 레이아웃에 추가
        val scrollView = ScrollView(this)
        parentLayout.addView(scrollView)

        // 스크롤뷰 내에 새로운 FrameLayout 추가
        val scrollContent = FrameLayout(this)
        scrollView.addView(scrollContent)

        // 환승 횟수만큼 만들기
        val numberOfData = numTransfers?.toInt()

        var newStartTextView = TextView(this)

        // 폰트 리소스 가져오기
        val fontResourceId = R.font.pretendard_bold
        // 폰트 리소스를 사용하여 Typeface 객체 생성
        val typeface = ResourcesCompat.getFont(this, fontResourceId)
        // 폰트 설정
        typeface?.let {
            newStartTextView.typeface = it
        }

        // 출발 텍스트 내용 및 크기 설정
        newStartTextView.text = "출발"
        newStartTextView.textSize = 23f

        // 출발 텍스트 마진 설정
        val newStartTextViewmarginLeft = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics
        ).toInt()
        val newStartTextViewmarginTop = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 185f, resources.displayMetrics
        ).toInt()
        val newStartTextViewparams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        newStartTextViewparams.setMargins(
            newStartTextViewmarginLeft,
            newStartTextViewmarginTop,
            0,
            0
        )
        newStartTextView.layoutParams = newStartTextViewparams
        scrollContent.addView(newStartTextView)

        val transferStationsName = mutableListOf<String>()
        transferStations?.let { stations ->
            for (station in stations) {
                transferStationsName.add(station)
            }
        }

        // 교집합을 확인할 Set<Int>를 초기화합니다.
        val intersectionSet = mutableSetOf<Int>()
        var changeLineNum = 0
        val changeLineNumList = mutableListOf<Int>()
        val changeLine = mutableListOf<Int>()

        // linesList를 반복하면서 각 Set<Int>의 데이터를 뽑아내기
        for (set in linesList!!) {
            for (data in set) {
                // 이미 intersectionSet에 들어있는 데이터인지 확인
                if (data in intersectionSet) {
                    // 교집합이 있음을 처리하는 코드를 작성
//                    if (data !in changeLine) {
                        changeLineNumList.add(changeLineNum)
                        changeLine.add(data)
//                    }
                } else {
                    // intersectionSet에 데이터를 추가
                    intersectionSet.add(data)
                }
            }
            changeLineNum++
        }

        println(changeLineNum)
        println(changeLineNumList)

        for (i in 0..numberOfData!!) {
            val newstartTextView = TextView(this)
            val newendTextView = TextView(this)
            val newtimeTextView = TextView(this)
            val newnumTextView = TextView(this)
            val newENDTextView = TextView(this)

            // 폰트 설정
            typeface?.let {
                newstartTextView.typeface = it
                newendTextView.typeface = it
                newtimeTextView.typeface = it
                newnumTextView.typeface = it
                newStartTextView.typeface = it
                newENDTextView.typeface = it
            }

            // 글자 색상 설정
            val colorValue = Color.parseColor("#111111")
            newstartTextView.setTextColor(colorValue)
            newendTextView.setTextColor(colorValue)
            newtimeTextView.setTextColor(colorValue)
            newnumTextView.setTextColor(colorValue)
            newStartTextView.setTextColor(colorValue)
            newENDTextView.setTextColor(colorValue)

            if (i == 0) {
                // 출발역 내용 및 크기 설정
                newstartTextView.text = startStation.toString()
                newstartTextView.textSize = 30f

                // 출발역 마진 설정
                val newstartTextViewmarginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 130f, resources.displayMetrics
                ).toInt()
                val newstartTextViewmarginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 233f * (i + 1), resources.displayMetrics
                ).toInt()
                val newstartTextViewparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                newstartTextViewparams.setMargins(
                    newstartTextViewmarginLeft,
                    newstartTextViewmarginTop,
                    0,
                    0
                )
                newstartTextView.layoutParams = newstartTextViewparams
            } else {
                // 출발역 내용 및 크기 설정
                newstartTextView.text = transferStationsName[i - 1]
                newstartTextView.textSize = 30f

                // 출발역 마진 설정
                val newstartTextViewmarginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 130f, resources.displayMetrics
                ).toInt()
                val newstartTextViewmarginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 233f * (i + 1), resources.displayMetrics
                ).toInt()
                val newstartTextViewparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                newstartTextViewparams.setMargins(
                    newstartTextViewmarginLeft,
                    newstartTextViewmarginTop,
                    0,
                    0
                )
                newstartTextView.layoutParams = newstartTextViewparams
            }

            if (i == numberOfData) {
                // 도착역 내용 및 크기 설정
                newendTextView.text = endStation.toString()
                newendTextView.textSize = 30f

                // 도착역 마진 설정
                val newendTextViewmarginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 130f, resources.displayMetrics
                ).toInt()
                val newendTextViewmarginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, (233f * (i + 1)) + 147f, resources.displayMetrics
                ).toInt()
                val newendTextViewparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                newendTextViewparams.setMargins(
                    newendTextViewmarginLeft,
                    newendTextViewmarginTop,
                    0,
                    0
                )
                newendTextView.layoutParams = newendTextViewparams
            } else {
                // 도착역 내용 및 크기 설정
                newendTextView.text = transferStationsName[i]
                newendTextView.textSize = 30f

                // 도착역 마진 설정
                val newendTextViewmarginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 130f, resources.displayMetrics
                ).toInt()
                val newendTextViewmarginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    (233f * (i + 1)) + 147f,
                    resources.displayMetrics
                ).toInt()
                val newendTextViewparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                newendTextViewparams.setMargins(
                    newendTextViewmarginLeft,
                    newendTextViewmarginTop,
                    0,
                    0
                )
                newendTextView.layoutParams = newendTextViewparams
            }

            // 이동하는 역의 수 내용 및 크기 설정
            newnumTextView.text = "3개 역 이동"
            newnumTextView.textSize = 18f

            // 이동하는 역의 수 설정
            val newnumTextViewmarginLeft = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 140f, resources.displayMetrics
            ).toInt()
            val newnumTextViewmarginTop = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (233f * (i + 1)) + 52f, resources.displayMetrics
            ).toInt()
            val newnumTextViewparams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            newnumTextViewparams.setMargins(newnumTextViewmarginLeft, newnumTextViewmarginTop, 0, 0)
            newnumTextView.layoutParams = newnumTextViewparams

            // 스크롤에 추가
            scrollContent.addView(newstartTextView)
            scrollContent.addView(newendTextView)
            scrollContent.addView(newtimeTextView)
            scrollContent.addView(newnumTextView)

            val newStartRoundImg = ImageView(this)
            val newEndRoundImg = ImageView(this)
            val newStickImg = ImageView(this)
            val density = resources.displayMetrics.density

            val drawableResources: List<Int> = when (changeLine[i]-1) {
                0 -> listOf(R.drawable.round_station_info_1, R.drawable.stick_station_info_1)
                1 -> listOf(R.drawable.round_station_info_2, R.drawable.stick_station_info_2)
                2 -> listOf(R.drawable.round_station_info_3, R.drawable.stick_station_info_3)
                3 -> listOf(R.drawable.round_station_info_4, R.drawable.stick_station_info_4)
                4 -> listOf(R.drawable.round_station_info_5, R.drawable.stick_station_info_5)
                5 -> listOf(R.drawable.round_station_info_6, R.drawable.stick_station_info_6)
                6 -> listOf(R.drawable.round_station_info_7, R.drawable.stick_station_info_7)
                7 -> listOf(R.drawable.round_station_info_8, R.drawable.stick_station_info_8)
                8 -> listOf(R.drawable.round_station_info_9, R.drawable.stick_station_info_9)
                else -> listOf(R.drawable.round_station_info, R.drawable.stick_station_info)
            }

            // 출발역 표시 위치 설정
            val newStartRoundImgmarginLeft = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 53f, resources.displayMetrics
            ).toInt()
            val newStartRoundImgmarginTop = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 230f * (i + 1), resources.displayMetrics
            ).toInt()
            val newStartRoundImgparams = FrameLayout.LayoutParams(
                (45f * density).toInt(),
                (45f * density).toInt()
            )
            newStartRoundImgparams.setMargins(
                newStartRoundImgmarginLeft,
                newStartRoundImgmarginTop,
                0,
                0
            )
            newStartRoundImg.layoutParams = newStartRoundImgparams

            // 도착역 표시 위치 설정
            val newEndRoundImgmarginLeft = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 53f, resources.displayMetrics
            ).toInt()
            val newEndRoundImgmarginTop = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (230f * (i + 1)) + 150f, resources.displayMetrics
            ).toInt()
            val newEndRoundImgparams = FrameLayout.LayoutParams(
                (45f * density).toInt(),
                (45f * density).toInt()
            )
            newEndRoundImgparams.setMargins(newEndRoundImgmarginLeft, newEndRoundImgmarginTop, 0, 0)
            newEndRoundImg.layoutParams = newEndRoundImgparams

            // 역 사이 선 위치 설정
            val newStickImgmarginLeft = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 64f, resources.displayMetrics
            ).toInt()
            val newStickImgmarginTop = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (230f * (i + 1)) + 30f, resources.displayMetrics
            ).toInt()
            val newStickImgparams = FrameLayout.LayoutParams(
                (23f * density).toInt(),
                (130f * density).toInt()
            )
            newStickImgparams.setMargins(newStickImgmarginLeft, newStickImgmarginTop, 0, 0)
            newStickImg.layoutParams = newStickImgparams

            if (i != numberOfData) {
                // 역 사이 표시 위치 설정
                val betweenStations1 = ImageView(this)

                val betweenStations1marginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 70f, resources.displayMetrics
                ).toInt()
                val betweenStations1marginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, (230f * (i + 1)) + 203f, resources.displayMetrics
                ).toInt()
                val betweenStations1params = FrameLayout.LayoutParams(
                    (8f * density).toInt(),
                    (16f * density).toInt()
                )
                betweenStations1params.setMargins(
                    betweenStations1marginLeft,
                    betweenStations1marginTop,
                    0,
                    0
                )
                betweenStations1.layoutParams = betweenStations1params
                betweenStations1.setImageResource(R.drawable.between_stations)
                scrollContent.addView(betweenStations1)
            }
            // 이미지의 src 입력
            newStartRoundImg.setImageResource(drawableResources[0])
            newEndRoundImg.setImageResource(drawableResources[0])
            newStickImg.setImageResource(drawableResources[1])
            // 스크롤에 추가
            scrollContent.addView(newStickImg)
            scrollContent.addView(newStartRoundImg)
            scrollContent.addView(newEndRoundImg)
            if (i == numberOfData) {

                // 도착 텍스트 내용 및 크기 설정
                newENDTextView.text = "도착"
                newENDTextView.textSize = 23f

                // 도착 텍스트 마진 설정
                val newENDTextViewmarginLeft = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics
                ).toInt()
                val newENDTextViewmarginTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, (233f * (i + 1)) + 202f, resources.displayMetrics
                ).toInt()
                val newENDTextViewparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                newENDTextViewparams.setMargins(
                    newENDTextViewmarginLeft,
                    newENDTextViewmarginTop,
                    0,
                    0
                )
                newENDTextView.layoutParams = newENDTextViewparams
                scrollContent.addView(newENDTextView)
            }
        }

    }

    //알림 호출
    private fun showNotification() {
        val nb: NotificationCompat.Builder = notificationHelper.getChannelNotification()

        // Notification을 생성하고 표시하는 부분 수정
        val notificationManager = notificationHelper.getManager()
        val notification = nb.build()
        notificationManager.notify(1, notification)
    }
}