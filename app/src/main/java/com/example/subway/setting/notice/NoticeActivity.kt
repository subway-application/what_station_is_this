package com.example.subway.setting.notice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.subway.R
import com.example.subway.databinding.ActivityNoticeBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subway.MainActivity
import java.sql.Timestamp

class NoticeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BaseAdapter
    private val linkedList = LinkedList<Base>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)


        // RecyclerView 초기화 및 NoticeActivity
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        adapter = BaseAdapter(dataStructure)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager


        linkedList.push(Base("Title 1", "Content 1", Timestamp.valueOf("2023-12-01 12:00:00")))
        linkedList.push(Base("Title 2", "Content 2", Timestamp.valueOf("2023-12-02 15:30:00")))
        linkedList.push(Base("Title 3", "Content 3", Timestamp.valueOf("2023-12-03 10:45:00")))


        // RecyclerView 어댑터 설정
//        val adapter = BaseAdapter(linkedList)
//        recyclerView.adapter = adapter


//        if (writeBtnVisibility) {
//            findViewById<RelativeLayout>(R.id.writeBtn).visibility = View.VISIBLE
//            findViewById<RelativeLayout>(R.id.editPostBtn).visibility = View.VISIBLE
//            findViewById<RelativeLayout>(R.id.editPost1Btn).visibility = View.VISIBLE
//            }

            // RelativeLayout에 대한 클릭 리스너 설정
            val writeBtn = findViewById<ImageButton>(R.id.writeBtn)
            writeBtn.setOnClickListener {
                // RelativeLayout이 클릭되었을 때 수행할 동작
                // 예: 다른 화면으로 이동하는 코드
                Log.d("ActivityName", "onCreate called")
                val intent = Intent(this, WriteActivity::class.java)
                startActivity(intent)
            }

        // Divider 추가
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.divider)?.let {
            itemDecoration.setDrawable(it)
        }
        recyclerView.addItemDecoration(itemDecoration)
        }

    // 뒤로가기 버튼을 눌렀을 때 호출되는 메서드
    override fun onBackPressed() {
        // 메인 화면으로 이동하는 코드
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed() // 부모 클래스의 메서드 호출
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == WriteActivity.WRITE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // WriteActivity로부터 데이터를 받아와서 처리
            val receivedTitle = data?.getStringExtra("EXTRA_TITLE")
            val receivedContent = data?.getStringExtra("EXTRA_CONTENT")
            // 여기에서 dataStructure에 데이터를 추가하고 어댑터를 갱신
            // ...

            // 데이터를 linkedList에 추가
            if (receivedTitle != null && receivedContent != null) {
                linkedList.push(Base(receivedTitle, receivedContent, Timestamp(System.currentTimeMillis())))
                // RecyclerView 어댑터 갱신
                adapter.notifyDataSetChanged()
            }
        }
    }
}
