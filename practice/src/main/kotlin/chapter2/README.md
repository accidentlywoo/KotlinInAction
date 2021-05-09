# 코틀린 기초
1. [함수, 변수, 클래스, enum, 프로퍼티를 선언하는 방법]
2. 제어 구조
3. 스마트 캐스트
4. 예외 던지기와 예외 잡기

## 함수, 변수, 클래스, enum, 프로퍼티를 선언하는 방법
### 1. 함수와 변수
- 함수를 최상위 수준에 정의할 수 있다. : First Class Citizen 인가? HOF 인가? :grey_question:
- 배열도 일반적인 클래스와 마찬가지다. 코틀린에는 자바와 달리 배열 처리를 위한 문법이 따로 존재하지 않는다.:heart_eyes:
- 표준 자바 라이브러리 함수를 간결하게 사용할 수 있게 감싼 래퍼를 제공한다.:heart_eyes:
```
println("hi~")
```
- 세미콜론 안붙인다.붙여도 되네! :heart_eyes:

#### 1). 함수

> 코틀린과 자바의 큰차이점이라 느껴진 부분... 쩐다..!!
***문(statement)과 식(expression)의 구분***
- 코틀린에서 if는 식이지 문이 아니다.
```
print(if (1 > 2) 1 else 2)
```

- 자바에서는 모든 제어 구조가 문인 반면 코틀린에서는 루프를 제외한 대부분의 제어 구조가 식이다.
- 자바에서 대입문은 식이었으나 코틀린에서는 문이됐다.

java
```
String a = "aa";
System.out.println(a = "bb"); // bb
```

kotlin
```
val a = "aa";
// print(a = "bb") 컴파일 에러 
```

식이 본문인 함수의 반환 타입만 생략 가능하다. :heart_eyes:

블록이 본문인 함수가 값을 리턴할 때, 반드시 반환타입 지정!
 
#### 2). 변수

타입 지정을 생략가능.
```
val question = "머시꺵이 질문"

val number = 12
```

타입 명시 가능
```
val number: Int = 12
```

부동 소수점 상수를 사용한다면 변수 타입은 Double이 된다.

***변경 가능한 변수와 변경 불가능한 변수***
- val Immutable 참조 저장. 자바 final 키워드 붙은것과 같음 :heart_eyes:
- var mutable 참조 저장.

기본적으로 모든 변수는 val 키워드로 사용하고 꼭 필요할 때만 var 로 변경하자.

-> 함수형 코드를 위해..

var 키워드는 변수값을 변경할 수 있지만 변수의 타입은 고정돼 바뀌지 않는다.

신기한 타입추론

#### 3). 문자열 템플여러 스크립트 언어와 비숫하게 코틀린에서도 변수를 문자열 안에 사용할 수 있다.
변수를 {}로 쌈싸서 가독성을 높이자 

### 2. 클래스와 프로퍼티
#### 1). 프로퍼티
자바에서 필드와 접근제어자를 한데 묶어 프로퍼티(property)라고 부르며, 프로퍼티라는 개념을 활용하는 프레임워크가 많다.

코틀린은 프로퍼티를 언어 기본 기능으로 제공하며, 코틀린 프로퍼티는 자바의 필드와 접근자 메소드를 완전히 대신한다.

#### 2). 커스텀 접근자
일반적으로 클래스의 특성(프로퍼티)을 정의하고 싶다면 프로퍼티로 그 특성을 정의해야 한다.

#### 3). 디렉터리와 패키지
자바와 비슷.

패키지 이름 뒤에 .*를 추가하면 패키지 않의 모든 선언을 임포트할 수 있다.

패키지안에 있는 모든 클래스뿐 아니라 최상위에 정의된 함수나 프로파티까지 모두 불러온다는 점에 유의하라.

코틀린은 디스크상의 어느 디렉터리에 소스코드 파일을 위치시키든 관계없다.

