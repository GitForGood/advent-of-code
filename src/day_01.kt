package src

import java.io.File
import kotlin.math.abs

const val path_to_input_a = "advent-of-code/input/day_01_a.txt"
const val path_to_input_b = "advent-of-code/input/day_01_b.txt"
fun partA(){
    val (listA, listB) = readFileAndSplitIntoLists(path_to_input_a)
    listA.sorted()
        .zip(listB.sorted())
        .sumOf { (a,b) -> abs(a-b) }
        .also{ println(it) }
}

fun readFileAndSplitIntoLists(path: String): Pair<List<Int>,List<Int>>{
    return File(path)
        .readLines()
        .map { line: String -> val split =  line.split(" ")
            split.first().toInt() to split.last().toInt()}
        .unzip()
}

fun partB(){
    val (listValues, listB) = readFileAndSplitIntoLists(path_to_input_b)
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

