package com.github.sellersj.mms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.factory.Nd4j;

public class ExampleMatrixMultiplication {

    public static final int NUMBER_OF_FEATURES = 300;

    public static final int NUMBER_OF_USER_STORIES = 110;

    public static final int NUMBER_OF_USERS = 1;

    public List<Double> getCorrections(List<BigDecimal> normalizedInput) {
        if (NUMBER_OF_USER_STORIES != normalizedInput.size()) {
            // TODO log an error
            return getNonBreakingResult(normalizedInput.size());
        }

        INDArray input = convertTo(normalizedInput);
        INDArray calculated = calculate(input);
        List<Double> result = convertTo(calculated);

        return result;
    }

    private INDArray convertTo(List<BigDecimal> normalizedInput) {
        int size = NUMBER_OF_USER_STORIES;
        INDArray array = new NDArray(size, 1);
        int i = 0;
        for (BigDecimal bigDecimal : normalizedInput) {
            array.putScalar(i, bigDecimal.doubleValue());
        }

        return array;
    }

    /**
     * This will convert from the internal matrix multiplication library to normal java classes.
     * 
     * @param calculated objects calculated
     * @return
     */
    private List<Double> convertTo(INDArray calculated) {
        List<Double> list = new ArrayList<>();

        for (int i = 0; i < calculated.rows(); i++) {
            // get the double out of the first column
            double value = calculated.getDouble(i, 0);

            // TODO check why they are expecting a long in the original spec
            list.add(Double.valueOf(value));
        }

        return list;
    }

    private INDArray calculate(INDArray input) {
        // TODO actually do the calculation here

        // TODO return a mock for now
        INDArray mock = Nd4j.rand(1040, 1);

        return mock;
    }

    /**
     * This is for when the inputs don't match, we return a list that won't break the running app.
     * 
     * @param size
     * @return
     */
    private List<Double> getNonBreakingResult(int size) {
        List<Double> norm = new ArrayList<>(size);
        Double zero = Double.valueOf(0);
        for (int i = 0; i < size; i++) {
            norm.add(zero);
        }

        return norm;
    }
}
