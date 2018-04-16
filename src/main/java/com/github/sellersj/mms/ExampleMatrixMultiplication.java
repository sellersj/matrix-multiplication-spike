package com.github.sellersj.mms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

public class ExampleMatrixMultiplication {

    public static final int NUMBER_OF_FEATURES = 300;

    public static final int NUMBER_OF_USER_STORIES = 110;

    public static final int NUMBER_OF_USERS = 1;

    public static final int NUMBER_OF_PROGRAMS = 1040;

    // TODO these should be auto wired in
    private WeightHolder weightHolder = new WeightHolderImpl();

    private BiasHolder biasHolder = new BiasHolderImpl();

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

        // calculate the first 3 layers
        INDArray ehN = input;
        for (int layer = 1; layer <= 3; layer++) {
            ehN = calculateNetworkLayerOneTwoThree(ehN, layer);
        }

        // calculate the 4th layer slightly differently
        INDArray eh4 = calculateNetworkLayerFour(ehN);

        return eh4;
    }

    /**
     * The first 3 layers are the same, so the method is the same too.
     * 
     * @param input to use
     * @return the "a" answer
     */
    private INDArray calculateNetworkLayerOneTwoThree(INDArray input, int layer) {

        // k1...k3
        INDArray kayN = weightHolder.getLayer(layer).mmul(input);
        // z1...z3
        INDArray zedN = kayN.add(biasHolder.getLayer(layer));
        // a1...a3
        INDArray ehN = setNegitiveValuesToZero(zedN);

        // TODO change this to a debug logging statement
        System.out.println("For layer: " + layer + " the size is [" + ehN.rows() + ", " + ehN.columns() + "]");

        return ehN;
    }

    private INDArray calculateNetworkLayerFour(INDArray input) {
        int layer = 4;
        INDArray kayN = weightHolder.getLayer(layer).mmul(input);
        INDArray zedN = kayN.add(biasHolder.getLayer(layer));

        INDArray ehN = scaleElement(zedN);

        return ehN;
    }

    /**
     * Scale the values using: x = ( x - x_min ) / (x_max - x_min)
     * 
     * @param array
     * @return
     */
    private INDArray scaleElement(INDArray array) {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        // figure out the min and the max
        for (int rowIndex = 0; rowIndex < array.rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < array.columns(); colIndex++) {
                double value = array.getDouble(rowIndex, colIndex);
                max = Math.max(max, value);
                min = Math.min(min, value);
            }
        }
        // TOOD change to debug
        System.out.println(String.format("Min / Max are: %s, %s", min, max));

        // now figure out the new value
        for (int rowIndex = 0; rowIndex < array.rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < array.columns(); colIndex++) {
                double value = array.getDouble(rowIndex, colIndex);
                double scaled = (value - min) / (max - min);
                array.put(rowIndex, colIndex, scaled);
            }
        }

        return array;
    }

    /**
     * Sets the any negitive values to zero.
     * 
     * @param array to change
     * @return a "normalized" array
     */
    /* package */ INDArray setNegitiveValuesToZero(INDArray array) {

        for (int rowIndex = 0; rowIndex < array.rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < array.columns(); colIndex++) {
                double value = array.getDouble(rowIndex, colIndex);
                double normalizedValue = Math.max(0, value);
                array.put(rowIndex, colIndex, normalizedValue);
            }
        }

        return array;
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
