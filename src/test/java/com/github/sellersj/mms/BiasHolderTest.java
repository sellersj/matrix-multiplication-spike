package com.github.sellersj.mms;

import org.junit.Test;

public class BiasHolderTest {

    private BiasHolder biasHolder = new BiasHolderImpl();

    @Test(expected = IllegalArgumentException.class)
    public void testLayerZero() {
        biasHolder.getLayer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayerFive() {
        biasHolder.getLayer(5);
    }

}
