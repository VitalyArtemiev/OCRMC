package artemiev.ocrmcproto

internal open class BaseResult

internal class RealResult(var f: Double) : BaseResult()

internal class FractionResult(var f: Fraction) : BaseResult()

/*operator fun FractionMatrix.unaryMinus() {
    val
            R = RealMatrix(a.Rows, b.Cols)
    var
            i: Int


    var j: Int

    i = 0
    while (i < R.Rows) {
        j = 0
        while (j < R.Cols) {
            R.M[i][j] = a.M[i][j] + b.M[i][j]
            j++
        }
        i++
    }
    return R


    return MatrixOperator.
}*/

object MatrixOperator {
    fun getFractionMatrix(m: BaseMatrix): FractionMatrix {
        if (m is FractionMatrix)
            return m

        val fm = FractionMatrix(m.Rows, m.Cols)
        val rm = m as RealMatrix

        for (i in 0 until m.Rows)
            for (j in 0 until m.Cols) {
                val iPart = rm.M[i][j].toInt()
                var fPart = rm.M[i][j] - iPart
                if (fPart == 0.0) {
                    fm.M[i][j] = Fraction(iPart, 1)
                } else {
                    var k = 0
                    while (fPart != fPart.toInt().toDouble()) {
                        fPart *= 10.0
                        k++
                    }

                    val den = Math.pow(10.0, k.toDouble()).toInt()
                    val num = fPart.toInt() + den * iPart

                    fm.M[i][j] = Fraction(num, den)
                }
            }

        return fm
    }

    fun multiply(a: BaseMatrix, b: BaseMatrix): BaseMatrix {
        val result: BaseMatrix
        if (a is RealMatrix && b is RealMatrix) {
            result = multiply(a, b)

        } else {
            val fa = getFractionMatrix(a)
            val fb = getFractionMatrix(b)

            result = multiply(fa, fb)
        }

        return result
    }

    fun multiply(a: RealMatrix, b: RealMatrix): RealMatrix {

        val R = RealMatrix(a.Rows, b.Cols)  //todo: change to Strassen algorithm
        var i: Int
        var j: Int
        var k: Int
        i = 0
        while (i < R.Rows) {
            j = 0
            while (j < R.Cols) {
                R.M[i][j] = 0.0
                k = 0
                while (k < a.Cols) {
                    R.M[i][j] += a.M[i][k] * b.M[k][j]
                    k++
                }
                j++
            }
            i++
        }
        return R
    }

    fun multiply(a: FractionMatrix, b: FractionMatrix): FractionMatrix {
        val R = FractionMatrix(a.Rows, b.Cols)  //change to Strassen algorithm
        var i: Int
        var j: Int
        var k: Int
        i = 0
        while (i < R.Rows) {
            j = 0
            while (j < R.Cols) {
                R.M[i][j] = Fraction(0, 1)
                k = 0
                while (k < a.Cols) {
                    val f = a.M[i][k].copy()
                    f.times(b.M[k][j])
                    R.M[i][j].plus(f)
                    k++
                }
                j++
            }
            i++
        }
        return R
    }

    fun sum(a: BaseMatrix, b: BaseMatrix): BaseMatrix {
        val result: BaseMatrix
        if (a is RealMatrix && b is RealMatrix) {
            result = sum(a, b)

        } else {
            val fa = getFractionMatrix(a)
            val fb = getFractionMatrix(b)

            result = sum(fa, fb)
        }

        return result
    }

    fun sum(a: RealMatrix, b: RealMatrix): RealMatrix {
        val R = RealMatrix(a.Rows, b.Cols)
        var i: Int
        var j: Int

        i = 0
        while (i < R.Rows) {
            j = 0
            while (j < R.Cols) {
                R.M[i][j] = a.M[i][j] + b.M[i][j]
                j++
            }
            i++
        }
        return R
    }

    fun sum(a: FractionMatrix, b: FractionMatrix): FractionMatrix {
        val R = FractionMatrix(a.Rows, b.Cols)
        var i: Int
        var j: Int

        i = 0
        while (i < R.Rows) {
            j = 0
            while (j < R.Cols) {
                R.M[i][j] = a.M[i][j].copy()
                R.M[i][j].plus(b.M[i][j])
                j++
            }
            i++
        }
        return R
    }