자바와 같이 패키지별로 디렉터리를 구성하는 편이 낫다.

### 3. enum과 when
when 은 자바의 switch를 대치하되 훨씬 더 강력하며, 앞으로 더 자주 사용할 프로그래밍 요소라고 생각할 수 있다.

#### 1). when을 사용해 올바른 enum 값 찾기
```kotlin
fun getMnemonic(color: Color) =
	when (color) {
		Color.RED -> "Woo"
		Color.ORANGE -> "얼린쥐"
		else -> "?"
	}

fun main(args: Array<String>){
	println(getMnemonic(Color.ORANGE))
}
```
책과 다르게 when에서 else없으면 컴파일 에러남. 버전 차이인가? :grey_question:

- when과 임의의 객체를 함께 사용
```kotlin
fun mix (c1: Color, c2: Color) =
	when (setOf(c1, c2)) {
		setOf(RED, YELLOW) -> ORANGE
		setOf(BLUE, INDIGO) -> INDIGO
		else -> throw Exception("Dirty color")
	}
```
이 함수가 아주 자주 호출된다면 ,함수 인자로 주어진 두 색을 비교하기 위해 여러 Set 인스턴스를 생상헤, 불필요한 가비지 객체가 늘어난다.

인자가 없는 when 식을 사용하면 불필요한 객체 생성을 막을 수 있다.

```kotlin
fun mixOptimized(c1: Color, c2: Color) =
	when {
		(c1 == RED) && (c2 == ORANGE) -> ORANGE
		(c1 == BLUE) && (c2 == INDIGO) -> INDIGO
		else -> throw Exception("Dirty color")
	}
```



#### 2). 스마트 캐스트 : 타입 검사와 타입 캐스트를 조합
코틀린에서는 is를 사용해 변수 타입을 검사한다.:heart_eyes:

자바에서는 변수의 타입을 instanceof로 확인한 다음에 그 타입에 속한 멤버에 접근하기 위해서는 명시적으로 변수 타입을 캐스팅해야 한다.

이런 멤버 접근을 여러 번 수행해야 한다면 변수에 따로 캐스팅한 결결과를 저장한 후 사용해야 한다.

코틀린에서는 프로그래머 대신 컴파일러가 캐스팅을 해준다.

스마트 캐스트는 is로 변수에 든 값의 타입을 검사한 다음에 그 값이 바뀔 수 없는 경우에만 작동한다.

코틀린의 if가 값(식)을 만들어내기 때문에 삼항연산자가 따로없다.

- [코틀린 예제보러가기](./Expr.kt)

#### 3). if 와 when 의 분기에서 블록 사용
if나 when 모두 분기에 블록을 사용할 수 있다.

이 경우 블록의 마지막 문장이 블록 전체의 결과가 된다.

'블록의 마지막 식이 블록의 결과' 라는 규칙은 블록이 값을 만들어내야 하는 경우 항상 성립한다.

하지만 식이 본문인 함수는 블록을 본문으로 가질 수 없고 블록이 본문인 함수는 내부에 return문이 반드시 있어야 한다.

### 4. 이터레이션: while과 for 루프
코틀린 특성 중 자바와 가장 비슷한 것이 이터레이션이다.

for는 자바의 for-each 루프에 해당하는 형태만 존재한다.

코틀린의 for는 C#과 마찬가지로 for <아이템> in <원소들> 형태를 취한다.

#### 1). while 루프
코틀린에는 while과 do-while 루프가 있다. 두 루프의 문법은 자바와 다르지 않다.
```kotlin
while (조건) {
	/*...*/
}

do {
	/*...*/
} while (조건)
```

코틀린에서는 자바의 for 루프에 해당하는 요소가 없다.

이런 루프의 가장 흔한 용례인 초깃값, 증가 값, 최종 값을 사용한 루프를 대신하기 위해 코틀린에서는 범위를 사용한다.

