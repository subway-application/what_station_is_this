package com.example.subway.setting.notice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.subway.R
import com.example.subway.databinding.ActivityWriteBinding
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

        findViewById<ImageButton>(R.id.apply_write).setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // 현재 시간을 Timestamp로 생성
            val timestamp = Timestamp(System.currentTimeMillis())

            // Base 객체 생성
            val baseObject = Base(title,content,timestamp)
            dataStructure.push(baseObject)
            position_count++

            // 생성된 Base 객체를 사용하거나 저장 등의 작업 수행
            // 여기서는 예시로 로그에 출력
            //println(baseObject.toString())
            println(dataStructure.printLinkedList())

            //이전 화면 호출
            val intent = Intent(this, NoticeDetailActiviy::class.java)
            startActivity(intent)


        }
    }
    fun return_count(): Int{
        return position_count
    }

}