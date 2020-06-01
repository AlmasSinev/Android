package com.plassrever.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    StringBuilder sb = new StringBuilder(" ");
    String result;
    boolean changeable;

    EditText edit;
    Button bCancel, bDel, bX, bDelete,
            b7,     b8,   b9, bMinus,
            b4,     b5,   b6, bPlus,
            b1,     b2,   b3, bEquals,
            bPercent, b0, bDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeable = true;


        edit = findViewById(R.id.calculating_edit);

        bCancel = findViewById(R.id.btn_c);
        bDel = findViewById(R.id.btn_del);
        bX = findViewById(R.id.btn_x);
        bDelete = findViewById(R.id.btn_delete);
        b7 = findViewById(R.id.btn_7);
        b8 = findViewById(R.id.btn_8);
        b9 = findViewById(R.id.btn_9);
        bMinus = findViewById(R.id.btn_minus);
        b4 = findViewById(R.id.btn_4);
        b5 = findViewById(R.id.btn_5);
        b6 = findViewById(R.id.btn_6);
        bPlus = findViewById(R.id.btn_plus);
        b1 = findViewById(R.id.btn_1);
        b2 = findViewById(R.id.btn_2);
        b3 = findViewById(R.id.btn_3);
        bEquals = findViewById(R.id.btn_equals);
        bPercent = findViewById(R.id.btn_percent);
        b0 = findViewById(R.id.btn_0);
        bDot = findViewById(R.id.btn_dot);

        bEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.deleteCharAt(sb.indexOf(" "));
                calculate();
                edit.setText(sb.toString());
            }
        });

    }

    public void onClickListenerBtn(View view) {
        Button btn = (Button) view;

        switch (btn.getId()){
            default:
                if (Character.isDigit(btn.getText().toString().toCharArray()[0])){
                    if (btn.getText().toString().equals("0")){
                        if (!sb.toString().equals(" 0"))
                            sb.append(btn.getText());
                    }
                    else {
                        if (!sb.toString().equals(" 0"))
                            sb.append(btn.getText());
                        else{
                            sb.deleteCharAt(sb.length() - 1);
                            sb.append(btn.getText());
                        }
                    }
                }
                else {
                    if (Character.isDigit(sb.charAt(sb.length()-1)))
                        sb.append(btn.getText());
                    else {
                        if (Character.isDigit(sb.charAt(sb.length() - 2))) {
                            sb.deleteCharAt(sb.length() - 1);
                            sb.append(btn.getText());
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            sb.deleteCharAt(sb.length() - 1);
                            sb.append(btn.getText());
                        }
                    }
                }
                break;

            case R.id.btn_minus :
                if (sb.charAt(sb.length()-1) == '-' | sb.charAt(sb.length()-1) == ',') {}
                else if (sb.charAt(sb.length()-1) == '+') { sb.deleteCharAt(sb.length()-1); sb.append("-"); }
                else sb.append("-");
                break;

            case R.id.btn_delete : sb.deleteCharAt(sb.length()-1); break;
            case R.id.btn_c : sb = new StringBuilder(" "); break;
        }
        edit.setText(sb);
    }

    private void calculate() {
        int point = 0;
        String minus = "+";
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        char[] chars = sb.toString().toCharArray();

        for (int i = 0; i < chars.length; i++){
            if (!Character.isDigit(chars[i])){
                if (chars[i] == '-' && !Character.isDigit(chars[i-1])){
                    minus = "-";
                    point++;
                }
                else{
                    char[] cs = new char[i - point];
                    int o = 0;
                    for (int j = point; j < i; j++){
                        cs[o++] = chars[j];
                    }
                    integers.add(Integer.parseInt( minus + new String(cs)));
                    strings.add(String.valueOf(chars[i]));
                    point = i+1;
                }
            }
            if (i == chars.length-1){
                if (Character.isDigit(chars[i])){
                    char[] cs = new char[chars.length - point];
                    int o = 0;
                    for (int j = point; j < chars.length; j++){
                        cs[o++] = chars[j];
                    }
                    integers.add(Integer.parseInt(minus + new String(cs)));
                }
            }
        }

        int o = 0;
        int result;

        if (strings.indexOf("x") != -1){
            result = integers.get(strings.indexOf("x")) * integers.get(strings.indexOf("x") + 1);
            System.out.println("R = " + result);
            integers.remove(strings.indexOf("x"));
            integers.remove(strings.indexOf("x"));
            strings.remove(strings.indexOf("x"));
        }
        else if (strings.indexOf("%") != -1){
            result = integers.get(strings.indexOf("%")) % integers.get(strings.indexOf("%") + 1);
            System.out.println("R = " + result);
            integers.remove(strings.indexOf("%"));
            integers.remove(strings.indexOf("%"));
            strings.remove(strings.indexOf("%"));
        }
        else if (strings.indexOf("/") != -1){
            result = integers.get(strings.indexOf("/")) / integers.get(strings.indexOf("/") + 1);
            System.out.println("R = " + result);
            integers.remove(strings.indexOf("/"));
            integers.remove(strings.indexOf("/"));
            strings.remove(strings.indexOf("/"));
        }
        else result = integers.get(o++);

        for (String s : strings){
            switch (s){
                case "+" :
                    result += integers.get(o++);
                    System.out.println("R = " + result);
                    break;
                case "-" :
                    result = integers.get(o++) - result;
                    System.out.println("R := " + result);
                    break;
            }
        }

        sb = new StringBuilder( " " + String.valueOf(result));
    }
}
