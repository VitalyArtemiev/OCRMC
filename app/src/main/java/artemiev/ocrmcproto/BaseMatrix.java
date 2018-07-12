package artemiev.ocrmcproto;

import android.widget.EditText;

public abstract class BaseMatrix {
    public int Rows;
    public int Cols;

    public static BaseMatrix newMatrix(EditText t) throws Exception {
        if (t.getText().toString().contains("/")) {
            return new FractionMatrix(t);
        } else
            return new RealMatrix(t);
    }

    abstract public void swapRow(int r1, int r2);

    abstract public BaseMatrix copy();

    abstract public String asString();
}
