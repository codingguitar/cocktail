package com.vgg;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParanthesesTest {

    @Test
    public void testGenerateBalancedParantheses() throws Exception {
        Parantheses p = new Parantheses();
        p.generateBalancedParantheses(3);
    }
}