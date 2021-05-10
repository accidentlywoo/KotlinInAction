package chapter3

fun parsePath(path: String) {
	val directory = path.substringBeforeLast("/")
	val fullName = path.substringAfterLast("/")
	val fileName = fullName.substringBeforeLast(".")
	val extension = fullName.substringAfter(".")

	println("Dir: $directory, name: $fileName, ext: $extension")
}

fun parseRegPath(path: String){
	val regex = """(.+)/(.+)\.(.+)""".toRegex()
	val matchResult = regex.matchEntire(path)
	if (matchResult != null) {
		val (directory, filename, extension) = matchResult.destructured
		println("Dir: $directory, name: $filename, ext: $extension")
	}
}

fun main() {
	val path = "/Users/woo/kotlin/chapter.adoc"
	parsePath(path)

	parseRegPath(path)
}