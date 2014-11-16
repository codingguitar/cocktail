package com.vgg;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberToWordTest {

    @Test
    public void testConvertToWord() throws Exception {
        String word;

        word = NumberToWord.convertToWord("88999765");
        Assert.assertTrue(word.equals("eighty eight million, nine hundred ninety nine thousand, seven hundred sixty five"));

        word = NumberToWord.convertToWord("77");
        Assert.assertTrue(word.equals("seventy seven"));
    }
}