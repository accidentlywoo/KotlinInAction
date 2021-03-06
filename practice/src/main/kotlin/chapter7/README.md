# 연산자 오버로딩과 기타 관례

1. 산술 연산자 오버로딩
2. 비교 연산자 오버로딩
3. 컬렉션과 범위에 대해 쓸 수 있는 관례
4. 구조 분해 선언과 component 함수
5. 프로퍼티 접근자 로직 재활용: 위임 프로퍼티

언어의 기능을 타입에 의존하는 자바와 달리 코틀린은 (함수 이름을 통한) 관례에 의존한다.

코틀린에서 이런 관례를 채택한 이유는 기존 자바 클래스를 코틀린 언어에 적용하기 위함이다.

기존 자바 클래스가 구현하는 인터페이스는 이미 고정돼 있고 코틀린 쪽에서 자바 클래스가 새로운 인터페이스를 구현하게 만들 수는 없다.

반면 확장 함수를 사용하면 기존 클래스에 새로운 메소드를 추가할 수 있다.

따라서 기존 자바 코드를 바꾸지 않아도 새로운 기능을 쉽게 부여할 수 있다.

## 1. 산술 연산자 오버로딩

코틀린에서는 원시 타입뿐만아니라 BigInteger클래스, 컬렉션에 원소를 추가할는 경우에도 산술연산자를 사용할 수 있다.

### 1.1 이항 산술 연산 오버로딩

+ 연산자를 구현해보자.

```kotlin
data class Point(val x: Int, val y: Int) {
	operator fun plus(other: Point): Point {
		return Point(x + other.x, y + other.y)
	}
}

val p1 = Point(10, 20)
val p2 = Point(30, 40)
println(p1, p2)
// Point(x=40, y=60)
```

plus 함수 앞에 operator 키워드를 붙여야 한다.

연산자를 오버로딩하는 함수 앞에는 꼭 operator가 있어야 한다. :pushpin:

operator가 없는데 실수로 관례에서 사용하는 함수이름을 쓰고 우연히 그 이름에 해당하는 기능을 사용한다면

"operator modifier is required..." 오류를 통해 이름이 겹쳤다는 사실을 알고 문제를 해결할 수 있다.

operator 변경사를 추가해 plus 함수를 선언하고 나면 + 기호로 두 Point 객체를 더할 수 있다.

``` a + b -> a.plus(b)```

연산자를 멤버 함수로 만드는 대신 확장 함수로 정의할 수도 있다.

```kotlin
operator fun Point.plus(other: Point): Point {
	return Point(x + other.x, y + other.y)
}
```

코틀린에서 정의할 수 있는 이항 연산자와 그에 상응하는 연산자 함수 이름을 보여준다.

| 식 | 함수 이름 |
|------|-----------|
| a * b | times |
| a / b | div |
| a % b | mod(1.1부터 rem) |
| a + b | plus |
| a - b | minus |

직접 정의한 함수를 통해 구현하더라도 연산자 우선순위는 언제나 표준 숫자 타입에 대한 연산자 우선순위와 같다.

연산자를 정의할 때 두 피연산자가 같은 타입일 필요는 없다.

예를들어 확대/축소하는 연산자를 정의해보자.

```kotlin
operator fun Point.times(scale: Double): Point {
	return Point((x * scale).toInt(), (y * scale).toInt())
}

val p = Point(10, 20)
println(p * 1.5)
// Point(x=15, y=30)
```

코틀린 연산자가 자동으로 교환 법칙 (a op b == b op a인 성질)을 지원하지는 않음에 유의하라.

사용자가 p * 1.5 외에 1.5 * p라고도 쓸 수 있어야 한다면 p * 1.5와 같은 식에 대응하는 연산자 함수인

```kotlin
operator fun Double.times(p: Point): Point
```

를 더 정의해야 한다.

비트 연산자에 대해 특별한 연산자 함수를 사용하지 않는다.

### 1.2 복합 대입 연산자 오버로딩

plus와 같은 연산자를 오버로딩하면 코들린은 + 연산자뿐 아니라 그와 관련 있는 연산자인 +=도 자동으로 함꼐 지원한다.

+=, -= 등의 연산자는 복합 대입 연산자라 불린다.

경우에 따라 += 연산이 객체에 대한 참조를 다른 참조로 바꾸기보다 원래 객체의 내부 상태를 변경하게 만들고 싶을 때가 있다.

변경 가능한 컬렉션에 원소를 추가하는 경우가 대표적인 예다.

```kotlin
val numbers = ArrayList<Int>()
numbers += 42
println(numbers[0])
```

