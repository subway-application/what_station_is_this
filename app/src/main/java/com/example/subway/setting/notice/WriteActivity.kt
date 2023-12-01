package com.example.subway.setting.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.subway.R
import com.example.subway.databinding.ActivityWriteBinding
import java.sql.Timestamp
val dataStructure = Data_structure()



class WriteActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        // titleEditText와 contentEditText 초기화
        titleEditText = findViewById(R.id.editTextTitle)
        contentEditText = findViewById(R.id.editTextContent)

        findViewById<ImageButton>(R.id.apply_write).setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // 현재 시간을 Timestamp로 생성
            val timestamp = Timestamp(System.currentTimeMillis())

            // Base 객체 생성
            val baseObject = Data_structure.Base(title, content, timestamp)
            dataStructure.list.push(baseObject)
            //이전 화면 호출
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)

            // 생성된 Base 객체를 사용하거나 저장 등의 작업 수행
            // 여기서는 예시로 로그에 출력
           //println(baseObject.toString())
            println(dataStructure.list.printLinkedList())
        }
    }

}