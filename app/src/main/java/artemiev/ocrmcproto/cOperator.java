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
public class cOperator {
    
public static cMatrix MultMatrix(cMatrix a, cMatrix b)
//var
 // i, j, k: word;
{
  cMatrix R= new cMatrix(a.Rows, b.Cols);  //change to Strassen algorithm
  int i, j, k;
  for (i= 0; i<R.Rows; i++)
    for (j=0; j<R.Cols; j++)
    {
      R.M[i][j]= 0;
      for (k= 0; k< a.Cols; k++)
        R.M[i][j]+= a.M[i][k] * b.M[k][j];
    } 
  return R;
}

  public static cMatrix SumMatrix(cMatrix a, cMatrix b)
//var
  //i, j: word;
{
  cMatrix R= new cMatrix(a.Rows, b.Cols);
  int i, j;
  
  for (i= 0; i<R.Rows;i++) 
    for (j= 0; j< R.Cols;j++) 
    {
      R.M[i][j]= a.M[i][j] + b.M[i][j];
    }
  return R;
}

public static cMatrix[] LUDecompose(cMatrix a)
/*var
  i, j, k: integer;
  s: double;*/
{
  cMatrix u= new cMatrix(a.Rows, a.Cols);
  cMatrix l= new cMatrix(a.Rows, a.Cols);
  int i,j,k;

  for (i= 0; i<l.Rows; i++)
  {
    u.M[0][i]= a.M[0][i];
    l.M[i][i]= 1;
  }

  for (i= 0; i<u.Rows; i++)
  {
    for (j= i; j<u.Rows; j++)
    {
      double s= 0;
      for (k= 0; k<i; k++)
        s+= l.M[i][k] * u.M[k][j];
      u.M[i][j]= a.M[i][j] - s;
    }

    for (j= i + 1; j< u.Rows; j++)
    {
      double s= 0;
      for (k= 0; k<i; k++)
        s+= l.M[j][k] * u.M[k][i];

      /*writeln('u');
      u.Print;
      writeln('l');
      l.Print;
      writeln(i,' ',j);*/

      if (u.M[i][i] != 0) 
        l.M[j][i]= (a.M[j][i] - s)/u.M[i][i];
      else
        l.M[j][i]= 1; //???
    }
  }
  
  cMatrix[] R= new cMatrix[2];
  R[0] = u;
  R[1] = l;
  
  return R;

 /* mainform.m1.ColsCount= u.Cols;
  mainform.m1.RowsCount= u.Cols;
  mainform.m2.ColsCount= u.Cols;
  mainform.m2.RowsCount= u.Cols;
  u.PasteToGrid(MainForm.M1);
  l.PasteToGrid(MainForm.M2);*/
}

    public static double DetMatrix(cMatrix m)
/*var
  i, j, k: integer;
  s: double;
  a: tMatrix;*/
{
  cMatrix a= m.Copy();
  int i, j, k;
  for (i= 0; i<a.Cols; i++)   //this ignores extra rows
  {
    for (j= i; j<a.Cols; j++)
    {
      double s= 0;
      for (k= 0; k<i; k++)
        s+= a.M[i][k] * a.M[k][j];
      a.M[i][j]= a.M[i][j] - s;
    }

    for (j= i + 1; j<a.Cols; j++)
    {
      double s= 0;
      for (k= 0; k<i; k++)
        s+= a.M[j][k] * a.M[k][i];

      if (a.M[i][i] != 0)
        a.M[j][i]= (a.M[j][i] - s)/a.M[i][i];
      else
        return(0);
    }
  }

  double R= 1;

  for (i= 0; i<a.Cols; i++)
    R*= a.M[i][i];
  a= null;
  return R;
}

    public static cMatrix InvertMatrix(cMatrix a)
//var
  //i, j: integer;
{
  cMatrix R= new cMatrix(a.Rows, a.Cols);
  int i, j;
  for (i= 0; i< R.Rows; i++)
    for (j= 0; j<R.Cols; j++)
    {
      R.M[i][j]= - a.M[i][j];
    }
  return R;
}

    public static cMatrix TransponMatrix(cMatrix a)
//var
  //i, j: integer;
{
  int i, j;
  cMatrix R= new cMatrix(a.Cols, a.Rows);
    for (i= 0; i<R.Rows; i++)
      for (j= 0; j<R.Cols; j++)
        R.M[i][j]= a.M[j][i];
  return R;
}
    
}