반환 타입이 Unit인 plusAssign 함수를 정의하면 코틀린은 += 연산자에 그 함수를 사용한다.

다른 복합 대입 연산자 함수도 비슷하게 minusAssign, timesAssign 등의 이름을 사용한다.

코틀린 표준 라이브러리는 변경 가능한 컬렉션에 대해 plusAssign을 정의하며, 앞의 예제는 그 plusAssign을 사용한다.

```kotlin
operator fun <T> MutableCollection<T>.plusAssign(element: T) {
	this.add(element)
}
```

일반적으로 새로운 클래스를 일관성 있게 설계하는 게 가장 좋다.

plus와 plusAssign 연산을 동시에 정의하지 말라.

클래스가 앞에서 본 Point처럼 변경 불가능하다면 plus와 같이 새로운 값을 반환라는 연산만 추가해야 한다.

빌더와 같이 변경 가능한 클래스를 설계한다면 plusAssign이나 그와 비슷한 연산만 제공하라.

코틀린 표준 라이브러리는 컬렉션에 대해 두 가지 접근 방법을 함께 제공한다.

+, - 는 항상 새로운 컬렉션을 반환하며,

+=, -= 는 변경 가능한 컬렉션에 적용해 메모리에 있는 객체 상태를 변화시킨다.

또한 읽기 전용 컬렉션에서 +=, -=는 변경을 적용한 복사본을 반환한다. (그래서 var로 선언한 변수만 사용 가능)

이런 연산자의 피연산자로는 개별 원소를 사용하거나 원소 타입이 일치하는 다른 컬렉션을 사용할 수 있다.

### 1.3 단항 연산자 오버로딩

단항 연산자를 오버로딩하는 절타도 이항 연산자와 마찬가지다.

미리 정해진 이름의 함수를 멤버나 확장함수로 선언하면서 operator로 표시하면 된다.

```kotlin

import java.awt.Pointoperator

fun Point.unaryMinus(): Point {
	return Point(-x.- y)
}

val p = Point(10, 20)
println(-p)
// Point(-10, -20)
```

단항 연산자를 오버로딩하기 위해 사용하는 함수는 인자를 취하지 않는다.

| 식 | 함수 이름 |
|-----|---------|
| +a | unaryPlus |
| -a | unaryMinus |
| !a | not |
| ++a, a++ | inc |
| --a, a-- | dec |

inc나 dec 함수를 정의해 증가/감소 연산자를 오버로딩하는 경우 컴파일러는 일반적인 값에 대한 전위와 후위 증가/감소 연산자와 같은 의미를 제공한다.

```kotlin
operator fun BigDecimal.inc() = this + BigDecimal.ONE

var bd = BigDecimal.ZERO
print(bd++) // 0
print(++bd) // 1
```

## 2. 비교 연산자 오버로딩

코틀린에서는 == 비교 연산자를 직접 사용할 수 있어서 비교 코드가 간결하며 이해하기 쉽다.

### 2.1 동등성 연산자: equals

코틀린이 == 연산자 호출을 equals 메소드 호출로 컴파일한다는 사실을 배웠다.

직접 equals를 구현한다면,

```kotlin
class Point(val x: Int, val y: Int) {
	override fun equals(obj: Any?): Boolean {
		if (obj == this) return true
		if (obj !is Point) return false
		return obj.x == x && obj.y == y
	}
}

println(Point(10, 20) == Point(10, 20))
// true
println(Point(10, 20) != Point(5, 5))
// true
println(null == Point(1, 2))
// false
```

식별자 비교 연산자 (===)를 사용해 서로 같은 객체를 가르키는지 비교한다.

=== 를 오버로딩할 수는 없다는 사실을 기억하라.

equals 함수에는 override가 붙어있다.

다른 연산자 오버로딩 관례와 달리 equals는 Any에 정의된 메소드이므로 override가 필요하다 :grey_question:

Any의 equals에서 operator가 붙어있지만 그 메소드를 오버라이드하는 메소드 앞에는 operator 변경자를 붙이지 않아도 자동으로 상위 클래스의 operator 지정이 적용된다.

또한 Any에서 상속받은 equlas가 확장 함수보다 우선순위가 높기 떄문에 equals를 확장 함수로 정의할 수는 없다는 사실에 유의하라.:pushpin:

### 2.2 순서 연산자: compareTo

코틀린도 자바와 같이 Comparable 인터페이스를 지원한다.

Comparable 인터페이스 안에 있는 compareTo 메소드를 호출하는 관례를 제공한다.

비교 연산자( <, >, <=, >= )는 compareTo 호출로 컴파일된다.

compareTo가 반환하는 값은 Int다.

