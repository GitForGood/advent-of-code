package src

import java.io.File
import kotlin.math.abs

private const val path_to_input_a = "advent-of-code/input/day_01.txt"
private fun partA(){
    val (listA, listB) = readFileAndSplitIntoLists()
    listA.sorted()
        .zip(listB.sorted())
        .sumOf { (a,b) -> abs(a-b) }
        .also{ println(it) }
}

private fun readFileAndSplitIntoLists(): Pair<List<Int>,List<Int>>{
    return File(path_to_input_a)
        .readLines()
        .map { line: String -> val split =  line.split(" ")
            split.first().toInt() to split.last().toInt()}
        .unzip()
}

private fun partB(){
    val (listValues, listB) = readFileAndSplitIntoLists()
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

