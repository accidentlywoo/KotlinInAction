package chapter2

import chapter2.Color.*
import java.lang.Exception

fun getMnemonic(color: Color) =
	when (color) {
		RED -> "Woo"
		ORANGE -> "얼린쥐"
		else -> "?"
	}

fun getWarmth (color: Color) = when(color) {
	RED, ORANGE -> "warn"
	BLUE, INDIGO -> "cold"
	else -> "?"
}

fun mix (c1: Color, c2: Color) =
	when (setOf(c1, c2)) {
		setOf(RED, YELLOW) -> ORANGE
		setOf(BLUE, INDIGO) -> INDIGO
		else -> throw Exception("Dirty color")
	}

fun mixOptimized(c1: Color, c2: Color) =
	when {
		(c1 == RED) && (c2 == ORANGE) -> ORANGE
		(c1 == BLUE) && (c2 == INDIGO) -> INDIGO
		else -> throw Exception("Dirty color")
	}

fun main(args: Array<String>){
	println(getMnemonic(ORANGE))

	println(getWarmth(INDIGO))

	println(mix(BLUE, YELLOW))
}