import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {


    private Calculator cal;

    @BeforeEach                         //jUnit4에서 @Befoe 과 동일함.
    public void setup(){
        cal = new Calculator();
        System.out.println("before");
    }
    
    @Test
    public void add(){
        assertEquals(9, cal.add(6,3));
        System.out.println("add");
    }

    @Test
    public void subtract(){
        assertEquals(3,cal.subtract(6,3));
        System.out.println("subtract");
    }

    @AfterEach
    public void teardown(){
        System.out.println("teardown");
    }
    




}


