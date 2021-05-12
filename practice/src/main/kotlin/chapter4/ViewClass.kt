package chapter4

import javax.naming.Context
import javax.print.attribute.AttributeSet

open class ViewClass {
	constructor(ctx: Context) {}
	constructor(ctx: Context, attr: AttributeSet) {}
}