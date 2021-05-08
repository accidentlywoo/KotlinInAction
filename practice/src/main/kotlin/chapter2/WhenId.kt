package chapter2

fun recognoze(c: Char) = when (c) {
	in '0'..'9' -> "It's a digit!"
	in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
	else -> "I don't know"
}