import javax.swing.*;
import java.awt.*;


/**
 *  계산기 디자인 파일
 */
public class Calculator extends JFrame {
    public Calculator() {

        setTitle("계산기");
        setSize(335, 508);
//        setLayout(new FlowLayout());
        setLayout(new BorderLayout(0, -3));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("D:/NewGit/workplace/img/cal.png");
        setIconImage(img);

        showMenu(); showResult(); showNumBtn();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     *  계산기 설정, 기록 확인 버튼
     */
    private void showMenu() {
        JMenuBar mainPanel = new JMenuBar();
        JPanel resultPanel = showResult();

        mainPanel.add(resultPanel);

        add(mainPanel, BorderLayout.NORTH);
    }

    /**
     * 숫자 연산, 결과, 미리보기 출력
     */
    private JPanel showResult() {
        
        JPanel resultMain = new JPanel();
        
        JPanel previewPanel = new JPanel();
        JTextField preview = new JTextField(23);
        preview.setText("0001");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);
        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel();
        Font resultFont = new Font("Dialog", Font.PLAIN, 36);
        JTextField result = new JTextField(10);
        result.setText("0001");
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setFont(resultFont);
        result.setEditable(false);
        resultViewPanel.add(result);
        //        System.out.println(result.getFont());

        resultMain.add(previewPanel);
        resultMain.add(resultViewPanel);

//        add(resultViewPanel, BorderLayout.NORTH);

        return resultMain;
    }

    /**
     * 숫자, 연산, 초기화 등 버튼
     */
    private void showNumBtn() {
        JPanel mainPanel = new JPanel();

        JPanel memoryP = new JPanel();
        memoryP.setLayout(new GridLayout(1, 6, 2, 0));
        JButton memoryClear = new JButton("MC");
        JButton memoryReset = new JButton("MR");
        JButton memoryAdd = new JButton("M+");
        JButton memorySub = new JButton("M-");
        JButton memorySave = new JButton("MS");
        JButton memoryView= new JButton("M∨");

//        memoryClear.setPreferredSize(new Dimension(52, 28));

        memoryP.add(memoryClear);
        memoryP.add(memoryReset);
        memoryP.add(memoryAdd);
        memoryP.add(memorySub);
        memoryP.add(memorySave);
        memoryP.add(memoryView);

        JPanel numBtnPanel = new JPanel();
        numBtnPanel.setLayout(new GridLayout(6, 4, 2, 2));
        JButton num1 = new JButton("1");
        JButton num2 = new JButton("2");
        JButton num3 = new JButton("3");
        JButton num4 = new JButton("4");
        JButton num5 = new JButton("5");
        JButton num6 = new JButton("6");
        JButton num7 = new JButton("7");
        JButton num8 = new JButton("8");
        JButton num9 = new JButton("9");
        JButton num0 = new JButton("0");
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JButton multiply = new JButton("X");
        JButton divide = new JButton("÷");
        JButton percent = new JButton("％");
        JButton equal = new JButton("=");
        JButton root = new JButton("²√χ");
        JButton square = new JButton("χ²");
        JButton fountain = new JButton("¹／χ");
        JButton clear = new JButton("C");
        JButton clearEntry = new JButton("CE");
        JButton back = new JButton("<");
        JButton comma = new JButton(".");
        JButton plusAndMinus = new JButton("+/-");

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

        System.out.println(num1.getPreferredSize());
        System.out.println(memoryClear.getPreferredSize());

        mainPanel.add(memoryP);
        mainPanel.add(numBtnPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}