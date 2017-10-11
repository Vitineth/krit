package com.github.vitineth.game.krit.view;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.storage.KritStorage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 07/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 07/10/2017
 */
public class MapView extends Canvas implements Runnable, MouseListener {

    BufferedImage grid;
    private JFrame frame;
    private boolean running;
    private InspectView view;

    public MapView(InspectView view) {
        this.view = view;

        setPreferredSize(new Dimension(KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10));

        running = true;
        frame = new JFrame("Krit Map");
        frame.setSize(KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10);
//        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setLocation(view.getX() + view.getWidth(), view.getY());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addMouseListener(this);
        addMouseListener(this);

        grid = new BufferedImage(KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = grid.createGraphics();
        g.setColor(new Color(60, 60, 60));
        g.fillRect(0, 0, KritStorage.getSize().getWidth() * 10, KritStorage.getSize().getHeight() * 10);
        g.setColor(new Color(76, 76, 76));
        for (int x = 0; x < grid.getWidth(); x += 10) {
            g.drawLine(x, 0, x, KritStorage.getSize().getHeight() * 10);
        }
        for (int y = 0; y < grid.getHeight(); y += 10) {
            g.drawLine(0, y, KritStorage.getSize().getWidth() * 10, y);
        }

        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis();
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        while (running) {
            if (!view.isPaused())
                if (System.currentTimeMillis() - last >= 500) {
                    update();
                    view.update();
                }
        }
    }

    public void update() {
        if (getBufferStrategy() == null) {
            createBufferStrategy(2);
            return;
        }

        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(49, 51, 54));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(grid, 0, 0, null);


        CopyOnWriteArrayList<Krit> copy = new CopyOnWriteArrayList<>(KritStorage.getCreatures());
        copy.stream().forEach(Krit::update);
        copy.stream().forEach(k -> {
//            ##################

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

            g.setColor(k.getTribe().getColor());
            g.fillPolygon(tribe);

            g.setColor(k.getColor());
            g.fillPolygon(krit);

//            ##################

//            g.setColor(k.getTribe().getColor());
//            g.drawRect(k.getLocation().getX() * 10, k.getLocation().getY() * 10, 10, 10);
//            g.setColor(k.getColor());
//            g.fillRect((k.getLocation().getX() * 10) + 1, (k.getLocation().getY() * 10) + 1, 8, 8);
        });

        g.setColor(new Color(0, 0, 0, 126));
        g.fillRect(0, getHeight() - 20, getWidth(), 20);
        g.setColor(Color.WHITE);
        g.drawString(copy.size() + "", 0, getHeight());


        KritStorage.prune();

        g.dispose();
        bs.show();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int) ((double) e.getX() / 10d);
        int y = (int) ((double) e.getY() / 10d);

        if (!KritStorage.isFree(x, y)) {
            view.update(KritStorage.get(x, y));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
