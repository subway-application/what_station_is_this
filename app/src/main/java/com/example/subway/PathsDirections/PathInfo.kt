package com.example.subway.PathsDirections

data class PathInfo(
    val minTransfersPath: List<Node>, // 최소 환승 경로
    val startStation: Node, // 출발역
    val numStationsMoved: List<Int>, // 역 이동 개수
    val transferStations: List<String>, // 환승역
    val endStation: Node, // 도착역
    val numTransfers: Int, // 환승 횟수
    val totalCost: Int, // 총 금액
    val totalTime: Int // 총 시간
)
