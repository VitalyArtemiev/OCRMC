package artemiev.ocrmcproto;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestFraction {
    @Test
    public void test() {
        Fraction f = new Fraction(256, 14);

        assertEquals("Fraction constructor failed", "128/7", f.toString());
        assertEquals("Fraction factors are wrong", "2*2*2*2*2*2*2/7", f.toStringFactorized());

        f = new Fraction(30, 30);
        assertTrue(f.equals(1));

        Fraction a = new Fraction();
        assertEquals("Fraction constructor failed", "0/1", a.toString());

        a.plus(1);
        assertEquals("Fraction sum failed", "1/1", a.toString());

        a.times(5);
        assertEquals("Fraction mult failed", "5/1", a.toString());

        f = a;
        a.times(2);
        assertTrue("Equals for int is broken", f.equals(10));

        a = new Fraction(5, 6);
        Fraction b = a.copy();
        assertTrue("Equals with fraction is broken", a.equals(b));

        f = new Fraction(6, 5);
        Fraction c = f.copy();

        a.times(f);
        assertTrue("Multiplication by fraction is broken", a.equals(1));

        b.plus(c);
        c = new Fraction(61, 30);
        assertTrue("Sum with fraction is broken", b.equals(c));

        c.minus(f);
        assertEquals("Fraction minus failed", "5/6", c.toString());

        a = new Fraction(7, 6);
        b = a.copy();
        a.div(b);
        assertEquals("Division by fraction error", "1/1", a.toString());

        b.div(7);
        assertEquals("Division by int error", "1/6", b.toString());

        a = new Fraction();
        a.times(b);
        assertTrue("Mult by 0 failure", a.equals(0));

        b.times(a);
        assertTrue("Mult by 0 failure", b.equals(0));

        a = new Fraction(125, 6);
        a.unaryMinus();
        assertEquals("Unary minus failed", "-125/6", a.toString());

        b = new Fraction(-5, 1);
        assertEquals("Problem with negative construction", "-5/1", b.toString());

        a.times(b);
        assertEquals("Problem with negative mult", "-625/6", a.toString());
        assertEquals("Problem with factor string", "-5*5*5*5/2*3", a.toStringFactorized());

        a.times(0);
        assertTrue("Mult by 0 failure", a.equals(0));

        b = new Fraction(234, -72);
        assertEquals("Problem with negative construction", "-13/4", b.toString());

        Fraction f1 = new Fraction(65535, 20);
        assertEquals("Fraction constructor failed", "13107/4", f1.toString());
        assertEquals("Fraction factors are wrong", "3*17*257/2*2", f1.toStringFactorized());

        f1 = Fraction.valueOf("-501/85");
        assertEquals("-501/85", f1.toString());
        f1 = Fraction.valueOf("-501/-85");
        assertEquals("501/85", f1.toString());
        f1 = Fraction.valueOf("501/-85");
        assertEquals("-501/85", f1.toString());
        f1 = Fraction.valueOf("-501");
        assertEquals("-501/1", f1.toString());

        f1.minus(1);
        assertEquals("-502/1", f1.toString());

        f1 = Fraction.valueOf("0");
        assertEquals("0/1", f1.toString());
        assertTrue(f1.isZero());
    }
}
