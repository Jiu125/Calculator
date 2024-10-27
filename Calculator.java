import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;
import java.text.DecimalFormat;

class JButtonMemory extends JButton {
    Font font = new Font("맑은 고딕", Font.PLAIN, 13);
    Color c = new Color(0xeeeeee);

    public JButtonMemory(String text) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getSource().toString());
            }
        });
    }
}

/**
 * 연산 버튼
 */
class JButtonS extends JButton {
    Color c = new Color(0xfbfbfb);
    Font font = new Font("맑은 고딕", Font.PLAIN, 14);

    public static String formatDouble(double number) {
        // 소수점 이하가 0인지 확인
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    public JButtonS(String text, JTextField  result, JTextField  privew, Stack temp, Stack preveiwStack) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strNum = "";

                for(int i=0; i < temp.size(); i++) { // 하나의 문자열로 가공
                    strNum += temp.get(i);
                }

                int num;
                String numStr = strNum.replaceFirst("^0+", ""); // 0은 한 번만 나오게 가공
                if(numStr == "" || numStr.isBlank()) // 문자의 가독성을 높이는 가공
                    num = 0;
                else
                    num = Integer.parseInt(numStr);

                // 퍼센트 연산 기능 처리
                if(Objects.equals(text, "％")){
                    double percentNum;
                    if(preveiwStack.isEmpty() || Objects.equals(result.getText(), "0")){
                        privew.setText("0");
                        result.setText("0");
                    }
                    else if(preveiwStack.size() >= 2){
                        percentNum = Double.parseDouble(String.valueOf(preveiwStack.get(0)));
                        String oper = String.valueOf(preveiwStack.get(1));
                        double percentResult = (percentNum/100) * percentNum;
                        privew.setText(String.format("%s %s %s", result.getText(), oper, formatDouble(percentResult)));
                        result.setText(String.format("%s", formatDouble(percentResult)));
                    }
                }

                //전체 리셋 기능 처리
                if(Objects.equals(text, "C")){
                    preveiwStack.clear();
                    temp.clear();
                    privew.setText("");
                    result.setText("0");
                }

                // 결과창 리셋 기능 처리
                if(Objects.equals(text, "CE")){
                    temp.clear();
                    result.setText("0");
                }
                // 지우기 연산 처리
                if(Objects.equals(text, "<")){
                    String str = result.getText();
                    str = str.substring(0, str.length()-1);
                    temp.pop();
                    if(str.isEmpty()){
                        result.setText("0");
                    } else
                        result.setText(str);
                }

                // 1/(A) 연산 처리
                if(Objects.equals(text, "¹／χ")){
                    double number = Double.parseDouble(result.getText());
                    number = 1 / number;
                    privew.setText(String.format("1/(%s)", result.getText()));
                    result.setText(String.format("%s", formatDouble(number)));
                }

                // sqr 연산 처리
                if(Objects.equals(text, "χ²")){
                    double number = Double.parseDouble(result.getText());
                    number *= number;
                    privew.setText(String.format("sqr(%s)", result.getText()));
                    result.setText(String.format("%s", formatDouble(number)));
                }

                // root 연산 처리
                if(Objects.equals(text, "²√χ")){
                    double number = Double.parseDouble(result.getText());
                    number = Math.sqrt(number);
                    privew.setText(String.format("√(%s)", result.getText()));
                    result.setText(String.format("%s", formatDouble(number)));
                }

                // 더하기 연산 처리
                if (Objects.equals(text, "+")) {

//                     비어있을 때는 => "0 + " 이게 뜸
                    if (Objects.equals(result.getText(), "0")) {

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "+")) {
                            preveiwStack.push("+");
                            privew.setText(String.join(" ", preveiwStack));
                        }
                        privew.setText("0 +  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) { // 'A + ' => "3 + " 이케 떠야함
                        preveiwStack.push(result.getText());
                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "+"))
                            preveiwStack.push("+");
                        privew.setText(String.format("%s +  ", result.getText()));
                        temp.clear();
                    }
                }

                // 빼기 연산 처리
                if (Objects.equals(text, "-")) {

                    if (Objects.equals(result.getText(), "0")) {
                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "-")) {
                            preveiwStack.push("-");
                            privew.setText(String.join(" ", preveiwStack));
                        }
                        privew.setText("0 -  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) {
                        preveiwStack.push(result.getText());

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "-"))
                            preveiwStack.push("-");

                        privew.setText(String.format("%s -  ", result.getText()));
                        temp.clear();
                    }
                }

                // 곱하기 연산 처리
                if (Objects.equals(text, "×")) {

                    if (Objects.equals(result.getText(), "0")) {
                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "×")) {
                            preveiwStack.push("×");
                            privew.setText(String.join(" ", preveiwStack));
                        }
                        privew.setText("0 *  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) {
                        preveiwStack.push(result.getText());

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "×"))
                            preveiwStack.push("×");

                        privew.setText(String.format("%s ×  ", result.getText()));
                        temp.clear();
                    }
                }

                // 나누기 연산 처리
                if (Objects.equals(text, "÷")) {

                    if (Objects.equals(result.getText(), "0")) {
                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "÷")) {
                            preveiwStack.push("*");
                            privew.setText(String.join(" ", preveiwStack));
                        }
                        privew.setText("0 ÷  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) {
                        preveiwStack.push(result.getText());

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "÷"))
                            preveiwStack.push("÷");

                        privew.setText(String.format("%s ÷  ", result.getText()));
                        temp.clear();
                    }
                }


            }
        });
    }
}

