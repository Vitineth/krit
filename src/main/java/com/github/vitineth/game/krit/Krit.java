package com.github.vitineth.game.krit;

import com.github.vitineth.game.krit.genes.Gene;
import com.github.vitineth.game.krit.genes.Mutation;
import com.github.vitineth.game.krit.genes.Trigger;
import com.github.vitineth.game.krit.storage.GlobalStorage;
import com.github.vitineth.game.krit.storage.KritStorage;
import com.github.vitineth.game.krit.storage.NameStorage;
import com.github.vitineth.game.krit.utils.ColorUtils;
import com.github.vitineth.game.krit.utils.LocationUtils;
import com.github.vitineth.game.krit.utils.MathUtils;
import com.github.vitineth.game.krit.utils.RandomUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The representation of the living entity that populates the world of the game. Krits work off a set of 23 rules (as
 * of 2017-10-11):
 * <ol>
 * <li>Only one Krit can occupy one tile at a time.</li>
 * <li>Krits can only move within a 1 square radius (x and y offset between -1 and 1).</li>
 * <li>A Krits health will decay at a constant rate which will be determined at its birth</li>
 * <li>At each update, each expressed genetic trait will receive an update which can edit the Krits details and each unexpressed trait will have its triggers ran</li>
 * <li>If a Krit is within 5 of a member of another tribe for 3 or more turns, it will become aggressive. If it is separated (greater than 5 away) from another tribe for 3 turns it will become passive</li>
 * <li>When a Krit is aggressive, it will identify the closest member of another tribe and move towards it. If it is next to it, it will attempt to strike for between 1 and 4 damage with ~80% chance of hitting.</li>
 * <li>If the creature is not aggressive, it will try to move towards the averaged center of the tribe or pick a random x and y offset between -1 and 1 with a chance of 30%.</li>
 * <li>Pregnancy will take 9 updates to complete.</li>
 * <li>A female krit can have between 1 and 3 children</li>
 * <li>A female krit has a ~0.8% chance of losing a child per update during pregnancy</li>
 * <li>A child should be placed within a 1 block radius of the parent and should be lost if a space is not available.</li>
 * <li>A child krit should determine its new health by averaging the parents values with a jitter of between -4 and 4 (including decimals).</li>
 * <li>A child krit should determine its new decay rate by averaging the parents with jitter of -0.2 to 0.2 for the decay rate and -3 to 3 for its frequency.</li>
 * <li>A child krit should have a randomly assigned sex</li>
 * <li>A child krit should take the average of the parents colours with a jitter of -20 to 20 for each of the r g and b values.</li>
 * <li>A child krit should have a minimum decay rate of 0.2/2 (set if the values are set below 0)</li>
 * <li>A child should take all dominant genes of the parents</li>
 * <li>A child should take all recessive genes which exist on both the parents</li>
 * <li>A child should take the tribe of the mother</li>
 * <li>A krit has ~100% chance of mating with its mate when adjacent to each other</li>
 * <li>A krit has ~60% chance of mating with a member of its own tribe if it does not have a Mate</li>
 * <li>A krit has a ~40% chance of mating with a member of another tribe if it does not have a mate</li>
 * <li>If a krit does not have a mate and mates with another krit, that krit will become its mate 　</li>
 * </ol>
 *
 *
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 06/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 06/10/2017
 */
public class Krit {

    private final UUID id;
    private Location location;
    private Location origin;
    private double health;
    private double maxHealth;
    private double decayRate;
    private int decayFrequency;
    private boolean alive;
    private Sex sex;
    private Color color;
    private Tribe tribe;
    private boolean aggressive;
    private Krit mate;
    private boolean pregnant;
    private int pregnancyTerm;
    private int childrenCount;
    private Krit[] parents;
    private List<UUID> children;
    private List<Gene> genetics;
    private int[][] safetyMap;
    private int updateCount;
    private int separationTurns;
    private int proximityTurns;

