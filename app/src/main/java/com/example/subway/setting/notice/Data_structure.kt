package com.example.subway.setting.notice

import java.sql.Timestamp

class Data_structure {
    data class Base(
        val title: String,
        val content: String,
        val timestamp: Timestamp
    )
    val list = LinkedList<Base>()
}
