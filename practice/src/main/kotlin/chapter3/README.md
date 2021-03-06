# 함수 정의와 호출
1. 컬렉션, 문자열, 정규식을 다루기 위한 함수
2. 이름 붙인 인자, 디폴트 파라미터 값, 중위 호출 문법 사용
3. 확장 함수와 확장 프로퍼티를 사용해 자바 라이브러리 적용
4. 최상위 및 로컬 함수와 프로퍼티를 사용해 코드 구조화

## 1. 코틀린에서 컬렉션 만들기
- [예제보러가기](./Collection.kt)

코틀린이 자신만의 컬렉션 기능을 제공하지 않는다는 뜻이다.

코틀린이 자체 컬렉션을 제공하지 않는 이유는 뭘까?

표준 자바 컬렉션을 활용하면 자바 코드와 상호작용하기 훨씬 더 쉽다.

코틀린 콜렉션은 자바 컬렉션과 똑같은 클래스지만, 자바보다 더 많은 기능을 제공한다.  

## 2. 함수를 호출하기 쉽게 만들기
자바 컬렉션에는 디폴트 toString 구현이 들어있다.

코틀린은 출력시 기본으로 toString 형식으로 출력한다.

```kotlin
val list = listOf(1, 2, 3)
println(list)
```

```java
class Arrays {
//    ...
    public static String toString(long[] a) {
        if (a == null)
        return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
        return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
        b.append(a[i]);
        if (i == iMax)
        return b.append(']').toString();
        b.append(", ");
        }
    }
}
```

출력값을 개발자 마음대로 수정하고 싶을떈, 어떻게 해야할까?

또, 코틀린으로 작성한 함수를 호출할 때는 함수에 전달하는 인자 중 일부(또는 전부)의 이름을 명시할 수 있다. :heart_eyes:

또, 코틀린은 함수 선언에서 파라미터의 디폴트 값을 지정할 수 있다. :heart_eyes:

- [예제보러가기](./joinToString.kt)

일반 호출 문법을 사용하려면 함수를 선언할 때와 같은 순서로 인자를 지정해야 한다.

- 디폴트 값과 자바
자바에는 디폴트 파라미터 값이라는 개념이 없어서 코틀린 함수를 자바에서 호출하는 경우에는 그 코틀린 함수가 디폴트 파라미터 값을 제공하더라도 모든 인자를 명시해야 한다.:pushpin:
  
@JvmOverloads 애노테이션을 함수에 추가하면, 코틀린 컴파일러가 자동으로 맨 마지막 파라미터로부터 파라미터를 하나씩 생략한 오버로딩한 자바 메소드를 추가해준다.

코틀린에서는 함수를 클래스 안에 선언할 필요가 전혀 없다. :heart_eyes:

- 정적인 유틸리티 클래스 없애기 : 최상위 함수와 프로퍼티
자바는 함수가 없다. 클래스가 있어야하는 메소드 뿐
  , JDK의 Collections 클래스 같이 Util이 들어있는 클래스를 찾아보자..
  
코틀린에서는 무의미한 클래스가 필요 없다. 대신 함수를 직접 소스 파일의 최상위 수준, 모든 다른 클래스의 밖에 위치시키면 된다. :heart_eyes:

코틀린 컴파일러가 생성하는 클래스의 이름은 최상위 함수가 들어있던 코틀린 소스 파일의 이름과 대응한다.

-> 직접 클래스명을 지정하고 싶다면? @file:JvmName 사용하기 [예제소스보러가기](./join.kt)

- 최상위 프로퍼티
어떤 데이터를 클래스 밖에 위치시키면 정적 필드에 저장되는 최상위 프로퍼티가 생성된다.
  
## 3. 메소드를 다른 클래스에 추가 : 확장 함수와 확장 프로퍼티
PASS

## 4. 컬렉션 처리 : 가변 길이 인자, 중위 함수 호출, 라이브러리 지원
- vararg 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 함수를 정의할 수 있다.:heart_eyes:
- 중위함수 호출 구문을 사용하면 인자가 하나뿐인 메소드를 간편하게 호출할 수 있다.:grey_question:
- 구조 분해 선언을 사용하면 복합적인 값을 분해해서 여러 변수에 나눠 담을 수 있다.:heart_eyes:

### 1). 자바 컬렉션 API 확장
앞에서 코틀린 컬렉션은 자바와 같은 클래스를 사용하지만 더 확장된 API를 제공한다.

예시로 봤던 코드
```kotlin
val strings: List<String> = listOf("first", "second", "fourteenth")

println(strings.last())

val numbers: Collection<Int> = setOf(1, 14, 2)

println(numbers.maxOrNull())
```
위의 last와 max 모두 확장함수 였다.

코틀린 표준 라이브러리에서 제공하는 확장함수는 IDE를 통해 골라서 써라.:heart_eyes:

### 2). 가변 인자 함수: 인자의 개수가 달라질 수 있는 함수 정의
리스트를 생성하는 함수를 호출할 때 원하는 만큼 많이 원소를 전달할 수 있다.

