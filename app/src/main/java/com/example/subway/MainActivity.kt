package com.example.subway

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.subway.bookmark.BookmarkActivity
import com.example.subway.databinding.ActivityMainBinding
import com.example.subway.setting.notice.NoticeActivity
import com.example.subway.search.SearchActivity
import com.example.subway.setting.ComplaintActivity
import com.example.subway.setting.SettingActivity
import com.github.chrisbanes.photoview.PhotoView
import com.github.chrisbanes.photoview.PhotoViewAttacher
import org.w3c.dom.Node
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    //역 터치 관련
    private lateinit var binding: ActivityMainBinding
    private lateinit var stationPhotoView: PhotoView
    private lateinit var stationAttacher: PhotoViewAttacher

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 역 터치 관련
        stationPhotoView = binding.stationMap

        // stationphotoView에 대한 attacher 초기화
        stationAttacher = PhotoViewAttacher(stationPhotoView)
        stationAttacher.isZoomable = true
        stationAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP)


        //노선도 터치 시
        stationAttacher.setOnPhotoTapListener { _, x, y ->
            // 이미지 상의 특정 좌표 얻기
            // 전체 이미지의 크기
            val fullImageWidth = 1468// 전체 이미지의 폭
            val fullImageHeight = 1051// 전체 이미지의 높이

            //이미지뷰의 크기
            val imageViewWidth = stationPhotoView.width
            val imageViewHeight = stationPhotoView.height

            // 현재 이미지뷰 내에서의 상대적인 좌표를 전체 이미지의 좌표로 변환
            val imageX = (x * fullImageWidth / imageViewWidth).toFloat() * 1000
            val imageY = (y * fullImageHeight / imageViewHeight).toFloat() * 2000

            // 변환된 좌표를 사용하여 원하는 작업 수행
            handleClickEvent(imageX, imageY)
        }

        //검색 버튼
        binding.searchBtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        //즐겨찾기 버튼
        binding.bookmarkBtn.setOnClickListener {
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
        }

        //설정 버튼
        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        //공지사항 버튼
        binding.noticeBtn.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }

        //민원신고 버튼
        binding.complaintBtn.setOnClickListener {
            val intent = Intent(this, ComplaintActivity::class.java)
            startActivity(intent)
        }

        // 메인화면 공지사항 터치시 공지사항 안내 화면으로 전환
        val noticeBox = findViewById<View>(R.id.notice_box)
        noticeBox.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }

        // startBtn 클릭 시
        val startBtn: ImageButton = findViewById(R.id.startBtn)
        startBtn.setOnClickListener {
            handleStartClickEvent()
        }

        // endBtn 클릭 시
        val endBtn: ImageButton = findViewById(R.id.endBtn)
        endBtn.setOnClickListener {
            handleEndClickEvent()
        }
    }

    fun toggleAdditionalButtonsVisibility() {
        // 공지사항과 민원신고 버튼의 가시성을 토글
        binding.noticeBtn.isVisible = !binding.noticeBtn.isVisible
        binding.complaintBtn.isVisible = !binding.complaintBtn.isVisible
    }

    fun onMoreButtonClick(view: View) {
        // 더보기 버튼을 클릭했을 때
        toggleAdditionalButtonsVisibility()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //역 터치 관련
    private fun handleClickEvent(x: Float, y: Float) {
        // 여기에서 클릭한 좌표를 이용하여 역을 클릭하는 로직을 구현
        // 예: 특정 좌표 범위 내에 클릭이 감지되면 해당 역에 대한 처리 수행

        val result = isClickedOnStation(x, y)
        val isClicked = result.stationClicked
        val stationName = result.stationName
        val stationX = result.stationX
        val stationY = result.stationY

        // 예시: 특정 좌표 범위 내에 클릭되면 토스트 메시지를 표시
        if (isClicked) {
            showToast("역을 클릭했습니다!\n좌표: x=$x, y=$y")

            //하단 역 정보 표시
            if (binding.stationInfo.visibility == View.GONE) {
                binding.stationInfo.isVisible = !binding.stationInfo.isVisible
            } else {

            }
            val textView = findViewById<TextView>(R.id.stationInfoText)
            textView.text = stationName
            sttName = stationName
        } else {
            binding.stationInfo.visibility = View.GONE
        }

    }

    data class Result(
        val stationClicked: Boolean,
        val stationName: String,
        val stationX: Float,
        val stationY: Float
    )

    //역 터치 관련
    private fun isClickedOnStation(x: Float, y: Float): Result {
        // 특정 좌표 범위 내에 클릭되었는지 여부를 확인하는 로직을 구현
        // 예: 이미지 상의 특정 좌표 범위 계산

        val toleranceX = 15f
        val toleranceY = 10f

        val inputStream = resources.openRawResource(R.raw.stationlocation)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val lines: List<String> = reader.readLines()

        // 2x3 크기의 2차원 배열 생성
        val rows = 3
        val cols = 111
        val stationArray = Array(rows) { FloatArray(cols) { 0f } }
        var count = 0

        for (line in lines) {
            val parts = line.split(' ')
            stationArray[0][count] = parts[0].toFloat()
            stationArray[1][count] = parts[3].toFloat()
            stationArray[2][count] = parts[6].toFloat()
            count++
        }


        var stationName = 0
        var stationX = 0f
        var stationY = 0f
        for (col in 0..(cols - 1)) {
            if (x >= stationArray[1][col] - toleranceX && x <= stationArray[1][col] + toleranceX
                && y >= stationArray[2][col] - toleranceY && y <= stationArray[2][col] + toleranceY
            ) {
                stationName = stationArray[0][col].toInt()
                stationX = stationArray[1][col].toFloat()
                stationY = stationArray[2][col].toFloat()
                break
            }
        }

        return Result(stationName != 0, stationName.toString(), stationX, stationY)
    }

    var sttName: String? = ""
    var startSttName: String? = ""
    var endSttName: String? = ""
    private fun handleStartClickEvent() {
        val startBlankText: TextView = findViewById(R.id.startStationName)
        startSttName = sttName
        startBlankText.text = startSttName
        if (binding.startSttInfo.visibility == View.GONE && binding.endBlankBackImg.visibility == View.GONE) {
            if (binding.endSttInfo.visibility == View.GONE && binding.startBlankBackImg.visibility == View.GONE) {
                binding.startSttInfo.visibility = View.VISIBLE
                binding.endBlankBackImg.visibility = View.VISIBLE
            } else {
                if (startSttName == endSttName) {
                    showToast("출발역과 도착역이 같습니다.")
                    startSttName = ""
                } else {
                    binding.startBlankBackImg.visibility = View.GONE
                    binding.startSttInfo.visibility = View.VISIBLE
                    startSttName = ""
                    endSttName = ""
                    // 화면 바꾸기
                }
            }
        }
        println("start:${startSttName},end:${endSttName}")
    }

    private fun handleEndClickEvent() {
        val endBlankText: TextView = findViewById(R.id.endStationName)
        endSttName = sttName
        endBlankText.text = endSttName
        if (binding.endSttInfo.visibility == View.GONE && binding.startBlankBackImg.visibility == View.GONE) {
            if (binding.startSttInfo.visibility == View.GONE && binding.endBlankBackImg.visibility == View.GONE) {
                binding.endSttInfo.visibility = View.VISIBLE
                binding.startBlankBackImg.visibility = View.VISIBLE
            } else {
                if (startSttName == endSttName) {
                    showToast("출발역과 도착역이 같습니다.")
                    endSttName = ""
                } else {
                    binding.endBlankBackImg.visibility = View.GONE
                    binding.endSttInfo.visibility = View.VISIBLE
                    startSttName = ""
                    endSttName = ""
                    // 화면 바꾸기
                }
            }
        }
        println("start:${startSttName},end:${endSttName}")
    }

}
