package com.github.vitineth.game.krit.view;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.Location;
import com.github.vitineth.game.krit.storage.NameStorage;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 07/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 07/10/2017
 */
public class InspectView extends JFrame {

    private boolean paused = false;
    private JTextField id;
    private JTextField location;
    private JTextField origin;
    private JTextField health;
    private JTextField initialHealth;
    private JTextField decay;
    private JTextField sex;
    private JTextField color;
    private JTextField aggressive;
    private JTextField tribe;
    private JTextField mated;
    private JTextField mate;
    private JTextField pregnant;
    private JTextField term;
    private JTextField children;
    private JTextField parents;
    private JTextField childrenCount;
    private JList<String> childrenIds;
    private JTextField geneticCount;
    private JList<String> geneticIds;
    private JTextField separation;
    private JTextField proximity;
    private JTextField updates;
    private Krit krit;

    public InspectView() throws HeadlessException {
        setTitle("Inspection");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setup();
        pack();
        setSize(new Dimension(getWidth() + 400, getHeight()));

        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InspectView().setVisible(true));
    }

    public void setup() {


        JButton freeze = new JButton("Freeze");
        freeze.setText("Unfreeze");
        freeze.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused) {
                    paused = false;
                    freeze.setText("Freeze");
                } else {
                    paused = true;
                    freeze.setText("Unfreeze");
                }
            }
        });
        GridBagConstraints freezeGBC = new GBCBuilder().x(0).y(0).w(2).h(1).f(GridBagConstraints.HORIZONTAL).build();

        GBCBuilder base = new GBCBuilder().x(0).y(1).a(GridBagConstraints.WEST).i(new Insets(0, 10, 0, 10));

        JLabel basicDetails = new JLabel("Basic Details");
        basicDetails.setFont(basicDetails.getFont().deriveFont(Font.BOLD));

        JLabel idLabel = new JLabel("ID: ");
        JLabel locationLabel = new JLabel("Location: ");
        JLabel originLabel = new JLabel("Origin: ");

        JLabel healthDetails = new JLabel("Health Details");
        healthDetails.setFont(healthDetails.getFont().deriveFont(Font.BOLD));

        JLabel healthLabel = new JLabel("Health: ");
        JLabel intialHealthLabel = new JLabel("Initial Health: ");
        JLabel decayLabel = new JLabel("Decay Rate: ");

        JLabel miscDetails = new JLabel("Misc Details");
        miscDetails.setFont(miscDetails.getFont().deriveFont(Font.BOLD));

        JLabel sexLabel = new JLabel("Sex: ");
        JLabel colorLabel = new JLabel("Color: ");
        JLabel aggressiveLabel = new JLabel("Is Aggressive: ");
        JLabel tribeLabel = new JLabel("Tribe: ");

        JLabel familialDetails = new JLabel("Familial Details");
        familialDetails.setFont(familialDetails.getFont().deriveFont(Font.BOLD));

        JLabel matedLabel = new JLabel("Has Mated: ");
        JLabel mateLabel = new JLabel("Mate: ");
        JLabel pregnantLabel = new JLabel("Is Pregnant: ");
        JLabel termLabel = new JLabel("Term: ");
        JLabel childrenLabel = new JLabel("Children: ");
        JLabel parentsLabel = new JLabel("Parents: ");

        JLabel childrenDetails = new JLabel("Children Details");
        childrenDetails.setFont(childrenDetails.getFont().deriveFont(Font.BOLD));

        JLabel childrenCountLabel = new JLabel("Count: ");
        JLabel childrenIdsLabel = new JLabel("Children: ");

        JLabel geneticDetails = new JLabel("Genetic Details");
        geneticDetails.setFont(geneticDetails.getFont().deriveFont(Font.BOLD));

        JLabel geneticCountLabel = new JLabel("Count: ");
        JLabel geneticIdsLabel = new JLabel("Genetics: ");

        JLabel otherDetails = new JLabel("Other Details");
        otherDetails.setFont(otherDetails.getFont().deriveFont(Font.BOLD));

        JLabel separationLabel = new JLabel("Separation: ");
        JLabel proximityLabel = new JLabel("Proximity: ");
        JLabel updatesLabel = new JLabel("Updates: ");

        add(freeze, freezeGBC);
        add(basicDetails, base.copy().y(1).build());
        add(idLabel, base.copy().y(2).build());
        add(locationLabel, base.copy().y(3).build());
        add(originLabel, base.copy().y(4).build());

        add(healthDetails, base.copy().y(5).build());
        add(healthLabel, base.copy().y(6).build());
        add(intialHealthLabel, base.copy().y(7).build());
        add(decayLabel, base.copy().y(8).build());

        add(miscDetails, base.copy().y(9).build());
        add(sexLabel, base.copy().y(10).build());
        add(colorLabel, base.copy().y(11).build());
        add(aggressiveLabel, base.copy().y(12).build());
        add(tribeLabel, base.copy().y(13).build());

        add(familialDetails, base.copy().y(14).build());
        add(matedLabel, base.copy().y(15).build());
        add(mateLabel, base.copy().y(16).build());
        add(pregnantLabel, base.copy().y(17).build());
        add(termLabel, base.copy().y(18).build());
        add(childrenLabel, base.copy().y(19).build());
        add(parentsLabel, base.copy().y(20).build());

        add(childrenDetails, base.copy().y(21).build());
        add(childrenCountLabel, base.copy().y(22).build());
        add(childrenIdsLabel, base.copy().y(23).build());

        add(geneticDetails, base.copy().y(24).build());
        add(geneticCountLabel, base.copy().y(25).build());
        add(geneticIdsLabel, base.copy().y(26).build());

        add(otherDetails, base.copy().y(27).build());
        add(separationLabel, base.copy().y(28).build());
        add(proximityLabel, base.copy().y(29).build());
        add(updatesLabel, base.copy().y(30).build());

        id = new JTextField();
        location = new JTextField();
        origin = new JTextField();

        health = new JTextField();
        initialHealth = new JTextField();
        decay = new JTextField();

        sex = new JTextField();
        color = new JTextField();
        aggressive = new JTextField();
        tribe = new JTextField();

        mated = new JTextField();
        mate = new JTextField();
        pregnant = new JTextField();
        term = new JTextField();
        children = new JTextField();
        parents = new JTextField();

        childrenCount = new JTextField();
        childrenIds = new JList<>();

        geneticCount = new JTextField();
        geneticIds = new JList<>();

        separation = new JTextField();
        proximity = new JTextField();
        updates = new JTextField();

        JButton family = new JButton("Export Family Tree");
        family.addActionListener((event) -> {
            createGraph();
        });

        id.setEditable(false);
        location.setEditable(false);
        origin.setEditable(false);
        health.setEditable(false);
        initialHealth.setEditable(false);
        decay.setEditable(false);
        sex.setEditable(false);
        color.setEditable(false);
        aggressive.setEditable(false);
        tribe.setEditable(false);
        mated.setEditable(false);
        mate.setEditable(false);
        pregnant.setEditable(false);
        term.setEditable(false);
        children.setEditable(false);
        parents.setEditable(false);
        childrenCount.setEditable(false);
        childrenIds.setEnabled(false);
        geneticCount.setEditable(false);
        geneticIds.setEnabled(false);
        separation.setEditable(false);
        proximity.setEditable(false);
        updates.setEditable(false);

        base = base.copy().x(1).y(1).a(GridBagConstraints.WEST).f(GridBagConstraints.HORIZONTAL).wx(1d);
        add(id, base.copy().y(2).build());

        add(location, base.copy().y(3).build());
        add(origin, base.copy().y(4).build());

        add(health, base.copy().y(6).build());
        add(initialHealth, base.copy().y(7).build());
        add(decay, base.copy().y(8).build());

        add(sex, base.copy().y(10).build());
        add(color, base.copy().y(11).build());
        add(aggressive, base.copy().y(12).build());
        add(tribe, base.copy().y(13).build());

        add(mated, base.copy().y(15).build());
        add(mate, base.copy().y(16).build());
        add(pregnant, base.copy().y(17).build());
        add(term, base.copy().y(18).build());
        add(children, base.copy().y(19).build());
        add(parents, base.copy().y(20).build());

        add(childrenCount, base.copy().y(22).build());
        add(new JScrollPane(childrenIds), base.copy().y(23).f(GridBagConstraints.BOTH).wy(1d).build());

        add(geneticCount, base.copy().y(25).build());
        add(new JScrollPane(geneticIds), base.copy().y(26).f(GridBagConstraints.BOTH).wy(1d).build());

        add(separation, base.copy().y(28).build());
        add(proximity, base.copy().y(29).build());
        add(updates, base.copy().y(30).build());
        add(family, base.copy().y(31).i(new Insets(0, 10, 10, 10)).build());
    }

    public Krit getHighestParent(Krit krit){
        Krit k = krit;
        while(k.getParents() != null && k.getParents()[0] != null){
            k = k.getParents()[0];
        }

        return k;
    }

    public void createGraph(){
        Krit highestMother = getHighestParent(krit);

        // add mother and father with link
    }

    public boolean isPaused() {
        return paused;
    }

    public void update() {
        if (krit != null) update(krit);
    }

    public void clear() {
        Color c = new Color(255, 91, 101);
        id.setForeground(c);
        location.setForeground(c);
        origin.setForeground(c);
        health.setForeground(c);
        initialHealth.setForeground(c);
        decay.setForeground(c);
        sex.setForeground(c);
        color.setForeground(c);
        aggressive.setForeground(c);
        tribe.setForeground(c);
        mated.setForeground(c);
        mate.setForeground(c);
        pregnant.setForeground(c);
        term.setForeground(c);
        children.setForeground(c);
        parents.setForeground(c);
        childrenCount.setForeground(c);
        childrenIds.setForeground(c);
        geneticCount.setForeground(c);
        geneticIds.setForeground(c);
        separation.setForeground(c);
        proximity.setForeground(c);
        updates.setForeground(c);
//        id.setText("");
//        location.setText("");
//        origin.setText("");
//        health.setText("");
//        initialHealth.setText("");
//        decay.setText("");
//        sex.setText("");
//        color.setText("");
//        aggressive.setText("");
//        tribe.setText("");
//        mated.setText("");
//        mate.setText("");
//        pregnant.setText("");
//        term.setText("");
//        children.setText("");
//        parents.setText("");
//        childrenCount.setText("");
//        childrenIds.setListData(new String[0]);
//        geneticCount.setText("");
//        geneticIds.setListData(new String[0]);
//        separation.setText("");
//        proximity.setText("");
    }

    public void update(Krit krit) {
        if (!krit.isAlive() || krit.getHealth() <= 0) {
            clear();
            return;
        }
        this.krit = krit;

        id.setForeground(Color.WHITE);
        location.setForeground(Color.WHITE);
        origin.setForeground(Color.WHITE);
        health.setForeground(Color.WHITE);
        initialHealth.setForeground(Color.WHITE);
        decay.setForeground(Color.WHITE);
        sex.setForeground(Color.WHITE);
        color.setForeground(Color.WHITE);
        aggressive.setForeground(Color.WHITE);
        tribe.setForeground(Color.WHITE);
        mated.setForeground(Color.WHITE);
        mate.setForeground(Color.WHITE);
        pregnant.setForeground(Color.WHITE);
        term.setForeground(Color.WHITE);
        children.setForeground(Color.WHITE);
        parents.setForeground(Color.WHITE);
        childrenCount.setForeground(Color.WHITE);
        childrenIds.setForeground(Color.WHITE);
        geneticCount.setForeground(Color.WHITE);
        geneticIds.setForeground(Color.WHITE);
        separation.setForeground(Color.WHITE);
        proximity.setForeground(Color.WHITE);
        updates.setForeground(Color.WHITE);

        String name = NameStorage.get(krit);
        id.setText((name == null ? "No Face" : name) + "     (" + krit.getId().toString() + ")");
        location.setText("(" + krit.getLocation().getX() + ", " + krit.getLocation().getY() + ")");
        origin.setText("(" + krit.getOrigin().getX() + ", " + krit.getOrigin().getY() + ")");
        health.setText(krit.getHealth() + "");
        initialHealth.setText(krit.getMaxHealth() + "");
        decay.setText(krit.getDecayRate() + " per " + krit.getDecayFrequency() + " updates");
        sex.setText(krit.getSex().toString());
        color.setText("(" + krit.getColor().getRed() + ", " + krit.getColor().getBlue() + ", " + krit.getColor().getGreen() + ")");
        aggressive.setText(krit.isAggressive() + "");
        Location tl = krit.getTribe().getAverageLocation();
        tribe.setText(krit.getTribe().getId().toString() + "(" + tl.getX() + ", " + tl.getY() + ")");
        mated.setText((krit.getMate() != null) + "");
        mate.setText((krit.getMate() == null) ? "" : krit.getMate().getId().toString());
        pregnant.setText(krit.isPregnant() + "");
        term.setText(krit.isPregnant() ? krit.getPregnancyTerm() + "" : "");
        children.setText(krit.isPregnant() ? krit.getChildrenCount() + "" : "");
        parents.setText((krit.getParents()[0] == null ? "" : krit.getParents()[0].getId().toString()) + " / " + (krit.getParents()[1] == null ? "" : krit.getParents()[1].getId().toString()));

        childrenCount.setText(krit.getChildren().size() + "");
        String[] childrenIdStrings = new String[krit.getChildren().size()];
        for (int i = 0; i < childrenIdStrings.length; i++) childrenIdStrings[i] = krit.getChildren().get(i).toString();
        childrenIds.setListData(childrenIdStrings);

        geneticCount.setText(krit.getGenetics().size() + "");
        String[] geneticIdStrings = new String[krit.getGenetics().size()];
        for (int i = 0; i < geneticIdStrings.length; i++) geneticIdStrings[i] = krit.getGenetics().get(i).toString();
        geneticIds.setListData(geneticIdStrings);

        separation.setText(krit.getSeparationTurns() + "");
        proximity.setText(krit.getProximityTurns() + "");
        updates.setText(krit.getUpdateCount() + "");
    }
}