    public Krit(Location location, double health, double decayRate, int decayFrequency, Sex sex, Color color, Tribe tribe, Krit mother, Krit father, List<Gene> genetics) {
        id = UUID.randomUUID();
        NameStorage.map(this);

        this.location = location;
        this.origin = new Location(location);
        KritStorage.set(this, location);
        KritStorage.add(this);

        this.health = health;
        this.maxHealth = health;

        this.decayRate = decayRate;
        this.decayFrequency = decayFrequency;

        this.sex = sex;
        this.color = color;

        this.aggressive = false;

        this.mate = null;
        this.pregnant = false;
        this.pregnancyTerm = 0;
        this.childrenCount = 0;

        this.tribe = tribe;
        this.parents = new Krit[]{mother, father};
        this.children = new ArrayList<>();

        this.genetics = genetics;

        this.updateCount = 0;
        this.alive = health > 0;

        this.separationTurns = 0;
        this.proximityTurns = 0;

//        this.safetyMap = new int[KritStorage.getSize().getHeight()][KritStorage.getSize().getWidth()];
    }

    public void update() {
        this.updateCount += 1;
        if (this.updateCount >= 20000) this.updateCount = 0;

        decayUpdate();
        geneticUpdate();

        if (!aggressionUpdate()) {
            moveUpdate();
        }

        if (aggressive) aggressionAction();

        pregnancyUpdate();
//        safetyMapUpdate();
    }

    public void moveUpdate() {
        Location tribe_location = tribe.getAverageLocation();

        int xo = 0;
        int yo = 0;

        if (tribe_location.getX() > location.getX()) xo = 1;
        else if (tribe_location.getX() < location.getX()) xo = -1;

        if (tribe_location.getY() > location.getY()) yo = 1;
        else if (tribe_location.getY() < location.getY()) yo = -1;

        if (GlobalStorage.RANDOM.nextDouble() <= 0.3) xo = RandomUtils.choice(new Integer[]{-1, 0, 1});
        if (GlobalStorage.RANDOM.nextDouble() <= 0.3) yo = RandomUtils.choice(new Integer[]{-1, 0, 1});

        Location newLocation = new Location(location, xo, yo);
        if (KritStorage.isFree(newLocation)) {
            setLocation(newLocation);
        }
    }

    public void aggressionAction() {
        List<Location> possibilities = new ArrayList<>();
        for (int xO = -1; xO < 2; xO++) {
            for (int yO = -1; yO < 2; yO++) {
                if (KritStorage.isValid(location.getX() + xO, location.getY() + yO)) {
                    if (!KritStorage.isFree(location.getX() + xO, location.getY() + yO)) {
                        Krit tile = KritStorage.get(location.getX() + xO, location.getY() + yO);
                        if (!tile.tribe.getId().equals(tribe.getId())) {
                            possibilities.add(new Location(location, xO, yO));
                        }
                    }
                }
            }
        }


        if (possibilities.size() > 0) {
            System.out.print("");
            Location selection = RandomUtils.choice(possibilities);
            Krit krit = KritStorage.get(selection);
            if (GlobalStorage.RANDOM.nextDouble() <= 0.8) {
                int damage = GlobalStorage.RANDOM.nextInt(3) + 1;

                krit.setHealth(krit.getHealth() - damage);
                krit.setAlive(krit.getHealth() > 0);
            }
        }
    }

    public void handleBirth() {
        for (int i = 0; i < childrenCount; i++) {
            // Average the parents health and add a jitter between -4 and 4
            double newHealth = ((maxHealth + mate.maxHealth) / 2) + RandomUtils.random(-4, 4);
            double newDecayRate = ((decayRate + mate.decayRate) / 2) + RandomUtils.random(-0.2, 0.2);
            int newDecayFrequency = (int) (((decayFrequency + mate.decayFrequency) / 2) + RandomUtils.random(-3, 3));
            Sex newSex = Sex.select();
            Color newColor = ColorUtils.averageParents(this, mate);

            if (newDecayRate <= 0) newDecayRate = 0.2;
            if (newDecayFrequency <= 0) newDecayRate = 2;

            List<Gene> finalGenes = new ArrayList<>();

            for (Gene gene : genetics) {
                if (gene.getDominance() == Gene.Dominance.DOMINANT) finalGenes.add(gene);
                else if (mate.genetics.contains(gene)) finalGenes.add(gene);
            }

            finalGenes.addAll(GlobalStorage.mutations.stream().filter(mutation -> GlobalStorage.RANDOM.nextDouble() <= mutation.getExpressionChance()).collect(Collectors.toList()));

            Location newLocation = null;
            for (int xo = -1; xo < 2 && newLocation == null; xo++) {
                for (int yo = -1; yo < 2 && newLocation == null; yo++) {
                    if (KritStorage.isFree(location.getX() + xo, location.getY() + yo))
                        newLocation = new Location(location, xo, yo);
                }
            }

            if (newLocation != null) {
                children.add(new Krit(newLocation, newHealth, newDecayRate, newDecayFrequency, newSex, newColor, tribe, this, mate, finalGenes).getId());
            }
        }

        pregnancyTerm = 0;
        pregnant = false;
        childrenCount = 0;
    }

