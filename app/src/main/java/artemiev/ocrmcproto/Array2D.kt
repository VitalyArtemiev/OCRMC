package artemiev.ocrmcproto

abstract class Array2D/*<T> where T: Number*/ {
    abstract operator fun get(i: Int, j: Int): Any

    abstract operator fun get(i: Int): Any

    abstract operator fun set(i: Int, j: Int, v: Any)

    abstract operator fun set(i: Int, a: Any)
}

class Array2DDouble(r: Int, c: Int) : Array2D() {
    var A: Array<DoubleArray> = Array(r) { DoubleArray(c) }
    //todo: range check?
    override fun get(i: Int, j: Int): Double {
        return A[i][j]
    }

    override fun get(i: Int): DoubleArray {
        return A[i]
    }

    override fun set(i: Int, j: Int, v: Any) {
        A[i][j] = v as Double
    }

    override fun set(i: Int, a: Any) {
        A[i] = a as DoubleArray
    }

}

class Array2DFraction(r: Int, c: Int) : Array2D() {
    var A: Array<Array<Fraction>> = Array(r) { Array(c) { Fraction() } }

    override fun get(i: Int, j: Int): Fraction {
        return A[i][j]
    }

    override fun get(i: Int): Array<Fraction> {
        return A[i]
    }

    override fun set(i: Int, j: Int, v: Any) {
        A[i][j] = v as Fraction
    }

    override fun set(i: Int, a: Any) {
        A[i] = a as Array<Fraction>
    }
}