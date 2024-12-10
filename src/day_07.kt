package src

import java.io.File
import java.math.BigInteger

private val operators = {a:Int,b:Int -> a+b} to {a:Int,b:Int -> a*b}
private const val path = "advent-of-code/input/day_07.txt"

private val inputRegex = File(path)
    .readLines()
    .map { """(\d+)""".toRegex().findAll(it) }
    .map {
        it.first().value.toBigInteger() to
                it.drop(1).map { it.value.toBigInteger() }
                    .toList()
    }

private val testInput = listOf("190: 10 19",
        "3267: 81 40 27",
        "83: 17 5",
        "156: 15 6",
        "7290: 6 8 6 15",
        "161011: 16 10 13",
        "192: 17 8 14",
        "21037: 9 7 18 13",
        "292: 11 6 16 20")
    .map { """(\d+)""".toRegex().findAll(it) }
    .map {
        it.first().value.toBigInteger() to
                it.drop(1).map { it.value.toBigInteger() }
                    .toList()
    }


private fun partA(){
    inputRegex
        .map { (total, operands) ->
            total to listToTree(total, operands)
        }
        .filter {(total, tree) ->
            tree?.endsIn(total) ?: false
        }
        .onEach { (total, tree) ->
            println("total: $total,")
            tree?.allNodesThatEndsIn(total)?.forEach{
                println("formula: ${it.formula}")
            }
        }
        .sumOf { it.first }
        .also { println(it) }
}
private fun listToTree(total: BigInteger, list: List<BigInteger>, tree: Tree? = null): Tree?{
    if (list.isEmpty())
        return tree
    return tree?.let {
        val next: BigInteger = list.first()
        it.left = listToTree(total, list.drop(1), Tree(it.formula + " * $next", it.value * next))
        it.right = listToTree(total, list.drop(1), Tree(it.formula + " + $next", it.value + next))
        return@let it
    } ?: listToTree(total, list.drop(1), Tree(list.first().toString(), list.first()))
}
private class Tree(val formula: String, val value: BigInteger, var left: Tree? = null, var right: Tree? = null){
    fun endsIn(i: BigInteger): Boolean{
        if (left == null && right == null)
            return i == value
        return left?.endsIn(i) ?: false || right?.endsIn(i) ?: false
    }
    fun allNodesThatEndsIn(i: BigInteger, acc: MutableList<Tree> = mutableListOf()): List<Tree>{
        if (left == null && right == null)
            if (i == value)
                acc.add(this)
        left?.allNodesThatEndsIn(i, acc)
        right?.allNodesThatEndsIn(i,acc)
        return acc.toList()
    }
}
fun main(){
    partA()
}