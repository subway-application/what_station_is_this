package com.example.subway.PathsDirections

import com.example.subway.PathsDirections.Edge

data class Node(
    val station: Int,
    val edges: MutableList<Edge> = mutableListOf()
)