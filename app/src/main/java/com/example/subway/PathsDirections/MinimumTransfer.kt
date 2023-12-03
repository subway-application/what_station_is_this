package com.example.subway.PathsDirections

import java.io.File
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue

fun minTransfers(nodes: Map<Int, Node>, startStation: Node, endStation: Node): Int {
    val visited = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    val prevNode = mutableMapOf<Node, Node?>()
    val queue: Queue<Pair<Node, Int>> = LinkedList()

    // 환승 횟수와, 큐 초기화
    visited[startStation] = 0
    queue.add(Pair(startStation, 0))

    var minTransfers = Int.MAX_VALUE
    var minPath: Map<Node, Node?>? = null

    while (queue.isNotEmpty()) { // transfers: 현재 노드까지의 환승 횟수
        val (currentNode, transfers) = queue.remove() // queue에 있는 노드와 transfers 꺼냄

        for (edge in currentNode.edges) {

            var currentTransfers = transfers
            var prevLines = prevNode[currentNode]?.lines ?: emptySet()
            if(prevLines.isNotEmpty()){
                if (prevLines.intersect(edge.destination.lines).isEmpty()) {  // 최소 환승 경로를 출력해 주는 내 구세주...
                    // currentNode의 이전 노드의 lines와 edge.destination.lines의 교집합이 없으면 환승 횟수 추가
                    currentTransfers++
                }
            }
            if (edge.destination == endStation && currentTransfers < minTransfers) {
                prevNode[edge.destination] = currentNode // endStation의 이전 노드 연결하니까 경로는 출력할 수 있음.
                minTransfers = currentTransfers
                minPath = prevNode.toMap()
            } else {
                if (!visited.containsKey(edge.destination) || currentTransfers < visited.getValue(edge.destination)) {
                    // edge의 목적지를 방문하지 않았을 때 visited가 ture임. false일 때 조건문 적용
                    // current < 현재까지 기록된 edge의 목적지 방문 횟수보다 작을 때
                    // 방문한 적이 없는 경우: 해당 노드를 처음 방문하는 경우, visited 맵에 추가하고 현재까지의 환승 횟수를 기록
                    // 방문한 적이 있지만 새로운 경로가 더 짧은 경우: 이전에 기록된 방문 횟수 갱신하고 queue에 새로운 경로 추가
                    visited[edge.destination] = currentTransfers
                    prevNode[edge.destination] = currentNode
                    queue.add(Pair(edge.destination, currentTransfers))
                }
            }
        }
    }

    if (minPath != null) {
        printPath(minPath, startStation, endStation)
    }

    return if (minTransfers == Int.MAX_VALUE) -1 else minTransfers
}

fun findPath(prevNode: Map<Node, Node?>, startStation: Node, endStation: Node): List<Node> {
    val path = mutableListOf<Node>()
    var currentNode = endStation

    // 도착역에서 출발역 역추적
    while (currentNode != startStation) {
        path.add(currentNode)
        val prev = prevNode[currentNode]
        if (prev == null) {
            println("Error: ${currentNode.id}의 이전 노드를 찾을 수 없습니다.")
            return emptyList()
        }
        currentNode = prev
    }

    path.add(startStation)
    return path.reversed()
}

fun printPath(prevNode: Map<Node, Node?>, startStation: Node, endStation: Node) {
    val path = findPath(prevNode, startStation, endStation) // 최소 경로 역추적해서 저장

    // 환승 횟수와 환승역 찾기
    var transfers = 0 // 환승 횟수
    var transferStation = mutableListOf<String>() // 환승역

    var numsmove = LinkedList<Int>() // 이동하는 역 개수를 저장하는 리스트
    var count = 1 // 이동하는 역 수를 세는 변수

    var totalTime = path[0].edges.find { it.destination == path[1] }?.time ?: 0
    var totalCost = path[0].edges.find { it.destination == path[1] }?.cost ?: 0

    for (i in 1 until path.size) {
        var nextStation : Node // 현재 역의 다음 역
        var prevStation = path[i - 1] // 현재 역의 이전 역

        if (i < path.size-1) {
            nextStation  = path[i + 1]
            if (nextStation .lines.intersect(prevStation.lines).isEmpty()) {
                // 현재 역의 이전 역과 다음 역 노선의 교집합이 없으면 환승 횟수 증가
                transferStation.add(path[i].toString())
                transfers++
                numsmove.add(count)
                count = 0
            }
            // 각 엣지의 시간과 비용을 더함
            totalTime += path[i].edges.find { it.destination == path[i + 1] }?.time ?: 0
            totalCost += path[i].edges.find { it.destination == path[i + 1] }?.cost ?: 0
        }
        count++
    }
    numsmove.add(count-1)

    // 경로 출력
    println("최소 환승: ${path.joinToString(" -> ")}")
    println("출발역: ${startStation}")
    if(numsmove.isNotEmpty()) { // 이동은 하니까 무조건 출력
        println("${numsmove.removeFirst()}개역 이동") // 첫 번째 요소 출력 후 제거
    }
    if (transferStation != null) {
        for (ts in transferStation) {
            println("환승역: ${ts}")
            if(numsmove.isNotEmpty()) {
                println("${numsmove.removeFirst()}개역 이동")
            }
        }
    }
    println("도착역: ${endStation}")
    println("총 환승 횟수: ${transfers}회") // 환승 횟수 출력
    println("총 금액: ${totalCost} 원") // 금액 출력

    // 시간 출력
    val hours = totalTime / 3600
    val minutes = (totalTime % 3600) / 60
    val seconds = totalTime % 60

    if (hours > 0) {
        println("총 시간: ${hours}시간 ${minutes}분 ${seconds}초")
    } else if (minutes > 0){
        println("총 시간: ${minutes}분 ${seconds}초")
    } else {
        println("총 시간: ${seconds}초")
    }

}


fun findPrevAndNext(path: List<Node>, startStation: Node, endStation: Node, currentStation: Node): Pair<Node?, Node?> {
    // -------------------------------- 현재 역을 매개 변수로 받으면 이전 역과 다음 역을 리턴해 주는 함수 --------------------------------

    val prev = if (path.contains(currentStation)) {
        val currentIndex = path.indexOf(currentStation)
        if (currentIndex > 0) path[currentIndex - 1] else null
    } else {
        null
    }

    val next = if (path.contains(currentStation)) {
        val currentIndex = path.indexOf(currentStation)
        if (currentIndex < path.size - 1) path[currentIndex + 1] else null
    } else {
        null
    }

    return Pair(prev, next)
}


fun main() {
    val nodes = mutableMapOf<Int, Node>()
    val lines = File("C:\\Users\\YunDoHyeong\\what_station_is_this\\app\\src\\main\\java\\com\\example\\subway\\PathsDirections\\Data").readLines()
    // 내 PC 경로에 맞춤.

    for (line in lines) {
        val parts = line.split(',')
        val start = parts[0].toInt()
        val end = parts[1].toInt()
        val time = parts[2].toInt()
        val distance = parts[3].toInt()
        val cost = parts[4].toInt()

        val startNode = nodes.getOrPut(start) { Node(start) }
        val endNode = nodes.getOrPut(end) { Node(end) }

        startNode.edges.add(Edge(endNode, time, distance, cost))
        // endNode.edges.add(Edge(startNode, time, distance, cost))
    }

    // 여기에 테스트 코드 작성

}