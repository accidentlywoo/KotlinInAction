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

무명 내부 클래스를 통해 코드를 함수에 넘기거나 변수에 저장할 수 있는 번거로운 코드

```java
button.setOnClickListener(new OnClickListener() {
   @Override
    public void onClick(View view) {
        /* 클릭 시 수행할 동작 */   
    }
});
```

코틀린에서는 자바8과 마찬가지로 람다를 쓸 수 있다.
```kotlin
button.setOnClickListener {/* 클릭 시 수행할 동작 */}
```
이 코틀린 코드는 자바 무명 내부 클래스와 같은 역할을 하지만 훨씬 더 간결하다.

람다를 메소드가 하나뿐인 무명 객체 대신 사용할 수 있다는 사실을 보여준다.

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
위같은 방식은 상당히 많은 코드가 들어있기  때문에 작성하다 실수를 저지르시 쉽다.
  
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

***람다는 값처럼 여기저기 전달할 수 있는 동작의 모음이다.***:heart_eyes:

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

마지막으로 람다의 파라미터 이름을 ***디폴트 이름인 it***으로 바꾸면 람다 식을 더 간단하게 만들 수 있다.:heart_eyes:

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

### 4. 현재 영역에 있는 변수에 접근
람다를 함수 안에서 정의하면 함수의 파라미터뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용할 수 있다.

이런 기능을 보여주기 위해 forEach 표준 함수를 사용해보자.

forEach는 가장 기본적인 컬렉션 조작 함수 중 하나다. forEach는 컬렉션의 모든 원소에 대해 람다를 호출해준다.

forEach는 일반적인 for 루프보다 훨씬 간결하지만 그렇다고 다른 장점이 많지는 않다.

```kotlin
fun printMessagesWithPrefix(message: Collection<String>, prefix: String) {
	message.forEach{
		println("${prefix} $it")
	}
}

fun main() {
	val errors = listOf("403 Forbidden", "404 Not Found")
	printMessagesWithPrefix(errors, "Error: ")
}
```
자바와 다른 점 중 중요한 한 가지는 코틀린 람다 안에서 파이널 변수가 아닌 변수에 접근할 수 있다는 점이다.

또한 람다 안에서 바깥의 변수를 변경해도 된다.

```kotlin
fun printProblemCounts(response: Collection<String>) {
	var clientErrors = 0
	var serverErrors = 0
	response.forEach {
		if (it.startsWith("4")) {
			clientErrors++ // 람다 안에서 암다 밖의 변수를 변경한다.
		} else if (it.startsWith("5")) {
			serverErrors++
		}
	}

	println("${clientErrors} client errors, ${serverErrors} server errors")
}
```
***코틀린에서는 자바와 달리 람다에서 람다 밖 함수에 있는 파이널이 아닌 변수에 접근할 수 있고, 그 변수를 변경할 수도 있다.***:heart_eyes:

예제에서 prefix, clientErrors, serverErrors와 같이 람다 안에서 사용하는 외부 변수를 '람다가 포획한 변수'라고 부른다.

기본적으로 함수 안에 정의된 로컬 변수의 생명주기는 함수가 반환되면 끝난다.

하지만 어떠 함수가 자신의 로컬 변수를 포획한 람다를 반환하거나 다른 변수에 저장한다면 로컬 변수의 생명주기와 함수의 생명주기가 달라질 수 있다.

람다가 포획한 변수에따라 생명주기가 호도리~ (209페이지 읽기 정리 Pass)


한 가지 꼭 알아둬야 할 함정이 있다.

람다를 이벤트 핸들러나 다른 비동기적으로 실행되는 코드로 활용하는 경우 함수 호출이 끝난 다음에 로컬 변수가 변경될 수도 있다.

```kotlin
fun tryToCountButtonClicks(button: Button): Int {
  var clicks = 0
  button.onClick { clicks++ }
  return clicks
}
```

위 예제는 항상 0을 반환한다. (당연한거 아닌가?)

### 5. 멤버 참조
코틀린에서는 자바8과 마찬가지로 함수를 값으로 바꿀 수 있다. 이때 이중 콜론(::)을 사용한다.

