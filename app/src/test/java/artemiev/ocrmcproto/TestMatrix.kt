package artemiev.ocrmcproto

import org.junit.Test

import org.junit.Assert.*

class TestMatrix {
    @Test
    fun testDoubleMatrix() {
        var m = Matrix(4, 5)
        assertEquals(4, m.Rows)
        assertEquals(5, m.Cols)
        assertEquals(MatrixMode.mDouble, m.Mode)

        var s = "1.5"
        m = Matrix(s)
        assertEquals(1, m.Rows)
        assertEquals(1, m.Cols)
        assertEquals(MatrixMode.mDouble, m.Mode)
        assertEquals(1.5, m.M[0, 0])

        s = "1.5 2.5"
        m = Matrix(s)
        assertEquals(1, m.Rows)
        assertEquals(2, m.Cols)
        assertEquals(MatrixMode.mDouble, m.Mode)
        assertEquals(1.5, m.M[0, 0])
        assertEquals(2.5, m.M[0, 1])

        s = "1.5/n" +
                "2.5"
        m = Matrix(s)
        assertEquals(2, m.Rows)
        assertEquals(1, m.Cols)
        assertEquals(MatrixMode.mDouble, m.Mode)
        assertEquals(1.5, m.M[0, 0])
        assertEquals(2.5, m.M[1, 0])
    }

    @Test
    fun testFractionMatrix() {

    }
}
