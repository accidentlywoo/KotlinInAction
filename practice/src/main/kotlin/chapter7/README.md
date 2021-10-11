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

``` a + b  -> a.plus(b)```

연산자를 멤버 함수로 만드는 대신 확장 함수로 정의할 수도 있다.

```kotlin
operator fun Point.plus(other: Point) : Point {
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
	return Point((x*scale).toInt(), (y*scale).toInt())
}

val p = Point(10, 20)
println(p*1.5)
// Point(x=15, y=30)
```

코틀린 연산자가 자동으로 교환 법칙 (a op b == b op a인 성질)을 지원하지는 않음에 유의하라.

사용자가 p * 1.5 외에 1.5 * p라고도 쓸 수 있어야 한다면 p * 1.5와 같은 식에 대응하는 연산자 함수인

```kotlin
operator fun Double.times(p: Point) : Point
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

 import java.awt.Pointoperator fun Point.unaryMinus(): Point {
	return Point(-x. -y)
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

## 3. 컬렉션과 범위에 대해 쓸 수 있는 관례

## 4. 구조 분해 선언과 component 함수

## 5. 프로퍼티 접근자 로직 재활용: 위임 프로퍼티