```kotlin
val getAge = Person::age
```
::를 사용하는 식을 멤버 참조라고 부른다.

멤버 참조는 프로퍼티나 메소드를 단 하나만 호출하는 함수 값을 만들어준다.

::는 클래스 이름과 여러분이 참조하려는 멤버(프로퍼티나 메소드) 이름 사이에 위치한다.

참조 대상이 함수인지 프로퍼티인지와는 관계없이 멤버 참조 뒤에는 괄호를 넣으면 안된다.

멤버 참조는 그 멤버를 호출하는 람다와 같은 타입이다. 따라서 다음 예처럼 그 둘을 자유롭게 바꿔 쓸 수 있다.

```kotlin
people.maxByOrNull(Person::age)
people.maxByOrNull { p -> p.age }
people.maxByOrNull { it.age }
```

최상위에 선언된 함수나 프로퍼티를 참조할 수도 있다.

```kotlin
fun maeve() = println("maeve")
run(::maeve)
```

생성자 참조를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다. :heart_eyes:

```kotlin
data class Person(val name: String, val age: Int)

val createPerson = ::Person
	val p = createPerson("maeve", 20)
	println(p)
```

확장 함수도 멤버 함수와 똑같은 방식으로 참조할 수 있다

```kotlin
fun Person.isAdult() = age >= 21
val prdicate = Person::isAdult
```
isAdult는 Person 클래스의 멤버가 아니고 확장 함수다.

그렇지만 isAdult를 호출할 때 person.isAdult()로 인스턴스 멤버 호출 구문을 쓸 수 있는 것처럼 Person::isAdult로 멤버 참조 구문을 사용해 이 확장 함수에 대한 참조를 얻을 수 있다.

- 바운드 멤버 참조
코틀린 1.1 부터는 바운드 멤버 참조(bound member reference)를 지원한다.
  
바운드 멤버 참조를 사용하면  멤버 참조를 생성할 때 클래스 인스턴스를 함께 저장한 다음 나중에 그 인스턴스에 대해 멤버를 호출해준다.

따라서 호출 시 수신 대상 객체를 별도로 지정해 줄 필요가 없다.

```kotlin
val p = Person("maeve", 19)
val personsAgeFunction = Person::age
println(personsAgeFunction(p))
// 기존 일반 동작 
val maeveAgeFunction = p::age
println(personsAgeFunction())
//kotlin 1.1부터 사용할 수 있는 바운드 참조
```

## 컬렉션 함수형 API 
filter, map 함수와 그 함수를 뒷받침하는 개념으로부터 시작한다.

또 다른 유용한 함수를 살펴본다.

설명하는 함수 중에 코들린을 설계한 사람이 발명한 함수는 전혀 없다.

이와 같거나 비슷한 함수를 C#, 그루비, 스칼라 등 람다를 지원하는 대부분의 언어에서 찾아볼 수 있다.

### 1). 필수적인 함수: filter와 map
filter함수는 컬렉션을 이터레이션하면서 주어진 람다에 각 원소를 넘겨서 람다가 true를 반환하는 원소만 모은다.

```kotlin
data class Person(val name: String, val age: Int)

val list = listOf(1, 2, 3, 4, 5)
println(list.filter { it % 2 == 0 })
// -> [2, 4]

val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.filter { it.age > 25 })
// [Person(name=Alice, age=29]
```
filter 함수는 컬렉션에서 원치 않는 원소를 제거한다.

하지만 filter는 원소를 변환할 수 없다. 원소를 변환하려면 map 함수를 사용해야 한다.

map 함수는 주어진 람다를 컬렉션의 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다.

```kotlin
val list = listOf(1, 2, 3, 4, 5)
println(list.map { it * it })
// [1, 4, 9, 16]
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.map { it.name })
// [Alice, Maeve]
println(people.filter { it.age < 25}.map(Person::name))
println(people.filter { it.age < 25}.map { it.name })
// [Maeve]
```
람다를 남발하면 안되는 케이스
```kotlin
people.filter { it.age == people.maxByOrNull(Person::age)!!.age }
```
위 코드는 people변수가 담긴 리스트의 양 만큼 최댓값을 계속 반복한다. 불필요한 연산을 없애보자

