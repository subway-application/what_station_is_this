package com.example.subway.PathsDirections

import android.util.Log
import java.io.File
import java.util.PriorityQueue

fun dijkstraTime(nodes: Map<Int, Node>, startStation: Node, endStation: Node) {

    // 각 노드에서 시작 노드까지의 최단 거리를 저장하는 맵 생성, 초기값은 무한대로 설정
    val distances = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    distances[startStation] = 0

    // 최단 경로를 저장할 맵 생성
    val shortestPath = mutableMapOf<Node, Node?>()
    shortestPath[startStation] = null

    // 최단 경로의 소비되는 금액과 환승 횟수
    val totalCosts = mutableMapOf<Node, Int>().withDefault { 0 }
    //val transferCounts = mutableMapOf<Node, Int>().withDefault { 0 }
    //var newTransfers = 0

    // 노드를 거리에 따라 정렬하는 우선순위 큐 생성
    val queue = PriorityQueue<Node>(compareBy { distances.getValue(it) })
    queue.add(startStation)

    while (queue.isNotEmpty()) { // 빌 때까지 반복
        val current = queue.remove() // 가장 거리가 짧은 노드를 꺼내고 제거
        for (edge in current.edges) { // 현재 노드에 연결된 모든 엣지에 대해 반복
            val newDistance = distances.getValue(current) + edge.time // 현재 노드를 경유하는 시간 계산
            if (newDistance < distances.getValue(edge.destination)) {
                // 만약 현재 노드를 경유하는 시간 < 기존의 시간
                distances[edge.destination] = newDistance // 거리 갱신
                shortestPath[edge.destination] = current // 최단 경로 갱신
                queue.add(edge.destination) // 노드를 큐에 추가

                totalCosts[edge.destination] = totalCosts.getValue(current) + edge.cost
                //newTransfers += transferCounts.getValue(current) + (if (current.lines.intersect(edge.destination.lines).isEmpty()) 1 else 0)
                //transferCounts[edge.destination] = newTransfers

            }
        }
    }

    // 출발역부터 도착역까지의 경로 출력
    var currentStation: Node? = endStation
    val path = mutableListOf<Node>()
    while (currentStation != null) {
        path.add(0, currentStation)
        currentStation = shortestPath[currentStation]
    }

    println("최소 시간: ${path.joinToString(" -> ")}")

    val totalSeconds = distances[endStation] ?: 0
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    if (hours > 0) {
        println("총 시간: ${hours}시간 ${minutes}분 ${seconds}초")
    } else if (minutes > 0){
        println("총 시간: ${minutes}분 ${seconds}초")
    } else {
        println("총 시간: ${seconds}초")
    }

    println("총 금액: ${totalCosts[endStation]}원")
    //println("환승 횟수: ${transferCounts[endStation]}회")

}

fun main() {

    val nodes = mutableMapOf<Int, Node>()
    val lines = File("app/src/main/java/com/example/subway/PathsDirections/Data").readLines()

        for (line in lines) {
            val parts = line.split(',')
            val start = parts[0].toInt()
            val end = parts[1].toInt()
            val time = parts[2].toInt()
            val distance = parts[3].toInt()
            val cost = parts[4].toInt()

            val startNode = nodes.getOrPut(start) { Node(start) }
            val endNode = nodes.getOrPut(end) { Node(end) }

            startNode.lines.add(start / 100) // 각 앞자리에 해당하는 숫자로 노선 배당
            endNode.lines.add(end / 100)

            startNode.edges.add(Edge(endNode, time, distance, cost))
            endNode.edges.add(Edge(startNode, time, distance, cost))
        }

    // 추가 노선 배당
    val lineInfo = File("app/src/main/java/com/example/subway/PathsDirections/DataTransfer").readLines()

    for (info in lineInfo) {
        val parts = info.split(',')
        val id = parts[0].toInt()
        val line = parts[1].toInt()

        if (nodes[id]?.lines?.contains(line) != true) {
            nodes[id]?.lines?.add(line)
        }
    }

    // 여기에 테스트 코드 작성
    val startNode = nodes[406]  // 시작 노드 ID
    val endNode = nodes[608]  // 종착 노드 ID
    if (startNode != null && endNode != null) {
        dijkstraTime(nodes, startNode, endNode)
    }


}


