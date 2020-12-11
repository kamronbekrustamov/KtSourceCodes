package nicestring

fun String.isNice(): Boolean {
    val first = listOf("bu", "ba", "be").none { it in this }
    val second = filter { it in "aeiou" }.count() >= 3
    var third = zipWithNext().count {(char1, char2) -> char1 == char2} > 0
    return listOf(first, second, third).filter { it }.count() >= 2
}