/**
 * 숫자 버튼
 */
class JButtonWhite extends JButton {
    Font font = new Font("맑은 고딕", Font.PLAIN, 18);

    public static String formatDouble(double number) {
        // 소수점 이하가 0인지 확인
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    public JButtonWhite(String text, JTextField  result, JTextField preview, Stack temp) {
        super.setBackground(Color.WHITE);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(font);
        setPreferredSize(new Dimension(77, 46));
        this.setText(text);

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String btnStr = getText();
                if (btnStr.matches("\\d+")) {  // GPT로 정규식 물어보고 가져옴 숫자일 때 경우
                    if (temp.isEmpty() && btnStr.equals("0")) {
                        return;  // 첫 숫자가 0일 때 스택에 추가하지 않고 리턴
                    }

                    temp.push(btnStr);

                    String str = "";
                    for (int i = 0; i < temp.size(); i++) {
                        str += temp.get(i);
                    }
                    result.setText(str);
                }

                // 부호 바꾸기 연산 처리 (negate)
                if(Objects.equals(text, "+/-")){
                    double number = Double.parseDouble(result.getText());
                    preview.setText(String.format("negate(%s)", formatDouble(number)));
                    number *= -1;
                    result.setText(String.format("%s", formatDouble(number)));
                }
            }
        });
    }
}

/**
 *  계산기 디자인 파일
 *  코드 구조 참고
 * @link : https://github.com/tejasmanohar/jframe-calculator/blob/master/src/calculator/Calculator.java
 *
 */
public class Calculator extends JFrame {
    Stack<String> result = new Stack<>();
    Stack<String> temp = new Stack<>();
    Stack<String> preResult = new Stack<>();
    Color c = new Color(0xeeeeee);
    String[] memoryArr = {"MC", "MR", "M+", "M-", "MS", "M∨"};
    String[] numberPadArr = {
            "％", "CE", "C", "<",
            "¹／χ", "χ²", "²√χ", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
    };
    JTextField preview, resultView;

