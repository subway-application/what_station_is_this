package com.example.subway.PathsDirections

import com.example.subway.PathsDirections.Edge

data class Node(val id: Int) {
    val edges: MutableList<Edge> = mutableListOf()
    val lines = mutableSetOf<Int>() // 각 노드가 속한 노선 정보를 저장하는 집합

    override fun toString(): String {
        return id.toString()
    }
}