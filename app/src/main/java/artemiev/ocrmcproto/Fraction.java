/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artemiev.ocrmcproto;

/**
 * @author Виталий
 */
public class Fraction {
    public IntList Numerator = new IntList();
    public IntList Denominator = new IntList();
    public int Num;
    public int Den;


    public Fraction() {
        Num = 0;
        Den = 1;

        Numerator = factorize(Num);
        Denominator = factorize(Den);
    }

    public Fraction(int num, int den) throws NumberFormatException {
        Num = num;
        Den = den;

        if (Den == 0) {
            throw new NumberFormatException("Denominator cannot be null");
        }

        Numerator = factorize(Num);
        Denominator = factorize(Den);
        simplify();
    }

    private static IntList factorize(int number) {
        int n = number;
        IntList factors = new IntList();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    public Fraction copy() {
        Fraction result = new Fraction();
        result.Num = Num;
        result.Den = Den;
        result.Numerator = Numerator.copy();
        result.Denominator = Denominator.copy();
        return result;
    }

    public boolean equals(Fraction f) {
        return Num == f.Num && Den == f.Den;
    }

    public boolean isZero() {
        return Num == 0;
    }

    public void negate() {
        Num *= -1;
        Numerator.addsorted(-1);
        simplify();
    }

    public void add(Fraction f) {
        Num = Num * f.Den + Den * f.Num;
        Den *= f.Den;
        Numerator.clear();
        Numerator = factorize(Num);
        Denominator.addsorted(f.Denominator);
        simplify();
    }

    public void subtract(Fraction f) {
        Num = Num * f.Den - Den * f.Num;
        Den *= f.Den;
        Numerator.clear();
        Numerator = factorize(Num);
        Denominator.addsorted(f.Denominator);
        simplify();
    }

    public void multiply(Fraction f) {
        Numerator.addsorted(f.Numerator);
        Denominator.addsorted(f.Denominator);
        Num *= f.Num;
        Den *= f.Den;
        simplify();
    }

    public void divide(Fraction f) {
        Numerator.addsorted(f.Denominator);
        Denominator.addsorted(f.Numerator);
        Num *= f.Den;
        Den *= f.Num;
        simplify();
    }

    public void add(int a) {
        Num += a * Den;
        Numerator.clear();
        Numerator = factorize(Num);
        simplify();
    }

    public void subtract(int a) {
        Num -= a * Den;
        Numerator.clear();
        Numerator = factorize(Num);
        simplify();
    }

    public void multiply(int a) {
        Numerator.addsorted(factorize(a));
        simplify();
    }

    public void simplify() {//todo: seems fukken broken
        IntList.IntMember CN = Numerator.Root;   //current
        IntList.IntMember CD = Denominator.Root;
        IntList.IntMember PN = null;             //previous
        IntList.IntMember PD = null;
        while (CN != null) {
            while (CD != null && CD.Value < CN.Value) {
                PD = CD;
                CD = CD.Next;
            }

            if (CD == null)
                return;

            if (CD.Value == CN.Value /*&& CD.Value != 1*/) {
                if (PN != null && PD != null) {
                    Numerator.deletenext(PN);
                    Numerator.deletenext(PD);
                } else {
                    Numerator.deletefirst();
                    Denominator.deletefirst();
                }
            }

            PN = CN;
            CN = CN.Next;
        }

        Num = Numerator.product();
        Den = Denominator.product();
    }

    public void divide(int a) {
        Denominator.addsorted(factorize(a));
        simplify();
    }

    public String toString() {
        return Integer.toString(Num) + "/" + Integer.toString(Den);
    }

    public static Fraction valueOf(String s) {
        try {
            if (s.contains("/")) {
                String[] elements = s.split("/");
                int n, d;
                n = Integer.valueOf(elements[0]);
                d = Integer.valueOf(elements[1]);
                return new Fraction(n, d);
            } else {
                return new Fraction(Integer.valueOf(s), 1);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Malformed fraction");
        }
    }

    public String toStringFactorized() {
        String Result = "1";
        IntList.IntMember Cur = Numerator.Root;
        while (Cur != null) {
            Result += "*" + Integer.toString(Cur.Value);
            Cur = Cur.Next;
        }

        Result += "/1";

        Cur = Denominator.Root;
        while (Cur != null) {
            Result += "*" + Integer.toString(Cur.Value);
            Cur = Cur.Next;
        }

        return Result;
    }
}