    public Calculator() {
        setTitle("계산기");
        setMinimumSize(new Dimension(336, 509));
        setSize(340, 510);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("img/cal.png");
        setIconImage(img);

        showMenu();
        showResult();
        showNumBtn();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * float 값의 소수점 이하가 0일 때 정수로 출력하고, 소수점이 있을 때는 소수점을 표시하는 코드
     * @param number : 결과 값
     * @return 결과 값의 소수점 여부와 0의 여부를 확인하여 가공한 값
     * 이거 나중에 다른 class에 작성해서 적용
     */
    public static String formatDouble(double number) {
        // 소수점 이하가 0인지 확인
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    /**
     * 계산기 스택의 기호를 찾는 방법을 GPT를 사용해 적용함.
     */
    public void arithmetic(Stack preResult) {
        // 연산자를 찾기 위해 preResult 리스트를 순회
        String operation = null;
        int position = -1;
        DecimalFormat df = new DecimalFormat("0.##");

        // 연산자 찾기
        for (int i = preResult.size()-1; i >= 0; i--) {
            String current = (String) preResult.get(i);
            if (isOperator(current)) {
                operation = current;
                position = i ;
                break;
            }
        }

        int index = position;


//        System.out.println("index: " + index);
//        System.out.println("position: " + position);
//        System.out.println("preResult.size: " + preResult.size());
//        System.out.println("index - 1: " + preResult.get(index - 1));
//        System.out.println("index: " + preResult.get(index));
//        System.out.println(preResult);

        Double num2 = Double.parseDouble(String.valueOf(preResult.get(index + 1))); // 뒤에 있는 수
        if ((index - 1) == -1) {
            preview.setText(String.format("0 %s %s =  ", operation, formatDouble(num2)));
            resultView.setText(String.format("%s", formatDouble(num2)));
        }
        else {
            Double num1 = Double.parseDouble(String.valueOf(preResult.get(index - 1))); // 앞에 있는 수
            Double num3;

            if (position == -1) {
                preview.setText(String.format("%s =  ", resultView.getText()));
            } else {
                num3 = Double.parseDouble(resultView.getText());

                Double result = calculateResult(num1, num2, operation);

                // 계산 결과가 에러인 경우 (예: 0으로 나누기)
                if (result == Integer.MIN_VALUE) {
                    preview.setText("0으로 나눌 수 없습니다");
                    return;
                }

//                System.out.println("num2: "+num2);
//                System.out.println("num3: "+num3);

                if (num2.equals(num3)) {
                    preview.setText(String.format("%s %s %s =  ", formatDouble(num1), operation, formatDouble(num2)));
                    resultView.setText(String.format("%s", formatDouble(result)));
                } else if (preResult.get(index - 1) == null) {
                    preview.setText(String.format("0 %s %s =  ", operation, formatDouble(num2)));
                    resultView.setText(String.format("%s", formatDouble(result)));
                } else {
                    String doubleResult = formatDouble(calculateResult(num3, num2, operation));
                    preview.setText(String.format("%s %s %s =  ", formatDouble(num3), operation, formatDouble(num2)));
                    resultView.setText(String.format("%s", doubleResult));
                }
            }
        }
        temp.clear();
    }

    // 연산자인지 확인하는 헬퍼 메소드
    private boolean isOperator(String str) {
        return "+".equals(str) || "-".equals(str) ||
                "×".equals(str) || "÷".equals(str);
    }

    // 실제 계산을 수행하는 헬퍼 메소드
    private double calculateResult(Double num1, Double num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "×":
                return num1 * num2;
            case "÷":
                if (num2 != 0) {
                    return  (double) num1 / num2;
                }
                return Double.MIN_VALUE; // 에러 표시를 위한 특별한 값
            default:
                return Double.MIN_VALUE;
        }
    }

    /**
     *  계산기 설정, 기록 확인 버튼
     *  showResult()를 여기에 넣어서 같은 panel에 두고 layout 설정해보자.
     */
    private void showMenu() {
        ImageIcon recordImg = new ImageIcon("img/record.png");
        Image img = recordImg.getImage();
        Image changeImg = img.getScaledInstance(17, 17, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);

        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new GridLayout());
        JPanel subToolBtnPanel = new JPanel(new BorderLayout());
        subToolBtnPanel.setPreferredSize(new Dimension(40, 0));


        JPanel subToolPanel1 = new JPanel();
        Font settingBtnFont = new Font("맑은 고딕", Font.BOLD, 18);
        JButtonMemory settingBtn = new JButtonMemory("≡");
        settingBtn.setPreferredSize(new Dimension(46, 20));
        settingBtn.setFont(settingBtnFont);

        JLabel calculatorName = new JLabel("표준");
        calculatorName.setFont(settingBtnFont);

        subToolPanel1.add(settingBtn);
        subToolPanel1.add(calculatorName);

        JPanel subToolPanel2 = new JPanel();
        JButton recordBtn = new JButton(changeIcon);
        recordBtn.setBackground(new Color(0xeeeeee));
        recordBtn.setBorderPainted(false);
        recordBtn.setFocusPainted(false);
        recordBtn.setPreferredSize(new Dimension(20, 20));
        recordBtn.setFont(settingBtnFont);

        subToolPanel2.add(recordBtn);

        subToolBtnPanel.add(subToolPanel1, BorderLayout.WEST);
        subToolBtnPanel.add(subToolPanel2, BorderLayout.EAST);
        toolBarPanel.add(subToolBtnPanel);


        add(toolBarPanel);
    }

