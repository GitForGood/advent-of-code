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

private fun partA(){
    inputRegex
        .map { (total, operands) ->
            total to listToTree(total, operands)
        }
        .filter {(total, tree) ->
            tree?.endsIn(total) ?: false
        }
        .sumOf { it.first }
        .also { println(it) }
}
private fun listToTree(total: BigInteger, list: List<BigInteger>, tree: Tree? = null): Tree?{
    if (list.isEmpty())
        return tree
    return tree?.let {
        val next: BigInteger = list.first()
        it.left = listToTree(total, list.drop(1), Tree(it.value * next))
        it.right = listToTree(total, list.drop(1), Tree(it.value + next))
        return@let it
    } ?: listToTree(total, list.drop(1), Tree(list.first()))
}
private class Tree(val value: BigInteger, var left: Tree? = null, var right: Tree? = null){
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
private fun partB(){
    inputRegex.map {(target, operands) ->
        target to start(target, operands)
    }.filter { (target, lbi) ->
        lbi.any { it.first == target }
    }.sumOf { it.first }
        .also { println(it) }
}
// total, remaining operands
typealias LBI = Pair<BigInteger, List<BigInteger>>
private fun start(target: BigInteger, operands: List<BigInteger>): List<LBI>{
    val head = operands.first()
    val list = operands.drop(1)
    return iterator(target, head to list)
}
private fun iterator(target: BigInteger, step: LBI): List<LBI>{
    if (step.first > target)
        return emptyList()
    if (step.second.isEmpty())
        return listOf(step)
    val addStep = step.first + step.second.first() to step.second.drop(1)
    val mulStep = step.first * step.second.first() to step.second.drop(1)
    val conStep = (step.first.toString() + step.second.first().toString()).toBigInteger() to step.second.drop(1)
    return listOf(iterator(target, addStep), iterator(target, mulStep), iterator(target, conStep)).flatten()
}

fun main(){
    //partA()
    partB()
}