package artemiev.ocrmcproto;

class BaseResult {
}

class RealResult extends BaseResult {
    double f;

    public RealResult(double r) {
        f = r;
    }
}

class FractionResult extends BaseResult {
    Fraction f;

    public FractionResult(Fraction r) {
        f = r;
    }
}


public class MatrixOperator {
    public static FractionMatrix getFractionMatrix(BaseMatrix m) {
        if (m instanceof FractionMatrix)
            return (FractionMatrix) m;

        FractionMatrix fm = new FractionMatrix(m.Rows, m.Cols);
        RealMatrix rm = (RealMatrix) m;

        for (int i = 0; i < m.Rows; i++)
            for (int j = 0; j < m.Cols; j++) {
                int iPart = (int) rm.M[i][j];
                double fPart = rm.M[i][j] - iPart;
                if (fPart == 0) {
                    fm.M[i][j] = new Fraction(iPart, 1);
                } else {
                    int k = 0;
                    while (fPart != (int) fPart) {
                        fPart *= 10;
                        k++;
                    }

                    int den = (int) Math.pow(10, k);
                    int num = (int) fPart + den * iPart;

                    fm.M[i][j] = new Fraction(num, den);
                }
            }

        return fm;
    }

    public static BaseMatrix multiply(BaseMatrix a, BaseMatrix b) {
        BaseMatrix result;
        if (a instanceof RealMatrix && b instanceof RealMatrix) {
            result = multiply((RealMatrix) a, (RealMatrix) b);

        } else {
            FractionMatrix fa = getFractionMatrix(a);
            FractionMatrix fb = getFractionMatrix(b);

            result = multiply(fa, fb);
        }

        return result;
    }

