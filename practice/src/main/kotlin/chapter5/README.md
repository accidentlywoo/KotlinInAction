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
button.setOnClickListener(new OnClickListener(){
@Override
public void onClick(View view){
        /* 클릭 시 수행할 동작 */
        }
        });
```

코틀린에서는 자바8과 마찬가지로 람다를 쓸 수 있다.

```kotlin
button.setOnClickListener {/* 클릭 시 수행할 동작 */ }
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
  위같은 방식은 상당히 많은 코드가 들어있기 때문에 작성하다 실수를 저지르시 쉽다.

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
{ x: Int, y: Int -> x + y }
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
people.maxByOrNull({ p: Pereson -> p.age })
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
	transform = { p: Person -> p.name })
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
val sum = { x: Int, y: Int ->
	println("꺄앙!!!")
	x + y
}

println(sum(1, 2))
```

### 4. 현재 영역에 있는 변수에 접근

람다를 함수 안에서 정의하면 함수의 파라미터뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용할 수 있다.

이런 기능을 보여주기 위해 forEach 표준 함수를 사용해보자.

forEach는 가장 기본적인 컬렉션 조작 함수 중 하나다. forEach는 컬렉션의 모든 원소에 대해 람다를 호출해준다.

forEach는 일반적인 for 루프보다 훨씬 간결하지만 그렇다고 다른 장점이 많지는 않다.

```kotlin
fun printMessagesWithPrefix(message: Collection<String>, prefix: String) {
	message.forEach {
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

- 바운드 멤버 참조 코틀린 1.1 부터는 바운드 멤버 참조(bound member reference)를 지원한다.

바운드 멤버 참조를 사용하면 멤버 참조를 생성할 때 클래스 인스턴스를 함께 저장한 다음 나중에 그 인스턴스에 대해 멤버를 호출해준다.

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
println(people.filter { it.age < 25 }.map(Person::name))
println(people.filter { it.age < 25 }.map { it.name })
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

flatMap 함수는 먼저 인자로 주어진 람다를 컬렉션의 모든 객체에 적용하고(또는 매핑),

람다를 적용한 결과 얻어지는 여러 리스트를 한 리스트로 모은다.

```kotlin
val strings = listOf("abc", "def")
println(strings.flatMap { it.toList() })
// [a, b, c, d, e, f]
```

toList 함수를 문자열에 적용하면 그 문자열에 속한 모든 문자로 이뤄진 리스트가 만들어진다.

Book 으로 표현한 책에 대한 정보는 책 제목과 여러명의 저자가 있다.

```kotlin
class Book(val title: String, val authors: List<String>)

val books = listOf(
	Book("Help me Summer", listOf("Maeve", "Charlie")),
	Book("I Want to go Home", listOf("Maeve")),
	Book("Agile", listOf("Martinflower")),
)

println(books.flatMap { it.authors }.toSet())
// ["Maeve", "Charlie", "Martinflower"]
```

리스트의 리스트가 있는데 모든 중첩된 리스트의 원소를 한 리스트로 모아야 한다면 flatMap을 떠올릴 수 있다.

하지만 특별히 변환햐여 할 내용이 없다면 리스트의 리스트를 평평하게 펼치기만 하면 된다.

그런 경우 listOfLists.flatten()을 사용하면 된다.

## 3. 지연 계산(lazy) 컬렉션 연산

map이나 fillter 같은 몇가지 함수를 살펴봤다. 이 함수는 결과 컬렉션을 즉시 생성한다.

이는 컬렉션 함수를 연쇄하면 매 단계마다 계산의 중간 결과를 새로운 컬렉션에 임시로 담는다는 말이다.

시퀀스를 사용하면 중간 임시 컬렉션을 사용하지 않고도 컬렉션 연산을 연쇄할 수 있다.

```kotlin
people.map(Person::name).filter { it.startsWith('A') }
```

코틀린 표준 라이브러리 참조 문서에는 filter와 map이 리스트를 반환한다고 써 있다.

이는 이 연괘 호출이 리스트를 2개 만든다는 뜻이다.

원소가 많아질 수록 효율이 떨어진다.

이를 더 효율적으로 만들기 위해서는 각 연산이 컬렉션을 직접 사용하는 대신 시퀀스를 사용하게 만들어야 한다.

```kotlin
people.asSequence()
	.map(Person::name)
	.filter { it.startsWith('A') }
	.toList()
```

위 예제는 이전 예제와 똑같은 결과를 반환하지만, 중간 결과를 저장하는 컬렉션이 생기지 않기 때문에 원소가 많은 경우 성능이 눈에 띄게 좋아진다.:pushpin:

코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다.

Sequence 인터페이스의 강점은 그 인터페이스 위에 구현된 연산이 계산을 수행하는 방법 때문에 생긴다.

시퀀스의 원소는 필요할 때 비로소 계산된다.

따라서 중간 처리 결과를 저장하지 않고도 연산을 연쇄적으로 적용해서 효율적으로 계산을 수행할 수 있다.

asSequence 확장 함수를 호출하면 어떤 컬렉션이든 시퀀스로 바꿀 수 있다.:heart_eyes:

시퀀스를 리스트를 만들때는 toList를 사용한다.

:pushpin: 왜 시퀀스를 다시 컬렉션으로 되돌려야 될까?

시퀀스의 원소를 차례로 이터레이션해야 한다면 시퀀스를 직접 써도 된다.

하지만 시퀀스 원소를 인덱스를 사용해 접근하는 등의 다른 API 메소드가 필요하다면 시퀀스를 리스트로 변환해야 한다.

### 1). 시퀀스 연산 실행 : 중간 연산과 최종 연산

시퀀스에 대한 연산은 중간연산과 최종 연산으로 나뉜다.

중간 연산은 다른 시퀀스를 반환한다.

최종 연산은 결과를 반환한다.

```kotlin
listOf(1, 2, 3, 4, 5).asSequence()
	.map { print("map($it)"); it * it }
	.filter { print("filter($it)"); it % 2 == 0 }
```

위 코드를 실행하면 아무 내용도 출력되지 않는다.

```kotlin
listOf(1, 2, 3, 4, 5).asSequence()
	.map { print("map($it)"); it * it }
	.filter { print("filter($it)"); it % 2 == 0 }
	.toList()
```

컬랙션에 대한 map과 filter는 map함수를 각 원소에 대해 먼저 수행하고, filter를 수행한다.

하지만, 시퀀스에 대한 map과 filter는 모든 연산은 각 원소에 대해 순차적으로 적용된다.

즉, 첫 번째 원소가 처리되고, 다시 두 번째 원소가 처리되며, 이런 처리가 모든 원소에 대해 적용된다.

따라서 원소에 연산을 차례대로 적용하다가 결과가 얻어지면 그 이후의 원소에 대해서는 변환이 이뤄지지 않을 수 있다.:heart_eyes:

```kotlin
println(listOf(1, 2, 3, 4).asSequence()
	.map { it * it }
	.find { it > 3 }
)
```

위 예제는 원소가 하나씩 각 연산을 처리하면서 find에 만족하는 결과를 찾으면 그 값을 반환하고, 그 이후의 연산을 처리하지 않는다.

성능 갱!

컬렉션에 대해 수행하는 연산의 순서도 성능에 영향을 끼친다.

```kotlin
val people = listOf(Person("Alice", 29), Person("Bob", 31), Person("Maeve", 25))

println(
	people.asSequence()
		.map(Person::name)
		.filter { it.length < 4 }
		.toList()
)
// [Bob]

println(
	people.asSequence()
		.filter { it.length < 4 }
		.map(Person::name)
		.toList()
)
// [Bob]
```

위 예제에서 map을 먼저 연산한 경우, filter를 먼저 연산한 예제보다 변환횟수가 많다

## 4. 자바 함수형 인터페이스 사용

어떻게 코틀린 람다를 자바 API에 활용할 수 있는지 살펴보자.

```java
button.setOnClickListener{ /* 클릭시 수행 */}
```

```java
public class Button {
    public void setOnClickListener(OnClickListener l) { ...}
}
```

OnClickListener 인터페이스는 onClick이라는 메소드만 선언된 인터페이스다.

```java
public interface OnClickListener {
    void onClick(View v);
}
```

자바 8 이전의 자바에서는 setOnClickListener 메소드에게 인자로 넘기기 위해 무명 클래스의 인스턴스를 만들어야만 했다.

```java
button.setOnClickListener(new OnClickListener(){
@Override
public void onClick(View b){
        ...
        }
        }
        )
```

코틀린에서는 무명 클래스 인스턴스 대신 람다를 넘길 수 있다.

```kotlin
button.setOnClickListener { view -> ... }
```

이런 코드가 작동하는 이유는 OnClickListener에 추상 메소드가 단 하나만 있기 때문이다.

그런 인터페이스를 함수형 인터페이스 또는 SAM(Single abstract method) 인터페이스라고 한다.

자바 API에는 Runnacle이나 Callable 과 같은 함수형 인터페이스와 그런 함수형 인터페이스를 활용하는 메소드가 많다.

코틀린은 함수형 인터페이스를 인자로 취하는 자바 메소드를 호출할 때 람다를 넘길 수 있게 해준다.

:pushpin:
자바와 달리 코틀린에는 제대로 된 함수 타입이 존재한다.

따라서 코틀린에서 함수를 인자로 받을 필요가 있는 함수는 함수형 인터페이스가 아니라 함수 타입을 인자 타입으로 사용해야 한다.

코틀린 함수를 사용할 때는 코틀린 컴파일러가 코틀린 람다를 함수형 인터페이스로 변환해주지 않는다.

함수 선언에서 함수 타입을 사용하는 방법은 8.1 절에서 설명한다.

### 1). 자바 메소드에 람다를 인자로 전달

함수형 인터페이스를 인자로 원하는 자바 메소드에 코틀린 람다를 전달하는 경우 어떤 일이 벌어지는지 자세히 살펴보자.

```java
void postponeComputation(int delay,Runnable computation);
```

코틀린에서 람다를 이 함수에 넘길 수 있다.

컴파일러는 자동으로 람다를 Runnalbe 인스턴스로 변환해준다.

```kotlin
postponeComputation(1000) { println(42) }
```

Runnable을 구현하는 무명 객체를 명시적으로 만들어서 사용할 수도 있다.

```kotlin
postponeComputation(1000, object : Runnable {
	override fun run() {
		println(42)
	}
})
```

람다와 무명 객체 사이에는 차이가 있다.

객체를 명시적으로 선언하는 경우 메소드를 호출할 때마다 새로운 객체가 생성된다. 람다는 다르다.

정의가 들어있는 함수의 변수에 접근하지 않는 람다에 대응하는 무명 객체를 메소드를 호출할 때마다 반복 사용한다.

```kotlin
postponeComputation(1000) { println(42) } // 프로그램 전체에서 Runnable의 인스턴스는 단 하나만 만들어진다.
```

따라서 명시적인 object 선언을 사용하면서 람다와 동일한 코드는 다음과 같다.

이 경우 Runnable 인스턴스를 변수에 저장하고 메소드를 호출할 때마다 그 인스턴스를 사용한다.

```kotlin
val runnable = Runnable { println(42) } // Runnable 은 SAM 생성자
fun handleComputation() {
	postponeComputation(1000, runnable) // 모든 hadndleComputation 호출에 같은 객체를 사용한다.
}
```

람다가 주변 영역의 변수를 포획한다면 매 호출마다 같은 인스턴스를 사용할 수 없다.

그런 경우 컴파일러는 매번 주변 영역의 변수를 포획한 새로운 인스턴스를 생성해준다.

:pushpin:
코틀린 1.0에서 인라인 되지 않은 모든 람다 식은 무명 클래스로 컴파일된다.

코틀린 1.1부터는 자바 8 바이트코드를 생성할 수 있지만 여전히 코틀린 1.0처럼 람다마다 별도의 클래스를 만들어낸다.

람다가 변수를 포획하면 무명 클래스 안에 포획한 변수를 저장하는 필드가 생기며, 매 호출마다 그 무명 클래스의 인스턴스를 새로 만든다.

하지만 포획하는 변수가 없는 람다에 대해서는 인스턴스가 단 하나만 생긴다.

HandleComputation$1처럼 람다가 선언된 함수 이름을 접두사로하는 이름이 람다를 컴파일한 클래스에 붙는다.

```kotlin :: decompiled
class HandleComputation$1(val id: String) : Runnable {
	override fun run() {
		println(id)
	}
}

fun handleComputation(id: String) {
	postponeComputation(1000, HandleComputation$1(id))
}
```

위는 포획이 있는 람다 식의 바이트코드를 디컴파일하면 볼 수 있는 코드다.

코드를 보면 알 수 있지만 컴파일러는 포획한 변수마다 그 값을 저장하기 위한 필드를 만든다.

```kotlin
fun handleComputation(id: String) {
	postponeComputation(1000) { println(id) }
}
```

코틀린 inline으로 표시된 코틀린 함수에게 람다를 넘기면 아무런 무명 클래스도 만들어지지 않는다.

대부분의 코틀린 확장 함수들은 inline표시가 붙어있다.

대부분의 경우 람다와 자바 함수형 인터페이스 사이의 변환은 컴파일러를 통해 자동으로 이루어진다.

하지만, 어쩔 수 없이 수동으로 변환해야 하는 경우가 있다.

### 2). SAM 생성자: 람다를 함수형 인터페이스로 명시적으로 변경

SAM 생성자는 람다를 함수형 인터페이스의 인스턴스로 변환할 수 있게 컴파일러가 자동으로 생성한 함수다.

컴파일러가 자동으로 람다를 함수형 인터페이스 무명 클래스로 바꾸지 못하는 경우 SAM 생성자를 사용할 수 있다.

예를 들어 함수형 인터페이스의 인스턴스를 반환하는 메소드가 있다면 람다를 직접 반환할 수 없고, 반환하고픈 람다를 SAM 생성자로 감싸야 한다.

```kotlin
fun createAllDoneRunnable(): Runnable {
	return Runnable { println("All done!") }
}

>>> createAllDoneRunnable().run()
// All done!
```

SAM 생성자의 이름은 사용하려는 함수형 인터페이스의 이름과 같다.

SAM 생성자는 그 함수형 인터페이스의 유일한 추상 메소드의 본문에 사용할 람다만을 인자로 받아서 함수형 인터페이스를 구현하는 클래스의 인스턴스를 반환한다.

람다로 생성한 함수향 인터페이스 인스턴스를 변수에 저장해야 하는 경우에도 SAM생성자를 사용할 수 있다.

여러 버튼에 같은 리스터를 적용하고 싶다면 다음 리스트처럼 SAM 생성자를 통해 람다를 함수형 인터페이스 인스턴스로 만들어서 변수에 저장해 활용할 수 있다.

```kotlin
val listener = OnClickListener { view ->
	val text = when (view.id) {
		R.id.button1 -> "First button"
		R.id.button2 -> "Second button"
        else -> "Unknown button"
	}
  toast(text)
}

button1.setOnClickListener(listener)
button2.setOnClickListener(listener)
```
OnClickListener 를 구현하는 객체 선언을 통해 리스너를 만들 수도 있지만 SAM 생성자를 쓰는 쪽이 더 간결하다.

:pushpin:
* 람다와 리스너 등록/해제하기
람다에는 무명 객체와 달리 인스턴스 자신을 가리키는 this가 없다는 사실에 유의하라.
  
따라서 람다를 변환한 무명 클래스의 인스턴스를 참조할 방법이 없다.

컴파일러 입장에서 보면 람다는 코드 블록일 뿐이고, 객체가 아니므로 객체처럼 람다를 참조할 수는 없다.

람다 안에서 this는 그 람다를 둘러싼 클래스의 인스턴스를 가리킨다.

 이벤트 리스너가 이벤트를 처리하다가 자기 자신의 리스너 등록을 해제해야 한다면 람다를 사용할 수 없다.

그런 경우 람다 대신 무명 객체를 사용해 리스너를 구현해라.

무명 객체 안에서는 this가 그 무명 객체 인스턴스 자신을 가리킨다.

따라서 리스너를 해제하는 API 함수에게 This를 넘길 수 있다.

 또한 함수형 인터페이스를 요구하는 메소드를 호출할 때 대부분의 SAM 변환을 컴파일러가 자동으로 수행할 수 있지만, 

가끔 오버로드한 메소드 중에서 어떤 타입의 메소드를 선택해 람다를 변환해 넘겨줘야 할지 모호할 때가 있다.

그런 경우 명시적으로 SAM생성자를 적용하면 컴파일 오류를 피할 수 있다.

## 5. 수식 객체 지정 람다: with와 apply :pushpin: :heart_eyes:

### 1). with 함수 :pushpin: :heart_eyes:

### 2). apply 함수 :pushpin: :heart_eyes:


