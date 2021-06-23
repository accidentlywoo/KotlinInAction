package chapter5

import java.lang.StringBuilder

fun alphabet(): String {
	val result = StringBuilder()
	for (letter in 'A'..'Z') {
		result.append(letter)
	}
	result.append("\nNow I Know the alphabet!")
	return result.toString()
}

fun alphabetWith(): String {
	val result = StringBuilder()
	return with(result) { // 메소드를 호출하려는 수신 객체를 지정한다.
		for (letter in 'A'..'Z') {
			this.append(letter) // this를 명시해서 앞에서 지정한 수신 객체의 메소드를 호출한다.
		}
		this.append("\nNow I Know the alphabet!") // this 를 생략하고 메소드를 호출한다.
		this.toString() // 람다에서 값을 반환한다.
	}
}

fun alphabetWith2(): String {
	val result = StringBuilder()
	return with(result) { // 메소드를 호출하려는 수신 객체를 지정한다.
		for (letter in 'A'..'Z') {
			append(letter) // this를 명시해서 앞에서 지정한 수신 객체의 메소드를 호출한다.
		}
		append("\nNow I Know the alphabet!") // this 를 생략하고 메소드를 호출한다.
		toString() // 람다에서 값을 반환한다.
	}
}

fun alphabetSimple() = buildString {
	for (letter in 'A'..'Z') {
		append(letter)
	}

	append("\nNow I Know the alphabet!")
}

fun main() {
	println(alphabet())
	println(alphabetWith())
	println(alphabetWith2())
}