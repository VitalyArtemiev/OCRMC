package artemiev.ocrmcproto

import android.widget.EditText

class RealMatrix : Matrix {
    var M: Array<DoubleArray> = emptyArray()

    constructor(r: Int, c: Int) {
        Rows = r
        Cols = c
        M = Array(r) { DoubleArray(c) }
    }

    @Throws(Exception::class)
    constructor(t: EditText) {
        Rows = t.lineCount

        val multiLines = t.text.toString()
        val Lines = multiLines.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val Elements = arrayOfNulls<Array<String>>(Lines.size)

        for (i in Lines.indices) {
            Elements[i] = Lines[i].split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (Elements[i].size > Cols)
                Cols = Elements[i].size
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(Rows) + ' ' + String.valueOf(Cols), Toast.LENGTH_SHORT).show();

        M = Array(Rows) { DoubleArray(Cols) }

        for (i in 0 until Rows)
            for (j in 0 until Cols) {
                if (j < Elements[i].size)
                    try {
                        M[i][j] = java.lang.Double.valueOf(Elements[i][j])!!
                    } catch (e: NumberFormatException) {
                        throw Exception("Malformed element")
                    }
                else
                    throw Exception("Lacking matrix element")
            }
    }

    override fun swapRow(r1: Int, r2: Int) {
        val a = M[r2]
        M[r2] = M[r1]
        M[r1] = a
        /*for i= 0 to Col - 1 do
  M[r1, i]*= -1; //change sign so to not change det  */
    }

    override fun copy(): RealMatrix {
        val R = RealMatrix(Rows, Cols)
        for (i in 0 until Rows)
            System.arraycopy(M[i], 0, R.M[i], 0, Cols)
        return R
    }

    override fun asString(): String {
        var s = ""
        for (i in 0 until Rows) {
            for (j in 0 until Cols)
                s += java.lang.Double.toString(M[i][j]) + ' '

            if (i != Rows - 1)
                s += '\n'.toString()
        }
        return s
    }
}