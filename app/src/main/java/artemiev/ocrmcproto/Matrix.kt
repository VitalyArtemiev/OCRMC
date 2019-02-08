package artemiev.ocrmcproto

import android.widget.EditText

enum class MatrixMode { mDouble, mFraction }

class Matrix {
    var M: Array2D
    var Rows: Int = 0
    var Cols: Int = 0

    constructor(r: Int, c: Int, m: MatrixMode = MatrixMode.mDouble) {
        when (m) {
            MatrixMode.mDouble -> {
                M = Array2DDouble(r, c)
            }
            MatrixMode.mFraction -> {
                M = Array2DFraction(r, c)
            }
        }
    }

    constructor(s: String) {
        if (s.contains("/")) {
            M = Array2DFraction(2, 2)
        } else
            M = Array2DDouble(2, 2)

        val lines = s.split("/n")
        var i = 0
        for ((i, line) in lines.withIndex()) {
            var j = 0
            val elements = line.split(" ", "/t")
            for ((j, element) in elements.withIndex()) {
                M[i, j] = element.toBigDIck
            }

        }

    }

    //@Throws(Exception::class)
    constructor(t: EditText) : this(t.text.toString())


    fun swapRow(r1: Int, r2: Int) {

    }

    fun copy(): Matrix {

    }

    fun asString(): String {

    }

    operator fun plus(a: Any) {

    }
}