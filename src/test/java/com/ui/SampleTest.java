import static org.testng.Assert.assertEquals;

public class SampleTest {
    @org.testng.annotations.Test
    void sample() {
        // La prueba pasa si el valor esperado (5) coincide con el valor real (5)
        assertEquals(5, 3 + 2);
    }
}