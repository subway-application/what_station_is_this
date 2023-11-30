package com.example.subway.PathsDirections

import java.io.File
import java.util.LinkedList
import java.util.Queue

fun minTransfers(nodes: Map<Int, Node>, startStation: Node, endStation: Node): Int {
    val visited = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    val prevNode = mutableMapOf<Node, Node?>()
    val queue: Queue<Pair<Node, Int>> = LinkedList()

    // 환승 횟수와, 큐 초기화
    visited[startStation] = 0
    queue.add(Pair(startStation, 0))

    while (queue.isNotEmpty()) { // 큐가 비어 있지 않는 동안 반복
        val (currentNode, transfers) = queue.remove() // 노드와 그 노드에 도달하기까지의 환승 횟수 꺼냄
        for (edge in currentNode.edges) { // 현재 노드에 연결된 모든 엣지 순회
            if (edge.destination == endStation) {
                // 엣지의 목적지가 종착 역이라면, 현재까지의 환승 횟수 반환
                prevNode[edge.destination] = currentNode // prevNode 맵에 해당 역의 이전 노드 저장
                printPath(prevNode, startStation, endStation) // printPath 함수를 호출하여 최소 환승 경로를 출력하고, 현재까지의 환승 횟수를 반환
                return transfers
            } else if (!visited.containsKey(edge.destination) || transfers < visited.getValue(edge.destination)) {
                // 엣지의 목적지를 아직 방문하지 않았거나, 환승 횟수가 visited 맵에 저장된 환승 횟수보다 작다면
                visited[edge.destination] = transfers // visited 맵 해당 역의 환승 횟수를 현재의 환승 횟수로 갱신
                prevNode[edge.destination] = currentNode // prevNode 맵에 해당 역의 이전 노드 저장
                queue.add(Pair(edge.destination, transfers + 1)) // 해당 역과 그 역에 도달하기까지의 환승 횟수 추가
            }
        }
    }
    return -1 // 시작 역에서 종착 역까지 도달할 수 없다는 동작
}

fun printPath(prevNode: Map<Node, Node?>, startStation: Node, endStation: Node) {
    val path = LinkedList<Node>()
    var currentNode = endStation // 종착 역부터 시작하여 이전 노드 추적

    while (currentNode != startStation) { // 시작 역에 도달할 때까지 반복
        path.addFirst(currentNode) // 현재 노드를 경로의 앞에 추가
        currentNode = prevNode[currentNode]!! // 현재 노드를 이전 노드로 변경
    }
    path.addFirst(startStation) // 시작 역을 경로의 앞에 추가

    println("최소 환승 경로: ${path.joinToString(" -> ")}")
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