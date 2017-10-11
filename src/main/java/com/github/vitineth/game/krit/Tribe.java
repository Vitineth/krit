package com.github.vitineth.game.krit;

import com.github.vitineth.game.krit.storage.GlobalStorage;
import com.github.vitineth.game.krit.storage.KritStorage;
import com.github.vitineth.game.krit.utils.ColorUtils;
import com.github.vitineth.game.krit.utils.MathUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
public class Tribe {

    private final UUID id = UUID.randomUUID();
    private List<Krit> members = new ArrayList<>();
    private Color color = ColorUtils.newRandomColor();

    public Tribe() {
    }

    public void add(Krit krit) {
        members.add(krit);
    }

    public boolean containsKrit(Krit krit) {
        return containsKrit(krit);
    }

    public Krit randomSelect() {
        return members.get(GlobalStorage.RANDOM.nextInt(members.size()));
    }

    public void remove(Krit krit) {
        remove(krit.getId());
    }

    public void remove(UUID uuid) {
        members.remove(uuid);
    }

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

    public Krit[] getWithinRadius(int radius, UUID uuid) {
        Krit krit = KritStorage.resolve(uuid);
        return getWithinRadius(radius, krit);
    }

    public Krit getClosest(Krit krit) {
        Krit closest = null;
        double closestDistance = -1;
        for (Krit active : members) {
            int x = krit.getLocation().getX() - active.getLocation().getX();
            int y = krit.getLocation().getY() - active.getLocation().getY();
            double distance = MathUtils.pythagoras(x, y);
            if (distance <= closestDistance || closest == null) {
                closest = active;
                closestDistance = distance;
            }
        }

        return closest;
    }

    public Krit getClosest(UUID uuid) {
        return getClosest(KritStorage.resolve(uuid));
    }

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