    fun LUDecompose(a: RealMatrix): Array<RealMatrix?> {
        val u = RealMatrix(a.Rows, a.Cols)
        val l = RealMatrix(a.Rows, a.Cols)
        var i: Int
        var j: Int
        var k: Int

        i = 0
        while (i < l.Rows) {
            u.M[0][i] = a.M[0][i]
            l.M[i][i] = 1.0
            i++
        }

        i = 0
        while (i < u.Rows) {
            j = i
            while (j < u.Rows) {
                var s = 0.0
                k = 0
                while (k < i) {
                    s += l.M[i][k] * u.M[k][j]
                    k++
                }
                u.M[i][j] = a.M[i][j] - s
                j++
            }

            j = i + 1
            while (j < u.Rows) {
                var s = 0.0
                k = 0
                while (k < i) {
                    s += l.M[j][k] * u.M[k][i]
                    k++
                }

                if (u.M[i][i] != 0.0)
                    l.M[j][i] = (a.M[j][i] - s) / u.M[i][i]
                else
                    l.M[j][i] = 1.0 //???
                j++
            }
            i++
        }

        val R = arrayOfNulls<RealMatrix>(2)
        R[0] = u
        R[1] = l

        return R
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

    fun det(m: RealMatrix): Double {
        var a: RealMatrix? = m.copy()
        var i: Int
        var j: Int
        var k: Int
        i = 0
        while (i < a!!.Cols)
        //this ignores extra rows
        {
            j = i
            while (j < a.Cols) {
                var s = 0.0
                k = 0
                while (k < i) {
                    s += a.M[i][k] * a.M[k][j]
                    k++
                }
                a.M[i][j] = a.M[i][j] - s
                j++
            }

            j = i + 1
            while (j < a.Cols) {
                var s = 0.0
                k = 0
                while (k < i) {
                    s += a.M[j][k] * a.M[k][i]
                    k++
                }

                if (a.M[i][i] != 0.0)
                    a.M[j][i] = (a.M[j][i] - s) / a.M[i][i]
                else
                    return 0.toDouble()
                j++
            }
            i++
        }

        var R = 1.0

        i = 0
        while (i < a.Cols) {
            R *= a.M[i][i]
            i++
        }
        a = null
        return R
    }

    fun det(m: FractionMatrix): Fraction {
        var a: FractionMatrix? = m.copy()
        var i: Int
        var j: Int
        var k: Int
        i = 0
        while (i < a!!.Cols)
        //this ignores extra rows
        {
            j = i
            while (j < a.Cols) {
                val s = Fraction()
                k = 0
                while (k < i) {
                    val t = a.M[i][k].copy()
                    t.times(a.M[k][j])
                    s.plus(t)
                    k++
                }
                a.M[i][j].minus(s)
                j++
            }

            j = i + 1
            while (j < a.Cols) {
                val s = Fraction()
                k = 0
                while (k < i) {
                    val t = a.M[j][k].copy()
                    t.times(a.M[k][i])
                    s.plus(t)
                    k++
                }

                if (!a.M[i][i].isZero) {
                    val t = a.M[j][i].copy()
                    t.minus(s)
                    t.div(a.M[i][i])
                    a.M[j][i] = t
                    //a.M[j][i] = (a.M[j][i] - s) / a.M[i][i];
                } else
                    return Fraction()
                j++
            }
            i++
        }

        val R = Fraction(1, 1)

        i = 0
        while (i < a.Cols) {
            R.times(a.M[i][i])
            i++
        }
        a = null
        return R
    }

    fun invert(a: BaseMatrix): BaseMatrix {
        val result: BaseMatrix
        if (a is RealMatrix) {
            result = invert(a)
        } else {
            val fa = getFractionMatrix(a)
            result = invert(fa)
        }

        return result
    }

    fun invert(a: RealMatrix): RealMatrix {
        val R = RealMatrix(a.Rows, a.Cols)
        var i: Int
        var j: Int
        i = 0
        while (i < R.Rows) {
            {
                j = 0
                while (j < R.Cols) {
                    R.M[i][j] = -a.M[i][j]
                    j++
                }
            }
            i++
        }
        return R
    }

    fun invert(a: FractionMatrix): FractionMatrix {
        val R = FractionMatrix(a.Rows, a.Cols)
        var i: Int
        var j: Int
        i = 0
        while (i < R.Rows) {
            {
                j = 0
                while (j < R.Cols) {
                    R.M[i][j].unaryMinus()
                    j++
                }
            }
            i++
        }
        return R
    }

    fun transpose(a: BaseMatrix): BaseMatrix {
        val result: BaseMatrix
        if (a is RealMatrix) {
            result = transpose(a)
        } else {
            val fa = getFractionMatrix(a)
            result = transpose(fa)
        }

        return result
    }

    fun transpose(a: RealMatrix): RealMatrix {
        var i: Int
        var j: Int
        val R = RealMatrix(a.Cols, a.Rows)
        i = 0
        while (i < R.Rows) {
            {
                j = 0
                while (j < R.Cols) {
                    R.M[i][j] = a.M[j][i]
                    j++
                }
            }
            i++
        }
        return R
    }

    fun transpose(a: FractionMatrix): FractionMatrix {
        var i: Int
        var j: Int
        val R = FractionMatrix(a.Cols, a.Rows)
        i = 0
        while (i < R.Rows) {
            {
                j = 0
                while (j < R.Cols) {
                    R.M[i][j] = a.M[j][i]
                    j++
                }
            }
            i++
        }
        return R
    }

}
