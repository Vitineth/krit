package com.github.vitineth.game.krit.view;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.Location;
import com.github.vitineth.game.krit.Starter;
import com.github.vitineth.game.krit.storage.KritStorage;
import com.github.vitineth.game.krit.storage.NameStorage;
import com.github.vitineth.game.krit.view.util.GBCBuilder;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
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
    private boolean resetCalled;

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
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inspect", setupInspect());
        tabbedPane.addTab("Manage", setupConfig());

        add(tabbedPane, new GBCBuilder().x(0).y(0).wx(1d).wy(1d).f(GridBagConstraints.BOTH).build());
    }

    public JPanel setupConfig(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        GBCBuilder b = new GBCBuilder().x(0).y(0).wx(1).wy(1).f(GridBagConstraints.HORIZONTAL).a(GridBagConstraints.NORTH);

        JButton reset = new JButton("Restart");
        reset.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCalled = true;
            }
        });
        p.add(reset, b.build());
        reset.setText("Restart");

        return p;
    }

    public JPanel setupInspect() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        JButton freeze = new JButton("Freeze");
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

        p.add(freeze, freezeGBC);
        freeze.setText("Unfreeze");
        p.add(basicDetails, base.copy().y(1).build());
        p.add(idLabel, base.copy().y(2).build());
        p.add(locationLabel, base.copy().y(3).build());
        p.add(originLabel, base.copy().y(4).build());

        p.add(healthDetails, base.copy().y(5).build());
        p.add(healthLabel, base.copy().y(6).build());
        p.add(intialHealthLabel, base.copy().y(7).build());
        p.add(decayLabel, base.copy().y(8).build());

        p.add(miscDetails, base.copy().y(9).build());
        p.add(sexLabel, base.copy().y(10).build());
        p.add(colorLabel, base.copy().y(11).build());
        p.add(aggressiveLabel, base.copy().y(12).build());
        p.add(tribeLabel, base.copy().y(13).build());

        p.add(familialDetails, base.copy().y(14).build());
        p.add(matedLabel, base.copy().y(15).build());
        p.add(mateLabel, base.copy().y(16).build());
        p.add(pregnantLabel, base.copy().y(17).build());
        p.add(termLabel, base.copy().y(18).build());
        p.add(childrenLabel, base.copy().y(19).build());
        p.add(parentsLabel, base.copy().y(20).build());

        p.add(childrenDetails, base.copy().y(21).build());
        p.add(childrenCountLabel, base.copy().y(22).build());
        p.add(childrenIdsLabel, base.copy().y(23).build());

        p.add(geneticDetails, base.copy().y(24).build());
        p.add(geneticCountLabel, base.copy().y(25).build());
        p.add(geneticIdsLabel, base.copy().y(26).build());

        p.add(otherDetails, base.copy().y(27).build());
        p.add(separationLabel, base.copy().y(28).build());
        p.add(proximityLabel, base.copy().y(29).build());
        p.add(updatesLabel, base.copy().y(30).build());

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
        family.addActionListener((event) -> createGraph());

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
        p.add(id, base.copy().y(2).build());

        p.add(location, base.copy().y(3).build());
        p.add(origin, base.copy().y(4).build());

        p.add(health, base.copy().y(6).build());
        p.add(initialHealth, base.copy().y(7).build());
        p.add(decay, base.copy().y(8).build());

        p.add(sex, base.copy().y(10).build());
        p.add(color, base.copy().y(11).build());
        p.add(aggressive, base.copy().y(12).build());
        p.add(tribe, base.copy().y(13).build());

        p.add(mated, base.copy().y(15).build());
        p.add(mate, base.copy().y(16).build());
        p.add(pregnant, base.copy().y(17).build());
        p.add(term, base.copy().y(18).build());
        p.add(children, base.copy().y(19).build());
        p.add(parents, base.copy().y(20).build());

        p.add(childrenCount, base.copy().y(22).build());
        p.add(new JScrollPane(childrenIds), base.copy().y(23).f(GridBagConstraints.BOTH).wy(1d).build());

        p.add(geneticCount, base.copy().y(25).build());
        p.add(new JScrollPane(geneticIds), base.copy().y(26).f(GridBagConstraints.BOTH).wy(1d).build());

        p.add(separation, base.copy().y(28).build());
        p.add(proximity, base.copy().y(29).build());
        p.add(updates, base.copy().y(30).build());
        p.add(family, base.copy().y(31).i(new Insets(0, 10, 10, 10)).build());

        return p;
    }

    public Krit getHighestParent(Krit krit) {
        Krit k = krit;
        while (k.getParents() != null && k.getParents()[0] != null) {
            k = k.getParents()[0];
        }

        return k;
    }

    public void createGraph() {
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

    public boolean isResetCalled() {
        return resetCalled;
    }

    public void resetClear(){
        Starter.initialize();
        resetCalled = false;
    }
}