class GBCBuilder {

    private GridBagConstraints gbc;

    public GBCBuilder() {
        gbc = new GridBagConstraints();
    }

    public GBCBuilder copy() {
        GBCBuilder builder = new GBCBuilder();
        builder.gbc = (GridBagConstraints) gbc.clone();
        return builder;
    }

    public GBCBuilder x(int x) {
        gbc.gridx = x;
        return this;
    }

    public GBCBuilder y(int y) {
        gbc.gridy = y;
        return this;
    }

    public GBCBuilder a(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    public GBCBuilder w(int width) {
        gbc.gridwidth = width;
        return this;
    }

    public GBCBuilder h(int height) {
        gbc.gridheight = height;
        return this;
    }

    public GBCBuilder f(int fill) {
        gbc.fill = fill;
        return this;
    }

    public GBCBuilder i(Insets insets) {
        gbc.insets = insets;
        return this;
    }

    public GBCBuilder ipx(int ipadx) {
        gbc.ipadx = ipadx;
        return this;
    }

    public GBCBuilder ipy(int ipady) {
        gbc.ipady = ipady;
        return this;
    }

    public GBCBuilder wx(double weightx) {
        gbc.weightx = weightx;
        return this;
    }

    public GBCBuilder wy(double weighty) {
        gbc.weighty = weighty;
        return this;
    }

    public GridBagConstraints build() {
        return gbc;
    }


}
