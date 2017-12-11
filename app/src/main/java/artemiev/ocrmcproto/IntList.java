/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artemiev.ocrmcproto;

/**
 *
 * @author Виталий
 */

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
    }
    
    void addsorted(int v) {
        if (Root == null) {
            Root= new IntMember(v);
        }
        else {
            IntMember Cur= Root;
            while ((Cur.Next != null)||(Cur.Next.Value < v)) {               
                    Cur= Cur.Next;               
            }           
            Cur.Next= new IntMember(v, Cur.Next); //null handled
        }
        MemberCount++;
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
            
            while (CurL != null) {
                while ((Cur.Next != null)||(Cur.Next.Value < CurL.Value)) {               
                    Cur= Cur.Next;               
                }           
                Cur.Next= new IntMember(CurL.Value, Cur.Next); //null handled
            }
        }
        MemberCount+= L.MemberCount;
    }
    
    void deletefirst(){
        Root= Root.Next;
        MemberCount--;
    }
    
    void deletenext(IntMember p) {
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
    }
}
