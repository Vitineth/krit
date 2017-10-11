package com.github.vitineth.game.krit;

/**
 * A representation of an X and Y location
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location location) {
        this.x = location.x;
        this.y = location.y;
    }

    /**
     * Creates a new version of the {@link Location} with the x and y offset by the xO and yO values respectively.
     *
     * @param location {@link Location} the base location from which to offset
     * @param xO       int the X offset
     * @param yO       int the Y offset
     */
    public Location(Location location, int xO, int yO) {
        this.x = location.x + xO;
        this.y = location.y + yO;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