    /**
     * 숫자 연산, 결과, 미리보기 출력
     *
     */
    private void showResult() {
        JPanel resultMain = new JPanel();
        resultMain.setLayout(new GridLayout(2, 1));

        JPanel previewPanel = new JPanel(new GridLayout());
        previewPanel.setPreferredSize(new Dimension(10, 0));

        preview = new JTextField(27) {
            public void setBorder(Border border) {}
        };
        preview.setText("");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);

        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel(new GridLayout());
        resultViewPanel.setPreferredSize(new Dimension(50, 40));

        Font resultFont = new Font("Dialog", Font.BOLD, 48);
        resultView = new JTextField(11) {
            public void setBorder(Border border) {}
        };
        resultView.setText("0");
        resultView.setHorizontalAlignment(SwingConstants.RIGHT);
        resultView.setFont(resultFont);
        resultView.setEditable(false);
        resultViewPanel.add(resultView);

        resultMain.add(previewPanel);
        resultMain.add(resultViewPanel);

        add(resultMain);
    }

    /**
     * 숫자, 연산, 초기화 등 버튼
     */
    private void showNumBtn() {
        Font font = new Font("맑은 고딕", Font.PLAIN, 21);
        JPanel mainPanel = new JPanel();

        JPanel memoryP = new JPanel();
        memoryP.setLayout(new GridLayout(1, 6, 2, 0));
        for(int i=0; i < 6; i++) {
            JButtonMemory memoryBtn = new JButtonMemory(memoryArr[i]);
            memoryP.add(memoryBtn);
        }

        JPanel numBtnPanel = new JPanel();
        numBtnPanel.setLayout(new GridLayout(6, 4, 3, 3));
        for(int i=0; i < 24; i++) {
            JButton numberPadBtn = new JButtonWhite(numberPadArr[i], resultView, preview,temp);
            if(i < 8)
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, temp, preResult);
            else if(i % 4 ==3){
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, temp, preResult);
                numberPadBtn.setFont(font);
            }

            if (i == 23){
                numberPadBtn.setBackground(Color.BLUE);
                numberPadBtn.setForeground(Color.WHITE);
                numberPadBtn.setFont(font);
                numberPadBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        /**
                         * 이건 나중에 method로 만들자.
                         */
                        String str = "";

                        for(int i=0; i < temp.size(); i++) {
                            str += temp.get(i);
                        }

                        String num;
                        String numStr = str.replaceFirst("^0+", "");
                        if(numStr == "" || numStr.isBlank())
                            num = "0";
                        else
                            num = numStr;
                        // 여기까지 메소드

                        preResult.push(resultView.getText());
//                        System.out.println(num);
                        System.out.println(preResult.size());



                        // 아무것도 없을 시 미리보기에 0 = 출력
                        if(Objects.equals(resultView.getText(), preResult.peek()) && preResult.size() == 1) {
                            preview.setText(String.format("%s =  ", resultView.getText()));
                            resultView.setText(String.format("%s", resultView.getText()));
                            preResult.clear();
                            temp.clear();
                        } else {
                            arithmetic(preResult);
                            result.push(resultView.getText());
                        }

//
//                        System.out.println("나가는 것: "+resultView.getText());
//                        System.out.println(preResult.isEmpty());
                        System.out.println("temp: "+temp);
                        System.out.println("result "+ result);
                        System.out.println(preResult);

                    }
                });
            }

            numBtnPanel.add(numberPadBtn);
        }

        mainPanel.add(memoryP, BorderLayout.NORTH);
        mainPanel.add(numBtnPanel, BorderLayout.CENTER);

        add(mainPanel);
    }



    public static void main(String[] args) {
        new Calculator();
    }
}
