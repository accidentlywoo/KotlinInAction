package chapter4

class Outer {
	inner class Inner {
		fun getOuterReeference() : Outer = this@Outer
	}
}