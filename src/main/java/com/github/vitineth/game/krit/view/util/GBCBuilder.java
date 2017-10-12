package com.github.vitineth.game.krit.view.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 12/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 12/10/2017
 */
public class GBCBuilder {

    private GridBagConstraints gbc;

    public GBCBuilder() {
        gbc = new GridBagConstraints();
    }

    public GBCBuilder copy() {
        GBCBuilder builder = new GBCBuilder();
        builder.gbc = (GridBagConstraints) gbc.clone();
        return builder;
    }

    public GBCBuilder x(int x) {
        gbc.gridx = x;
        return this;
    }

    public GBCBuilder y(int y) {
        gbc.gridy = y;
        return this;
    }

    public GBCBuilder a(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    public GBCBuilder w(int width) {
        gbc.gridwidth = width;
        return this;
    }

    public GBCBuilder h(int height) {
        gbc.gridheight = height;
        return this;
    }

    public GBCBuilder f(int fill) {
        gbc.fill = fill;
        return this;
    }

    public GBCBuilder i(Insets insets) {
        gbc.insets = insets;
        return this;
    }

    public GBCBuilder ipx(int ipadx) {
        gbc.ipadx = ipadx;
        return this;
    }

    public GBCBuilder ipy(int ipady) {
        gbc.ipady = ipady;
        return this;
    }

    public GBCBuilder wx(double weightx) {
        gbc.weightx = weightx;
        return this;
    }

    public GBCBuilder wy(double weighty) {
        gbc.weighty = weighty;
        return this;
    }

    public GridBagConstraints build() {
        return gbc;
    }


}
