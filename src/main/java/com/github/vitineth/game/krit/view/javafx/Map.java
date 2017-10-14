package com.github.vitineth.game.krit.view.javafx;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.storage.KritStorage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 12/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 12/10/2017
 */
@SuppressWarnings("Duplicates")

public class Map extends Application {

    private Image grid;
    private KritManager manager;
    private Canvas canvas;

    private DecimalFormat decimalFormat = new DecimalFormat("00000.00");

    public Map(KritManager manager) {
        this.manager = manager;

        BufferedImage grid = new BufferedImage(KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = grid.createGraphics();
        g.setColor(new java.awt.Color(60, 60, 60));
        g.fillRect(0, 0, KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10);
        g.setColor(new java.awt.Color(76, 76, 76));
        for (int x = 0; x < grid.getWidth(); x += 10) {
            g.drawLine(x, 0, x, KritStorage.getSize().getHeight() * 10);
        }
        for (int y = 0; y < grid.getHeight(); y += 10) {
            g.drawLine(0, y, KritStorage.getSize().getWidth() * 10, y);
        }

        this.grid = SwingFXUtils.toFXImage(grid, null);
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    public Scene alternateStart() {
        canvas = new Canvas(KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10);
        BorderPane pane = new BorderPane(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.drawImage(grid, 0, 0);

        canvas.setOnMouseClicked(e -> {
            int x = (int) (e.getX() / 10d);
            int y = (int) (e.getY() / 10d);

            if (!KritStorage.isFree(x, y)) {
                manager.select(KritStorage.get(x, y));
            }
        });

        long last = System.currentTimeMillis();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!manager.isFrozen())
                    if (System.currentTimeMillis() - last >= 500) {
                        if (manager.isResetTriggered()) {
                            manager.resetClear();
                        } else {
                            update();
                        }
                    }
            }
        }.start();

        return new Scene(pane);
    }

    public void update() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(new Color(0.192, 0.2, 0.212, 1d));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.drawImage(grid, 0, 0);

        CopyOnWriteArrayList<Krit> copy = new CopyOnWriteArrayList<>(KritStorage.getCreatures());
        copy.stream().forEach(Krit::update);
        copy.stream().forEach(k -> {
            int leftX = k.getLocation().getX() * 10;
            int rightX = leftX + 9;
            int topY = k.getLocation().getY() * 10;
            int bottomY = topY + 9;

            Polygon tribe = new Polygon();
            tribe.addPoint(leftX, topY);
            tribe.addPoint(leftX, bottomY);
            tribe.addPoint(rightX, topY);

            Polygon krit = new Polygon();
            krit.addPoint(leftX, bottomY);
            krit.addPoint(rightX, bottomY);
            krit.addPoint(rightX, topY);

            context.setFill(convertColor(k.getTribe().getColor()));
            context.fillPolygon(
                    Arrays.stream(tribe.xpoints).asDoubleStream().toArray(),
                    Arrays.stream(tribe.ypoints).asDoubleStream().toArray(),
                    tribe.npoints
            );
            context.setFill(convertColor(k.getColor()));
            context.fillPolygon(
                    Arrays.stream(krit.xpoints).asDoubleStream().toArray(),
                    Arrays.stream(krit.ypoints).asDoubleStream().toArray(),
                    krit.npoints
            );
        });

        context.setFill(new Color(0, 0, 0, 0.5));
        context.fillRect(0, canvas.getHeight() - 32d, canvas.getWidth(), 40);
        context.setFill(Color.WHITE);
        context.setStroke(Color.WHITE);
        context.setTextAlign(TextAlignment.LEFT);
        context.setTextBaseline(VPos.CENTER);
        context.strokeText(getStatusText(), 10, canvas.getHeight() - 17);
        KritStorage.prune();
    }

    public String formatInt(int value) {
        return String.format("%05d", value);
    }

    public String getStatusText(int count, int aggressive, double tribeAverage, int tribeCount, double decayRateAverage, double decayFrequencyAverage) {
        return "Count: " + formatInt(count) + " | " +
                "Aggression Count: " + formatInt(aggressive) + " | " +
                "Average Members per Tribe: " + decimalFormat.format(tribeAverage) + " | " +
                "Tribe Count: " + formatInt(tribeCount) + " | " +
                "Average Decay Rate: " + decimalFormat.format(decayRateAverage) + " | " +
                "Average Decay Frequency: " + decimalFormat.format(decayFrequencyAverage);
    }

    public String getStatusText() {
        int count = KritStorage.getCreatures().size();
        int aggressive = (int) KritStorage.getCreatures().stream().filter(Krit::isAggressive).count();
        int tribeCount = KritStorage.getTribes().size();
        double tribeAverage = count / tribeCount;

        double totalDecay = 0;
        double totalFrequency = 0;
        for (Krit k : KritStorage.getCreatures()) {
            totalDecay += k.getDecayRate();
            totalFrequency += k.getDecayFrequency();
        }
        double decayRateAverage = totalDecay / count;
        double decayFrequencyAverage = totalFrequency / count;

        manager.update(aggressive, count, decayRateAverage, decayFrequencyAverage);

        return getStatusText(count, aggressive, tribeAverage, tribeCount, decayRateAverage, decayFrequencyAverage);
    }

    private Color convertColor(java.awt.Color color) {
        return new Color(color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d, color.getAlpha() / 255d);
    }
}