```kotlin
val list = listOf(2, 3, 5, 7, 11)
```
라이브러리에서 이 함수의 정으를 보면 다음과 같다

```kotlin
fun listOf<T>(vararg value: T): List<T> {...}
```
코틀린은 가변길이 인자의 문법이 조금 다르다. 

타입 뒤에 ...를 붙이는 대신 코틀린에서는 파라미터 앞에 ***vararg*** 변경자를 붙인다.

이미 배열에 들어있는 원소를 가변 길이 인자로 넘길 때도 코틀린과 자바 구문이 다르다.

자바에서는 배열을 그냥 넘기면 되지만 코틀린에서는 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되게 해야한다.

스프레드 연산자가 그런 작업을 해준다.

```kotlin
fun main(args: Array<String>) { 
  val list = listOf("args: ", *args)
  println(list)
}
```

:grey_question: 파이썬이랑 똑같네, 근데 왜 펼처서 보내야됬더라.. 까먹음.. :grey_question:

### 3). 값의 쌍 다루기: 중위 호출과 구조 분해 선언
맵을 만들려면 mapOf 함수를 사용한다.

```kotlin
val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```

여기서 ***to*** 라는 키워드는 코틀린 키워드가 아니다.

이 코드는 중위 호출(infix call)이라는 특별한 방식으로 to 라는 일반 메소드를 호출한 것이다.

중위 호출 시에는 수신 객체와 유일한 메소드 인자 사이에 메소드 이름을 넣는다.:grey_question:
(이때 객체, 메소드 이름, 유일한 인자 사이에는 공백이 들어가야 한다.)

다음 두 호출은 동일하다
```kotlin
1.to("one")

2 to "one"
```

인자가 하나뿐인 일반 메소드나 인자가 하나뿐인 확장 함수에 중위 호출을 사용할 수 있다.

함수를 중위 호출에 사용하게 허용하고 싶으면 infix 변경자를 함수 선언 앞에 추가한다.

다음은 to 함수의 정의를 간략하게 줄인 코드다.

```kotlin
infix fun Any.to(other: Any) = Pair(this, other)
```
이 to 함수는 Pair의 인스턴스를 반환한다. Pair는 코틀린 표준 라이브러리 클래스로, 그 이름대로 두 원소로 이뤄진 순서쌍을 표현한다.

실제로 to는 제네릭 함수지만 여기서는 설명을 위해 그런 세부 사항을 생략한다.

```kotlin
val (number, name) = 1 to "one"
```

이런 기능을 구조 분해 선언이라고 부른다. 

루프에서도 구조 분해 선언을 활용할 수 있따.

```kotlin
for ((index, element) in collection.withIndex()) {
	println("$index: $element")
}
```

7.4절에서는 식의 구조 분해와 구조 분해를 사용해 여러 변수를 초기화하는 방법에 대한 일반 규칙을 다룬다.

## 5. 문자열과 정규식 다루기
코틀린은 다양한 확장 함수를 제공함으로써 표준 자바 문자열을 더 즐겁게 다루게 해준다.

또한 혼동이 야기될 수 있는 일부 메소드에 대해 더 명확한 코틀린 확장 함수를 제공함으로써 프로그래머의 실수를 줄여준다.

### 1). 문자열 나누기
자바 split 메소드는 정규식을 사용해서, 개발자의 기대와 다른 동작을 할 수 있다. (split(".")을 하면 빈문자열)

코틀린에서는 자바 split 대신에 여러 가지 다른 조합의 파라미터를 받는 split 확장함수를 제공함으로써 혼동을 야기하는 메소드를 감준다.:heart_eyes:

```kotlin
println("12.345-6.A".split("\\.|-".toRegex()))
```
자바 split메소드처럼 정규식을 사용할 때, 위처럼 toRegex 확장함수를 사용한다.

```kotlin
println("12.345-6.A".split(".", "-"))
```
위처럼 간단한 문자열 기준으로 쪼갤땐, 위처럼 쓴다.

- [String 확장 함수를 사용해 경로 파싱하기](./PathParsingUsingStringExtension.kt)

## 6. 코드 다듬기: 로컬 함수와 확장
자바에서 DRY(Don't Repeat Yourself)를 위해 메소드를 나눠 각 부분을 재활용할 수 있다.

하지만, 클래스 안에 작은 메소드가 많아지고, 각 메소드 사이의 관계를 파악하기 힘들어서 코드를 이해하기 어려워질 수도 있다.

내부 클래스를 구성해, 코드를 깔끔하기 조직할 수는 있지만, 그에 따른 불필요한 준비 코드가 늘어난다.

코틀린은 함수에서 추출한 함수를 원 함수내부에 중첩시킬 수 있다.:heart_eyes:

그렇게하면 문법적인 부가 비용이 들이지 않고도 깔끔하기 코드를 조작할 수 있다.

- [예제 코드 보기(테스트 코드와 진화 과정 보기)](./User.kt)

중첩된 함수의 깊이가 깊어지면 코드를 읽기가 상당히 어려워진다. 

따라서 :green_book: 일반적으로 한 단계만 함수를 준첩시키라고 권장한다.

