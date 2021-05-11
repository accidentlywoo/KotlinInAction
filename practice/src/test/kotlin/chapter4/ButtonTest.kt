package chapter4

import org.junit.jupiter.api.Test

class ButtonTest {
	@Test
	fun `click`() {
		val button = Button()
		button.showOff()
		button.setFocus(true)
		button.click()
	}
}
