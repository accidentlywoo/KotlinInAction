package chapter5

fun printMessagesWithPrefix(message: Collection<String>, prefix: String) {
	message.forEach{
		println("${prefix} $it")
	}
}

fun printProblemCounts(response: Collection<String>) {
	var clientErrors = 0
	var serverErrors = 0
	response.forEach {
		if (it.startsWith("4")) {
			clientErrors++ // 람다 안에서 암다 밖의 변수를 변경한다.
		} else if (it.startsWith("5")) {
			serverErrors++
		}
	}

	println("${clientErrors} client errors, ${serverErrors} server errors")
}

fun main() {
	val errors = listOf("403 Forbidden", "404 Not Found")
	printMessagesWithPrefix(errors, "Error: ")

	val response = listOf("200 OK", "418 I', a teapot")
	printProblemCounts(response)

	fun maeve() = println("maeve")
	run(::maeve)

	val createPerson = ::Person
	val p = createPerson("maeve", 20)
	println(p)
}