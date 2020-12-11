package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

class Rational(var n: BigInteger, var d: BigInteger):Comparable<Rational>{

    init {
        if (d == 0.toBigInteger())
            throw IllegalArgumentException("Denominator cannot be zero")
        if (d < 0.toBigInteger()) {
            d = -d
            n = -n
        }
        val gcd = getGCD()
        n /= gcd
        d /= gcd
    }

    private fun getGCD() = n.gcd(d)

    operator fun plus(other: Rational): Rational {
        val numerator = this.n * other.d + this.d * other.n
        val denominator = this.d * other.d
        return Rational(numerator, denominator)
    }

    operator fun minus(other: Rational): Rational {
        val numerator = this.n * other.d - this.d * other.n
        val denominator = this.d * other.d
        return Rational(numerator, denominator)
    }

    operator fun times(other: Rational): Rational {
        return Rational(this.n * other.n, this.d * other.d)
    }

    operator fun div(other: Rational): Rational {
        return Rational(this.n * other.d, this.d * other.n)
    }

    operator fun unaryMinus(): Rational {
        return Rational(-(this.n), this.d)
    }

    override operator fun compareTo(other: Rational): Int {
        val firstOne = this.n * other.d
        val secondOne = this.d * other.n
        return when {
            firstOne > secondOne -> 1
            firstOne == secondOne -> 0
        else -> -1
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Rational)
            return (this.n == other.n) && (this.d == other.d)
        return false
    }

    override fun hashCode(): Int {
        return (n + d).toInt()
    }

    fun rangeTo(other: Rational): Any {
        return RationalRange(other, this)
    }

    override fun toString(): String {
        return if (d == 1.toBigInteger()) "$n" else "$n/$d"
    }
}

class RationalRange(override val endInclusive: Rational, override val start: Rational) : ClosedRange<Rational>

fun String.toRational(): Rational {
    if (this.contains('/')) {
        val n = this.substringBefore('/').toBigInteger()
        val d = this.substringAfter('/').toBigInteger()
        return Rational(n, d)
    }
    return Rational(this.toBigInteger(), 1.toBigInteger())
}

infix fun Int.divBy(other: Int): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun Long.divBy(other: Long): Rational {
    return Rational(this.toBigInteger(), other.toBigInteger())
}

infix fun BigInteger.divBy(other: BigInteger): Rational {
    return Rational(this, other)
}