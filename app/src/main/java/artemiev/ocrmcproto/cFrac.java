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
public class cFrac {
    public IntList Numerator = new IntList(); 
    public IntList Denominator = new IntList(); 
    public int Num;
    public int Den;
    
    private static IntList Factorize(int number) {
        int n = number;
        IntList factors = new IntList();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }
    
    public void Add(cFrac f){
        Num= Num*f.Den+Den*f.Num;
        Den*= f.Den;
        Numerator.clear();
        Numerator= Factorize(Num);
        Denominator.addsorted(f.Denominator);      
    }
    
    public void Detract(cFrac f){
        Num= Num*f.Den-Den*f.Num;
        Den*= f.Den;
        Numerator.clear();
        Numerator= Factorize(Num);
        Denominator.addsorted(f.Denominator);             
    }
    
    public void Multiply(cFrac f){
    	Numerator.addsorted(f.Numerator);
	Denominator.addsorted(f.Denominator);
        Num*= f.Num;
        Den*= f.Den;
    }
    
    public void Divide(cFrac f){
    	Numerator.addsorted(f.Denominator);
	Denominator.addsorted(f.Numerator);
	Num*= f.Den;
	Den*= f.Num;       
    }
    
    public void Add(int a){
        Num+= a*Den;
        Numerator.clear();
        Numerator= Factorize(Num);
    }
    
    public void Detract(int a){
        Num-= a*Den;
        Numerator.clear();
        Numerator= Factorize(Num);       
    }
    
    public void Multiply(int a){
        Numerator.addsorted(Factorize(a));
    }
    
    public void Divide(int a){
        Denominator.addsorted(Factorize(a));
    }
    
    public void Simplify(){
        IntList.IntMember CN = Numerator.Root;
        IntList.IntMember CD = Denominator.Root;
        IntList.IntMember PN = null;
        IntList.IntMember PD = null;
        while (CN!=null){
            while (CD!=null&&CD.Value<CN.Value){
               PN= CN;
               CN= CN.Next; 
            }
            
            if (CD==null)
                return;
            
            if (CD.Value==CN.Value){
                if (PN!=null&&PD!=null){
                    Numerator.deletenext(PN);
                    Numerator.deletenext(PD);
                }
                else {
                    Numerator.deletefirst();
                    Denominator.deletefirst();
                }
            }
            
            PN= CN;
            CN= CN.Next;
        }        
    }
    
    public String Display(){
        return Integer.toString(Num) + "/" + Integer.toString(Den);
    }
    
    public String DisplayFactorized(){
        String Result= "1";
        IntList.IntMember Cur= Numerator.Root;
        while (Cur!=null) {
            Result+= "*" + Integer.toString(Cur.Value);
            Cur= Cur.Next;
        }
        
        Result+= "/";
        
        Cur= Denominator.Root;
        while (Cur!=null) {
            Result+= "*" + Integer.toString(Cur.Value);
            Cur= Cur.Next;
        }
        
        return Result;
    }
}
