package chapter3

fun main() {
	val set = hashSetOf(1, 7, 53)

	val list = arrayListOf(1, 7, 53)

	val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
	// to 는 키워드가 아니라 일반 함수!
	println(set.javaClass)

	println(list.javaClass)

	println(map.javaClass)

	val strings = listOf("first", "second", "fourteenth")

	println(strings.last())

	val numbers = setOf(1, 14, 2)

	println(numbers.max())

	println(numbers.maxOrNull())
}