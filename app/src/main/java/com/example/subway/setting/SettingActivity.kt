package com.example.subway.setting

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.example.subway.R

class SettingActivity : AppCompatActivity() {
    private var writeBtnVisibility = false
    private lateinit var switch: Switch
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        //관리자 인증
        switch = findViewById<TextView>(R.id.adminBtn) as Switch
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        switch.isChecked = sharedPreferences.getBoolean("switch_state", false)

        // "관리자 인증" 텍스트 클릭 시
        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("switch_state", isChecked).apply()
            // 스위치 상태가 변경될 때마다 SharedPreferences에 저장
            // isChecked에는 스위치의 현재 상태가 전달됩니다.
            if (isChecked) {
                showPasswordDialog()
            } else {
                editor.putBoolean("writeBtnVisibility", false)
                editor.commit()
            }
        }

    }

    private fun showPasswordDialog() {
        //관리자 비밀번호를 인증하는 함수
        val builder = AlertDialog.Builder(this)
        builder.setTitle("관리자 인증")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        val preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("writeBtnVisibility", false)

        builder.setPositiveButton("확인") { _, _ ->
            val password = input.text.toString()
            // 여기에 비밀번호 확인 로직 추가
            // 예를 들어, 실제로는 서버로 비밀번호를 보내어 확인하는 등의 작업이 필요
            if (password == "1111") {
                // 비밀번호가 일치하는 경우 원하는 작업 수행
                showToast("관리자 인증 성공!")
                editor.putBoolean("writeBtnVisibility", true)
                editor.commit()
            } else {
                // 비밀번호가 일치하지 않는 경우 원하는 작업 수행
                showToast("비밀번호가 일치하지 않습니다.")
                editor.putBoolean("writeBtnVisibility", false)
                editor.commit()
            }
        }

        builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel()
            switch.isChecked = false
        }

        builder.show()
    }

    private fun showToast(message: String) {
        //알림을 보여주는 함수
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}