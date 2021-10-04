# Consider static factory methods insteaad of constructors

1. Effective Java 복습
2. Companion factory method
3. Extension factory
4. Top-level functions
5. Fake construtors
6. Top-level functions and scope
7. Primary constructor

## 2. Companion factory method

## 4. Top-level functions
코틀린에서 CFM(Companion object factory method)을 대신해 top-level function(이하 TLF)을 정의하는 것은 일반적이다.

흔히 사용하는 예로는 listOf, setOf 그리고 mapOf가 있다.

마찬가지로 라이브러리 디자이너는 객체생성에 사용되는 TLF을 명세하고 있다.

야들은 정말 널리 사용되고 있다.

문제는 public TLF은 어디에서나 사용할 수 있지만, IDE 팁을 버리기 쉽다.

더 큰 문제는 누군가가 메소드가 아님을 직접적으로 가리키지 않는 이름으로 TLF을 만들 때 시작된다.

TLF을 이용한 객체생성은 List 나 Map같은 작고, 일반적인 객체에 좋은 선택이다.

왜냐하면, ```listOf(1,2,3)```은 ```List.of(1,2,3)```보다 더 간단하고 가독성이 좋다.

하지만, public TLF은 신중하고 의도대로 사용되야합니다. 

## 5. Fake constructors
훼이크 생성자는 top-level functions(이하 TLF)와 비슷하게 동작한다.
```kotlin
class A()
val a = A()
```

또한 TLF과 똑같이 참조된다.
```kotlin
val aReference = ::A
```
생성자와 함수의 유일한 차이점은 함수는 대문자로 시작하지 않는다는 것이다. 그러나 기술적으로는 가능하다.

실제로 코틀린 기본 라이브러리를 포함한 여기저기에서 사용되어진다.

List와 MutableList는 인터페이스라, 생성자를 가질 수 없지만, 코틀린 개발자는 다음과 같은 List 생성을 원했다.

```kotlin
List(3) {"$it"}
// [0, 1, 2] same as listOf("0", "1", "2")
```

    inceKotlin("1.1")
    @kotlin.internal.InlineOnly
    public inline fun <T> List(size: Int, init: (index: Int) -> T): List<T> = MutableList(size, init)
    
    /**
    * Creates a new mutable list with the specified [size], where each element is calculated by calling the specified
    * [init] function.
    *
    * The function [init] is called for each list element sequentially starting from the first one.
    * It should return the value for a list element given its index.
    *
    * @sample samples.collections.Collections.Lists.mutableListFromInitializer
      */
      @SinceKotlin("1.1")
      @kotlin.internal.InlineOnly
      public inline fun <T> MutableList(size: Int, init: (index: Int) -> T): MutableList<T> {
      val list = ArrayList<T>(size)
      repeat(size) { index -> list.add(init(index)) }
      return list
      }


이 함수들은 생성자처럼 보이고, 동작한다.

많은 개발자들은 이 함수가 TLF으로 동작하는 사실을 인지못하고 있습니다.(Under the hood - 자동차 후드 아래에서 벌어지는 일이라는 의미. 그것을 몰라도 운전하는데 지장은 없지만, 제대로 할려면 원리를 알 필요가 있는 일)

동시에 SFM(Static Factory Methods)의 장점을 갖고 있다. 훼이크 생성자는 타입의 서브타입을 반환할 수 있고, 매번 객체를 생성할 필요가 없다.

또한, 훼이크 생성자는 생성자에 필요한 요구사항이 없다.

예를 들어 보조생성자(secondary constructor)는 기본 생성자 또는 슈퍼클래스의 생성자를 즉시 호출해야 한다.

훼이크 생성자를 사용할 때 생성자 사용을 연기할 수 있다.

```kotlin
fun ListView(config: Config) : ListView {
	val items = TODO() // Here we read items frpm config
 return ListView(items) // We call actual constructor 
}
```