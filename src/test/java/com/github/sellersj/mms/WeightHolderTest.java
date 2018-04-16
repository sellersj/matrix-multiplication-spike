package com.github.sellersj.mms;

import org.junit.Test;

public class WeightHolderTest {

    private WeightHolder weightHolder = new WeightHolderImpl();

    @Test(expected = IllegalArgumentException.class)
    public void testLayerZero() {
        weightHolder.getLayer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayerFive() {
        weightHolder.getLayer(5);
    }

}
