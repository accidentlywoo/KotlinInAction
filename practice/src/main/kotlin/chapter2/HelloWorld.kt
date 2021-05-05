package chapter1

fun main(args: Array<String>) {
    println("Hello World") // 표준 라이브러리 함수를 간결하게 wrapper 제공
    println(if (1 > 2) 1 else 2)

    println(max(1, 2))

    println(max2(1, 2))
}

fun max(a: Int, b: Int) : Int {
    return if (a > b) a else b // 코틀린은 루프를 제외한 대부분의 제어 구조가 식이다.
    // + Functional Programing에서 식은 값으로 쓸 수 있다.
//    val a = "aa";
// print(a = "bb") //컴파일 에러
}

fun max2(a: Int, b: Int) : Int = if (a > b) a else b // 식이 본문인 함수

fun max3(a: Int, b: Int) = if (a > b) a else b // 식이 본문인 함수, 반환타입 생략

fun valVarTest () {
    val message: String
    if (1 > 5) {
        message = "Success"
        //...
    }else{
        message = "Failed"
    }

    var number = 3
//    number = "꺄잉" // 컴파일 에러
}