    public static RealMatrix multiply(RealMatrix a, RealMatrix b) {

        RealMatrix R = new RealMatrix(a.Rows, b.Cols);  //todo: change to Strassen algorithm
        int i, j, k;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = 0;
                for (k = 0; k < a.Cols; k++)
                    R.M[i][j] += a.M[i][k] * b.M[k][j];
            }
        return R;
    }

    public static FractionMatrix multiply(FractionMatrix a, FractionMatrix b) {
        FractionMatrix R = new FractionMatrix(a.Rows, b.Cols);  //change to Strassen algorithm
        int i, j, k;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = new Fraction(0, 1);
                for (k = 0; k < a.Cols; k++) {
                    Fraction f = a.M[i][k].copy();
                    f.multiply(b.M[k][j]);
                    R.M[i][j].add(f);
                }
            }
        return R;
    }

    public static BaseMatrix sum(BaseMatrix a, BaseMatrix b) {
        BaseMatrix result;
        if (a instanceof RealMatrix && b instanceof RealMatrix) {
            result = sum((RealMatrix) a, (RealMatrix) b);

        } else {
            FractionMatrix fa = getFractionMatrix(a);
            FractionMatrix fb = getFractionMatrix(b);

            result = sum(fa, fb);
        }

        return result;
    }

    public static RealMatrix sum(RealMatrix a, RealMatrix b) {
        RealMatrix R = new RealMatrix(a.Rows, b.Cols);
        int i, j;

        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = a.M[i][j] + b.M[i][j];
            }
        return R;
    }

    public static FractionMatrix sum(FractionMatrix a, FractionMatrix b) {
        FractionMatrix R = new FractionMatrix(a.Rows, b.Cols);
        int i, j;

        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = a.M[i][j].copy();
                R.M[i][j].add(b.M[i][j]);
            }
        return R;
    }

    public static RealMatrix[] LUDecompose(RealMatrix a) {
        RealMatrix u = new RealMatrix(a.Rows, a.Cols);
        RealMatrix l = new RealMatrix(a.Rows, a.Cols);
        int i, j, k;

        for (i = 0; i < l.Rows; i++) {
            u.M[0][i] = a.M[0][i];
            l.M[i][i] = 1;
        }

        for (i = 0; i < u.Rows; i++) {
            for (j = i; j < u.Rows; j++) {
                double s = 0;
                for (k = 0; k < i; k++)
                    s += l.M[i][k] * u.M[k][j];
                u.M[i][j] = a.M[i][j] - s;
            }

            for (j = i + 1; j < u.Rows; j++) {
                double s = 0;
                for (k = 0; k < i; k++)
                    s += l.M[j][k] * u.M[k][i];

                if (u.M[i][i] != 0)
                    l.M[j][i] = (a.M[j][i] - s) / u.M[i][i];
                else
                    l.M[j][i] = 1; //???
            }
        }

        RealMatrix[] R = new RealMatrix[2];
        R[0] = u;
        R[1] = l;

        return R;
    }

    /*public static BaseResult det(BaseMatrix a) {
        BaseResult result;
        if (a instanceof RealMatrix) {
            Double d = det((RealMatrix) a);
            result = new RealResult(d);
        }
        else {
            Fraction f = det((FractionMatrix) a);
            result = new FractionResult(f);
        }

        return result;
    }*/

    public static double det(RealMatrix m) {
        RealMatrix a = m.copy();
        int i, j, k;
        for (i = 0; i < a.Cols; i++)   //this ignores extra rows
        {
            for (j = i; j < a.Cols; j++) {
                double s = 0;
                for (k = 0; k < i; k++)
                    s += a.M[i][k] * a.M[k][j];
                a.M[i][j] = a.M[i][j] - s;
            }

            for (j = i + 1; j < a.Cols; j++) {
                double s = 0;
                for (k = 0; k < i; k++)
                    s += a.M[j][k] * a.M[k][i];

                if (a.M[i][i] != 0)
                    a.M[j][i] = (a.M[j][i] - s) / a.M[i][i];
                else
                    return (0);
            }
        }

        double R = 1;

        for (i = 0; i < a.Cols; i++)
            R *= a.M[i][i];
        a = null;
        return R;
    }

    public static Fraction det(FractionMatrix m) {
        FractionMatrix a = m.copy();
        int i, j, k;
        for (i = 0; i < a.Cols; i++)   //this ignores extra rows
        {
            for (j = i; j < a.Cols; j++) {
                Fraction s = new Fraction();
                for (k = 0; k < i; k++) {
                    Fraction t = a.M[i][k].copy();
                    t.multiply(a.M[k][j]);
                    s.add(t);
                }
                a.M[i][j].subtract(s);
            }

            for (j = i + 1; j < a.Cols; j++) {
                Fraction s = new Fraction();
                for (k = 0; k < i; k++) {
                    Fraction t = a.M[j][k].copy();
                    t.multiply(a.M[k][i]);
                    s.add(t);
                }

                if (!a.M[i][i].isZero()) {
                    Fraction t = a.M[j][i].copy();
                    t.subtract(s);
                    t.divide(a.M[i][i]);
                    a.M[j][i] = t;
                    //a.M[j][i] = (a.M[j][i] - s) / a.M[i][i];
                } else
                    return (new Fraction());
            }
        }

        Fraction R = new Fraction(1, 1);

        for (i = 0; i < a.Cols; i++)
            R.multiply(a.M[i][i]);
        a = null;
        return R;
    }

    public static BaseMatrix invert(BaseMatrix a) {
        BaseMatrix result;
        if (a instanceof RealMatrix) {
            result = invert((RealMatrix) a);
        } else {
            FractionMatrix fa = getFractionMatrix(a);
            result = invert(fa);
        }

        return result;
    }

    public static RealMatrix invert(RealMatrix a) {
        RealMatrix R = new RealMatrix(a.Rows, a.Cols);
        int i, j;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = -a.M[i][j];
            }
        return R;
    }

    public static FractionMatrix invert(FractionMatrix a) {
        FractionMatrix R = new FractionMatrix(a.Rows, a.Cols);
        int i, j;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j].negate();
            }
        return R;
    }

    public static BaseMatrix transpose(BaseMatrix a) {
        BaseMatrix result;
        if (a instanceof RealMatrix) {
            result = transpose((RealMatrix) a);
        } else {
            FractionMatrix fa = getFractionMatrix(a);
            result = transpose(fa);
        }

        return result;
    }

    public static RealMatrix transpose(RealMatrix a) {
        int i, j;
        RealMatrix R = new RealMatrix(a.Cols, a.Rows);
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++)
                R.M[i][j] = a.M[j][i];
        return R;
    }

    public static FractionMatrix transpose(FractionMatrix a) {
        int i, j;
        FractionMatrix R = new FractionMatrix(a.Cols, a.Rows);
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++)
                R.M[i][j] = a.M[j][i];
        return R;
    }

}