범위는 기본적으로 두 값으로 이뤄진 구간이다. 보통은 그 두 값은 정수 등의 숫자 타입의 값이며,

***..*** 연산자로 시작 값과 끝 값을 연결해서 범위를 만든다

```kotlin
val oneToTen = 1..10
```

정수 범위로 수행할 수 있는 가장 단순한 작업은 범위에 속한 모든 값에 대한 이터레이션이다.

이런 식으로 어떤 범위에 속한 값을 일정한 순서로 이터레이션하는 경우를 수열리하고 부른다.

- 증가 값을 갖고 범위 이터레이션하기 

````kotlin
for (i in 100 downTo 1 step 2) {
    println(fizzBuzz(i))
}
````
step을 갖는 수열에 대해 이터레이션한다. 100 downTo 1은 역발향 수열을 만든다. (역방향 수열의 기본 증가 값은 -1이다.)

***..***는 항상 범위의 끝 값을 포함한다.

하지만 끝 값을 포함하지 않는 반만 닫힌 범위에 대해 이터레이션하면 편할때가 자주 있다.

나중에 downTo, step, until에 대해 더 자세히 다룬다.

- 맵에 대한 이터레이션
키를 사용햐 맵의 값을 가져오거나 키에 해당하는 값을 설정하는 멋진 코틀린 기능을 보여준다.
  
```kotlin
binaryReps[c] = binary
```

```java
binaryReps.put(c, binary)
```
위 두 코드는 같은 동작을 한다.

코틀린은 맵에서 사용했던 구조 분해 구문을 맵이 아닌 컬렉션에도 활용할 수 있따.

그런 구조 분해 구문을 사용하면 원소의 현재 인덱스를 유지하면서 컬렉션을 이터레이션할 수 있다.

- [맵, 리스트 콜렉션 예제보러 가기](/../../test/kotlin/chapter2/IterationTest.kt)

- in 으로 컬렉션이나 범위의 원소 검사 
in 연산자를 사용해 어떤 값이 범위에 속하는지 검사할 수 있다.
  
반대로 !in을 사용하면 어떤 값이 범위에 속하지 않는지 검사할 수 있다.

```
c in 'a'..'z' <- 'a' <= c && c <= 'z'로 변환
```
- [when에서 in사용하기](./WhenId.kt)

7.4.3 절에서 범위나 수열, 직접 만든 데이터 타입을 함께 사영하는 방법을 살펴본당





### 5. 코틀린의 예외 처리
val를 이용해서, 변수의 초기화 여부를 뭐식꺵이하는네 설명하다 말았다. 머선 129

초기화 안된상태에서 throw 식을 활용할 내용은 6.2.6절

- try - catch
자바와 마찬가지로 예외를 처리하려면 try와 catch, finally 절을 함께 사용한다.
  
자바 코드와 가장 큰 차이는 throws절이 코드에 없다는 점이다.

다른 최신 JVM 언어와 마찬가지로 코틀린도 체크 예외와 언체크 예외를 구별하지 않는다. :heart_eyes:

- try를 식으로 사용

코틀린의 try 키워드는 if 나 when 과 마찬가지로 식이다. 

```kotlin
fun readNumber(reader: BufferedReader) {
	val number = try {
		Integer.parseInt(reader.readLine())
	} catch (e: NumberFormatException) {
		return // flow 중단
	}
	println(number)
}
```

예외가 발생한 후 계속 진행하고 싶다면, catch 블록도 값을 만들어야 한다.

```kotlin
fun readNumber(reader: BufferedReader) {
	val number = try {
		Integer.parseInt(reader.readLine())
	} catch (e: NumberFormatException) {
		null
	}
	println(number)
}
```
- [예제보러 가기](/../../test/kotlin/chapter2/TryCatchTest.kt) :grey_question:
  :grey_question: : null이 null이아니고 kotlin.Unit 뭐식깽이로 나온다. 뭘까.?
  

