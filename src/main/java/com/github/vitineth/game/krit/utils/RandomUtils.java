package com.github.vitineth.game.krit.utils;

import com.github.vitineth.game.krit.storage.GlobalStorage;

import java.util.List;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class RandomUtils {

    public static <T> T choice(List<T> values) {
        return values.get(GlobalStorage.RANDOM.nextInt(values.size()));
    }

    public static <T> T choice(T[] values) {
        return values[GlobalStorage.RANDOM.nextInt(values.length)];
    }

    public static double random(double min, double max){
        return (int)(GlobalStorage.RANDOM.nextDouble() * (max - min) + min);
    }

}
