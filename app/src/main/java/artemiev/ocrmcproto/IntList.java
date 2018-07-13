package artemiev.ocrmcproto;

public class IntList {
    public class IntMember {
        public int Value;
        IntMember Next;
        
        IntMember(int v) {
            Value= v;
            Next= null;
        }
        
        IntMember(int v, IntMember n) {
            Value= v;
            Next= n;
        }
    }

    public int MemberCount = 0;
    IntMember Root = null;

    private int prod;
    private boolean prodReady = false;

    public int product() {
        if (prodReady)
            return prod;

        if (MemberCount == 0) {
            prod = 0;
            return prod;
        }

        prodReady = true;

        prod = 1;
        IntMember cur = Root;

        for (int i = 0; i < MemberCount; i++) {
            prod *= cur.Value;
            cur = cur.Next;
        }
        return prod;
    }

    public IntList copy() {
        IntList result = new IntList();
        if (MemberCount == 0)
            return result;

        IntMember cur = Root;
        result.Root = new IntMember(Root.Value);
        IntMember resPrev;
        IntMember resCur = result.Root;

        for (int i = 0; i < MemberCount; i++) {
            resPrev = resCur;
            resCur = new IntMember(cur.Value);
            resPrev.Next = resCur;

            cur = cur.Next;
        }

        result.MemberCount = MemberCount;
        return result;
    }
    
    void add(int v) {
        if (Root == null) {
            Root= new IntMember(v);
        }
        else {
            IntMember Cur= Root;
            while (Cur.Next != null) {
                Cur= Cur.Next;
                
            }
            Cur.Next= new IntMember(v);
        }
        MemberCount++;
        prodReady = false;
    }
    
    void addsorted(int v) {
        if (Root == null) {
            Root= new IntMember(v);
        }
        else {
            if (Root.Value >= v) {
                IntMember t = new IntMember(v);
                t.Next = Root;
                Root = t;
                return;
            }

            IntMember Cur= Root;
            while ((Cur.Next != null) && (Cur.Next.Value < v)) {
                    Cur= Cur.Next;               
            }           
            Cur.Next= new IntMember(v, Cur.Next); //null handled
        }
        MemberCount++;
        prodReady = false;
    }
    
    void addsorted(IntList L) {
        if (L.MemberCount == 0)
            return;
        
        if (Root == null) {
            Root= L.Root;          
        }
        else {
            IntMember CurL= L.Root;
            IntMember Cur= Root;

            while ((CurL.Next != null) && (Root.Value >= CurL.Value)) {
                IntMember t = CurL;
                CurL = CurL.Next;
                t.Next = Root;
                Root = t;
            }

            while (CurL != null) {//todo: why&
                while ((Cur.Next != null) && (Cur.Next.Value < CurL.Value)) {
                    Cur= Cur.Next;
                }

                Cur.Next= new IntMember(CurL.Value, Cur.Next); //null handled
                CurL = CurL.Next;
            }
        }
        MemberCount+= L.MemberCount;
        prodReady = false;
    }
    
    void deletefirst(){
        if (Root == null)
            return;
        prod /= Root.Value;         //doesnt change prodredy status

        Root= Root.Next;
        MemberCount--;
    }
    
    void deletenext(IntMember p) {
        if (p.Next == null)
            return;
        prod /= p.Next.Value;       //doesnt change prodredy status

        p.Next= p.Next.Next;
        MemberCount--;
    }
    
    void clear(){
        while (Root!=null) {
            IntMember p= Root;
            Root= Root.Next;
            p.Next= null;
        }
        MemberCount= 0;

        prod = 0;
        prodReady = true;
    }
}
