package com.ui;

import static org.testng.Assert.assertEquals;

public class SampleTest {
    @org.testng.annotations.Test
    void sample() {
        // La prueba pasa si el valor esperado (5) coincide con el valor real (5)
        assertEquals(8, 3 + 2);//Se cambia el valor actual
    }
}