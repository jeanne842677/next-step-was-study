import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {
    // 빈문자열 또는 null 값을 입력할 겨우 0을 반환해야한다.
    private StringCalculator cal;
    
    @BeforeEach
    public void setup(){
        cal = new StringCalculator();
    }
    
    @Test
    public void add_null_또는_빈문자(){
        assertEquals(0,cal.add(null));
        assertEquals(0,cal.add(""));
    }

    //숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다.
    @Test
    public void add_숫자하나() throws Exception{
        assertEquals(1,cal.add("1"));
    }

    //숫자 두개를 쉼표 구분자로 입력할 경우 두 숫자의 합을 반환한다.
    @Test
    public void add_쉼표구분자() throws Exception{
        assertEquals(3,cal.add("1,2"));
    }

    //구분자를 쉼표(,) 이외에 콜론(:)을 사용할 수 있다.
    @Test void add_쉼표_또는_콜론_구분자() throws Exception{
        assertEquals(6,cal.add("1,2:3"));
    }

    //"//"와 "\n" 문자 사이에 커스텀 구분자를 지정할 수 있다.
    @Test
    public void add_custom_구분자() throws Exception{
        assertEquals(6, cal.add("//;\n1;2;3"));
    }

    //문자열 계산기에 음수를 전달하는 경우 RuntimeException 예외를 throw 한다.
    //JUnit4 RuntimeException 방법
    /*@Test(expected = RuntimeException.class){
        public void add_negative() throws Exception{
            cal.add("-1,2,3");
        }
    }*/

    //JUnit5 방법
    @Test
    public void testExceptionHandling() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            cal.add("-1,2,3");
        });
    }
}
