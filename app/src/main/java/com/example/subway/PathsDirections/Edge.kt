package com.example.subway.PathsDirections

class Edge(val destination: Node, val time: Int, private val distance: Int, val cost: Int) {
    override fun hashCode(): Int {
        var result = destination.id
        result = 31 * result + time
        result = 31 * result + distance
        result = 31 * result + cost
        return result
    }
}