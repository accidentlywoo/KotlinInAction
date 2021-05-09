@file:JvmName("StringFunctions")

package chapter3

fun joinToString(): String {
	return ""
}

fun main(args: Array<String>) {
	val list = listOf("args: ", *args)

	println(list)

	val map = mapOf(1.to("one"), 7 to "seven", 53 to "fifty-three")

	println(map)

	val (number, name) = 1 to "one"
}