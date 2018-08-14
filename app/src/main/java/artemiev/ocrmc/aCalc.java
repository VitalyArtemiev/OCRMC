package artemiev.ocrmc;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;

import artemiev.ocrmcproto.BaseMatrix;
import artemiev.ocrmcproto.MatrixOperator;
import artemiev.ocrmcproto.RealMatrix;

import static android.text.InputType.TYPE_CLASS_DATETIME;
import static artemiev.ocrmcproto.BaseMatrix.newMatrix;

public class aCalc extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Calc Activity";
    private ToggleButton bInputA;
    private ToggleButton bInputB;
    private ToggleButton bToggleAction;
    private ToggleButton bResult;
    private TextView tMatrixTitle;
    private Spinner sOPChoice;
    private Spinner sRows;
    private Spinner sCols;
    private EditText tMatrixDisplay;

    private DrawerLayout dMatrixBuffer;
    private LinearLayout llDrawerLayout;
    private Button bAddToBuffer;
    private Button bClearBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_calc);

        dMatrixBuffer = findViewById(R.id.dMatrixBuffer);
        llDrawerLayout = findViewById(R.id.llDrawerLayout);

        bAddToBuffer = findViewById(R.id.bAddToBuffer);
        bAddToBuffer.setOnClickListener(this);

        bClearBuffer = findViewById(R.id.bClearBuffer);
        bClearBuffer.setOnClickListener(this);

        MatrixBuffer = new ArrayList<BaseMatrix>();
        MatrixIndices= new ArrayList<Integer>();

        bInputA = findViewById(R.id.bInputA);
        bInputA.setOnClickListener(this);

        bInputB = findViewById(R.id.bInputB);
        bInputB.setOnClickListener(this);

        bResult = findViewById(R.id.bResult);
        bResult.setOnClickListener(this);

        bToggleAction = findViewById(R.id.bToggleAction);
        bToggleAction.setOnClickListener(this);

        tMatrixTitle = findViewById(R.id.tMatrixTitle);

        sRows = findViewById(R.id.sRows);
        sCols = findViewById(R.id.sCols);

        tMatrixDisplay = findViewById(R.id.tMatrixDisplay);
        tMatrixDisplay.setRawInputType(TYPE_CLASS_DATETIME);

        sOPChoice = findViewById(R.id.sOPChoice);
        sOPChoice.setOnItemSelectedListener(this);

        tMatrixDisplay.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_FLAG_NO_ENTER_ACTION:{
                        tMatrixDisplay.append("\n");
                        Toast.makeText(getApplicationContext(), "nea", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    default:
                        return false;
                }
            }
        });
    }

    private BaseMatrix A;
    private BaseMatrix B;
    private BaseMatrix C;
    private ArrayList<BaseMatrix> MatrixBuffer;
    private ArrayList<Integer> MatrixIndices;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getApplicationContext(), (String) sOPChoice.getSelectedItem(), Toast.LENGTH_SHORT).show();
        switch ((String) sOPChoice.getSelectedItem()) {
            case "Sum": {
                bInputB.setVisibility(View.VISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("+");
                bToggleAction.setTextOn("-");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(true);
            } break;

            case "Mul": {
                bInputB.setVisibility(View.VISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("*");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(false);
            } break;

            case "Pow": {
                bInputB.setVisibility(View.INVISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("^");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(false);
            } break;

            case "Transp": {
                bInputB.setVisibility(View.INVISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("T");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(false);
            } break;

            case "Det": {
                bInputB.setVisibility(View.INVISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("det");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(false);
            } break;

            case "REF": {
                bInputB.setVisibility(View.INVISIBLE);
                bToggleAction.setVisibility(View.VISIBLE);
                bToggleAction.setTextOff("REF");
                bToggleAction.setChecked(false);
                bToggleAction.setEnabled(false);
            } break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private enum eMatrixState {
        sInputA,
        sInputB,
        sResult
    }

    private eMatrixState State= eMatrixState.sInputA;

    private void ChangeState(eMatrixState s){
        try {
            switch (State) {
                case sInputA: {
                    A = newMatrix(tMatrixDisplay);
                }
                break;
                case sInputB: {
                    B = newMatrix(tMatrixDisplay);
                }
                break;
                case sResult: {

                }
                break;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        switch (s) {
            case sInputA: {
                tMatrixTitle.setText("Input matrix A");
                bInputB.setChecked(false);
                bResult.setChecked(false);
                sRows.setEnabled(true);
                sCols.setEnabled(true);

                if (A!=null)
                    tMatrixDisplay.setText(A.asString());
                else
                    tMatrixDisplay.setText("");
            } break;

            case sInputB: {
                tMatrixTitle.setText("Input matrix B");
                bInputA.setChecked(false);
                bResult.setChecked(false);
                sRows.setEnabled(true);
                sCols.setEnabled(true);

                if (B!=null)
                    tMatrixDisplay.setText(B.asString());
                else
                    tMatrixDisplay.setText("");
            } break;

            case sResult: {
                Calculate();
                tMatrixTitle.setText("Resulting matrix:");
                bInputA.setChecked(false);
                bInputB.setChecked(false);
                bResult.setChecked(true);

                if (C!=null)
                    tMatrixDisplay.setText(C.asString());
                else
                    tMatrixDisplay.setText("");
            } break;
        }
        State= s;
    }

    private static final int RC_OCR_CAPTURE = 9003;

    @Override
    public void onClick(View view) {
        int ViewID= view.getId();
        switch (ViewID) {
            case R.id.bInputA: {
                if (bInputA.isChecked()) {
                    ChangeState(eMatrixState.sInputA);
                }
                else {
                    //Calculate();
                    ChangeState(eMatrixState.sResult);
                }
            }
            break;
            case R.id.bInputB: {
                if (bInputB.isChecked()) {
                    ChangeState(eMatrixState.sInputB);
                }
                else {
                    //Calculate();
                    ChangeState(eMatrixState.sResult);
                }
            } break;
            //case R.id.bToggleAction: {};
            case R.id.bResult: {
                //Calculate();
                ChangeState(eMatrixState.sResult);
            } break;

            case R.id.bActivateOcr: {
                // launch Ocr capture activity.
                Intent intent = new Intent(this, OCRActivity.class);
                intent.putExtra(OCRActivity.AutoFocus, true);
                intent.putExtra(OCRActivity.UseFlash, true);

                startActivityForResult(intent, RC_OCR_CAPTURE);
            } break;

            case R.id.bOpenBuffer: {
                dMatrixBuffer.openDrawer(GravityCompat.END);
            } break;

            case R.id.bAddToBuffer: {
                try {
                    MatrixBuffer.add(newMatrix(tMatrixDisplay));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Button btn = new Button(this);

                int ID= View.generateViewId();
                btn.setId(ID);

                MatrixIndices.add(ID);

                btn.setLongClickable(true);

                /*AlertDialog.Builder alert = new AlertDialog.Builder(this);

                final EditText edittext = new EditText(this);
                alert.setMessage("Enter matrix name");
                alert.setTitle("Matrix buffer");

                alert.setView(edittext);
                alert.setCancelable(false);
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        edittext.setText("Matrix "+ String.valueOf(ID));
                        MatrixName = edittext.getText().toString();
                    }
                });

                alert.show();*/

                /*if (MatrixName!="")
                    btn.setText(MatrixName);
                else*/
                btn.setText("Matrix "+ String.valueOf(ID));
                /*btn.setLayoutParams(new Button.LayoutParams(LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));*/
                btn.setOnClickListener(this);
                llDrawerLayout.addView(btn);
            } break;

            case R.id.bClearBuffer: {
                MatrixBuffer.clear();
                while (!MatrixIndices.isEmpty()) {
                    llDrawerLayout.removeView(findViewById(MatrixIndices.get(0)));
                    MatrixIndices.remove(0);
                }

            } break;

            default: {
                int Index= MatrixIndices.indexOf(ViewID);
                if (Index!= -1) {
                    //MatrixIndices.remove(Index);
                    tMatrixDisplay.setText(MatrixBuffer.get(Index).asString());
                }
            }
        }

    }

    private void Calculate(){
        if (A==null&&B!=null)
            A = new RealMatrix(B.Rows, B.Cols);
        switch ((String) sOPChoice.getSelectedItem()) {
            case "Sum": {
                if (A==null&&B!=null)
                    A = new RealMatrix(B.Rows, B.Cols);

                if (B==null&&A!=null)
                    B = new RealMatrix(A.Rows, A.Cols);

                if (A!=null&&B!=null) {
                    if (bToggleAction.isChecked())
                        C = MatrixOperator.INSTANCE.sum(A, MatrixOperator.INSTANCE.invert(B));
                    else
                        C = MatrixOperator.INSTANCE.sum(A, B);
                }
                else
                    C = new RealMatrix(0, 0);
            } break;

            case "Mul": {
                if (A==null&&B!=null)
                    A = new RealMatrix(B.Cols, B.Rows);
                if (B==null&&A!=null)
                    B = new RealMatrix(A.Cols, A.Rows);

                if (A!=null&&B!=null) {
                    C = MatrixOperator.INSTANCE.multiply(A, B);
                }
                else
                    C = new RealMatrix(0, 0);
            } break;

            //case "Pow": {for (int i=0; i<R= MatrixOperator.times(A,B);} break;
            case "Transp": {
                if (A==null)
                    A = new RealMatrix(1, 1);
                if (A!=null)
                    C = MatrixOperator.INSTANCE.transpose(A);
                else
                    C = new RealMatrix(0, 0);
            } break;
            case "Det": {
                C = new RealMatrix(1, 1);
                if (A!=null)
                    ((RealMatrix) C).M[0][0] = MatrixOperator.INSTANCE.det((RealMatrix) A);
                else
                    ((RealMatrix) C).M[0][0] = 0;
            } break;
            case "REF": {} break;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.dMatrixBuffer.isDrawerOpen(GravityCompat.END)) {
            this.dMatrixBuffer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OCRActivity.TextBlockObject);
                    /*statusMessage.setText(R.string.ocr_success);
                    textValue.setText(text);*/
                    tMatrixDisplay.setText(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    //statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                /*statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));*/
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