```kotlin
val maxAge = people.maxByOrNull(Person::age)!!.age
people.filter { it.age == maxAge }
```

꼭 필요하지 않은 경우 굳이 계산을 반복하지 말라.! 항상 작성한 코드로 인해 어떤일이 명확히 이해해야 한다.

필터와 변환 함수를 맵에 적용할 수도 있다.

```kotlin
val numbers = mapOf(0 to "zero", 1 to "one")
println(numbers.mapValues { it.value.toUpperCase() })
// {0=ZERO, 1=ONE}
```
맵의 경우 키와 값을 처리하는 함수가 따로 존재한다.

filterKeys와 mapKeys는 키를 걸러 내거나 변환하고, filterValues와 mapValues는 값을 걸러 내거나 변환한다.

### 2). all, any, count, find: 컬렉션에 술어 적용
컬렉션에 대해 자주 수행하는 연산으로 컬렉션의 모든 원소가 어떤 조건을 만족하는지 판단하는 연산이 있다.

코틀린에서는 all과 any가 이런 연산이다.

count 함수는 조건을 만족하는 원소의 개수를 반환하며,

find 함수는 조건을 만족하는 첫 번쨰 원소를 반환한다.

나이가 25살 이하인지 판단하는 술어 함수를 만들어보자

```kotlin
val canBeInClub25 = { p: Person -> p.age <= 25 }
```
모든 원소가 이 술어를 만족하는지 궁금하다면 all 함수를 쓴다.

```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.all(canBeInClub25))
// false
```

술어를 만족하는 원소가 하나라도 있는지 궁금하다면 any를 쓴다.
```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.any(canBeInClub25))
// true
```

!all을 수행하는 결과는 any를 수행한 결과와 같다
```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(!people.all(canBeInClub25))
// true
```

술어를 만족하는 원소의 갯수를 구하려면 count를 사용한다.

```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.count(canBeInClub25))
// 1
```

술어를 만족하는 원소를 하나 찾고 싶으면 find 함수를 사용한다.

```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.find(canBeInClub25))
// Person(name=Maeve, age=19)
```

find는 조건을 만족하는 원소가 하나라도 있는 경우 가장 먼저 조건을 만족한다고 확인된 원소를 반환하며,

만족하는 원소가 없을 경우 null을 반환한다.

find는 firstOrNull과 같다.

### 3). groupBy: 리스트를 여러 그룹으로 이뤄진 맵으로 변경
컬렉션의 모든 원소를 어떤 특성에 따라 여러 그룹으로 나누고 싶다면 groupBy 함수를 사용하자.

```kotlin
val people = listOf(Person("Alice", 29), Person("Maeve", 19))
println(people.groupBy { it.age })
// {19=[Person(name=Maeve, age=19)], 29=[Person(name=Alice, age=29)]}
```

groupBy의 결과 타입은 Map<Int, List<Person>> 이다.

필요하면 이 맵을 mapKeys나 mapValues 등을 사용해 변경할 수 있다.

```kotlin
val list = listOf("a", "ab", "b")
println(list.groupBy(String::first))
// {a=[a, ab], b=[b] }
```
first는 String의 확장 함수다. 멤버 참조를 사용해 first에 접근할 수 있다.

### 4). flatMap과 flatten : 중첩된 컬렉션 안의 원소 처리

## 3. 지연 계산(lazy) 컬렉션 연산

### 1). 시퀀스 연산 실행 : 중간 연산과 최종 연산

## 4. 자바 함수형 인터페이스 사용

### 1). 자바 메소드에 람다를 인자로 전달

### 2). SAM 생성자: 람다를 함수형 인터페이스로 명시적으로 변경

## 5. 수식 객체 지정 람다: with와 apply :pushpin: :heart_eyes:

### 1). with 함수 :pushpin: :heart_eyes:

### 2). apply 함수 :pushpin: :heart_eyes:


