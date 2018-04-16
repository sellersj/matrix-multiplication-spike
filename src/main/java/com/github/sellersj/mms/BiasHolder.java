package com.github.sellersj.mms;

import org.nd4j.linalg.api.ndarray.INDArray;

public interface BiasHolder {

    INDArray getLayer(int layer);

}
