/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artemiev.ocrmcproto

import java.lang.Math.abs
import java.lang.Math.signum

/**
 * @author Виталий
 */
class Fraction/*: Number*/ {
    /*override fun toChar(): Char {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toDouble(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toFloat(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toInt(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toLong(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toShort(): Short {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toByte(): Byte {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    var Numerator: IntList
    var Denominator: IntList
    var Num: Int = 0
    var Den: Int = 0

    var Sign = 1

    val isZero: Boolean
        get() = Num == 0

    constructor() {
        Num = 0
        Den = 1

        Numerator = factorize(Num)
        Denominator = factorize(Den)
    }

    @Throws(NumberFormatException::class)
    constructor(num: Int, den: Int) {
        Num = num
        Den = den

        if (Den == 0) {
            throw NumberFormatException("Denominator cannot be null")
        }

        if (Den < 0) {
            Num *= -1
            Den = abs(Den)
        }

        Numerator = factorize(Num)
        Denominator = factorize(Den)
        simplify()
    }

    private inline fun handleZero(): Boolean {
        if (Num == 0) {
            Den = 1
            Numerator.clear()
            Numerator.add(0)
            Denominator.clear()
            Denominator.add(1)
            return true
        } else
            return false
    }

    private fun factorize(number: Int): IntList {
        Sign *= signum(number.toFloat()).toInt()
        var n = abs(number)

        val factors = IntList()
        var i = 2
        while (i <= n / i) {
            while (n % i == 0) {
                factors.add(i)
                n /= i
            }
            i++
        }

        /*for (i in 2..(n / i)) {
            while (n % i == 0) {
                factors.add(i)
                n /= i
            }
        }*/
        if (n > 1) {
            factors.add(n)
        }
        return factors
    }

    fun copy(): Fraction {
        val result = Fraction()
        result.Num = Num
        result.Den = Den
        result.Sign = Sign
        result.Numerator = Numerator.copy()
        result.Denominator = Denominator.copy()
        return result
    }

    override operator fun equals(f: Any?): Boolean {
        return when (f) {
            is Fraction -> {
                Num == f.Num && Den == f.Den
            }
            is Int -> {
                Num == f && Den == 1
            }
            else -> {
                false
            }
        }
    }

    fun negate() {
        Num *= -1
        Sign *= -1
        //Numerator.addSorted(-1);
        //simplify(); todo: possible error
    }

    operator fun unaryMinus(): Fraction {
        val result = copy()
        //System.out.println("un0 " + result.toString())
        with(result) {
            negate()
        }
        //System.out.println("un1 " + result.toString())
        return result
    }

    fun add(f: Fraction) {
        Num = Num * f.Den + Den * f.Num

        if (handleZero()) {
            return
        }

        Den *= f.Den
        Numerator.clear()
        Numerator = factorize(Num)
        Denominator.addSorted(f.Denominator)
        simplify()
    }

    fun subtract(f: Fraction) {
        Num = Num * f.Den - Den * f.Num

        if (handleZero()) {
            return
        }

        Den *= f.Den
        Numerator.clear()
        Numerator = factorize(Num)
        Denominator.addSorted(f.Denominator)
        simplify()
    }

    fun multiply(f: Fraction) {
        Num *= f.Num

        if (handleZero()) {
            return
        }

        Den *= f.Den

        Numerator.addSorted(f.Numerator)
        Denominator.addSorted(f.Denominator)

        simplify()
    }

    fun divide(f: Fraction) {
        Num *= f.Den
        Den *= f.Num

        if (Den == 0) {
            throw NumberFormatException("Division by zero")
        }

        Numerator.addSorted(f.Denominator)
        Denominator.addSorted(f.Numerator)

        simplify()
    }

    fun add(a: Int) {
        Num += a * Den
        Numerator.clear()
        Numerator = factorize(Num)
        simplify()
    }

    fun subtract(a: Int) {
        Num -= a * Den
        Numerator.clear()
        Numerator = factorize(Num)
        simplify()
    }

    fun multiply(a: Int) {
        Num *= a

        if (Num == 0) {
            Den = 1
            Numerator.clear()
            Numerator.add(0)
            Denominator.clear()
            Denominator.add(1)
            Sign = 1
            return
        }

        Numerator.addSorted(factorize(a))
        simplify()
    }

    fun divide(a: Int) {
        Den *= a

        if (Den == 0) {
            throw NumberFormatException("Division by zero")
        }

        Denominator.addSorted(factorize(a))
        simplify()
    }

    operator fun plus(f: Any): Fraction {
        val result = copy()
        with(result) {
            when (f) {
                is Fraction -> {
                    add(f)
                }
                is Int -> {
                    add(f)
                }
                else -> {
                    throw Exception("Wrong parameter type")
                }
            }
        }
        return result
    }

    operator fun minus(f: Any): Fraction {
        val result = copy()
        with(result) {
            when (f) {
                is Fraction -> {
                    subtract(f)
                }
                is Int -> {
                    subtract(f)
                }
                else -> {
                    throw Exception("Wrong parameter type")
                }
            }
        }
        return result
    }

    operator fun times(f: Any): Fraction {
        val result = copy()
        with(result) {
            when (f) {
                is Fraction -> {
                    multiply(f)
                }
                is Int -> {
                    multiply(f)
                }
                else -> {
                    throw Exception("Wrong parameter type")
                }
            }
        }
        return result
    }

    operator fun div(f: Any): Fraction {
        val result = copy()
        with(result) {
            when (f) {
                is Fraction -> {
                    divide(f)
                }
                is Int -> {
                    divide(f)
                }
                else -> {
                    throw Exception("Wrong parameter type")
                }
            }
        }
        return result
    }

    fun simplify() {
        if (Den < 0) {
            Sign *= -1
            Num *= -1
            Den = abs(Den)
        }

        if (Num * Sign < 0) {
            System.out.println("Warning: sign mismatch!!!")
            Sign *= -1
        }

        if (Num == 1 || Den == 1) {
            return
        }

        if (Num == Den) {
            Num = 1
            Den = 1
            Numerator.clear()
            Denominator.clear()
            Numerator.add(1)
            Denominator.add(1)
            return
        }

        var CN = Numerator.Root   //current
        var CD = Denominator.Root
        var PN: IntList.IntMember? = null             //previous
        var PD: IntList.IntMember? = null

        while (CN != null) {
            while (CD != null && CD.Value < CN.Value) {
                if (CD.Value < 0) {
                    //Sign *= -1; no need
                    CD.Value = abs(CD.Value)
                }

                PD = CD
                CD = CD.Next
            }

            if (CD == null) {
                break
            }

            if (CN.Value < 0) {
                //Sign *= -1; no need
                CN.Value = abs(CN.Value)
            }

            if (CN.Value == CD.Value) {
                if (PN != null) {
                    Numerator.deleteNext(PN)
                    CN = PN.Next
                } else {
                    Numerator.deleteFirst()
                    CN = Numerator.Root
                }

                if (PD != null) {
                    Denominator.deleteNext(PD)
                    CD = PD.Next
                } else {
                    Denominator.deleteFirst()
                    CD = Denominator.Root
                }
            } else {
                PN = CN
                CN = CN.Next
            }
        }

        if (Numerator.MemberCount == 0) {
            Numerator.add(1)
        }
        if (Denominator.MemberCount == 0) {
            Denominator.add(1)
        }

        Num = Sign * Numerator.product()
        Den = Denominator.product()
    }

    fun toStringTruncated(): String {
        if (Num == 0) {
            return "0"
        }
        if (Den == 1) {
            return Integer.toString(Num)
        }
        return toString()
    }

    override fun toString(): String {
        return Integer.toString(Num) + "/" + Integer.toString(Den)
    }

    fun toStringFactorized(): String {
        return (if (Sign < 0) "-" else "") + Numerator.toString() + "/" + Denominator.toString()
    }

    companion object {

        fun valueOf(s: String): Fraction {
            try {
                if (s.contains("/")) {
                    val elements = s.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val n: Int
                    val d: Int
                    n = Integer.valueOf(elements[0])
                    d = Integer.valueOf(elements[1])
                    return Fraction(n, d)
                } else {
                    return Fraction(Integer.valueOf(s), 1)
                }
            } catch (e: NumberFormatException) {
                throw NumberFormatException("Malformed fraction")
            }

        }
    }
}