Person을 비교할 때는 주소록 순서를 사용한다.

```kotlin
class Person(
	val firstName: String, val lastName: String
) : Comparable<Person> {
	override fun compareTo(other: Person): Int {
		return compareValuesBy(this, other, Person::lastName, Person::firstName)
	}
}

val p1 = Person("Alice", "Smith")
val p2 = Person("Bob", "Johnson")
```

Person 객체의 Comparable 인터페이스를 코틀린뿐 아니라 자바 쪽의 컬렉션 정렬 메소드 등에도 사용할 수 있다.

equals와 마찬가지로 Comparable의 compareTo에도 operator 변경자가 붙어있으므로 하위 클래스의 오버라이딩 함수에 operator를 붙일 필요가 없다.

위 예제는 compareValuesBy 함수를 사용해 compareTo를 쉽고 간결하게 정의할 수 있음을 보여준다.

Comparable 인터페이스를 구현하는 모든 자바 클래스를 코틀린에서는 간결한 연산자 구문으로 비교할 수 있다.

## 3. 컬렉션과 범위에 대해 쓸 수 있는 관례

컬렉션을 다룰 때 가장 많이 쓰는 연산은 인덱스를 사용해 원소를 읽거나 쓰는 연산과 어떤 값이 컬렉션에 속해있는지 검사하는 연산이다.

이 모든 연산을 연산자 구문으로 사용할 수 있다.

인덱스를 사용해 원소를 설정하거나 가져오고 싶을 때는 a[b]라는 식을 사용한다.(인덱스 연산자라고 부름)

in 연산자는 원소가 컬렉션이나 범위에 속하는지 검사하거나 컬렉션에 있는 원소를 이터레이션할 때 사용한다.

사용자 지정 클래스에 이런 연산을 추가할 수 있다.

### 3.1 인덱스로 원소에 접근: get과 set

인덱스 연산자를 사용해 원소를 읽는 연산은 get 연산자 메소드로 변환되고,

원소를 대입하는 연산은 set연산자 메소드로 변환된다.

```kotlin

import java.lang.IndexOutOfBoundsExceptionoperator

fun Point.get(index: Int): Int {
	return when (index) {
		0 -> x
		1 -> y
		else ->
			throw java.lang.IndexOutOfBoundsException("Invalid coordinate $index")
	}
}

val p = Point(10, 20)
println(p[1]) // 20
```

인덱스에 해당하는 컬렉션 원소에 값을 대입하고 싶을 때는 set이라는 이름의 함수를 정의하면 된다.

```kotlin

import java.lang.IndexOutOfBoundsExceptiondata

class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int) {
	when (index) {
		0 -> x = value
		1 -> y = value
		else ->
			throw IndexOutOfBoundsException("Invalid coordinate $index")
	}
}
val p = MutablePoint(10, 20)
p[1] = 42
println(p)
MutablePoint(x = 10, y = 42)
```

### 3.2 in 관례

in은 객체가 컬렉션에 들어있는지 검사(멤버십 검사)한다.

이런 경우 in 연산자와 대응하는 함수를 contains다.

어떤 점이 사각형 영역에 들어가는지 판단하는 코드

```kotlin
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
	return p.x in upperLeft.x until lowerRight.x &&
			p.y in upperLeft.y until lowerRight.y
}

val rect = Rectangle(Point(10, 20), Point(50, 50))

println(Point(20, 30) in rect) // true

println(Point(5, 5) in rect) // false
```

### 3.3 rangeTo 관례

범위를 만들려면 .. 구문을 사용해야한다.

.. 연산자는 rangeTo함수를 간략하게 표현하는 방법이다.

rangeTo 함수는 아무 클래스에나 정의할 수 있다.

하지만 어떤 클래스가 Comparable 인터페이스를 구현하면 rangeTo를 정의할 필요가 없다.

코틀린 표준 라이브러리에는 모든 Comparable 객체에 대해 적용 가능한 rangeTo 함수가 들어있다.

```kotlin
operator fun <T : Comparable<T>> T.rangeTo(that: T): ClosedRange<T>
```

이 함수는 범위를 반환하며, 어떤 원소가 그 범위 안에 들어있는지 in을 통해 검사할 수 있다.

- 날짜 범위 다루기

```kotlin
val now = LocalDate.now()
val vacation = now..now.plusDays(10)
println(now.plusWeeks(1) in vacation) // true
```

rangeTo 함수는 LocalDate의 멤버는 아니며, Comparable에 대한 확장 함수다.

rangeTo 연산자는 다른 산술 연산자보다 우선순위가 낮다.

하지만, 혼동을 피하기 위해 괄호로 인자를 쌈싸주면 더 좋다.

