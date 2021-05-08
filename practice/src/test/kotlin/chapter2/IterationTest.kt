package chapter2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class IterationTest{
	@Test
	fun `map` () {
		val binaryReps = TreeMap<Char, String>()

		for (c in 'A'..'F') {
			val binary = Integer.toBinaryString(c.toInt())
			binaryReps[c] = binary
		}

		for ((letter, binary) in binaryReps) {
			println("$letter = $binary")
		}
	}

	@Test
	fun `collection` () {
		val list = arrayListOf("10", "11", "1001")
		for ((index, element) in list.withIndex()) {
			println("$index: $element")
		}
	}

	@Test
	fun `in`() {
		fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
		fun isNotDigit(c: Char) = c !in '0'..'9'

		assertTrue(isLetter('q'))
		assertTrue(isNotDigit('q'))
	}

}