    public void pregnancyUpdate() {
        if (getSex() != Sex.FEMALE) return;

        if (isPregnant()) {
            pregnancyTerm += 1;
            if (GlobalStorage.RANDOM.nextDouble() <= 0.008) {
                pregnancyTerm = 0;
                pregnant = false;
                childrenCount = 0;
            } else if (pregnancyTerm >= 9) {
                handleBirth();
            }
        } else {
            List<Location> tiles = LocationUtils.getAdjacentKritTiles(this);
            if (tiles.size() > 0) {
                Krit selected = null;
                if (mate != null) {
                    for (Location l : tiles) {
                        if (KritStorage.get(l) == mate) {
                            selected = mate;
                            break;
                        }
                    }
                } else {
                    List<Krit> sameTribe = new ArrayList<>();
                    List<Krit> alternateTribe = new ArrayList<>();

                    for (Location l : tiles) {
                        Krit k = KritStorage.get(l);
                        if (k.tribe == tribe) sameTribe.add(k);
                        else alternateTribe.add(k);
                    }

                    if (sameTribe.size() > 0 && GlobalStorage.RANDOM.nextDouble() <= 0.6) {
                        selected = RandomUtils.choice(sameTribe);
                    } else if (alternateTribe.size() > 0 && GlobalStorage.RANDOM.nextDouble() <= 0.4) {
                        selected = RandomUtils.choice(alternateTribe);
                    }
                }

                if (selected != null) {
                    mate = selected;
                    pregnant = true;
                    pregnancyTerm = 0;
                    childrenCount = GlobalStorage.RANDOM.nextInt(2) + 1;
                }
            }
        }
    }

