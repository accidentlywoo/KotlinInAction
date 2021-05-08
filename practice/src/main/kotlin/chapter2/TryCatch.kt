package chapter2

import java.io.BufferedReader
import java.io.StringReader
import java.lang.NumberFormatException

fun readNumber(reader: BufferedReader) {
	val number = try {
		Integer.parseInt(reader.readLine())
	} catch (e: NumberFormatException) {
		return
	}
	println(number)
}

fun main() {
	val reader = BufferedReader(StringReader("239"))
	println(readNumber(reader))
}