package src

import java.io.File

//so i hear you like regex

private const val path_to_input_a = "advent-of-code/input/day_03_a.txt"

private const val test_input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

private fun partA(){
    val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    val input = File(path_to_input_a)
        .readLines()
        .map { regex.findAll(it) }
        .map { it.map {
                it.destructured.component1().toInt() * it.destructured.component2().toInt()
            }
            .sum()
        }
        .also { println(it) }
        .sum()
        .also { println(it) }
}

private fun partB(){
    val regex = """don't\(\)|do\(\)|mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
    val input = File(path_to_input_a)
        .readLines()
        .map { regex.findAll(it) }
        .map { sequence ->
            sequence.fold(true to 0){ (enable, sum), next ->
                if (next.value == "do()")
                    return@fold true to sum
                if (next.value == "don't()")
                    return@fold false to sum
                if (!enable)
                    return@fold false to sum
                val (a1, a2) = next.destructured
                true to (sum + a1.toInt() * a2.toInt())
            }
        }
            .also { println(it) }
        .sumOf { (_,sum) -> sum }
        .also { println(it) }
}

private fun main(){
    partA()
    partB()
}