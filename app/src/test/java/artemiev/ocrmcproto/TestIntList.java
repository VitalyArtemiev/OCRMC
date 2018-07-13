package artemiev.ocrmcproto;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestIntList {
    @Test
    public void test() {
        IntList l = new IntList();
        l.add(1);
        l.add(3);
        assertEquals("Wrong number of elements", 2, l.MemberCount);
        l.addsorted(2);
        l.deletefirst();
        assertEquals("Sort or df failed", 2, l.Root.Value);
        l.addsorted(4);
        l.addsorted(1);
        assertEquals("Sort first element failed", 1, l.Root.Value);
        IntList t = l.copy();
        assertEquals("Copy failed", 1, t.Root.Value);
        assertEquals("Copy failed", l.MemberCount, t.MemberCount);

        l.clear();
        t.clear();

        l.add(3);
        l.add(4);

        t.add(2);
        t.add(2);

        l.addsorted(t);

        assertEquals("Addsorted failed", 4, l.MemberCount);
        assertEquals("Addsorted failed", 2, l.Root.Value);

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

        l.addsorted(t);

        assertEquals("Addsorted failed", 10, l.MemberCount);
        assertEquals("Addsorted failed", 1, l.Root.Value);
    }
}
