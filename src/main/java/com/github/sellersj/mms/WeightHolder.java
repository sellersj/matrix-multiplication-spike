package com.github.sellersj.mms;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface WeightHolder {

    INDArray getLayer(int layer);

}
