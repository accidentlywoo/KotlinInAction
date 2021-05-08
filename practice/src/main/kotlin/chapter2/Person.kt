package chapter2

class Person(
	val name: String,
	var isMarried: Boolean
)

fun main(array: Array<String>) {
	val person = Person("Bob", true)
	println(person.name)

	println(person.isMarried) // getter 뚝딱

	person.isMarried = false // setter 뚝딱

	println(person.isMarried)
}