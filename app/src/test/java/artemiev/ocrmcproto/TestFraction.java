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
        System.out.println(f.toString());
        //assertTrue(f.equals(1));

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

        Fraction f1 = new Fraction(65535, 20);
        assertEquals("Fraction constructor failed", "65535/20", f1.toString());
        assertEquals("Fraction factors are wrong", "3*17*257/2*2", f1.toStringFactorized());
        System.out.println(f1.toStringFactorized());
        f1.simplify();
        System.out.println(f1.toString());
    }
}
