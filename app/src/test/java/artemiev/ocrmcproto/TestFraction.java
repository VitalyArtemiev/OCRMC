package artemiev.ocrmcproto;

import org.junit.Test;

public class TestFraction {
    @Test
    public void test() {
        Fraction f = new Fraction(256, 14);
        System.out.println(f.toStringFactorized());
        System.out.println(f.toString());
        Fraction a = f;
        a.multiply(5);
        System.out.println(f.toString());
        System.out.println(f.toStringFactorized());
        Fraction f1 = new Fraction(65535, 20);
        System.out.println(f1.toStringFactorized());
        f1.simplify();
        System.out.println(f1.toString());
    }
}
