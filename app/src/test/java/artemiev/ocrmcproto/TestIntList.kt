package artemiev.ocrmcproto

import org.junit.Test

import org.junit.Assert.*

class TestIntList {
    @Test
    fun test() {
        val l = IntList()
        l.add(1)
        assertEquals("Product failed", 1, l.product().toLong())


        l.add(3)
        assertEquals("Product failed", 3, l.product().toLong())

        assertEquals("Wrong number of elements", 2, l.MemberCount.toLong())
        assertEquals("1*3", l.toString())

        l.addSorted(2)
        assertEquals("Addsorted failed", "1*2*3", l.toString())
        l.deleteFirst()
        assertEquals("Deletefirst failed", "2*3", l.toString())
        assertEquals("Product failed", 6, l.product().toLong())

        l.addSorted(4)
        l.addSorted(1)
        assertEquals("Sort first element failed", "1*2*3*4", l.toString())
        val t = l.copy()
        assertEquals("Copy failed", l.toString(), t.toString())
        assertEquals("Copy failed", l.MemberCount.toLong(), t.MemberCount.toLong())

        l.clear()
        t.clear()

        l.add(3)
        l.add(4)

        t.add(2)
        t.add(2)

        l.addSorted(t)

        assertEquals("Addsorted failed", 4, l.MemberCount.toLong())
        assertEquals("Addsorted failed", "2*2*3*4", l.toString())
        l.deleteFirst()
        assertEquals("Product failed", (2 * 3 * 4).toLong(), l.product().toLong())

        l.clear()
        t.clear()

        l.add(2)
        l.add(4)
        l.add(6)
        l.add(8)

        t.add(1)
        t.add(3)
        t.add(5)
        t.add(7)
        t.add(9)
        t.add(9)

        var k = l.copy()

        l.addSorted(t)

        assertEquals("Addsorted failed", 10, l.MemberCount.toLong())
        assertEquals("Addsorted failed", "1*2*3*4*5*6*7*8*9*9", l.toString())
        assertEquals("product failed", (2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 9).toLong(), l.product().toLong())

        t.addSorted(k)
        assertEquals("Addsorted failed", "1*2*3*4*5*6*7*8*9*9", t.toString())

        l.clear()
        t.clear()

        t.add(2)
        t.add(2)
        t.add(5)

        l.add(1)
        l.add(4)
        l.add(6)

        l.addSorted(t)
        assertEquals("Addsorted failed", "1*2*2*4*5*6", l.toString())

        l.clear()
        t.clear()

        l.add(2)
        l.add(2)
        l.add(5)

        t.add(1)
        t.add(6)

        l.addSorted(t)
        assertEquals("Addsorted failed", "1*2*2*5*6", l.toString())
    }
}
