package Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.ceil;

public class Calculator {

    private boolean includeDecimal=true;
    private int openingBrackets=0;
    private int closingBrackets=0;
    private StringBuffer buff = new StringBuffer("");
    private ArrayList<String> arrayList = new ArrayList<String>();
    private boolean canConvertToNegative = true;
    private boolean canInsertNumber = true;
    private boolean canAddLeftParenthesisBool = true;
    private boolean canAddRightParenthesisBool = false;

    @FXML
    private TextField result;

    @FXML
    private Label input;

    private void operatorUtility(String str){
        if(buff.length()>0) {
            if(buff.charAt(buff.length()-1)=='.'){
                buff.deleteCharAt(buff.length()-1);
                if(buff.length()==0){
                    return;
                }
            }
            if(canInsertNumber == true){
                arrayList.add(buff.toString());
                input.setText(input.getText() + buff.toString() + str);
            }
            else{
                input.setText(input.getText() + str);
            }
            arrayList.add(str);
            buff.delete(0, buff.length());
            result.setText("");
            includeDecimal = true;
            canConvertToNegative = true;
            canInsertNumber = true;
            canAddLeftParenthesisBool = true;
            canAddRightParenthesisBool = false;
        }
    }

    private void operandUtility(String str){
        if(canInsertNumber){
            buff.append(str);
            result.setText(buff.toString());
            canAddLeftParenthesisBool = false;
            canAddRightParenthesisBool = true;
        }
    }

    private boolean canAddLeftParenthesis(){
        if((buff.length()==0) || (canAddLeftParenthesisBool && buff.length()>0 && buff.charAt(buff.length()-1)!='.'))
            return true;
        return false;
    }

    private boolean canAddRightParenthesis(){
        if(openingBrackets>closingBrackets && canAddRightParenthesisBool && buff.length()>0 && buff.charAt(buff.length()-1)!='.')
            return true;
        return false;
    }

    public void clear(ActionEvent event){
        buff.delete(0,buff.length());
        result.setText("");
        input.setText("");
        arrayList.clear();
        includeDecimal = true;
        openingBrackets = 0;
        closingBrackets = 0;
        canConvertToNegative = true;
        canInsertNumber = true;
        canAddLeftParenthesisBool = true;
        canAddRightParenthesisBool = false;
    }

    public void backspace(ActionEvent event){
        if(buff.length()>0){
            if(buff.charAt(buff.length()-1)=='.'){
                includeDecimal = true;
            }
            buff.deleteCharAt(buff.length()-1);
            result.setText(buff.toString());
        }
    }

    public void pressed0(ActionEvent event){
        operandUtility("0");
    }

    public void pressed1(ActionEvent event){
        operandUtility("1");
    }

    public void pressed2(ActionEvent event){
        operandUtility("2");
    }

    public void pressed3(ActionEvent event){
        operandUtility("3");
    }

    public void pressed4(ActionEvent event){
        operandUtility("4");
    }



    public void pressed5(ActionEvent event){
        operandUtility("5");
    }

    public void pressed6(ActionEvent event){
        operandUtility("6");
    }

    public void pressed7(ActionEvent event){
        operandUtility("7");
    }

    public void pressed8(ActionEvent event) {
        operandUtility("8");
    }

    public void pressed9(ActionEvent event){
        operandUtility("9");
    }

    public void pressedDecimal(ActionEvent event){
        if(includeDecimal){
            buff.append(".");
            result.setText(buff.toString());
        }
        includeDecimal=false;
    }

    public void pressedPlusMinus(ActionEvent event){
        if(buff.length() > 0) {
            if(canConvertToNegative) {
                buff.insert(0, "-");
            }
            else {
                buff.deleteCharAt(0);
            }
        }
        result.setText(buff.toString());
        canConvertToNegative^=true;
    }

    public void pressedAdd(ActionEvent event){
        operatorUtility("+");
    }

    public void pressedSubtract(ActionEvent event){
        operatorUtility("-");
    }

    public void pressedMultiply(ActionEvent event) {
        operatorUtility("*");
    }

    public void pressedDivide(ActionEvent event){
        operatorUtility("/");
    }

    public void pressedModulus(ActionEvent event){
        operatorUtility("%");
    }

    public void pressedPower(ActionEvent event){
        operatorUtility("^");
    }

    public void pressedCeil(ActionEvent event) {
        if(buff.length()>0) {
            float floatNum = (float) Math.ceil(Float.parseFloat(buff.toString()));
            System.out.println(floatNum);
            arrayList.add(Float.toString(floatNum));
            input.setText(input.getText() + "ceil(" + buff.toString() + ")");
            canInsertNumber = false;
        }
    }

    public void pressedFloor(ActionEvent event){
        if(buff.length()>0) {
            float floatNum = (float) Math.floor(Float.parseFloat(buff.toString()));
            arrayList.add(Float.toString(floatNum));
            input.setText(input.getText() + "floor(" + buff.toString() + ")");
            canInsertNumber = false;
        }
    }

    public void pressedLeftParenthesis(ActionEvent event){
        if(canAddLeftParenthesis()){
            input.setText(input.getText()+"(");
            arrayList.add("(");
            openingBrackets++;
        }
    }

    public void pressedRightParenthesis(ActionEvent event){
        if(canAddRightParenthesis()){
            operatorUtility(")");
            buff.append(")");
            canInsertNumber = false;
            canAddRightParenthesisBool = true;
            closingBrackets++;
        }
    }


    public void getIsPrime(ActionEvent event){
        String str = result.getText();
        String stringIsPrime="Prime";
        boolean flag = true;
        try {
            float x = Float.parseFloat(str);
            if (Math.ceil(x) == Math.floor(x)) {
                int n = (int) Math.floor(x);
                for (int i = 2; i * i <= n; i++) {
                    if (n % i == 0) {
                        flag = false;
                        break;
                    }
                }
                if(!flag || n==1){
                    stringIsPrime="Not a prime";
                }
            } else {
                stringIsPrime="Enter an integer value";
            }
        }
        catch (Exception e){
            stringIsPrime="Invalid Expression";
        }
        result.setText(stringIsPrime);
    }

    public void getResult(ActionEvent event){
        //System.out.println(buff.toString().charAt());
        for(String str: arrayList)
            System.out.print(str);
        Stack<String> s = new Stack<String>();
    }
}
