package chapter5

class Sequence {
}



fun main() {
	data class Person(val name: String, val age: Int)

	listOf(1, 2, 3, 4, 5).asSequence()
		.map { print("map($it)"); it * it }
		.filter { print("filter($it)"); it % 2 == 0 }

	listOf(1, 2, 3, 4, 5).asSequence()
		.map { print("map($it)"); it * it }
		.filter { print("filter($it)"); it % 2 == 0 }
		.toList()

	val people = listOf(Person("Alice", 29), Person("Maeve", 19))
	println(
		people.asSequence()
		.map(Person::name)
		.filter{ it.startsWith('A') }
		.toList()
	)
}