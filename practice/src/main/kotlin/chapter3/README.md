# 함수 정의와 호출
1. 컬렉션, 문자열, 정규식을 다루기 위한 함수
2. 이름 붙인 인자, 디폴트 파라미터 값, 중위 호출 문법 사용
3. 확장 함수와 확장 프로퍼티를 사용해 자바 라이브러리 적용
4. 최상위 및 로컬 함수와 프로퍼티를 사용해 코드 구조화

## 1. 코틀린에서 컬렉션 만들기
- [예제보러가기](./Collection.kt)

z코틀린이 자신만의 컬렉션 기능을 제공하지 않는다는 뜻이다.

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

또, 코틀린으로 작성한 함수를 호출할 때는 함수에 전달하는 인자 중 일부(또는 전부)의 이름을 명시할 수 있다.

- [예제보러가기](./joinToString.kt)

