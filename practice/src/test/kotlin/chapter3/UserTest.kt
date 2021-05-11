package chapter3

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class UserTest {
	@Test
	fun `코드의 중복을 보여주는 예제1`() {
		assertThatThrownBy {
//			saveUser(User(1, "", ""))
			User(1, "", "").validateBeforeSave()
		}.isInstanceOf(IllegalArgumentException::class.java)
	}
}