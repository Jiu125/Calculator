import javax.swing.*;
import java.awt.*;


/**
 *  계산기 디자인 파일
 */
public class Calculator extends JFrame {
    public Calculator() {

        setTitle("계산기");
        setSize(335, 509);
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
    }

    /**
     *  숫자 연산, 결과, 미리보기 출력
     */
    private void showResult() {

    }

    /**
     * 숫자, 연산, 초기화 등 버튼
     */
    private void showNumBtn() {

    }

    public static void main(String[] args) {
        new Calculator();
    }
}
