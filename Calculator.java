import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

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
    String[] memoryArr = {"MC", "MR", "M+", "M-", "MS", "M∨"}; // 나중에 Mv 구현.
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

        double num2 = Double.parseDouble(String.valueOf(preResult.get(index + 1))); // 뒤에 있는 수
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
                if (result == Double.MIN_VALUE) {
                    preview.setText("0으로 나눌 수 없습니다");
                    return;
                }

                System.out.println("num1: "+num1);
                System.out.println("num2: "+num2);
                System.out.println("num3: "+num3);
                System.out.println(preResult);
                System.out.println("==============");

                if (Objects.equals(num2, num3)) {
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

        /*
        나중에 기록 기능 구현
         */
//        JPanel subToolPanel2 = new JPanel();
//        JButton recordBtn = new JButton(changeIcon);
//        recordBtn.setBackground(new Color(0xeeeeee));
//        recordBtn.setPreferredSize(new Dimension(20, 20));
//        recordBtn.setFont(settingBtnFont);

//        subToolPanel2.add(recordBtn);

        //        subToolBtnPanel.add(subToolPanel2, BorderLayout.EAST);
        subToolBtnPanel.add(subToolPanel1, BorderLayout.WEST);

        toolBarPanel.add(subToolBtnPanel);


        add(toolBarPanel);
    }

    /**
     * 계산기 인터페이스의 결과 패널을 초기화하고 표시합니다.
     *
     * <p>
     * 이 메서드는 `resultMain`이라는 메인 결과 패널을 설정하며,
     * `previewPanel`과 `resultViewPanel`이라는 두 개의 하위 패널을 포함합니다.
     * `previewPanel`은 계산 과정 또는 현재 입력을 표시하는 읽기 전용 `JTextField`(`preview`)를 가지고,
     * `resultViewPanel`은 최종 결과를 굵은 글씨체로 표시하는 읽기 전용 `JTextField`(`resultView`)를 포함합니다.
     * </p>
     *
     * <p>
     * 각 패널과 텍스트 필드는 텍스트가 오른쪽 정렬되며, 특정 글꼴 크기와 스타일을 사용하도록 설정됩니다.
     * 특히 `resultView`는 기본적으로 크기 48의 굵은 글씨체로 결과를 표시합니다.
     * </p>
     *
     * <p>
     * 이 메서드는 별도의 매개변수를 받지 않으며, 메인 계산기 인터페이스에 필요한
     * 컴포넌트를 설정하고 추가하는 용도로 사용됩니다.
     * </p>
     *
     * @see JPanel
     * @see JTextField
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
//        JButtonMemory meViewBtn = new JButtonMemory(memoryArr[5], resultView, memory, memoryTemp);

        mcBtn.setEnabled(false);
        mrBtn.setEnabled(false);
//        meViewBtn.setEnabled(false);

        mcBtn.addActionListener(e -> {
            mcBtn.setEnabled(false);
            mrBtn.setEnabled(false);
//            meViewBtn.setEnabled(false);
        });

        mAddBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
//            meViewBtn.setEnabled(true);
        });

        mSubBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
//            meViewBtn.setEnabled(true);
        });

        msBtn.addActionListener(e -> {
            mcBtn.setEnabled(true);
            mrBtn.setEnabled(true);
//            meViewBtn.setEnabled(true);
        });

        memoryP.add(mcBtn);
        memoryP.add(mrBtn);
        memoryP.add(mAddBtn);
        memoryP.add(mSubBtn);
        memoryP.add(msBtn);
//        memoryP.add(meViewBtn);

        JPanel numBtnPanel = new JPanel();
        numBtnPanel.setLayout(new GridLayout(6, 4, 3, 3));
        for(int i=0; i < 24; i++) {
            JButton numberPadBtn;
            numberPadBtn = new JButtonWhite(
                    numberPadArr[i], resultView, preview,temp, preResult
            );
            if(i < 8)
                numberPadBtn = new JButtonS(
                        numberPadArr[i], resultView, preview, temp, preResult
                );
            else if(i % 4 ==3){
                numberPadBtn = new JButtonS(
                        numberPadArr[i], resultView, preview, temp, preResult
                );
                numberPadBtn.setFont(font);
            }

            if (i == 23){
                numberPadBtn.setBackground(new Color(0x00649E));
                numberPadBtn.setForeground(Color.WHITE);
                numberPadBtn.setFont(font);
                numberPadBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {


                        String num = resultView.getText();
                        String numStr = num.replaceFirst("^0+", "");
                        String previewStr;
                        if(numStr.isEmpty() || numStr.isBlank())
                            previewStr = "0";
                        else
                            previewStr = numStr;

                        System.out.println("##################");
                        System.out.println("previewStr: " + previewStr);
                        preResult.push(previewStr);
                        System.out.println("##################");

                        // 아무것도 없을 시 미리보기에 0 = 출력
                        if(Objects.equals(previewStr, preResult.peek()) && preResult.size() < 2) {
                            preview.setText(String.format("%s =  ", previewStr));
                            resultView.setText(String.format("%s", resultView.getText()));
                            preResult.clear();
                            temp.clear();
                            preResult.push(previewStr);
                        } else {
                            arithmetic(preResult);
                            result.push(previewStr);
                        }
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
