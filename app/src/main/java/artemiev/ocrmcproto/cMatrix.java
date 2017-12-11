/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artemiev.ocrmcproto;

import android.widget.EditText;

/**
 *
 * @author Виталий
 */
public class cMatrix {
    public /*cFrac*/double M[][];
    public int Rows;
    public int Cols;
    public cMatrix(int r, int c){
        Rows= r;
        Cols= c;
        M= new /*cFrac*/double[r][c];
    }
    public cMatrix(EditText t){

        /*var
        i, j, e: integer;
        s: string;
        a: double;*/

        Rows= t.getLineCount();

        String multiLines = t.getText().toString();
        String[] Lines= multiLines.split("\n");
        String[][] Elements = new String[Lines.length][];

        for (int i=0; i<Lines.length; i++){
            Elements[i]=  Lines[i].split(" ");
            if (Elements[i].length>Cols)
                Cols= Elements[i].length;
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(Rows) + ' ' + String.valueOf(Cols), Toast.LENGTH_SHORT).show();

        M= new /*cFrac*/double[Rows][Cols];


        for (int i= 0; i<Rows; i++)
            for (int j= 0; j<Cols; j++) {
                if (j<Elements[i].length)
                    try {
                        M[i][j] = Double.valueOf(Elements[i][j]);
                    }
                    catch (NumberFormatException e) {
                        M[i][j] = 0;
                    }
                else
                    M[i][j]= 0;
                /*if e<> 0 then
                raise Exception.Create('invalid value' + s);*/

            };

        
    }
    
    void SwapRow(int r1, int r2){
       
        //double a[]=;
        //i: integer;

  //writeln('swap start');
 // Print;

  double a[]= M[r2];
  M[r2]= M[r1];
  M[r1]= a;
  /*for i= 0 to Col - 1 do
  M[r1, i]*= -1; //change sign so to not change det  */

  //writeln('swap stop');
  //Print;

    }
    
    /*void PasteToGrid(G: tStringGrid){
        
    }*/
            
    public cMatrix Copy(){
        cMatrix R= new cMatrix(Rows, Cols);
        for (int i= 0; i< Rows; i++)
            System.arraycopy(M[i], 0, R.M[i], 0, Cols);
        return R;
    }
    
    public String AsString(){
        /*
        i, j: integer;*/
        String s= "";
            //writeln(Row, ' ', Col);
        for (int i=0; i<Rows; i++) {
            for (int j=0; j<Cols; j++)
                s+= Double.toString(M[i][j]) + ' ';

            if (i!= Rows-1)
                s+= '\n';
        };
        return s;
    }

 //constructor Create(G: tStringGrid);
}
/*

uses
  Classes, SysUtils, Grids;

type
  d2a = array of array of double;

  { tMatrix }

  tMatrix = class
  private
    fCol: word;
    fRow: word;
    M: d2a;
    function getM(i, j: integer): double;
    procedure SetCol(AValue: word);
    procedure setM(i, j: integer; AValue: double);
    procedure SetRow(AValue: word);

  public
    property pM[i, j: integer]: double read getM write setM; default;
    property Col: word read fCol write SetCol;
    property Row: word read fRow write SetRow;

    procedure SwapRow(r1, r2: integer);
    procedure PasteToGrid(G: tStringGrid);
    function Copy: tMatrix;
    procedure Print;

    constructor Create(G: tStringGrid);
    constructor Create(R, C: word);
    destructor Destroy; override;
  end;

function MultMatrix(a, b: tMatrix): tMatrix;
function SumMatrix(a, b: tMatrix): tMatrix;
function DetMatrix(m: tMatrix): double;
function InvertMatrix(a: tMatrix): tMatrix;
function TransponMatrix(a: tMatrix): tMatrix;

implementation

uses
  util, dialogs, unit1;





{ tMatrix }


procedure tMatrix.Print;
var
  i, j: integer;
begin
  writeln(Row, ' ', Col);
  for i:= 0 to Row - 1 do
  begin
    for j:= 0 to Col - 1 do
      write(M[i, j]:0:2,  ' ');

    writeln;
  end;

  writeln;
end;

procedure tMatrix.SwapRow(r1, r2: integer);
var
  a: array of double;
  //i: integer;
begin
  //writeln('swap start');
 // Print;

  a:= M[r2];
  M[r2]:= M[r1];
  M[r1]:= a;
  {for i:= 0 to Col - 1 do
  M[r1, i]*= -1; //change sign so to not change det  }

  //writeln('swap stop');
  //Print;
end;

procedure tMatrix.PasteToGrid(G: tStringGrid);
var
  i, j: integer;
begin
  if (G.RowCount < fRow) or (G.ColCount < fCol) then
    raise Exception.Create('invalid grid size');

  for i:= 0 to fRow - 1 do
    for j:= 0 to fCol - 1 do
    begin
      if frac(M[i,j]) = 0 then
        G.Cells[j,i]:= strf(integer(trunc(M[i,j]))) //tstringgrid inverted
      else
        G.Cells[j,i]:= strf(M[i,j]);
    end;
end;

function tMatrix.Copy: tMatrix;
var
  i, j: integer;
begin
  Result:= tMatrix.Create(Row, Col);
  for i:= 0 to Row - 1 do
    for j:= 0 to Col - 1 do
      Result[i,j]:= M[i,j]
end;

constructor tMatrix.Create(G: tStringGrid);
var
  i, j, e: integer;
  s: string;
  a: double;
begin
  fRow:= G.RowCount;
  fCol:= G.ColCount;
  setlength(M, fRow, fCol);

  for i:= 0 to fRow - 1 do
    for j:= 0 to fCol - 1 do
    begin
      s:= G.Cells[j,i]; //tstringgrid inverted
      val(s, a, e);
      if e<>0 then
        raise Exception.Create('invalid value' + s);
      M[i,j]:= a;
    end;
end;

constructor tMatrix.Create(R, C: word);
begin
  fRow:= R;
  fCol:= C;
  setlength(M, R, C);
end;

*/