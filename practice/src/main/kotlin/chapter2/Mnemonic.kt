package chapter2

fun getMnemonic(color: Color) =
	when (color) {
		Color.RED -> "Woo"
		Color.ORANGE -> "얼린쥐"
		else -> "?"
	}

fun getWarmth (color: Color) = when(color) {
	Color.RED, Color.ORANGE -> "warn"
	Color.BLUE, Color.INDIGO -> "cold"
	else -> "?"
}

fun main(args: Array<String>){
	println(getMnemonic(Color.ORANGE))

	println(getWarmth(Color.INDIGO))
}