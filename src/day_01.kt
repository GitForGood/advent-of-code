package src

import java.io.File
import kotlin.math.abs

const val path_to_input_a = "advent-of-code/input/day_01_a.txt"
const val path_to_input_b = "advent-of-code/input/day_01_b.txt"
fun partA(){
    val lists = File(path_to_input_a)
        .readLines()
        .map { line: String -> val split =  line.split(" ")
            split.first().toInt() to split.last().toInt()}
    val (listA, listB) = lists.unzip()
    println(listA.sorted()
        .zip(listB.sorted())
        .sumOf { (a,b) -> abs(a-b) }
    )
}

fun partB(){
    val lists = File(path_to_input_b)
        .readLines()
        .map { line: String -> val split =  line.split(" ")
            split.first().toInt() to split.last().toInt()}
    val (listValues, listB ) = lists.unzip()
    val mapOccurrences: Map<Int,Int> = listB
        .groupingBy { it }
        .eachCount()
    listValues.sumOf { mapOccurrences.getOrDefault(it, 0) * it }
        .also { println(it) }
}

fun main() {
    partA()
    partB()
}

