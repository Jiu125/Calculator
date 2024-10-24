import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

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
                    if(str.length() == 0){
                        result.setText("0");
                    } else
                        result.setText(str);
                }

                // "+" 연산 처리
                if (Objects.equals(text, "+")) {

//                     비어있을 때는 => "0 + " 이게 뜸
                    if (Objects.equals(result.getText(), "0")) {

                        preveiwStack.push("+");
                        privew.setText(String.join(" ", preveiwStack));
                        privew.setText("0 +  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) { // 'A + ' => "3 + " 이케 떠야함
                        preveiwStack.push(result.getText());
                        preveiwStack.push("+");
                        privew.setText(String.format("%s +  ", result.getText()));
                        temp.clear();
                    }
                }

                // "-" 연산 처리
                if (Objects.equals(text, "-")) {

                    if (Objects.equals(result.getText(), "0")) {
                        preveiwStack.push("-");
                        privew.setText(String.join(" ", preveiwStack));
                        privew.setText("0 -  ");
                        temp.clear();
                    }
                    else if (!preveiwStack.isEmpty() || num != 0) {
                        preveiwStack.push(result.getText());
                        preveiwStack.push("-");
                        privew.setText(String.format("%s -  ", result.getText()));
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

    public JButtonWhite(String text, JTextField  textField, Stack stack) {
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
                    if (stack.isEmpty() && btnStr.equals("0")) {
                        return;  // 첫 숫자가 0일 때 스택에 추가하지 않고 리턴
                    }

                    stack.push(btnStr);

                    String str = "";
                    for (int i = 0; i < stack.size(); i++) {
                        str += stack.get(i);
                    }
                    textField.setText(str);
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
        setSize(335, 509);
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
            JButton numberPadBtn = new JButtonWhite(numberPadArr[i], resultView, temp);
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
                        System.out.println(num);
                        System.out.println(preResult);

                        // 아무것도 없을 시 미리보기에 0 = 출력
                        if(Objects.equals(resultView.getText(), "0")) {
                            preview.setText("0 =  ");
                            resultView.setText("0");
                            preResult.clear();
                        }

                        // 더하기 연산 출력
                        int position = preResult.search("+");
                        int index = preResult.size() - position;
                        int num1 = Integer.parseInt(preResult.get(index - 1)); // 앞에 있는 수
                        int num2; // 뒤에 있는 수

                        if(position == -1) {
                            preview.setText(String.format("%s =  ", resultView.getText()));
                        }else  { // 나중에 예외 처리 다시
                            num2 = Integer.parseInt(resultView.getText());

                            preview.setText(String.format("%d + %d =  ", num2, num1));
                            resultView.setText(String.format("%d", num1 + num2));

                        }



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

    public void addNum(JButton btn) {
        String num = btn.getText();

    }

    public static void main(String[] args) {
        new Calculator();
    }
}
