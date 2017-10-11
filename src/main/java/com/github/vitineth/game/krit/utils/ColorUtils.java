package com.github.vitineth.game.krit.utils;

import com.github.vitineth.game.krit.Krit;

import java.awt.Color;
import java.util.Random;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class ColorUtils {

    private static Random random = new Random();

    public static Color newRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static Color averageParents(Krit k1, Krit k2) {
        Color c1 = k1.getColor();
        Color c2 = k2.getColor();

        int r = (int) Math.sqrt(((c1.getRed() * c1.getRed()) + (c2.getRed() * c2.getRed())) / 2);
        int g = (int) Math.sqrt(((c1.getGreen() * c1.getGreen()) + (c2.getGreen() * c2.getGreen())) / 2);
        int b = (int) Math.sqrt(((c1.getBlue() * c1.getBlue()) + (c2.getBlue() * c2.getBlue())) / 2);

        r += RandomUtils.random(-20, 20);
        g += RandomUtils.random(-20, 20);
        b += RandomUtils.random(-20, 20);

        if (r < 0) r = 0;
        if (r > 255) r = 255;
        if (g < 0) g = 0;
        if (g > 255) g = 255;
        if (b < 0) b = 0;
        if (b > 255) b = 255;

        return new Color(r, g, b);
    }

}
