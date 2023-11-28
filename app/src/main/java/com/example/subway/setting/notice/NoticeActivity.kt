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
import com.example.subway.R
import com.example.subway.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        // NoticeActivity
        val preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val writeBtnVisibility = preferences.getBoolean("writeBtnVisibility", false)

        if (writeBtnVisibility) {
            showToast("true")

            findViewById<RelativeLayout>(R.id.writelayoutBtn).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.editPostBtn).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.editPost1Btn).visibility = View.VISIBLE

            // RelativeLayout에 대한 클릭 리스너 설정
            val writeBtn = findViewById<ImageButton>(R.id.writeBtn)
            writeBtn.setOnClickListener {
                // RelativeLayout이 클릭되었을 때 수행할 동작
                // 예: 다른 화면으로 이동하는 코드
                Log.d("ActivityName", "onCreate called")
                val intent = Intent(this, WriteActivity::class.java)
                startActivity(intent)
            }
        }



    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}