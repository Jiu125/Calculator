import javax.swing.*;
import java.awt.*;


/**
 *  계산기 디자인 파일
 */
public class Calculator extends JFrame {
    public Calculator() {

        setTitle("계산기");
        setSize(335, 514);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//        setLayout(new FlowLayout(FlowLayout.CENTER));
//        setLayout(new BorderLayout(0, -3));

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
        JMenuBar mainPanel = new JMenuBar();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

//        JPanel resultPanel = showResult();

        JPanel toolBarPanel = new JPanel();

//        Font settingBtnFont = new Font("Dialog", Font.PLAIN, 18);
        JButton settingBtn = new JButton("≡");
//        settingBtn.setFont(settingBtnFont);
//        settingBtn.setPreferredSize(new Dimension(40, 35));
        toolBarPanel.add(settingBtn);

        mainPanel.add(toolBarPanel);
//        mainPanel.add(resultPanel);

        add(mainPanel);
    }

    /**
     * 숫자 연산, 결과, 미리보기 출력
     *
     */
    private void showResult() {
        
        JPanel resultMain = new JPanel();
//        resultMain.setLayout(new BorderLayout(0, 0));
        resultMain.setLayout(new BoxLayout(resultMain, BoxLayout.Y_AXIS));

        
        JPanel previewPanel = new JPanel();
        previewPanel.setPreferredSize(new Dimension(290, 1));
//        System.out.println(previewPanel.getPreferredSize());
        JTextField preview = new JTextField(26);
        preview.setText("0001");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);
        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel();
        resultViewPanel.setPreferredSize(new Dimension(10, 28));
//        System.out.println(resultViewPanel.getPreferredSize());
        Font resultFont = new Font("Dialog", Font.PLAIN, 33);
        JTextField result = new JTextField(11);
        result.setText("0001");
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
        JButton memoryClear = new JButton("MC");
        JButton memoryReset = new JButton("MR");
        JButton memoryAdd = new JButton("M+");
        JButton memorySub = new JButton("M-");
        JButton memorySave = new JButton("MS");
        JButton memoryView= new JButton("M∨");

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

//        System.out.println(num1.getPreferredSize());
//        System.out.println(memoryClear.getPreferredSize());

        mainPanel.add(memoryP);
        mainPanel.add(numBtnPanel);

        add(mainPanel);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
