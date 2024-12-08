package src

import java.io.File

private const val path_to_input_a = "advent-of-code/input/day_02_a.txt"
private const val path_to_input_b = "advent-of-code/input/day_02_b.txt"

private fun readFileAndProduceNestedListsOfInts(path: String): List<List<Int>>{
    return File(path)
        .readLines()
        .map { it.split(" ")
            .map{ ti ->
                ti.toInt()
            }
        }
}
private fun listPasses(list: List<Int>): Boolean{
    val zipped = list.zipWithNext()
    val descending = zipped.all { it.first - it.second in 1..3 }
    if (descending)
        return true
    return zipped.all { it.second - it.first in 1..3 }
}

private fun foldPassCheck(list: List<Int>): Boolean{
    val foldDescending = list.fold(true to list.first()+1){
        (descending, previous), next ->
        (descending && ((previous - next) in 1..3)) to next
    }.first
    if (foldDescending)
        return true
    return list.fold(true to list.first()-1){
        (descending, previous), next ->
        (descending && ((previous - next) in -3..-1)) to next
    }.first
}
private fun partA(){
    readFileAndProduceNestedListsOfInts(path_to_input_a)
        .count{ listPasses(it) }
        .also { println(it) }
}
fun main(){
    partA()
}
