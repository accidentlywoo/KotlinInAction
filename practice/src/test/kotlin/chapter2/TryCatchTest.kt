package chapter2

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.StringReader
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TryCatchTest{
	@Test
	fun `try 식으로 사용하기 평가` () {
		fun readNumber(reader: BufferedReader) {
			val number = try {
				Integer.parseInt(reader.readLine())
			} catch (e: NumberFormatException) {
				return
			}
			println("꺄잉")
		}
		readNumber(BufferedReader(StringReader("not a number")))
	}

	@Test
	fun `catch 값 반환하기`(){
		fun readNumber(reader: BufferedReader) {
			val number = try {
				Integer.parseInt(reader.readLine())
			} catch (e: NumberFormatException) {
				null
			}
			println(number)
		}

		assertEquals(kotlin.Unit, readNumber(BufferedReader(StringReader("not a number"))))
		// assertNull이아닌, 뭔 이상한걸로 비교하네
	}
}
