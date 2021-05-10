package chapter3

import java.lang.IllegalArgumentException

class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User) {

	fun validate(user: User, value: String, fieldName: String) {
		if (value.isEmpty())
			throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
	}

	validate(user, user.name, "Name")
	validate(user, user.address, "Address")

}
