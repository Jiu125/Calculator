import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


class JButtonMemory extends JButton {
    Font font = new Font("맑은 고딕", Font.PLAIN, 13);
    Color c = new Color(0xeeeeee);

    public JButtonMemory() {
    }

    public JButtonMemory(String text) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);
    }
}

/**
 * 연산 버튼
 */
class JButtonS extends JButton {
    Color c = new Color(0xfbfbfb);
    Font font = new Font("맑은 고딕", Font.PLAIN, 14);
    public JButtonS() {
    }

    public JButtonS(String text) {
        super.setBackground(c);
        setBorderPainted(false);
        setFocusPainted(false);
        this.setText(text);
        setFont(font);
    }
}

/**
 * 숫자 버튼
 */
class JButtonWhite extends JButton {
    Font font = new Font("맑은 고딕", Font.PLAIN, 18);

    public JButtonWhite() {
    }

    public JButtonWhite(String text) {
        super.setBackground(Color.WHITE);
        setBorderPainted(false);
        setFocusPainted(false);
        setPreferredSize(new Dimension(77, 46));
        this.setText(text);
        setFont(font);
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
    String[] memoryArr = {"MC", "MR", "M+", "M-", "MS", "M∨"};
    String[] numberPadArr = {
            "％", "CE", "C", "<",
            "¹／χ", "χ²", "²√χ", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
    };
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
        Font settingBtnFont = new Font("맑은 고딕", Font.BOLD, 18);
        JButtonMemory settingBtn = new JButtonMemory("≡");
        settingBtn.setFont(settingBtnFont);

        JLabel calculatorName = new JLabel("표준");
        calculatorName.setFont(settingBtnFont);

        subToolPanel1.add(settingBtn);
        subToolPanel1.add(calculatorName);

        JPanel subToolPanel2 = new JPanel();
        JButtonMemory recordBtn = new JButtonMemory("기록");
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
        resultMain.setLayout(new BoxLayout(resultMain, BoxLayout.Y_AXIS));

        JPanel previewPanel = new JPanel(new GridLayout());
        previewPanel.setPreferredSize(new Dimension(10, 0));

        JTextField preview = new JTextField(27) {
            public void setBorder(Border border) {}
        };
        preview.setText("0001");
        preview.setEditable(false);
        preview.setHorizontalAlignment(JTextField.RIGHT);

        previewPanel.add(preview);

        JPanel resultViewPanel = new JPanel(new GridLayout());
        resultViewPanel.setPreferredSize(new Dimension(10, 40));

        Font resultFont = new Font("Dialog", Font.BOLD, 48);
        JTextField result = new JTextField(11) {
            public void setBorder(Border border) {}
        };
        result.setText("0002");
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setFont(resultFont);
        result.setEditable(false);
        resultViewPanel.add(result);

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
            JButton numberPadBtn = new JButtonWhite(numberPadArr[i]);
            if(i < 8)
                numberPadBtn = new JButtonS(numberPadArr[i]);
            else if(i % 4 ==3)
                numberPadBtn.setFont(font);
            if (i == 23){
                numberPadBtn.setBackground(Color.BLUE);
                numberPadBtn.setForeground(Color.WHITE);
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
