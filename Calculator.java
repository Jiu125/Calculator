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

    public JButtonMemory(String text, JTextField resultView, Stack memory, Stack memoryTemp) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String str = resultView.getText();
                str = str.replace(",", "");


                // MemoryClear(메모리 초기화)
                if (Objects.equals(text, "MC")) {
                    memory.clear();
                }

                // MemoryRoad(메모리 값 불러오기)
                if (Objects.equals(text, "MR")) {
                    resultView.setText(String.valueOf(memory.peek()));
                }

                // MemoryAdd(메모리 값 더하기)
                if (Objects.equals(text, "M+")) {
                    if (memory.isEmpty()) {
                        memoryTemp.push(str);
                        memory.push(str);
//                        System.out.println(memory);
                    }
                    else {
                        int memoryNum =  Integer.parseInt(String.valueOf(memory.peek()));
                        int tempNum =  Integer.parseInt(String.valueOf(memoryTemp.peek()));
                        memoryNum = memoryNum + tempNum;
                        memory.push(memoryNum);
//                        System.out.println(memory);
                    }
                }

                // MemorySub(메모리 값 빼기)
                if (Objects.equals(text, "M-")) {
                    if (memory.size() == 0) {
                        int strN = Integer.parseInt(str);
                        strN = strN * -1;
                        str = String.valueOf(strN);
                        memoryTemp.push(str);
                        memory.push(str);
                        System.out.println(memory);
                    }
                    else {
                        int memoryNum =  Integer.parseInt(String.valueOf(memory.peek()));
                        int tempNum =  Integer.parseInt(String.valueOf(memoryTemp.peek()));
                        tempNum = tempNum + memoryNum;
                        memory.push(tempNum);
                        System.out.println(memory);
                    }
                }

                // MemorySave(메모리 값 불러오기)
                if (Objects.equals(text, "MS")) {
                    memoryTemp.push(str);
                    memory.push(str);
                }

                // MemoryView(메모리 값 리스트 보기)
