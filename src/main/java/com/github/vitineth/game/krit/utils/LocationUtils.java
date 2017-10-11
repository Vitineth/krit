package com.github.vitineth.game.krit.utils;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.Location;
import com.github.vitineth.game.krit.storage.KritStorage;

import java.util.ArrayList;
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
public class LocationUtils {

    public static List<Location> getAdjacentKritTiles(Krit krit) {
        List<Location> possibilities = new ArrayList<>();
        for (int xO = -1; xO < 2; xO++) {
            for (int yO = -1; yO < 2; yO++) {
                if (KritStorage.isValid(krit.getLocation().getX() + xO, krit.getLocation().getY() + yO)) {
                    if (!KritStorage.isFree(krit.getLocation().getX() + xO, krit.getLocation().getY() + yO)) {
                        possibilities.add(new Location(krit.getLocation(), xO, yO));
                    }
                }
            }
        }
        return possibilities;
    }

}
