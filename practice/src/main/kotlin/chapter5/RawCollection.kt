package chapter5

fun findTheOldest(people: List<Person>) {
	var maxAge = 0
	var theOldest: Person? = null
	for (person in people) {
		if (person.age > maxAge) {
			maxAge = person.age
			theOldest = person
		}
	}
	println(theOldest)
}

fun main() {
	val people = listOf<Person>(Person("Maboo", 29), Person("Bob", 38))
	findTheOldest(people)

	println(people.maxByOrNull { it.age })
}