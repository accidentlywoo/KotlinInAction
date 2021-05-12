package chapter4

class ButtonKotlin : View {
	override fun getCurrentState(): State = ButtonState()

	override fun restoreState(state: State) { /*.. */ }

	class ButtonState : State { /*..*/ } // default static
}