package com.example.subway.setting.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.example.subway.R
import com.example.subway.databinding.ActivityNoticeBinding
class NoticeDetailActiviy : AppCompatActivity(){

    private lateinit var binding: ActivityNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //UI 초기화
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //전달 받은 NoticeData
        val noticeData = LinkedList<Base>()
        val write = WriteActivity()
        val position = write.return_count()

        //데이터 클래스 base를 받아오는 변수
        val get_Data = noticeData.getDataNode(position)
            

        // UI 요소 초기화
        binding.editTextTitle.text = get_Data?.title
        binding.editTextContent.text = get_Data?.content
        binding.timeStamp.text = get_Data?.timestamp.toString()

    }
}