package com.example.subway.setting.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.subway.R
import java.sql.Timestamp
val dataStructure = LinkedList<Base>()



class WriteActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    var position_count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)


        // titleEditText, contentEditText, timeEditText 초기화
        titleEditText = findViewById(R.id.editTextTitle)
        contentEditText = findViewById(R.id.editTextContent)

        //// 제목을 title, 내용을 content, 현재 시간을 Timestamp로 생성
        findViewById<ImageButton>(R.id.apply_write).setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val timestamp = Timestamp(System.currentTimeMillis())

            // Base 객체 생성
            val baseObject = Base(title,content,timestamp)
            // LinkedList에 데이터 추가
            dataStructure.push(baseObject)
            position_count++

            // 데이터를 NoticeActivity로 전달하기 위한 Intent 생성
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_TITLE", title) // "EXTRA_TITLE"로 수정
            resultIntent.putExtra("EXTRA_CONTENT", content) // "EXTRA_CONTENT"로 수정


            // WriteActivity 종료 및 결과 전송
            setResult(RESULT_OK, resultIntent)
            finish()

            // 생성된 Base 객체를 사용하거나 저장 등의 작업 수행
            // 여기서는 예시로 로그에 출력
            //println(baseObject.toString())
            println(dataStructure.printLinkedList())


            //이전 화면 호출
            val intent = Intent(this, NoticeActivity::class.java)
            // 수정된 부분: startActivityForResult 대신 startActivity로 변경
            startActivity(intent)


        }

    }
    companion object {
        // 수정된 부분: WRITE_ACTIVITY_REQUEST_CODE를 상수로 정의
        const val WRITE_ACTIVITY_REQUEST_CODE = 123
    }

    fun return_count(): Int {
        return position_count
    }

}