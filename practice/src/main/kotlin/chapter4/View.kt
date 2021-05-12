package chapter4

import java.io.Serializable
import javax.naming.Context
import javax.print.attribute.AttributeSet

interface State: Serializable

interface View {
	fun getCurrentState() : State
	fun restoreState(state: State) {}
}