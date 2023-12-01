package com.example.subway.PathsDirections

import java.io.File
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue


fun minTransfers(nodes: Map<Int, Node>, startStation: Node, endStation: Node): Int {
    val visited = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    val prevNode = mutableMapOf<Node, Node?>()
    val queue: Queue<Pair<Node, Int>> = LinkedList()

    visited[startStation] = 0
    queue.add(Pair(startStation, 0))

    var minTransfers = Int.MAX_VALUE
    var minPath: Map<Node, Node?>? = null

    while (queue.isNotEmpty()) {
        val (currentNode, transfers) = queue.remove()
        for (edge in currentNode.edges) {
            var newTransfers = transfers
            if (currentNode.lines.intersect(edge.destination.lines).isEmpty()) {
                newTransfers++
            }
            if (edge.destination == endStation && newTransfers < minTransfers) {
                if (newTransfers < minTransfers) {
                    minTransfers = newTransfers
                    minPath = prevNode.toMap()
                }
                prevNode[edge.destination] = currentNode
                println("curt: ${prevNode[currentNode]}")
            } else {
                if (!visited.containsKey(edge.destination) || newTransfers < visited.getValue(edge.destination)) {
                    println("new: ${newTransfers}, visit: ${visited.getValue(edge.destination)}")
                    visited[edge.destination] = newTransfers
                    prevNode[edge.destination] = currentNode
                    queue.add(Pair(edge.destination, newTransfers))
                }
                println("else new: ${newTransfers}, visit: ${visited.getValue(edge.destination)}")
            }
        }
    }

    if (minPath != null) {
        printPath(minPath, startStation, endStation)
    }

    return if (minTransfers == Int.MAX_VALUE) -1 else minTransfers
}


fun printPath(prevNode: Map<Node, Node?>, startStation: Node, endStation: Node) {
    val path = LinkedList<Node>()
    var currentNode = endStation

//    for ((node, prev) in prevNode) {
//        val prevNodeId = prev?.id ?: "없음"
//        println("노드 ${node.id}의 이전 노드: $prevNodeId")
//    }

    while (currentNode != startStation) {
        path.addFirst(currentNode)
        val prev = prevNode[currentNode]
        if (prev == null) {
            println("Error: ${currentNode.id}의 이전 노드를 찾을 수 없습니다.")
            return
        }
        currentNode = prev
    }

    path.addFirst(startStation)
    var transfers = 0
    var prevLine = '0'
    for (i in 1 until path.size) {
        prevLine = path[i - 1].id.toString()[0]
        var currentLine = path[i].id.toString()[0]
        if (prevLine != currentLine) {
            print("start: ${prevLine}\t end: ${currentLine}\t")
            transfers++
        }
    }

    println("최소 환승: ${path.joinToString(" -> ")}")
    println("총 환승 횟수: ${transfers}회")

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
    val startNode = nodes[702] // 시작 노드 ID
    val endNode = nodes[116]  // 종착 노드 ID
    if (startNode != null && endNode != null) {
        minTransfers (nodes, startNode, endNode)
    }
}