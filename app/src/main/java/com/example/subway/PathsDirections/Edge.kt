package com.example.subway.PathsDirections

data class Edge(
    val destination: Node,
    val time: Int,
    val distance: Int,
    val cost: Int
)