    public boolean aggressionUpdate() { //TODO
        List<Location> radius = KritStorage.getInRadius(5, location).stream()
                .filter(l -> l.getX() > 0 && l.getY() > 0)
                .filter(l -> l.getX() < KritStorage.getSize().getWidth() && l.getY() < KritStorage.getSize().getHeight())
                .filter(l -> !KritStorage.isFree(l))
                .filter(l -> KritStorage.get(l).getTribe() != getTribe())
                .collect(Collectors.toList());
        if (radius.size() == 0){
            separationTurns += 1;
            proximityTurns = 0;
            return false;
        }else{
            separationTurns = 0;
            proximityTurns += 1;
        }

        if (proximityTurns >= 3) {
            // TODO: Skipping tribe check
            aggressive = true;
        } else if (separationTurns >= 3) {
            aggressive = false;
        }

        if (!aggressive) return false;

        int xo = 0;
        int yo = 0;

        Krit random = KritStorage.get(radius.get(0));

        if (random.getLocation().getX() > getLocation().getX()) xo = 1;
        else if (random.getLocation().getX() < getLocation().getX()) xo = -1;

        if (random.getLocation().getY() > getLocation().getY()) yo = 1;
        else if (random.getLocation().getY() < getLocation().getY()) yo = -1;

        Location newLocation = new Location(getLocation(), xo, yo);
        if (KritStorage.isFree(newLocation)) {
            setLocation(newLocation);
            return true;
        }

        return false;

//        HashMap<Krit, Double> distances = new HashMap<>();
//        for (Krit krit : KritStorage.getCreatures()) {
//            if (krit.getTribe() != getTribe()) {
//                int xd = krit.getLocation().getX() - getLocation().getX();
//                int yd = krit.getLocation().getY() - getLocation().getY();
//                distances.put(krit, MathUtils.pythagoras(xd, yd));
//            }
//        }
//
//        double closestDistance = -1;
//        Krit closestKrit = null;
//
//        //noinspection Convert2streamapi
//        for (Krit krit : distances.keySet()) {
//            if (closestKrit == null || distances.get(krit) < closestDistance) {
//                closestDistance = distances.get(krit);
//                closestKrit = krit;
//            }
//        }
//
//        if (closestKrit == null) {
//            separationTurns += 1;
//            proximityTurns = 0;
//            return false;
//        }
//
//        if (closestDistance <= 5) {
//            separationTurns = 0;
//            proximityTurns += 1;
//        } else {
//            separationTurns += 1;
//            proximityTurns = 0;
//        }
//
//        if (proximityTurns >= 3) {
//            // TODO: Skipping tribe check
//            aggressive = true;
//        } else if (separationTurns >= 3) {
//            aggressive = false;
//        }
//
//        if (!aggressive) return false;
//
//        int xo = 0;
//        int yo = 0;
//
//        if (closestKrit.getLocation().getX() > getLocation().getX()) xo = 1;
//        else if (closestKrit.getLocation().getX() < getLocation().getX()) xo = -1;
//
//        if (closestKrit.getLocation().getY() > getLocation().getY()) yo = 1;
//        else if (closestKrit.getLocation().getY() < getLocation().getY()) yo = -1;
//
//        Location newLocation = new Location(getLocation(), xo, yo);
//        if (KritStorage.isFree(newLocation)) {
//            setLocation(newLocation);
//            return true;
//        }
//
//        return false;
    }

    public void safetyCalculation() {

    }

    public void safetyMapUpdate() {

    }

    public void geneticUpdate() {
        genetics.stream().forEach(gene -> {
            if (gene.isExpressed()) gene.getAction();
            else gene.getTriggers().stream().forEach(t -> t.test(this));
        });
    }

    public void decayUpdate() {
        if (decayFrequency == 0) decayFrequency = 3;
        if (updateCount % decayFrequency == 0) health -= decayRate;
        if (health <= 0) alive = false;
    }

    public UUID getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) System.out.println("WHAT");
        KritStorage.clear(this.location);
        KritStorage.set(this, location);
        this.location = location;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(double decayRate) {
        this.decayRate = decayRate;
    }

    public int getDecayFrequency() {
        return decayFrequency;
    }

    public void setDecayFrequency(int decayFrequency) {
        this.decayFrequency = decayFrequency;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Tribe getTribe() {
        return tribe;
    }

    public void setTribe(Tribe tribe) {
        this.tribe = tribe;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public Krit getMate() {
        return mate;
    }

    public void setMate(Krit mate) {
        this.mate = mate;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public int getPregnancyTerm() {
        return pregnancyTerm;
    }

    public void setPregnancyTerm(int pregnancyTerm) {
        this.pregnancyTerm = pregnancyTerm;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Krit[] getParents() {
        return parents;
    }

    public void setParents(Krit[] parents) {
        this.parents = parents;
    }

    public List<UUID> getChildren() {
        return children;
    }

    public void setChildren(List<UUID> children) {
        this.children = children;
    }

    public List<Gene> getGenetics() {
        return genetics;
    }

    public void setGenetics(List<Gene> genetics) {
        this.genetics = genetics;
    }

    public int[][] getSafetyMap() {
        return safetyMap;
    }

    public void setSafetyMap(int[][] safetyMap) {
        this.safetyMap = safetyMap;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getSeparationTurns() {
        return separationTurns;
    }

    public void setSeparationTurns(int separationTurns) {
        this.separationTurns = separationTurns;
    }

    public int getProximityTurns() {
        return proximityTurns;
    }

    public void setProximityTurns(int proximityTurns) {
        this.proximityTurns = proximityTurns;
    }

    public enum Sex {
        MALE, FEMALE;

        public static Sex select() {
            if (GlobalStorage.RANDOM.nextBoolean()) return MALE;
            else return FEMALE;
        }
    }
}
