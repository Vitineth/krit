package com.github.vitineth.game.krit.genes;

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
public class Gene {

    public enum Dominance{
        DOMINANT, RECESSIVE;
    }

    public Gene(Dominance dominance, Action action, Activation activation, List<Trigger> triggers, boolean expressed) {
        this.id = UUID.randomUUID();
        this.dominance = dominance;
        this.action = action;
        this.activation = activation;
        this.triggers = triggers;
        this.expressed = expressed;
    }

    private final UUID id;
    private Dominance dominance;
    private Action action;
    private Activation activation;
    private List<Trigger> triggers;
    private boolean expressed;

    public UUID getId() {
        return id;
    }

    public Dominance getDominance() {
        return dominance;
    }

    public void setDominance(Dominance dominance) {
        this.dominance = dominance;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public boolean isExpressed() {
        return expressed;
    }

    public void setExpressed(boolean expressed) {
        this.expressed = expressed;
    }
}
