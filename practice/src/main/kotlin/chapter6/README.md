# 코틀린 타입 시스템

1. 널 가능성
2. 코틀린 원시 타입
3. 컬렉션과 배열

코틀린의 타입 시스템은 코드의 가독성을 향상시키는 데 도움이 되는 몇 가지 특성을 제공한다.

그 특성으로 널이 될 수 있는 타입과 읽기 전용 컬렉션이 있다.

또한 코틀린은 자바 타입 시스템에서 불필요하거나 문제가 되던 부분을 제거했다.

배열 지원이 그런 예에 속한다.

## 1. 널 가능성
널 가능성은 NPE(NullPointerException)를 피할 수 있게 돕기 위한 코틀린 타입 시스템의 특성이다.

코틀린을 비롯한 최신 언어에서 null에 대한 접근 방법은 이 문제를 가능한 컴파일 시점으로 옮가는 것이다.

널이 될 수 있는지 여부를 타입 시트메에 추가함으로써 컴파일러가 여러 가지 오류를 컴파일 시 미리 감지해서 실행 시점에 발생할 수 있는 예외의 가능성을 줄일 수 있다.

### 1.1 널이 될 수 있는 타입
코틀린과 자바의 첫 번째이자 가장 중요한 차이는 코틀린 타입 시스템이 널이 될 수 있는 타입을 명시적으로 지원한다는 점이다.

어떤 변수가 널이 될 수 있다면 그 변수에 대해(그 변수를 수신 객체로) 메소드를 호출하면 NPE이 발생할 수 있으므로 안전하지 않다.

코틀린은 그런 메소드 호출을 금지함으로써 많은 오류를 방지한다.

```java
int stringLength(String string) {
    return string.length()
}
```
위 메소드는 안전한가? 이 함수에 null을 넘기면 NPE가 발생한다.

```kotlin
fun stringLength(string: String) = string.length
```
코틀린에서는 stringLength함수에 null이거나, null이 될 수 있는 인자를 넘기는 것은 금지되며, 혹시 그런 값을 넘기면 컴파일 시 오류가 발생한다.

따라서, stringLength함수가 결코 실행 시점에 NPE을 발생시키지 않으리라 장담할 수 있다.

 이 함수가 널과 문자열을 인자로 받을 수 있게 하려면 타입 이름 뒤에 물음표를 명시해야한다.

```kotlin
fun stringLength(string: String?) = string.length
// ERROR: only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type kotlin.String>
```

널이 됳 수 있는 타입의 변수가 있다면 그에 대해 수행할 수 있는 연산이 제한된다.

또, 널이 될 수 있는 값을 널이 될 수 없는 타입의 변수에 대입할 수 없다
```kotlin
val x: String? = null
val y: String = x
// ERROR: Type mismatch: inferred type is String? but string was expected
```
널 가능성을 다루기 위해 사용할 수 있는 도구가 of 검사뿐이라면 코드가 번잡해지는 일을 피할 수 없을 것이다.

다행히 코틀린은 널이 될 수 있는 값을 다룰 때 도움이 되는 여러 도구를 제공한다.

### 1.2 타입의 의미
위키피디아에서 타입은 "타입은 분류(classification)로 .. 타입은 어떤 값들이 가능한지와 그 타입에 대해 수행할 수 있는 연산의 종류를 결정한다."

자바의 타입 시스템은 null이 들어있는 경우에는 사용할 수 있는 연산이 많지 않다.

이는 자바의 타입 시스템이 널을 제대로 다루지 못한다는 뜻이다.

변수에 선언된 타입이 있지만 널 여부를 추가로 검사하기 전에는 그 변수에 대한 어떤 연산을 수행할 수 있을지 알 수 없다.

프로그램을 작성하면서 프로그램의 데이터 흐름속에서 특정 위치에 특정 변수가 절대로 널일 수 없다는 사실을 확인하고 이런 검사를 생략하는 경우가 자주 있다.

하지만 그 생각이 틀리면 실행 시점에서 프로그램이 NPE 예외를 발생시키며 오류로 중단된다.

 코틀린의 널이 될 수 있는 타입은 이런 문제에 대해 종합적인 해법을 제공한다.

널이 될 수 있는 타입과 널이 될 수 없는 타입을 구분하면 각 타입의 값에 대해 어떤 연산이 가능할지 명확히 이해할 수 있고, 

실행 시점에 예외를 발생시킬 수 있는 연산을 판단할 수 있다. 따라서 그런 연산을 아예 금지시킬 수 있다.

> 널이 될 수 있는 타입은 널이 될 수 없는 타입을 감싼 래퍼 타입이 아니다.
>
> 모든 검사는 컴파일 시점에 수향된다. 
> 
> 따라서 코틀린에서는 널이 될 수 있는 타입을 처리하는 데 별도의 실행 시점 부가 비용이 들지 않는다.

### 1.3 안전한 호출 연산자 ?.
?. 은 null 검사와 메소드 호출을 한 번의 연산으로 수행한다.

예를 들어 ```s?.toUpperCase()```는 훨씬 더 복잡한 ```if (s != null) s.toUppserCase() else null ```과 같다

안전한 호출의 결과 타입도 널이 될 수 있는 타입이라는 사실에 위의 하자.

```kotlin
fun printAllCaps(s: String?) {
	val allCaps: String? = s?.toUpperCase()
    println(allCaps)
}

// >> printAllCaps("abc")
// ABC
// >> printAllCaps(null)
// null
```
메소드 호출뿐 아니라 프로퍼티를 읽거나 쓸 때도 안전한 호출을 사용할 수 있다.

객체 그래프에서 널이 될 수 있눈 중간 객체가 여럿 있다면 한 식 안에서 안전한 호출을 연쇄해서 함께 사용하면 편할 때가 자주 있다.

```kotlin
class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
	val country = this.company?.address?.country
    return if (country != null) country else "Unknown" 
}
```

### 1.4 엘비스 연산자 ?:
null 대신 사용할 디폴트 값을 지정할 때 편리하게 사용할 수 있는 엘비스 연산자(?:)를 제공한다.

```kotlin
fun foo(string: String) {
	val t: String = s ?: ""
}
```

위의 사람과 회사, 주소를 표현한 코드에서 countryName 함수도 한줄로 표현할 수 있다.
```kotlin

fun Person.countryName() = company?.address?.country ?: "Unknown"
```

코틀린에서는 return이나 throw 등의 연산도 식이다.

따라서 엘비스 연산자의 우항에 return, throw 등의 연산을 넣을 수 있고, 엘비스 연산자를 더욱 편하게 사용할 수 있다.

이런 경우 엘비스 연산자의 좌항이 널이면 함수가 즉시 어떤 값을 반환하거나 예외를 던진다.

이런 패턴의 경우 함수의 전제 조건을 검사하는 경우 특히 유용하다.

### 1.5 안전한 캐스트 as?
as? 연산자는 어떤 값을 지정한 타입으로 캐스트한다. as?는 값을 대상 타입으로 변환할 수 없으면 null을 반환한다.

## 2. 코틀린의 원시 타입
## 3. 컬렉션과 배열
