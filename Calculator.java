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

    public JButtonS(String text, JTextField  result, JTextField  privew, Stack resultStack, Stack preveiwStack) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(text, "C")){
                    preveiwStack.clear();
                    resultStack.clear();
                    privew.setText("");
                    result.setText("0");
                }
                if(Objects.equals(text, "CE")){
                    resultStack.clear();
                    result.setText("0");
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
                if (btnStr.matches("\\d+")) {  // \\d+는 하나 이상의 숫자를 의미하는 정규식
                    stack.push(btnStr);

                    // 스택의 모든 문자열을 합침
                    String str = "";
                    for (int i = 0; i < stack.size(); i++) {
                        str += stack.get(i);
                    }
                    // 숫자일 때만 textField의 텍스트를 설정
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
        Image changeImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
        preview.setText("0001");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);

        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel(new GridLayout());
        resultViewPanel.setPreferredSize(new Dimension(50, 40));

        Font resultFont = new Font("Dialog", Font.BOLD, 48);
        resultView = new JTextField(11) {
            public void setBorder(Border border) {}
        };
        resultView.setText("0002");
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
            JButton numberPadBtn = new JButtonWhite(numberPadArr[i], resultView, result);
            if(i < 8)
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, result, preResult);
            else if(i % 4 ==3){
                numberPadBtn = new JButtonS(numberPadArr[i], resultView, preview, result, preResult);
                numberPadBtn.setFont(font);
            }

            if (i == 23){
                numberPadBtn.setBackground(Color.BLUE);
                numberPadBtn.setForeground(Color.WHITE);
                numberPadBtn.setFont(font);
                numberPadBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String str = "";
                        for(int i=0; i < result.size(); i++) {
                            str += result.get(i);
                        }
                        String num = str.replaceFirst("^0+", "");

                        if(preResult.isEmpty() || num == "") {
                            result.clear();
                            preview.setText(String.format("0 %s", numberPadArr[23]));
                            preResult.clear();
                            result.clear();
                        }
                        preResult.push(num);
                        preview.setText(String.format("%s %s", num, numberPadArr[23]));

                        System.out.println(preResult.isEmpty());
                        System.out.println(preResult.size());
                        System.out.println(preResult);

                        result.clear();
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
