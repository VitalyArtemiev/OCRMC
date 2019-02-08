package artemiev.ocrmcproto

import org.junit.Test

import org.junit.Assert.*

class TestFraction {
    @Test
    fun test() {
        var f = Fraction(256, 14)

        assertEquals("Fraction constructor failed", "128/7", f.toString())
        assertEquals("Fraction factors are wrong", "2*2*2*2*2*2*2/7", f.toStringFactorized())

        f = Fraction(30, 30)
        assertTrue(f.equals(1))

        var a = Fraction()
        assertEquals("Fraction constructor failed", "0/1", a.toString())

        a += 1
        assertEquals("Fraction sum failed", "1/1", a.toString())

        a *= 5
        assertEquals("Fraction mult failed", "5/1", a.toString())

        a *= -1
        assertEquals("Fraction mult failed", "-5/1", a.toString())

        f = a
        a *= 2
        assertTrue("Equals for int is broken", f.equals(-5))

        a = Fraction(5, 6)
        var b = a.copy()
        assertTrue("Equals with fraction is broken", a.equals(b))

        f = Fraction(6, 5)
        var c = f.copy()

        a *= f
        assertEquals("Multiplication by fraction is broken", "1/1", a.toString())

        assertEquals("Multiplication by fraction is broken", "6/5", f.toString())

        b += c
        c = Fraction(61, 30)
        assertTrue("Sum with fraction is broken", b.equals(c))

        c -= f
        assertEquals("Fraction minus failed", "5/6", c.toString())

        a = Fraction(7, 6)
        b = a.copy()
        a /= b
        assertEquals("Division by fraction error", "1/1", a.toString())

        b /= 7
        assertEquals("Division by int error", "1/6", b.toString())

        a = Fraction()
        a *= b
        assertTrue("Mult by 0 failure", a.equals(0))

        b *= a
        assertTrue("Mult by 0 failure", b.equals(0))

        a = Fraction(125, 6)
        a.negate()
        assertEquals("Unary minus failed", "-125/6", a.toString())

        b = Fraction(-5, 1)
        assertEquals("Problem with negative construction", "-5/1", b.toString())

        a *= b
        assertEquals("Problem with negative mult", "625/6", a.toString())
        assertEquals("Problem with factor string", "5*5*5*5/2*3", a.toStringFactorized())

        a *= 0
        assertTrue("Mult by 0 failure", a.equals(0))

        b = Fraction(234, -72)
        assertEquals("Problem with negative construction", "-13/4", b.toString())

        var f1 = Fraction(65535, 20)
        assertEquals("Fraction constructor failed", "13107/4", f1.toString())
        assertEquals("Fraction factors are wrong", "3*17*257/2*2", f1.toStringFactorized())

        f1 = Fraction.valueOf("-501/85")
        assertEquals("-501/85", f1.toString())
        f1 = Fraction.valueOf("-501/-85")
        assertEquals("501/85", f1.toString())
        f1 = Fraction.valueOf("501/-85")
        assertEquals("-501/85", f1.toString())
        f1 = Fraction.valueOf("-501")
        assertEquals("-501/1", f1.toString())

        f1 -= 1
        assertEquals("-502/1", f1.toString())

        f1 = Fraction.valueOf("0")
        assertEquals("0/1", f1.toString())
        assertTrue(f1.isZero)

        f1 = Fraction(8, 6)
        f1 *= f1
        assertEquals("Multiply by self failed", "16/9", f1.toString())

        f1 -= f1
        assertEquals("Subtract by self failed", "0/1", f1.toString())

        a = Fraction(5, 6)

        b = Fraction(7, 8)

        c = -a
        assertEquals("", "-5/6", c.toString())
        assertEquals("", "5/6", a.toString())

        c = a + b
        assertEquals("", "41/24", c.toString())
        assertEquals("", "5/6", a.toString())
        assertEquals("", "7/8", b.toString())

        c = a * b
        assertEquals("", "35/48", c.toString())
        assertEquals("", "5/6", a.toString())
        assertEquals("", "7/8", b.toString())

        c = a - b
        assertEquals("", "-1/24", c.toString())
        assertEquals("", "5/6", a.toString())
        assertEquals("", "7/8", b.toString())

        c = a / b
        assertEquals("", "20/21", c.toString())
        assertEquals("", "5/6", a.toString())
        assertEquals("", "7/8", b.toString())

        c = -c
        assertEquals("", "-20/21", c.toString())
        c *= b
        c += a
        assertEquals("", "0/1", c.toString())

        c = Fraction.valueOf("20/21")

        var d = -c
        d *= b
        d += a
        assertEquals("", "0/1", d.toString())

        //System.out.println("a b c " + a.toString() + ' ' + b.toString() + ' ' + c.toString())

        c = a + b * -c
        assertEquals("", "0/1", c.toString())

        c = Fraction.valueOf("20/21")
        d = b * -c
        assertEquals("", "-5/6", d.toString())

        d = d + a
        assertEquals("", "0/1", d.toString())

        a = Fraction.valueOf("6/5")
        b = Fraction.valueOf("13/10")
        c = Fraction.valueOf("3/5")
        c = (a - b) / c
        assertEquals("", "-1/6", c.toString())

        var dd: Double = 15.67
        System.out.println(dd.toByte())
        System.out.println(dd.toInt())
        System.out.println(dd.toChar())
    }
}
