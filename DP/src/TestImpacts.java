import org.junit.Test;
import static org.junit.Assert.*;


public class TestImpacts {

    @Test
    public void test4(){
        Driver.main(new String[]{"inputs/4.in"});
        assertEquals(8, Driver.testResult);
    }

    @Test
    public void test6(){
        Driver.main(new String[]{"inputs/6.in"});
        assertEquals(5, Driver.testResult);
    }

    @Test
    public void test12(){
        Driver.main(new String[]{"inputs/12.in"});
        assertEquals(31, Driver.testResult);
    }

    @Test
    public void test256(){
        Driver.main(new String[]{"inputs/256.in"});
        assertEquals(553, Driver.testResult);
    }

    @Test
    public void test512(){
        Driver.main(new String[]{"inputs/512.in"});
        assertEquals(1027, Driver.testResult);
    }

}
