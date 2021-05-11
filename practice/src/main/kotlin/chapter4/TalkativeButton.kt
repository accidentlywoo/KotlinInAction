package chapter4

internal open class TalkativeButton : Focusable {
//	private fun yell() = println("Hey!")
//	protected fun whisper() = println("Let's talk")
	fun yell() = println("Hey!")
	fun whisper() = println("Let's talk")

}

internal fun TalkativeButton.giveSpeech() {
	yell()

	whisper()
}