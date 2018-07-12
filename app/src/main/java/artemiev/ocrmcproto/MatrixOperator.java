package artemiev.ocrmcproto;

public class MatrixOperator {

    public static RealMatrix multiply(RealMatrix a, RealMatrix b) {

        RealMatrix R = new RealMatrix(a.Rows, b.Cols);  //change to Strassen algorithm
        int i, j, k;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = 0;
                for (k = 0; k < a.Cols; k++)
                    R.M[i][j] += a.M[i][k] * b.M[k][j];
            }
        return R;
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

    public static RealMatrix invert(RealMatrix a) {
        RealMatrix R = new RealMatrix(a.Rows, a.Cols);
        int i, j;
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++) {
                R.M[i][j] = -a.M[i][j];
            }
        return R;
    }

    public static RealMatrix transpose(RealMatrix a) {
        int i, j;
        RealMatrix R = new RealMatrix(a.Cols, a.Rows);
        for (i = 0; i < R.Rows; i++)
            for (j = 0; j < R.Cols; j++)
                R.M[i][j] = a.M[j][i];
        return R;
    }

}
