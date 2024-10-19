import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * 연산에 필요한 이벤트들을 담을 파일
 */
public class CalculatorActionListener {
    CalculatorActionListener() {
        Stack<String> stackStr = new Stack<>();
    }

    ActionListener add, pop, printResult;
}

class buttonActionListener {
    ActionListener addNum, back, clear, clearEntry, plus, minus, multiply, divide, percent, pow, root, inverse;
    // 클릭하면 addNum => 변수.getText()로 결과 바꿀 수 잇는 것을 확인 이걸로 진행하고
    // 스택을 만들어서 미리보기를 구성하자 물론 연산도 스택으로 구현해야 할듯,,,?


}