//                if (Objects.equals(text, "M∨")) {
//                    memoryTemp.push(str);
//                    memory.push(str);
//                }
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
            return String.format("%,d", (long) number);
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

                double num;
                String numStr = strNum.replaceFirst("^0+", ""); // 0은 한 번만 나오게 가공
                if(numStr.isEmpty() || numStr.isBlank()) // 문자의 가독성을 높이는 가공
                    num = 0;
                else
                    num = Double.parseDouble(numStr);

                // 퍼센트 연산 기능 처리
                if(Objects.equals(text, "％")){
                    double percentNum;
                    if(preveiwStack.isEmpty() || Objects.equals(result.getText(), "0")){
                        privew.setText("0");
                        result.setText("0");
                        temp.clear();
                    }
                    else if(preveiwStack.size() >= 2){
                        percentNum = Double.parseDouble(String.valueOf(preveiwStack.get(0)));
                        String oper = String.valueOf(preveiwStack.get(1));
                        double percentResult = (percentNum/100) * percentNum;
                        privew.setText(String.format("%s %s %s", result.getText(), oper, formatDouble(percentResult)));
                        result.setText(String.format("%s", formatDouble(percentResult)));
                    }
                }

                String resultStr = result.getText();
                String previewStr = resultStr.replace(",", "");

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
                    resultStr = resultStr.replace(",", "");
                    resultStr = resultStr.substring(0, resultStr.length()-1);
                    temp.pop();
                    if(resultStr.isEmpty()){
                        result.setText("0");
                    } else
                        result.setText(resultStr);
                }

                // 1/(A) 연산 처리
                if(Objects.equals(text, "¹／χ")){
                    double number = Double.parseDouble(previewStr);
                    number = 1 / number;
                    privew.setText(String.format("1/(%s)", previewStr));
                    result.setText(String.format("%s", formatDouble(number)));
                    temp.clear();
                }

                // sqr 연산 처리
                if(Objects.equals(text, "χ²")){
                    double number = Double.parseDouble(previewStr);
                    number *= number;
                    privew.setText(String.format("sqr(%s)", previewStr));
                    result.setText(String.format("%s", formatDouble(number)));
                    temp.clear();
                }

                // root 연산 처리
                if(Objects.equals(text, "²√χ")){
                    double number = Double.parseDouble(previewStr);
                    number = Math.sqrt(number);
                    privew.setText(String.format("√(%s)", previewStr));
                    result.setText(String.format("%s", formatDouble(number)));
                    temp.clear();
                }

                // 더하기 연산 처리
                if (Objects.equals(text, "+")) {
//                     비어있을 때는 => "0 + " 이게 뜸
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
                        preveiwStack.push(previewStr);

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "-"))
                            preveiwStack.push("-");

                        privew.setText(String.format("%s -  ", previewStr));
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
                        preveiwStack.push(previewStr);

                        if (preveiwStack.isEmpty() || !Objects.equals(preveiwStack.peek(), "×"))
                            preveiwStack.push("×");

                        privew.setText(String.format("%s ×  ", previewStr));
                        temp.clear();
                    }
                }

                // 나누기 연산 처리
                if (Objects.equals(text, "÷")) {

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
            return String.format("%,d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    private boolean isOperator(String str) {
        return "+".equals(str) || "-".equals(str) ||
                "×".equals(str) || "÷".equals(str);
    }

    public JButtonWhite(String text, JTextField  result, JTextField preview, Stack temp, Stack preveiwStack) {
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


//                    StringBuffer sb = new StringBuffer(str);
//                    String reverse = sb.reverse().toString();
//                    reverse = reverse.replaceAll("(.{3})", "$1,");
//                    sb = new StringBuffer(reverse);
//                    if (sb.charAt(sb.length() - 1) == ',')
//                        sb.deleteCharAt(sb.length() - 1);

                    double doubleString = Double.parseDouble(str);
                    result.setText(String.format("%s", formatDouble(doubleString)));
                }

                // 부호 바꾸기 연산 처리 (negate)
                if(Objects.equals(text, "+/-")){
                    String operation = null;
                    int position = -1;

                    // 연산자 찾기
                    for (int i = preveiwStack.size()-1; i >= 0; i--) {
                        String current = (String) preveiwStack.get(i);
                        if (isOperator(current)) {
                            operation = current;
                            position = i ;
                            break;
                        }
                    }

                    double number = Double.parseDouble(result.getText());
                    if (position != -1) {
                        double num1 = Double.parseDouble(String.valueOf(preveiwStack.get(position - 1)));
                        preview.setText(String.format("%s %s negate(%s) =",formatDouble(num1), operation, formatDouble(number)));
                    }
                    number *= -1;
                    result.setText(String.format("%s", formatDouble(number)));
                }

                // 소수점 연산 처리
                if (Objects.equals(text, ".")) {
                    String currentText = result.getText();

                    // 소수점이 이미 존재하는지 확인
                    if (!currentText.contains(".")) {
                        // 소수점이 없으면 추가
                        temp.push(".");
                        result.setText(currentText + ".");
                    }
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
    Stack<String> temp = new Stack<>();
    Stack<String> result = new Stack<>();
    Stack<String> preResult = new Stack<>();
    Stack<String> memory = new Stack<>();
    Stack<String> memoryTemp = new Stack<>();
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
            System.out.println(number);
            return String.format("%,d", (long) number);
        } else {
            System.out.println(number);
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

        String str = resultView.getText();
        System.out.println("str: " + str);
        str = str.replace(",", "");
        System.out.println("str: " + str);

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
        System.out.println("index: " + index);

        Double num2 = Double.parseDouble(String.valueOf(preResult.get(index + 1))); // 뒤에 있는 수
        if ((index - 1) == -1) {
            preview.setText(String.format("0 %s %s =  ", operation, formatDouble(num2)));
            resultView.setText(String.format("%s", formatDouble(num2)));
        }
        else {
            double num1 = Double.parseDouble(String.valueOf(preResult.get(index - 1))); // 앞에 있는 수
            double num3;

            if (position == -1) {
                preview.setText(String.format("%s =  ", str));
            } else {
                num3 = Double.parseDouble(str);

                double result = calculateResult(num1, num2, operation);

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
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "×" -> num1 * num2;
            case "÷" -> {
                if (num2 != 0) {
                    yield (double) num1 / num2;
                }
                yield Double.MIN_VALUE;
            }
            default -> Double.MIN_VALUE;
        };
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
        Font settingBtnFont = new Font("맑은 고딕", Font.BOLD, 20);
        JLabel settingBtn = new JLabel(" ≡");
        settingBtn.setPreferredSize(new Dimension(30, 20));
        settingBtn.setFont(settingBtnFont);

        JLabel calculatorName = new JLabel("표준");
        calculatorName.setFont(settingBtnFont);

        subToolPanel1.add(settingBtn);
        subToolPanel1.add(calculatorName);

        JPanel subToolPanel2 = new JPanel();
        JLabel recordBtn = new JLabel(changeIcon);
        recordBtn.setBackground(new Color(0xeeeeee));
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
        JButtonMemory mcBtn = new JButtonMemory(memoryArr[0], resultView, memory, memoryTemp);
        JButtonMemory mrBtn = new JButtonMemory(memoryArr[1], resultView, memory, memoryTemp);
        JButtonMemory mAddBtn = new JButtonMemory(memoryArr[2], resultView, memory, memoryTemp);
        JButtonMemory mSubBtn = new JButtonMemory(memoryArr[3], resultView, memory, memoryTemp);
        JButtonMemory msBtn = new JButtonMemory(memoryArr[4], resultView, memory, memoryTemp);
        JButtonMemory meViewBtn = new JButtonMemory(memoryArr[5], resultView, memory, memoryTemp);

        mcBtn.setEnabled(false);
        mrBtn.setEnabled(false);
        meViewBtn.setEnabled(false);

        mcBtn.addActionListener(e -> {
            mcBtn.setEnabled(false);
            mrBtn.setEnabled(false);
            meViewBtn.setEnabled(false);
        });

        mAddBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
            meViewBtn.setEnabled(true);
        });

        mSubBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
            meViewBtn.setEnabled(true);
        });

        msBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
            meViewBtn.setEnabled(true);
        });

        memoryP.add(mcBtn);
        memoryP.add(mrBtn);
        memoryP.add(mAddBtn);
        memoryP.add(mSubBtn);
        memoryP.add(msBtn);
        memoryP.add(meViewBtn);

        JPanel numBtnPanel = new JPanel();
        numBtnPanel.setLayout(new GridLayout(6, 4, 3, 3));
        for(int i=0; i < 24; i++) {
            JButton numberPadBtn = new JButtonWhite(numberPadArr[i], resultView, preview,temp, preResult);
            if(i < 8)
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, temp, preResult);
            else if(i % 4 ==3){
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, temp, preResult);
                numberPadBtn.setFont(font);
            }

            if (i == 23){
                numberPadBtn.setBackground(new Color(0x00649E));
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
                        if(numStr.isEmpty() || numStr.isBlank())
                            num = "0";
                        else
                            num = numStr;
                        // 여기까지 메소드

                        String previewStr = resultView.getText();
                        previewStr = previewStr.replace(",", "");

                        preResult.push(previewStr);

                        // 아무것도 없을 시 미리보기에 0 = 출력
                        if(Objects.equals(previewStr, preResult.peek()) && preResult.size() == 1) {
                            preview.setText(String.format("%s =  ", previewStr));
                            resultView.setText(String.format("%s", resultView.getText()));
                            preResult.clear();
                            temp.clear();
                            preResult.push(previewStr);
                        } else {
                            arithmetic(preResult);
                            result.push(previewStr);
                        }
                        System.out.println(previewStr);
                        System.out.println(preResult);
                        System.out.println(preResult.size());


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
