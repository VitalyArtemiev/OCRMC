package artemiev.ocrmcproto

import android.widget.EditText

enum class MatrixMode { mDouble, mFraction }

class Matrix {
    var M: Array2D
    var Mode: MatrixMode
    var Rows: Int
    var Cols: Int

    constructor(r: Int, c: Int, m: MatrixMode = MatrixMode.mDouble) {
        Rows = r
        Cols = c
        Mode = m

        when (Mode) {
            MatrixMode.mDouble -> {
                M = Array2DDouble(Rows, Cols)
            }
            MatrixMode.mFraction -> {
                M = Array2DFraction(Rows, Cols)
            }
        }
    }

    constructor(s: String) {
        val lines = s.split("/n")
        Rows = lines.size
        Cols = lines[0].split(" ", "/t").size

        if (s.contains("/")) {
            Mode = MatrixMode.mFraction
            M = Array2DFraction(Rows, Cols)

            for ((i, line) in lines.withIndex()) {
                val elements = line.split(" ", "/t")

                if (elements.size != Cols)
                    throw Exception("Invalid matrix: malformed columns")

                for ((j, element) in elements.withIndex()) {
                    M[i, j] = Fraction.valueOf(element)
                }
            }
        } else {
            Mode = MatrixMode.mDouble
            M = Array2DDouble(Cols, Cols)

            for ((i, line) in lines.withIndex()) {
                val elements = line.split(" ", "/t")

                if (elements.size != Cols)
                    throw Exception("Invalid matrix: malformed columns")

                for ((j, element) in elements.withIndex()) {
                    M[i, j] = element.toDouble()
                }
            }
        }
    }

    constructor(t: EditText) : this(t.text.toString())

    operator fun get(i: Int, j: Int): Any {
        return M[i, j]
    }

    operator fun set(i: Int, j: Int, v: Any) {
        M[i, j] = v
    }

    fun swapRow(r1: Int, r2: Int) {
        val a = M[r2]
        M[r2] = M[r1]
        M[r1] = a
        /*for i= 0 to Col - 1 do
  M[r1, i]*= -1; //change sign so to not change det  */
    }

    fun copy(): RealMatrix {
        val R = RealMatrix(Rows, Cols)
        for (i in 0 until Rows)
            System.arraycopy(M[i], 0, R.M[i], 0, Cols)
        return R
    }

    fun asString(): String {
        var s = ""
        if (Mode == MatrixMode.mDouble) {
            for (i in 0 until Rows) {
                for (j in 0 until Cols)
                    s += (M[i, j] as Double).toString() + ' '

                if (i != Rows - 1)
                    s += '\n'.toString()
            }
        } else {
            for (i in 0 until Rows) {
                for (j in 0 until Cols)
                    s += (M[i, j] as Fraction).toString() + ' '

                if (i != Rows - 1)
                    s += '\n'.toString()
            }
        }
        return s
    }


    operator fun plus(a: Any) {

    }
}