package com.example.subway.PathsDirections

import com.example.subway.PathsDirections.Edge

class Node(val id: Int) {
    //var predecessor: Nothing? = null
    //var visited: Boolean = false
    val edges: MutableList<Edge> = mutableListOf()
    val lines = mutableSetOf<Int>() // 각 노드가 속한 노선 정보를 저장하는 리스트
    override fun hashCode(): Int {
        return id
    }
    override fun toString(): String {
        return id.toString()
    }
}