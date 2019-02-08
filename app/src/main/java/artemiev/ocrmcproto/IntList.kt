package artemiev.ocrmcproto

class IntList {

    var MemberCount = 0
    internal var Root: IntMember? = null

    private var prod: Int = 0
    private var prodReady = false

    inner class IntMember {
        var Value: Int = 0
        internal var Next: IntMember? = null

        internal constructor(v: Int) {
            Value = v
            Next = null
        }

        internal constructor(v: Int, n: IntMember?) {
            Value = v
            Next = n
        }
    }

    fun product(): Int {
        if (prodReady)
            return prod

        if (MemberCount == 0) {
            prod = 0
            return prod
        }

        prodReady = true

        prod = 1
        var cur = Root

        for (i in 1 until MemberCount) {
            prod *= cur!!.Value
            cur = cur.Next
        }

        prod *= cur!!.Value //to avoid nullptrexc

        return prod
    }

    fun copy(): IntList {
        val result = IntList()
        if (MemberCount == 0)
            return result

        var cur = Root!!.Next
        result.Root = IntMember(Root!!.Value)
        var resPrev: IntMember
        var resCur = result.Root

        for (i in 1 until MemberCount) {
            resPrev = resCur!!
            resCur = IntMember(cur!!.Value)
            resPrev.Next = resCur

            cur = cur.Next
        }

        result.MemberCount = MemberCount
        return result
    }

    internal fun add(v: Int) {
        if (Root == null) {
            Root = IntMember(v)
        } else {
            var Cur = Root
            while (Cur!!.Next != null) {
                Cur = Cur.Next
            }
            Cur.Next = IntMember(v)
        }
        MemberCount++
        prodReady = false
    }

    internal fun addSorted(v: Int) {
        if (Root == null) {
            Root = IntMember(v)
        } else {
            MemberCount++
            prodReady = false

            if (Root!!.Value >= v) {
                val t = IntMember(v)
                t.Next = Root
                Root = t
                return
            }

            var cur = Root
            while (cur!!.Next != null && cur.Next!!.Value < v) {
                cur = cur.Next
            }

            cur.Next = IntMember(v, cur.Next) //null handled
        }
    }

    internal fun addSorted(l: IntList) {
        val L = l.copy()

        if (L.MemberCount == 0)
            return

        if (Root == null) {
            Root = L.Root
        } else {
            var CurL = L.Root
            var Cur = Root

            while (CurL != null && Root!!.Value >= CurL.Value) {
                val t = CurL
                CurL = CurL.Next
                t.Next = Root
                Root = t
            }

            while (CurL != null) {//todo: why&
                while (Cur!!.Next != null && Cur.Next!!.Value < CurL.Value) {
                    Cur = Cur.Next
                }

                Cur.Next = IntMember(CurL.Value, Cur.Next) //null handled
                CurL = CurL.Next
            }
        }
        MemberCount += L.MemberCount
        prodReady = false
    }

    internal fun deleteFirst() {
        if (Root == null)
            return
        prod /= Root!!.Value         //doesnt change prodredy status

        Root = Root!!.Next
        MemberCount--


    }

    internal fun deleteNext(p: IntMember) {
        if (p.Next == null)
            return
        prod /= p.Next!!.Value       //doesnt change prodredy status

        p.Next = p.Next!!.Next
        MemberCount--
    }

    internal fun clear() {
        while (Root != null) {
            val p = Root
            Root = Root!!.Next
            p!!.Next = null
        }
        MemberCount = 0

        prod = 0
        prodReady = true
    }

    override fun toString(): String {
        if (MemberCount == 0)
            return "0"

        var Cur: IntList.IntMember? = Root
        var result = Integer.toString(Cur!!.Value)
        Cur = Cur.Next

        while (Cur != null) {
            result += "*" + Integer.toString(Cur.Value)
            Cur = Cur.Next
        }

        return result
    }
}
