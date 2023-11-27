package com.example.subway.setting

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.subway.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // "관리자 인증" 텍스트 클릭 시
        findViewById<TextView>(R.id.adminAuthText).setOnClickListener {
            showPasswordDialog()
        }
    }

    private fun showPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("관리자 인증")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("확인") { _, _ ->
            val password = input.text.toString()
            // 여기에 비밀번호 확인 로직 추가
            // 예를 들어, 실제로는 서버로 비밀번호를 보내어 확인하는 등의 작업이 필요
            if (password == "1111") {
                // 비밀번호가 일치하는 경우 원하는 작업 수행
                showToast("관리자 인증 성공!")
                val writeButton = findViewById<RelativeLayout>(R.id.writeBtn)
                writeButton.visibility = View.VISIBLE
            } else {
                // 비밀번호가 일치하지 않는 경우 원하는 작업 수행
                showToast("비밀번호가 일치하지 않습니다.")
            }
        }

        builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}