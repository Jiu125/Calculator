import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


class JButtonMemory extends JButton {
    Color c = new Color(0xeeeeee);

    public JButtonMemory() {
    }

    public JButtonMemory(String text) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
    }
}

/**
 * 연산 버튼
 */
class JButtonS extends JButton {
    Color c = new Color(0xfbfbfb);

    public JButtonS() {
    }

    public JButtonS(String text) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
    }
}

/**
 * 숫자 버튼
 */
class JButtonWhite extends JButton {
    Color c = new Color(0xeeeeee);

    public JButtonWhite() {
    }

    public JButtonWhite(String text) {
        super.setBackground(Color.WHITE);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
    }
}

/**
 *  계산기 디자인 파일
 *  기본 배경 RGB : r=238,g=238,b=238
 *  코드 구조 참고
 * @link : https://github.com/tejasmanohar/jframe-calculator/blob/master/src/calculator/Calculator.java
 *
 */
public class Calculator extends JFrame {
    Color c = new Color(0xeeeeee);
    public Calculator() {


        setTitle("계산기");
        setSize(335, 509);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

//        setLayout(new FlowLayout(FlowLayout.CENTER));
//        setLayout(new BorderLayout(0, -10));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("img/cal.png");
        setIconImage(img);

        showMenu();
        showResult();
        showNumBtn();

        System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     *  계산기 설정, 기록 확인 버튼
     *  showResult()를 여기에 넣어서 같은 panel에 두고 layout 설정해보자.
     */
    private void showMenu() {
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new GridLayout());
        JPanel subToolBtnPanel = new JPanel(new BorderLayout());
        subToolBtnPanel.setPreferredSize(new Dimension(40, 0));


        JPanel subToolPanel1 = new JPanel();
        //        Font settingBtnFont = new Font("Dialog", Font.PLAIN, 18)
        JButtonMemory settingBtn = new JButtonMemory("≡");

        JLabel calculatorName = new JLabel("표준");

        subToolPanel1.add(settingBtn);
        subToolPanel1.add(calculatorName);

        JPanel subToolPanel2 = new JPanel();
        JButtonMemory recordBtn = new JButtonMemory("기록");

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
//        resultMain.setLayout(new BorderLayout(0, 0));
        resultMain.setLayout(new BoxLayout(resultMain, BoxLayout.Y_AXIS));

        
        JPanel previewPanel = new JPanel(new GridLayout());
        previewPanel.setPreferredSize(new Dimension(10, 0));

//        System.out.println(previewPanel.getPreferredSize());
        JTextField preview = new JTextField(27) {
            public void setBorder(Border border) {}
        };
        preview.setText("0001");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);

        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel(new GridLayout());
        resultViewPanel.setPreferredSize(new Dimension(10, 40));
//        System.out.println(resultViewPanel.getPreferredSize());
        Font resultFont = new Font("Dialog", Font.BOLD, 48);
        JTextField result = new JTextField(11) {
            public void setBorder(Border border) {}
        };
        result.setText("0002");
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setFont(resultFont);
        result.setEditable(false);
        resultViewPanel.add(result);
        //        System.out.println(result.getFont());

        resultMain.add(previewPanel);
        resultMain.add(resultViewPanel);

        add(resultMain);

//        return resultMain;
    }

    /**
     * 숫자, 연산, 초기화 등 버튼
     */
    private void showNumBtn() {
        JPanel mainPanel = new JPanel();

//        mainPanel.setPreferredSize(new Dimension(10, -100));
//        System.out.println(mainPanel.getPreferredSize());

        JPanel memoryP = new JPanel();
        memoryP.setLayout(new GridLayout(1, 6, 2, 0));
        JButtonMemory memoryClear = new JButtonMemory("MC");
        JButtonMemory memoryReset = new JButtonMemory("MR");
        JButtonMemory memoryAdd = new JButtonMemory("M+");
        JButtonMemory memorySub = new JButtonMemory("M-");
        JButtonMemory memorySave = new JButtonMemory("MS");
        JButtonMemory memoryView= new JButtonMemory("M∨");

//        System.out.println(memoryClear.getPreferredSize());
//        memoryClear.setPreferredSize(new Dimension(memoryClear.getPreferredSize().width-29, 26));
//        System.out.println(memoryClear.getPreferredSize());

        memoryP.add(memoryClear);
        memoryP.add(memoryReset);
        memoryP.add(memoryAdd);
        memoryP.add(memorySub);
        memoryP.add(memorySave);
        memoryP.add(memoryView);

        JPanel numBtnPanel = new JPanel();
        numBtnPanel.setLayout(new GridLayout(6, 4, 3, 3));
        JButtonWhite num1 = new JButtonWhite("1");
        JButtonWhite num2 = new JButtonWhite("2");
        JButtonWhite num3 = new JButtonWhite("3");
        JButtonWhite num4 = new JButtonWhite("4");
        JButtonWhite num5 = new JButtonWhite("5");
        JButtonWhite num6 = new JButtonWhite("6");
        JButtonWhite num7 = new JButtonWhite("7");
        JButtonWhite num8 = new JButtonWhite("8");
        JButtonWhite num9 = new JButtonWhite("9");
        JButtonWhite num0 = new JButtonWhite("0");
        JButtonWhite comma = new JButtonWhite(".");
        JButtonWhite plusAndMinus = new JButtonWhite("+/-");

        JButtonS plus = new JButtonS("+");
        JButtonS minus = new JButtonS("-");
        JButtonS multiply = new JButtonS("X");
        JButtonS divide = new JButtonS("÷");
        JButtonS percent = new JButtonS("％");
        JButtonS root = new JButtonS("²√χ");
        JButtonS square = new JButtonS("χ²");
        JButtonS fountain = new JButtonS("¹／χ");
        JButtonS clear = new JButtonS("C");
        JButtonS clearEntry = new JButtonS("CE");
        JButtonS back = new JButtonS("<");

        JButtonS equal = new JButtonS("=");
        equal.setBackground(Color.BLUE);
        equal.setForeground(Color.WHITE);


        num1.setPreferredSize(new Dimension(77, 46));

        numBtnPanel.add(percent);
        numBtnPanel.add(clear);
        numBtnPanel.add(clearEntry);
        numBtnPanel.add(back);
        numBtnPanel.add(fountain);
        numBtnPanel.add(square);
        numBtnPanel.add(root);
        numBtnPanel.add(divide);
        numBtnPanel.add(num7);
        numBtnPanel.add(num8);
        numBtnPanel.add(num9);
        numBtnPanel.add(multiply);
        numBtnPanel.add(num4);
        numBtnPanel.add(num5);
        numBtnPanel.add(num6);
        numBtnPanel.add(minus);
        numBtnPanel.add(num1);
        numBtnPanel.add(num2);
        numBtnPanel.add(num3);
        numBtnPanel.add(plus);
        numBtnPanel.add(plusAndMinus);
        numBtnPanel.add(num0);
        numBtnPanel.add(comma);
        numBtnPanel.add(equal);

//        System.out.println(num1.getPreferredSize());
//        System.out.println(memoryClear.getPreferredSize());

        mainPanel.add(memoryP, BorderLayout.NORTH);
        mainPanel.add(numBtnPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
