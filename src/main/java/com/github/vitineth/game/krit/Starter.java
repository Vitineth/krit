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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * This launches the main bulk of the program. It will configure the Krit Storage with the sizes and names. It will
 * also create the tribes and krits before dispatching it out to the map renderer
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 07/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 07/10/2017
 */
public class Starter {

    public static void initialize(){
        // These are the initial sizes of the grid that the Krits live on. These are submitted to the storage in
        // order to configure it to the right size but are defined here as we use them in a moment to generate the
        // krits and tribes
        int width = 100;
        int height = 100;

        // Initialise the storage which will set up the storage mediums that we use to maintain the krits.
        KritStorage.initialise(new Size(width, height));

        // Here we create the tribes. Tribes have the following details:
        //   - ID
        //   - Members
        //   - Color
        // The ID is created automatically on initialisation, as is the color. The members can be blank for now so
        // there are no parameters required.
        // We want the tribes to be clustered together when they form so we need to pick a random location on the
        // map which will act as the 'base' of the tribe around which its members will be placed so we create those
        // and store them.
        List<Tribe> tribes = new ArrayList<>();
        List<Location> tribeLocations = new ArrayList<>();
        for (int i = 0; i < GlobalStorage.RANDOM.nextInt(5) + 3; i++) {
            Tribe tribe = new Tribe();
            KritStorage.addTribe(tribe);
            tribes.add(tribe);
            tribeLocations.add(new Location(GlobalStorage.RANDOM.nextInt(width), GlobalStorage.RANDOM.nextInt(height)));
        }

        // We start out generating between 25 and 49 krits which are then assigned to the random tribes. Here we
        // pick a random tribe and get its instance and location. We generate a new location by getting the tribes
        // loation and offsetting it between -20 and 20 against x and y. If the space is not free, we get 20
        // attempts at generating a free location, if we don't then we just cancel. Otherwise we generate the krit
        // and place it at that location.
        for (int i = 0; i < GlobalStorage.RANDOM.nextInt(25) + 25; i++) {
            int tribeIndex = GlobalStorage.RANDOM.nextInt(tribes.size());
            Tribe tribe = tribes.get(tribeIndex);
            Location center = tribeLocations.get(tribeIndex);

            Location newLocation = new Location(center, (int) RandomUtils.random(-20, 20), (int) RandomUtils.random(-20, 20));
            int count = 0;
            while (!KritStorage.isFree(newLocation) && count++ <= 20)
                newLocation = new Location(center, (int) RandomUtils.random(-20, 20), (int) RandomUtils.random(-20, 20));

            if (KritStorage.isFree(newLocation)) {
                // Krits have a large number of properties that we need to define so these are described below:
                // Location - The generated location descrbed previously. This is where the krit will start out
                // Health - The initial health of the creature. This is set to 20 for the entire set of initial
                //   krits
                // Decay Rate - The amount of health that is lost per frequency updates. This defaults to 1.5 for
                //   the entire set of initial krits
                // Decay Frequency - The amount of updates required to lose a rate amount of health. This defaults
                //   to 2 for the entire set of initial krits
                // Sex - Male or female (oh no, the binary) which is randomly selected.
                // Color - The color to draw the krit with on the map, randomly generated
                // Tribe - The tribe that owns the creature, this was randomly selected previously
                // Mother - The mother of the parent (used to define Krit#parents), set to null as this is one of
                //   the adams or eves
                // Father - The father of the parent (used to define Krit#parents), set to null as this is one of
                //   the adams or eves
                // Genetics - The set of genetic traits of this krit. Set to an empty list to begin as we do not
                //   have them implemented yet
                Krit child = new Krit(newLocation, 20d, 1.5d, 2, Krit.Sex.select(), ColorUtils.newRandomColor(), tribe, null, null, new ArrayList<>());
                // Finally append the krit to the tribe and we're done on this generation.
                tribe.add(child);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Set the theme. This allows the user to pick from the installed looks and feels as well as the installed
            // darcula version. This is a completely needless addition but I thought it was quite cool.
            try {
                String[] s = new String[UIManager.getInstalledLookAndFeels().length + 1];
                for (int i = 0; i < s.length - 1; i++) {
                    s[i + 1] = UIManager.getInstalledLookAndFeels()[i].getName();
                }
                s[0] = "Darcula";

                System.out.println(new UIManager.LookAndFeelInfo("Darcula", "com.bulenkov.darcula.DarculaLaf"));
                int laf = JOptionPane.showOptionDialog(null, "Select LAF", "Select LAF", JOptionPane.OK_CANCEL_OPTION, 0, null, s, s[0]);
                if (laf == 0) {
                    UIManager.setLookAndFeel(new DarculaLaf());
                } else {
                    UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[laf - 1].getClassName());
                }
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            // Set up the name storage to load the entire names file and configure the storage values that we need to
            // make sure that the names are good
            NameStorage.initialise();

            initialize();

            // Finally, we construct the inspect view with which the user can browse the creatues
            InspectView view = new InspectView();
            // Initially select the first ever krit
            view.update(KritStorage.getCreatures().get(0));
            // And launch the map window which will start the rendering process and set the krits off living.
            new Thread(new MapView(view), "Map Viewer").start();
        } catch (Exception e) {
            if (System.getProperty("idea.launcher.port") != null) {
                e.printStackTrace();
            } else {
                // This is our quick and dirty Error Catcher. It just takes any exception, prints the exception to a string
                // builder and displays it in a message dialog. Nothing important.
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

}
