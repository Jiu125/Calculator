package main;

import javax.swing.*;
import java.util.Objects;
import java.util.Stack;

public class OperationMethod {
    // 더하기 연산
    public static void add(JTextField privew, Stack temp, Stack preveiwStack, String resultStr, String previewStr, double num) {

        if (Objects.equals(resultStr, "0")) {

            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "+")) {
                preveiwStack.push("+");
                privew.setText(String.join(" ", preveiwStack));
            }
            privew.setText("0 +  ");
            temp.clear();
        }
        else if (!preveiwStack.isEmpty() || num != 0) { // 'A + ' => "3 + " 이케 떠야함

            preveiwStack.push(previewStr);
            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "+"))
                preveiwStack.push("+");
            privew.setText(String.format("%s +  ", previewStr));
            temp.clear();
        }
    }

    // 빼기 연산
    public static void sub(JTextField result, JTextField privew, Stack temp, Stack preveiwStack, String previewStr, double num) {
        if (Objects.equals(result.getText(), "0")) {
            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "-")) {
                preveiwStack.push("-");
                privew.setText(String.join(" ", preveiwStack));
            }
            privew.setText("0 -  ");
            temp.clear();
        }
        else if (!preveiwStack.isEmpty() || num != 0) {
            preveiwStack.push(previewStr);

            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "-"))
                preveiwStack.push("-");

            privew.setText(String.format("%s -  ", previewStr));
            temp.clear();
        }
    }

    // 곱하기 연산
    public static void mul(JTextField result, JTextField privew, Stack temp, Stack preveiwStack, String previewStr, double num) {
        if (Objects.equals(result.getText(), "0")) {
            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "×")) {
                preveiwStack.push("×");
                privew.setText(String.join(" ", preveiwStack));
            }
            privew.setText("0 *  ");
            temp.clear();
        }
        else if (!preveiwStack.isEmpty() || num != 0) {
            preveiwStack.push(previewStr);

            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "×"))
                preveiwStack.push("×");

            privew.setText(String.format("%s ×  ", previewStr));
            temp.clear();
        }
    }

    // 나누기 연산
    public static void div(JTextField result, JTextField privew, Stack temp, Stack preveiwStack, String previewStr, double num) {
        if (Objects.equals(result.getText(), "0")) {
            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "÷")) {
                preveiwStack.push("÷");
                privew.setText(String.join(" ", preveiwStack));
            }
            privew.setText("0 ÷  ");
            temp.clear();
        }
        else if (!preveiwStack.isEmpty() || num != 0) {
            preveiwStack.push(previewStr);

            if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "÷"))
                preveiwStack.push("÷");

            privew.setText(String.format("%s ÷  ", previewStr));
            temp.clear();
        }
    }
}
