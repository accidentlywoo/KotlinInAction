package chapter4

abstract class Animate {
	abstract fun animate()

	open fun stopAnimate() {} // 비 추상 메소드는 기본 final

	fun animateTwice() {}
}