```kotlin
println(0..(n + 1))
```

또한 0..n.forEach() 와 같은 식은 컴파일할 수 없음에 유의하라.

범위 연산자는 우선 순위가 낮아서 범위의 메소드를 호출하려면 범위를 괄호로 둘러싸야 한다.

```kotlin
(0..n).forEach { print(it) }
```

### 3.4 for 루프를 위한 iterator 관례

코틀린의 for 루프는 범위 검사와 똑같이 in 연산자를 사용한다.

for( x in list) { ... } 와 같은 문장은 list.iterator()를 호출해서 이터레이터를 얻은 다음,

자바와 마찬가지로 그 이터레이터에 대해 hasNext와 next 호출을 반복하는 식으로 변환된다.

코틀린에서는 iterator 메소드를 확장 함수로 정의할 수 있다.

코틀린 표준 라이브러리는 String의 상위 클래스인 CharSequence에 대한 iterator 확장 함수를 제공한다.

```kotlin
operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
	object : Iterator<LocalDate> {
		var current = start
		override fun hasNext() =
			current <= endInclusive
		override fun next() = current.apply {
			current = plusDays(1)
		}
	}

val nextYear = LocalDate.ofYearDay(2017, 1)
val daysOff = newYear.minusDays(1)..newYear
for (dayOff in daysOff) {
	println(dayOff)
}
// 2016-12-31
// 2017-01-01
```

## 4. 구조 분해 선언과 component 함수

구조 분해를 사용하면 복합적인 값을 분해해서 여러 다른 변수를 한꺼번에 초기화할 수 있다.

```kotlin
val p = Point(10, 20)
val (x, y) = p
println(x) // 10
println(y) // 20
```

내부에서 구조 분해 선언은 다시 관례를 사용한다.

구조 분해 선언의 각 변수를 초기화하기 위해 componentN이라는 함수를 호출한다.

여기서 N은 구조 분해 선언에 있는 변수 위치에 따라 붙는 번호다.

위 예제는 아래와 같이 컴파일된다.

```kotlin
val x = p.component1()
val y = p.component2()
```

data 클래스의 주 생성자에 들어있는 프로퍼티에 대해서는 컴파일러가 자동으로 componentN 함수를 만들어준다.

data 타입이 아닌 클래스에서 이런 함수를 어떻게 구현하는지 보여준다.

```kotlin
class Point(val x: Int, val y: Int) {
	operator fun component1() = x
	operator fun component2() = y
}
```

구조 분해 선언은 함수에서 여러 값을 반환할 때 유용하다.

파일 이름을 이름과 확장자로 나누는 코드를 보자

```kotlin
data class NameComponents(val name: String, val extension: String)

fun splitFilename(fullname: String): NameComponets {
	val result = fullName.split('.', limit = 2)
	return NameComponents(result[0], result[1])
}

val (name, ext) = splitFilename("example.kt")
println(name) // example
println(ext) // kt
```

배열이나 컬렉션에도 componentN 함수가 있음을 안다면 이 예제를 더 개선할 수 있다.

```kotlin
data class NameComponents(val name: String, val extension: String)

fun splitFilename(fullName: String): NameComponents {
	val (name, extention) = fullName.split('.', limit = 2)
    return NameComponents(name, extention)
}
```
코틀린 표준 라이브러리에서는 맨 앞의 다섯 원소에 대한 componentN을 제공한다.

 표준 라이브러리의 Pair나 Triple 클래스를 사용하면 함수에서 여러 값을 더 간단하게 반환할 수 있다.

Pair와 Triple은 그 안에 담겨있는 원소의 의미를 말해주지 않으므로 경우에 따라 가독성이 떨어질 수 있는 반면,

직접 클래스를 작성할 필요가 없으므로 코드는 더 단순해진다.

### 4.1 구조 분해 선언과 루프
함수 본문 내의 선언문뿐 아니라 변수 선언이 들어갈 수 있는 장소라면 어디든 구조 분해 선언을 사용할 수 있다.

```kotlin
fun printEntries(map: Map<String, String>) {
	for ((ket, value) in map) {
		print("$key -> $value")
    }
}

val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")

printEntries(map)
// Oracle -> Java
// JetBrains -> Kotlin
```
코틀린 표준 라이브러리에는 맵에 대한 확장 함수로 iterator가 들어있다.

그 iterator는 맵 원소에 대한 이터레이터를 반환한다.

또한, 코틀린 라이브러리는 Map.Entry에 대한 확장 함수로 component1과 component2를 제공한다.

## 5. 프로퍼티 접근자 로직 재활용: 위임 프로퍼티