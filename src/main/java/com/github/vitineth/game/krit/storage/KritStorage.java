package com.github.vitineth.game.krit.storage;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.Location;
import com.github.vitineth.game.krit.Tribe;
import com.github.vitineth.game.krit.utils.Size;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class KritStorage {

    private static HashMap<UUID, Krit> mappings = new HashMap<>();
    private static List<Tribe> tribes = new ArrayList<>();
    private static List<Krit> creatures = new ArrayList<>();
    private static Krit[][] map;
    private static Size size;

    public static HashMap<UUID, Krit> getMappings() {
        return mappings;
    }

    public static void prune() {
        List<Krit> creaturesCopy = new ArrayList<>();
        HashMap<UUID, Krit> mappingsCopy = new HashMap<>();
        Krit[][] mapCopy = new Krit[size.getHeight()][size.getWidth()];

        creatures.stream().filter(Krit::isAlive).forEach(c -> {
            creaturesCopy.add(c);
            mappingsCopy.put(c.getId(), c);
            mapCopy[c.getLocation().getY()][c.getLocation().getX()] = c;
        });


        mappings = mappingsCopy;
        creatures = creaturesCopy;
        map = mapCopy;
    }

    public static List<Krit> getCreatures() {
        return creatures;
    }

    public static Krit resolve(UUID uuid) {
        return mappings.get(uuid);
    }

    public static Krit get(int x, int y) {
        return map[y][x];
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < size.getWidth() && y < size.getHeight();
    }

    public static void addTribe(Tribe tribe) {
        tribes.add(tribe);
    }

    public static List<Tribe> getTribes() {
        return tribes;
    }

    public static boolean isFree(Location location) {
        return isFree(location.getX(), location.getY());
    }

    public static boolean isFree(int x, int y) {
        return isValid(x, y) && get(x, y) == null;
    }

    public static void set(Krit krit, Location location) {
        set(krit, location.getX(), location.getY());
    }

    public static Size getSize() {
        return size;
    }

    public static void clear(Location location) {
        clear(location.getY(), location.getY());
    }

    public static Krit get(Location location) {
        return get(location.getX(), location.getY());
    }

    public static void clear(int x, int y) {
        map[y][x] = null;
    }

    public static void set(Krit krit, int x, int y) {
        map[y][x] = krit;
    }

    public static void initialise(Size dimension) {
        mappings = null;
        tribes = null;
        creatures = null;
        map = null;
        size = null;

        mappings = new HashMap<>();
        tribes = new ArrayList<>();
        creatures = new ArrayList<>();
        map = new Krit[dimension.getHeight()][dimension.getWidth()];
        size = dimension;
    }

    public static void add(Krit krit) {
        mappings.put(krit.getId(), krit);
        creatures.add(krit);
    }

    public static void remove(Krit krit) {
        mappings.remove(krit.getId());
        creatures.remove(krit);
    }

    public static Set<Location> getInRadius(int radius, Location center) {
        Set<Location> matches = new HashSet<>();
        for (int x = center.getX() - radius; x < center.getX() + radius; x++) {
            double y_span = radius * Math.sin(Math.acos((center.getX() - x) / radius));
            for (double y = center.getY() - y_span; y < center.getY() + y_span; y++) {
                matches.add(new Location(x, (int) y));
            }
        }
        return matches;
    }

    public static void main(String[] args){
        System.out.println(getInRadius(5, new Location(0, 0)));
    }

}
