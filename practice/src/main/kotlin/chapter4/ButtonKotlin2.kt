package chapter4

class ButtonKotlin2 : View {
	override fun getCurrentState(): State {
		TODO("Not yet implemented")
	}

	inner class ButtonState : State {
		// this@Outer
	}
}