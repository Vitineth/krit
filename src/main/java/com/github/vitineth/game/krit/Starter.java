package com.github.vitineth.game.krit;

import com.bulenkov.darcula.DarculaLaf;
import com.github.vitineth.game.krit.storage.GlobalStorage;
import com.github.vitineth.game.krit.storage.KritStorage;
import com.github.vitineth.game.krit.storage.NameStorage;
import com.github.vitineth.game.krit.utils.ColorUtils;
import com.github.vitineth.game.krit.utils.RandomUtils;
import com.github.vitineth.game.krit.utils.Size;
import com.github.vitineth.game.krit.view.InspectView;
import com.github.vitineth.game.krit.view.MapView;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 07/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 07/10/2017
 */
public class Starter {

    public static void main(String[] args) {
        try {
            try {
                UIManager.setLookAndFeel(new DarculaLaf());
//            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            NameStorage.initialise();

            int width = 100;
            int height = 100;

            KritStorage.initialise(new Size(width, height));

            List<Tribe> tribes = new ArrayList<>();
            List<Location> tribeLocations = new ArrayList<>();
            for (int i = 0; i < GlobalStorage.RANDOM.nextInt(5) + 3; i++) {
                tribes.add(new Tribe());
                tribeLocations.add(new Location(GlobalStorage.RANDOM.nextInt(width), GlobalStorage.RANDOM.nextInt(height)));
            }

            for (int i = 0; i < GlobalStorage.RANDOM.nextInt(25) + 25; i++) {
                int tribeIndex = GlobalStorage.RANDOM.nextInt(tribes.size());
                Tribe tribe = tribes.get(tribeIndex);
                Location center = tribeLocations.get(tribeIndex);

                Location newLocation = new Location(center, (int) RandomUtils.random(-20, 20), (int) RandomUtils.random(-20, 20));
                int count = 0;
                while (!KritStorage.isFree(newLocation) && count++ <= 20)
                    newLocation = new Location(center, (int) RandomUtils.random(-20, 20), (int) RandomUtils.random(-20, 20));

                if (KritStorage.isFree(newLocation)) {
                    Krit child = new Krit(newLocation, 20d, 1.5d, 2, Krit.Sex.select(), ColorUtils.newRandomColor(), tribe, null, null, new ArrayList<>());
                    tribe.add(child);

                }
            }

            InspectView view = new InspectView();
            view.update(KritStorage.getCreatures().get(0));
            new Thread(new MapView(view), "Map Viewer").start();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            e.printStackTrace(new PrintWriter(new Writer() {
                @Override
                public void write(char[] cbuf, int off, int len) throws IOException {
                    sb.append(cbuf, off, len);
                }

                @Override
                public void flush() throws IOException {
                }

                @Override
                public void close() throws IOException {
                }
            }));
            JOptionPane.showMessageDialog(null, sb.toString(), "Exception: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

}
