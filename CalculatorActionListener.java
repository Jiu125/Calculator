import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * 연산에 필요한 이벤트들을 담을 파일
 */
public class CalculatorActionListener {
    CalculatorActionListener() {
        Stack<String> stackStr = new Stack<>();
    }

    ActionListener add, pop, printResult;
}

class addNum implements ActionListener {
//    ActionListener addNum, back, clear, clearEntry, plus, minus, multiply, divide, percent, pow, root, inverse;

    JButton btn;
    JTextField text;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn || e.getSource() == text) {
            String name = text.getText();
            int val = Integer.parseInt(name);
            text.setText("" + val);
            text.requestFocus();
        }
    }
}
