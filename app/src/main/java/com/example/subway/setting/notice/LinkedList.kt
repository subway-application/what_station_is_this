package com.example.subway.setting.notice

class LinkedList<T> {
    data class Node<T>(
        var data: T?,
        var next: Node<T>?
    )
    private var head: Node<T>? = null
    // 노드 개수를 세는 변수
    private var size: Int = 0
    private var last: Node<T>? = null
    fun push(data: T){
        //처음에 삽입하는 함수
        val newNode = Node(data, head)
        head = newNode
        if (last == null){ // lastNode가 없을 때 즉, 요소가 아무것도 없을 때
            last = head
        }
        size++
    }
    fun append(data: T){
        //맨뒤에 삽입하는 함수
        val newNode = Node(data, null)
        //headNode 가 비어있는 경우 즉, 요소가 없는 경우
        if (head == null){
            head = newNode
            last = newNode
            size++
            return
        }
        last?.next = newNode
        last = newNode
        size++
    }
    private fun findData(index: Int): Node<T>? {
        //인덱스 값을 통해 원하는 값을 찾는 함수
        var currentNode = head

        repeat(index){
            currentNode = currentNode?.next
        }
        return currentNode
    }
    fun removeFirst(){
        //처음값을 삭제하는 함수
        // Empty List
        if (head == null){
            return
        }
        // head == last -> 요소 1개
        if (head == last){ // or headNode.next == null or currentSize == 1
            last = null
            head = null
        }
        else {
            head = head?.next
        }
        size--
    }
    fun removeLast(){
        //마지막 값을 삭제하는 함수
        //Empty List
        if (head == null){
            return
        }
        // 요소 1개
        if (head == last){
            return removeFirst()
        }
        //임시 포인터
        var current = head
        var prev: Node<T>? = null
        //lastNode 이전 노드 즉 마지막에서 두번째 node
        while (current != last){
            prev = current
            current = current?.next
        }
        // currentNode == lastNode
        prev?.next = null
        last = prev
        size--
    }
    fun remove(index: Int){
        //인덱스를 통해서 원하는 값을 삭제하는 함수
        if (index > size) throw IndexOutOfBoundsException("out of index")
        // 이전/다음 노드 초기화
        var prev: Node<T>? = null
        val delete: Node<T>? = findData(index)
        // 이전 노드 찾기
        if(index != 0){
            prev = findData(index - 1)
        }
        //
        when {
            // 이전 노드가 없으면 -> index = 0
            prev == null -> removeFirst()
            // 다음 노드가 없으면 -> index = currentSize
            delete == null -> removeLast()
            // 이전 / 노드 둘 다 있으면 -> 0 < index < currentSize
            else -> {
                prev.next = delete.next
                delete == null
                size--
            }
        }
    }
    fun printLinkedList() {
        var str = "["
        var currentNode = head

        while (currentNode != null) {
            str += "${currentNode.data}"
            if (currentNode != last) {
                str += ","
            }
            currentNode = currentNode.next
        }
        str += "]"
        println(str)
    }
    fun clear(){
        for (i in 0 until size){
            removeFirst()
        }
    }
}