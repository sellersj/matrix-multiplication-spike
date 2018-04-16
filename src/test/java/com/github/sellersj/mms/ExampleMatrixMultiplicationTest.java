package com.github.sellersj.mms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ExampleMatrixMultiplicationTest {

    private ExampleMatrixMultiplication multiplier = new ExampleMatrixMultiplication();

    @Test
    public void testCreateRandomNormalizedInput() {
        int size = 10;
        List<BigDecimal> list = createRandomNormalizedInput(size);
        assertEquals("should be right size", size, list.size());

    }

    /**
     * This is in case the input given is different than what's coded in the app. We want it to keep working, but log an
     * error.
     */
    @Test
    public void testGetCorrections_WrongNumberOfStories() {
        // make an input of the wrong size
        int wrongSize = ExampleMatrixMultiplication.NUMBER_OF_USER_STORIES / 2;
        List<BigDecimal> wrongSizedList = createRandomNormalizedInput(wrongSize);

        // check that it's filled out and that all the values are zero
        List<Double> corrections = multiplier.getCorrections(wrongSizedList);
        assertNotNull("the list shouldn't be null", corrections);

        // make sure all the values are zero so it's not actually broken
        Double zero = Double.valueOf("0.0");
        for (Double actual : corrections) {
            assertEquals("the value shouldn't be ", zero, actual);
        }
    }

    @Test
    public void testGetCorrections_CorrectNumberOfStories() {
        // make an input of the wrong size
        int size = ExampleMatrixMultiplication.NUMBER_OF_USER_STORIES;
        List<BigDecimal> wrongSizedList = createRandomNormalizedInput(size);

        // check that it's filled out and that all the values are zero
        List<Double> corrections = multiplier.getCorrections(wrongSizedList);
        assertNotNull("the list shouldn't be null", corrections);

        assertEquals("result should have right size", 1040, corrections.size());

        // make sure all in range
        for (Double actual : corrections) {
            assertTrue("the value should greater than or equal to min but was " + actual, actual >= 0);
            assertTrue("the value should less than or equal to max but was " + actual, actual <= 1);
        }
    }

    private List<BigDecimal> createRandomNormalizedInput(int size) {
        List<BigDecimal> normalizedInput = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            normalizedInput.add(new BigDecimal(Math.random()));
        }

        return normalizedInput;
    }

}
