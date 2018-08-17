/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artemiev.ocrmcproto;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 * @author Виталий
 */
public class Fraction {
    public IntList Numerator;
    public IntList Denominator;
    public int Num;
    public int Den;

    public int Sign = 1;

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

        if (Den < 0) {
            Num *= -1;
            Den = abs(Den);
        }

        Numerator = factorize(Num);
        Denominator = factorize(Den);
        simplify();
    }

    private IntList factorize(int number) {
        Sign *= (int) signum(number);
        int n = abs(number);

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
        result.Sign = Sign;
        result.Numerator = Numerator.copy();
        result.Denominator = Denominator.copy();
        return result;
    }

    public boolean equals(Fraction f) {
        return Num == f.Num && Den == f.Den;
    }

    public boolean equals(int a) {
        return Num == a && Den == 1;
    }

    public boolean isZero() {
        return Num == 0;
    }

    public void unaryMinus() {
        Num *= -1;
        Sign *= -1;
        //Numerator.addSorted(-1);
        //simplify(); todo: possible error
    }

    public void plus(Fraction f) {
        Num = Num * f.Den + Den * f.Num;
        Den *= f.Den;
        Numerator.clear();
        Numerator = factorize(Num);
        Denominator.addSorted(f.Denominator);
        simplify();
    }

    public void minus(Fraction f) {
        Num = Num * f.Den - Den * f.Num;
        Den *= f.Den;
        Numerator.clear();
        Numerator = factorize(Num);
        Denominator.addSorted(f.Denominator);
        simplify();
    }

    public void times(Fraction f) {
        Num *= f.Num;

        if (Num == 0) {
            Den = 1;
            Numerator.clear();
            Numerator.add(0);
            Denominator.clear();
            Denominator.add(1);
            return;
        }

        Den *= f.Den;

        Numerator.addSorted(f.Numerator);
        Denominator.addSorted(f.Denominator);

        simplify();
    }

    public void div(Fraction f) {
        Num *= f.Den;
        Den *= f.Num;

        if (Den == 0) {
            throw new NumberFormatException("Division by zero");
        }

        Numerator.addSorted(f.Denominator);
        Denominator.addSorted(f.Numerator);

        simplify();
    }

    public void plus(int a) {
        Num += a * Den;
        Numerator.clear();
        Numerator = factorize(Num);
        simplify();
    }

    public void minus(int a) {
        Num -= a * Den;
        Numerator.clear();
        Numerator = factorize(Num);
        simplify();
    }

    public void times(int a) {
        Num *= a;

        if (Num == 0) {
            Den = 1;
            Numerator.clear();
            Numerator.add(0);
            Denominator.clear();
            Denominator.add(1);
            Sign = 1;
            return;
        }

        Numerator.addSorted(factorize(a));
        simplify();
    }

    public void div(int a) {
        Den *= a;

        if (Den == 0) {
            throw new NumberFormatException("Division by zero");
        }

        Denominator.addSorted(factorize(a));
        simplify();
    }

    public void simplify() {
        if (Den < 0) {
            Sign *= -1;
            Num *= -1;
            Den = abs(Den);
        }

        if (Num == 1 || Den == 1) {
            return;
        }

        if (Num == Den) {
            Num = 1;
            Den = 1;
            Numerator.clear();
            Denominator.clear();
            Numerator.add(1);
            Denominator.add(1);
            return;
        }

        IntList.IntMember CN = Numerator.Root;   //current
        IntList.IntMember CD = Denominator.Root;
        IntList.IntMember PN = null;             //previous
        IntList.IntMember PD = null;

        while (CN != null) {
            while (CD != null && CD.Value < CN.Value) {
                if (CD.Value < 0) {
                    //Sign *= -1; no need
                    CD.Value = abs(CD.Value);
                }

                PD = CD;
                CD = CD.Next;
            }

            if (CD == null) {
                break;
            }

            if (CN.Value < 0) {
                //Sign *= -1; no need
                CN.Value = abs(CN.Value);
            }

            if (CN.Value == CD.Value) {
                if (PN != null) {
                    Numerator.deleteNext(PN);
                    CN = PN.Next;
                } else {
                    Numerator.deleteFirst();
                    CN = Numerator.Root;
                }

                if (PD != null) {
                    Denominator.deleteNext(PD);
                    CD = PD.Next;
                } else {
                    Denominator.deleteFirst();
                    CD = Denominator.Root;
                }
            } else {
                PN = CN;
                CN = CN.Next;
            }
        }

        if (Numerator.MemberCount == 0) {
            Numerator.add(1);
        }
        if (Denominator.MemberCount == 0) {
            Denominator.add(1);
        }

        Num = Sign * Numerator.product();
        Den = Denominator.product();
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
        return (Sign < 0 ? "-" : "") + Numerator.toString() + "/" + Denominator.toString();
    }
}
