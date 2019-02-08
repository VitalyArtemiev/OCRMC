package artemiev.ocrmcproto;

import android.widget.EditText;

public class FractionMatrix extends BaseMatrix {
    public Fraction M[][];

    public FractionMatrix(int r, int c) {
        Rows = r;
        Cols = c;
        M = new Fraction[r][c];
    }

    public FractionMatrix(EditText t) throws Exception {
        Rows = t.getLineCount();

        String multiLines = t.getText().toString();
        String[] Lines = multiLines.split("\n");
        String[][] Elements = new String[Lines.length][];

        for (int i = 0; i < Lines.length; i++) {
            Elements[i] = Lines[i].split(" ");
            if (Elements[i].length > Cols)
                Cols = Elements[i].length;
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(Rows) + ' ' + String.valueOf(Cols), Toast.LENGTH_SHORT).show();

        M = new Fraction[Rows][Cols];

        for (int i = 0; i < Rows; i++)
            for (int j = 0; j < Cols; j++) {
                if (j < Elements[i].length)
                    try {
                        M[i][j] = Fraction.Companion.valueOf(Elements[i][j]);
                    } catch (NumberFormatException e) {
                        throw new Exception("Malformed element");
                    }
                else
                    throw new Exception("Lacking matrix element");
            }
    }

    @Override
    public void swapRow(int r1, int r2) {
        Fraction a[] = M[r2];
        M[r2] = M[r1];
        M[r1] = a;
  /*for i= 0 to Col - 1 do
  M[r1, i]*= -1; //change sign so to not change det  */
    }

    @Override
    public FractionMatrix copy() {
        FractionMatrix R = new FractionMatrix(Rows, Cols);
        for (int i = 0; i < Rows; i++)
            System.arraycopy(M[i], 0, R.M[i], 0, Cols);
        return R;
    }

    @Override
    public String asString() {
        String s = "";
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++)
                s += M[i][j].toString() + ' ';

            if (i != Rows - 1)
                s += '\n';
        }
        return s;
    }
}

