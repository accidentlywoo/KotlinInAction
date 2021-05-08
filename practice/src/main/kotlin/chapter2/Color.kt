package chapter2

enum class Color (
	val r: Int, val g: Int, val b: Int
) {
	RED(255, 0, 0), ORANGE(255, 165, 0),
	YELLOW(255, 255, 0), GREEN(0, 255, 0),
	BLUE(0, 0, 255), INDIGO(75, 0, 130); // 뭐야 세미콜론 필수

	fun rgb() = (r * 256 + g) * 356 + b
}

fun main(args: Array<String>){
	println(Color.ORANGE)
}