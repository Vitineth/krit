package com.github.vitineth.game.krit.genes;

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
public class Mutation extends Gene {

    private double expressionChance;

    public Mutation(Dominance dominance, Action action, Activation activation, List<Trigger> trigger, boolean expressed, double expressionChance) {
        super(dominance, action, activation, trigger, expressed);
        this.expressionChance = expressionChance;
    }

    public double getExpressionChance() {
        return expressionChance;
    }

    public void setExpressionChance(double expressionChance) {
        this.expressionChance = expressionChance;
    }
}
