# 람다로 프로그래밍
1. 람다 식과 멤버 참조
2. 함수형 스타일로 컬렉션 다루기
3. 시퀀스: 지연 컬렉션 연산
4. 자바 함수형 인터페이스를 코틀린에서 사용
5. 수신 객체 지정 람다 사용

람다 식 또는 람다는 기본적으로 다른 함수에 넘길 수 있는 작은 코드 조각을 뜻한다.

람다를 사용하면 쉽게 공통 코드 구조를 라이브러리 함수로 뽑아낼 수 있다.

코틀린 표준 라이브러리를 람다를 많이 사용한다.

## 람다 식과 멤버 참조
```java
button.setOnClickListener(new OnClickListener() {
   @Override
    public void onClick(View view) {
        /* 클릭 시 수행할 동작 */   
    }
});
```
```kotlin
button.setOnClickListener {/* 클릭 시 수행할 동작 */}
```
이 코틀린 코드는 자바 무명 내부 클래스와 같은 역할을 하지만 훨씬 더 간결하다.

### 2. 람다와 컬렉션
코드에서 중복을 제거하는 것은 프로그래밍 스타일을 개선하는 중요한 방법 중 하나다.

컬렉션을 다룰 때 수행하는 대부분의 작업은 몇 가지 일반적인 패턴에 속한다.

따라서 그런 패턴은 라이브러리 안에 있어야 한다.

하지만, 람다가 없다면 컬렉션을 편리하게 처리할 수 있는 좋은 라이브러리를 제공하기 힘들다.

예제를 보자. 사람의 이름과 나이를 저장하는 Person 클래스를 적용하자.

```kotlin
data class Person(val name: String, val age: Int)
```
사람들로 이뤄진 리스트가 있고 그중에 가장 연장자를 찾고 싶다.

컬렉션에서 직접 검색하기
```kotlin
fun findTheOldest(people: List<Person>) {
	var maxAge = 0
	var theOldest: Person? = null
	for (person in people) {
		if (person.age > maxAge) {
			maxAge = person.age
			theOldest = person
		}
	}
	println(theOldest)
}
```
- [위 코드 통으로 보러가기](./RawCollection.kt)

코틀린에는 더 좋은 방법이 있다. 라이브러리 함수를 쓰면 된다.

```kotlin
println(people.maxByOrNull { it.age })
```

모든 컬렉션에 대해 maxBy 함수를 호출할 수 있다.
```kotlin
{ it.age }
```
이 코드는 컬렉션의 원소를 인자로 받아서 비교에 사용할 값을 반환한다.

이런 식으로 단지 함수나 프로퍼티를 반환하는 역할을 수행하는 람다는 멤버 참조로 대치할 수 있다.

```kotlin
val people = listOf<Person>(Person("Maboo", 29), Person("Bob", 38))
people.maxByOrNull(Person::age)
```

자바 컬렉션에 대해 수행했던 대부분의 작업은 람다나 멤버 참조를 인자로 취하는 라이브러리 함수를 통해 개선할 수 있다.

그렇게 람다나 멤버 참조를 인자로 받는 함수를 통해 개선 코드는 더 짧고 더 이해하기 쉽다.


### 3. 람다 식의 문법

람다는 값처럼 여기저기 전달할 수 있는 동작의 모음이다.

람다를 따로 선언해서 변수에 저장할 수도 있다. 

하지만 함수에 인자로 넘기면서 바로 람다를 정의하는 경우가 대부분이다.

람다식을 선언하는 방법 > 
```kotlin
{x: Int, y: Int -> x + y }
```

코틀린 람다 식은 항상 중괄호로 둘러싸여 있다.

인자 목록 주변에 괄호가 없다는 사실을 꼭 기억하라.

화살표(->)가 인자 목록과 람다 본문을 구분해준다.

람다 식을 변수에 저장할 수 있다. 람다가 저장된 변수를 다른 일반 함수와 마찬가지로 다룰 수 있다.

```kotlin
val sum = { x: Int, y: Int -> x + y }
println(sum(1, 2))
```

람다를 직접 호출할 수 있다. (알고만 있긔)

```kotlin
{ println(42) }()
```
코드 블록을 쌈싸는 것보다 명시적이고 가독성좋은 run을 사용하자. run은 인자로 받은 람다를 실행해주는 라이브러리이다.

```kotlin
run { println(42) }
```

실행 시점에 코틀린 람다 호출에는 아무 부가 비용이 들지 않으며, 프로그램의 기본 구성요소와 비슷한 성능을 낸다.

다시 사람들 중 연장자를 찾는 코드로 호도리!

```kotlin
people.maxByOrNull({p: Pereson -> p.age })
```

이코드는 번잡하다(자바같다) 한단계씩 개선해보자

코틀린에는 함수 호출 시 맨 뒤에 있는 인자가 람다 식이라면 그 람다를 괄호 밖으로 뻬낼 수 있다는 문법 관습이 있다.

따라서, 이 예제는 괄호 뒤에 람다를 둘 수 있다.

```kotlin
people.maxByOrNull() { p: Person -> p.age }
```

람다가 어떤 함수의 유일한 인자이고 괄호 뒤에 람다를 썼다면 호출 시 빈 괄호를 없애도 된다

```kotlin
people.maxByOrNull { p: Person -> p.age }
```

3장에서 잠깐 등장한 joinToString 예제를 다시 보자
```kotlin
val people = listOf(Person("이몽룡", 29), Person("성춘향", 31))
val names = people.joinToString(separator = " ",
                                transform = {p: Person -> p.name})
println(names)
```

이 함수 호출에서 함수를 괄호 밖으로 뺀 모습은 다음과 같다.

```kotlin
people.joinToString("") { p: Person -> p.name }
```

이제 구문을 더 간단하게 다듬고 파라미터 타입을 없애자

```kotlin
people.maxByOrNull { p: Person -> p.age }
people.maxByOrNull { p -> p.age } // 컴파일러 타입 추론
```

컴파일러가 타입을 모르겠다고 불평할 경우에만 타입을 명시한다.

마지막으로 람다의 파라미터 이름을 디폴트 이름인 it으로 바꾸면 람다 식을 더 간단하게 만들 수 있다.

```kotlin
people.maxByOrNull { it.age }
```

람다 파라미터 이름을 따로 지정하지 않은 경우에만 it라는 이름이 자동으로 만들어진다.

- it을 사용하는 관습은 코드를 아주 간단하게 만들어준다. 하지만 이를 남용하면 안된다. 특히 람다가 중첩됬을 경우 각 람다의 파라미터를 명시하는 편이 낫다.

파라미터를 명시하지 않으면 각각의 it이 가리키는 파라미터가 어떤 람다에 속했는지 파악하기 어려울 수 있다.

람다를 변수에 저장할 때는 파라미터의 타입을 추론할 문맥이 존재하지 않는다. 따라서 파라미터 타입을 명시해야 한다.

```kotlin
val getAge = { p: Person -> p.age }
people.maxByOrNull(getAge)
```
여러줄로 이뤄진 람다는 본문 맨 마지막에 있는 식이 람다의 결과 값이 된다.

```kotlin
val sum = {x: Int, y: Int -> 
  println("꺄앙!!!")
  x + y
}

println(sum(1,2))
```


### 4. 