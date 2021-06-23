package chapter5

import javax.naming.Context

fun createViewWithCustomAttributes(context: Context) =
	TextView(context).apply {
		text = "sample"
		textSize = 20.0
		setPadding(10, 0, 0, 0)

	}

class TextView(
	context: Context,
	var text: String? = null,
	var textSize: Double? = null,
) {
	fun setPadding(right:Int, bottom: Int, left: Int, top: Int) {}
}

