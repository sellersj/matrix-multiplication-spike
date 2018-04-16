package com.github.sellersj.mms;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class WeightHolderImpl implements WeightHolder {

    @Override
    public INDArray getLayer(int layer) {
        switch (layer) {
            case 1:
                return Nd4j.rand(ExampleMatrixMultiplication.NUMBER_OF_FEATURES,
                    ExampleMatrixMultiplication.NUMBER_OF_USER_STORIES);

            case 2:
                return Nd4j.rand(ExampleMatrixMultiplication.NUMBER_OF_FEATURES,
                    ExampleMatrixMultiplication.NUMBER_OF_FEATURES);

            case 3:
                return Nd4j.rand(ExampleMatrixMultiplication.NUMBER_OF_FEATURES,
                    ExampleMatrixMultiplication.NUMBER_OF_FEATURES);

            case 4:
                return Nd4j.rand(ExampleMatrixMultiplication.NUMBER_OF_PROGRAMS,
                    ExampleMatrixMultiplication.NUMBER_OF_FEATURES);

            default:
                throw new IllegalArgumentException("The layer " + layer + " is outside the bounds of 1 to 4");
        }
    }

}
