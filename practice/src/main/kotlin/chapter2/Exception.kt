package chapter2

import java.lang.IllegalArgumentException

fun exception_example1 (number: Int) {
	val percentage =
		if (number in 0..100)
			number
		else
			throw IllegalArgumentException(
				"A percentage value must be between 0 and 100: $number")

	if(percentage !in 0..100) {
		throw IllegalArgumentException(
			"A percentage value must be between 0 and 100 : $percentage")
	}
}