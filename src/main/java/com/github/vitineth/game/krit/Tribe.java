package com.github.vitineth.game.krit;

import com.github.vitineth.game.krit.storage.GlobalStorage;
import com.github.vitineth.game.krit.storage.KritStorage;
import com.github.vitineth.game.krit.utils.ColorUtils;
import com.github.vitineth.game.krit.utils.MathUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Represents a tribe of Krits. When created, a Krit must be assigned to the tribe as it bases its movement and other
 * factors around it. Tribes are fairly simple as they just have an id, a set of member krits and a colour.
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class Tribe {

    /**
     * The unique identifier for the Tribe. This is designed not to collide with any other Tribe ids but it cannot be
     * guaranteed.
     */
    private final UUID id = UUID.randomUUID();
    /**
     * The set of members of the Tribe. Each of these refers to a living or dead member of the tribe.
     * <p/>
     * TODO:(Ryan Delaney//vitineth@gmail.com): Cull dead members of the tribe from this list.
     */
    private List<Krit> members = new ArrayList<>();
    /**
     * The colour for the tribe. This is also designed to be unique but it does not have to be.
     */
    private Color color = ColorUtils.newRandomColor();

    /**
     * Create a new tribe. The ID and colour are randomly generated on instantiation and the members list is created so
     * no parameters have to be passed in order to create it.
     */
    public Tribe() {
    }

    /**
     * Add a member to the tribe
     *
     * @param krit {@link Krit} The member of the tribe to add
     */
    public void add(Krit krit) {
        members.add(krit);
    }

    /**
     * Culls of dead members of the tribe from the list of members. This is designed to reduce the unnecessary storage
     * of members of the tribe who no longer should have an effect on the tribe.
     */
    public void cull() {
        Iterator<Krit> k = members.iterator();
        while (k.hasNext()) {
            Krit krit = k.next();
            if (!krit.isAlive() || krit.getHealth() <= 0) k.remove();
        }
    }

    /**
     * Tests whether the given krit exists as a member of the tribe
     *
     * @param krit {@link Krit} The krit to test
     * @return true if the given krit exists in the tribe
     */
    public boolean containsKrit(Krit krit) {
        return members.contains(krit);
    }

    /**
     * Returns a random member of the tribe. It uses the {@link GlobalStorage#RANDOM} with
     * {@link java.util.Random#nextInt(int)} to generate using the length of the members list in order to select a
     * member
     *
     * @return {@link Krit} A random member of the tribe
     */
    public Krit randomSelect() {
        return members.get(GlobalStorage.RANDOM.nextInt(members.size()));
    }

    /**
     * Removes a given Krit from the list
     *
     * @param krit {@link Krit} the krit to remove
     */
    public void remove(Krit krit) {
        members.remove(krit);
    }

    /**
     * Returns all Krits within the given radius value. This is not the most efficient method of doing it but it will
     * get all tiles within a 2*radius by 2*radius square and then test each of the points to determine its distance
     * from the given center point and only return matches that are less than or equal to the radius.
     *
     * @param radius int The radius with which the distance from the center to the Krits must be less than or equal to.
     * @param krit   {@link Krit} The krit to mark as the center
     * @return {@link Krit}[] All krits within radius of the given krit
     */
    public Krit[] getWithinRadius(int radius, Krit krit) {
        List<Krit> matches = new ArrayList<>();
        for (int xo = -radius; xo < radius + 1; xo++) {
            for (int yo = -radius; yo < radius + 1; yo++) {
                if (!KritStorage.isFree(krit.getLocation().getX() + xo, krit.getLocation().getY() + yo)) {
                    Krit krit1 = KritStorage.get(krit.getLocation().getX() + xo, krit.getLocation().getY() + yo);
                    int x = krit.getLocation().getX() - krit1.getLocation().getX();
                    int y = krit.getLocation().getY() - krit1.getLocation().getY();
                    if (MathUtils.pythagoras(x, y) <= radius) {
                        matches.add(krit1);
                    }
                }
            }
        }
        return matches.toArray(new Krit[matches.size()]);
    }

    /**
     * Return the closest member of the tribe. This will iterate through the entire tribe and therefore when the tribe
     * size is very large it will take a large amount of time. This will be O(n) for the size of the tribe.
     * <p/>
     * This will return null if there are no krits close to it (it is the only member of the tribe)
     *
     * @param krit {@link Krit} The krit from which to find the closest other member of the tribe
     * @return {@link Krit} The closest krit to the given krit or null if none can be found
     */
    public Krit getClosest(Krit krit) {
        Krit closest = null;
        double closestDistance = -1;
        for (Krit active : members) {
            if (active != krit) {
                int x = krit.getLocation().getX() - active.getLocation().getX();
                int y = krit.getLocation().getY() - active.getLocation().getY();
                double distance = MathUtils.pythagoras(x, y);
                if (distance <= closestDistance || closest == null) {
                    closest = active;
                    closestDistance = distance;
                }
            }
        }

        return closest;
    }

    /**
     * Returns the average location of the tibe. This will sum the x and y of every member of the tribe and then divide
     * by the number of members. If the tribe is empty it will return an error
     *
     * @return {@link} The location of the rounded center of the tribe
     */
    public Location getAverageLocation() {
        int xTotal = 0;
        int yTotal = 0;

        for (Krit active : members) {
            if (active == null) continue;

            xTotal += active.getLocation().getX();
            yTotal += active.getLocation().getY();
        }

        return new Location(xTotal / members.size(), yTotal / members.size());
    }

    public List<Krit> getMembers() {
        return members;
    }

    public void setMembers(List<Krit> members) {
        this.members = members;
    }

    public UUID getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
