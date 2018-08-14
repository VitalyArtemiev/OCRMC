package artemiev.ocrmcproto;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestIntList {
    @Test
    public void test() {
        IntList l = new IntList();
        l.add(1);
        assertEquals("Product failed", 1, l.product());

        l.add(3);
        assertEquals("Product failed", 3, l.product());

        assertEquals("Wrong number of elements", 2, l.MemberCount);
        assertEquals("1*3", l.toString());

        l.addSorted(2);
        assertEquals("Addsorted failed", "1*2*3", l.toString());
        l.deleteFirst();
        assertEquals("Deletefirst failed", "2*3", l.toString());
        assertEquals("Product failed", 6, l.product());

        l.addSorted(4);
        l.addSorted(1);
        assertEquals("Sort first element failed", "1*2*3*4", l.toString());
        IntList t = l.copy();
        assertEquals("Copy failed", l.toString(), t.toString());
        assertEquals("Copy failed", l.MemberCount, t.MemberCount);

        l.clear();
        t.clear();

        l.add(3);
        l.add(4);

        t.add(2);
        t.add(2);

        l.addSorted(t);

        assertEquals("Addsorted failed", 4, l.MemberCount);
        assertEquals("Addsorted failed", "2*2*3*4", l.toString());
        l.deleteFirst();
        assertEquals("Product failed", 2 * 3 * 4, l.product());

        l.clear();
        t.clear();

        l.add(2);
        l.add(4);
        l.add(6);
        l.add(8);

        t.add(1);
        t.add(3);
        t.add(5);
        t.add(7);
        t.add(9);
        t.add(9);

        l.addSorted(t);

        assertEquals("Addsorted failed", 10, l.MemberCount);
        assertEquals("Addsorted failed", "1*2*3*4*5*6*7*8*9*9", l.toString());
        assertEquals("product failed", 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 9, l.product());
    }
}
