package com.github.sellersj.mms;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class ExampleMatrixMultiplication {

    public static final int NUMBER_OF_FEATURES = 3000;

    public static final int NUMBER_OF_USER_STORIES = 86;

    public static final int NUMBER_OF_USERS = 1;

    public static void main(String[] args) throws Exception {
        //
        List<BigDecimal> normalizedList = new ArrayList<>();

        INDArray userstory = Nd4j.rand(NUMBER_OF_USER_STORIES, NUMBER_OF_USERS);

        int nRows = 1;
        int nColumns = 82;
        INDArray ones = Nd4j.ones(nRows, nColumns);
        INDArray rand = Nd4j.rand(nRows, nColumns);

        INDArray result = rand.mul(ones);

        result.add(addArray());

        System.out.println(result);
    }

    private static INDArray addArray() {
        return Nd4j.rand(1, 82);
    }

    public List<Long> getCorrections(List<BigDecimal> normalizedInput) {
        if (NUMBER_OF_USER_STORIES != normalizedInput.size()) {
            // TODO log an error
            return getNonBreakingResult(normalizedInput.size());
        }

        return null;
    }

    /**
     * This is for when the inputs don't match, we return a list that won't break the running app.
     * 
     * @param size
     * @return
     */
    private List<Long> getNonBreakingResult(int size) {
        List<Long> norm = new ArrayList<>(size);
        Long zero = Long.valueOf(0);
        for (int i = 0; i < size; i++) {
            norm.add(zero);
        }

        return norm;
    }
}
