package com.github.vitineth.game.krit.view.javafx;

import com.github.vitineth.game.krit.Krit;
import com.github.vitineth.game.krit.Starter;
import com.github.vitineth.game.krit.storage.NameStorage;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.StableTicksAxis;

import javax.swing.JFrame;
import java.util.Arrays;

/**
 * Class Description
 * <p/>
 * File created by Ryan (vitineth).<br>
 * Created on 12/10/2017.
 *
 * @author Ryan (vitineth)
 * @since 12/10/2017
 */
public class KritManager extends Application {

    private Krit krit;
    private boolean frozen;
    private boolean resetTriggered;
    private long start = System.currentTimeMillis();
    private XYChart.Series<Number, Number> aggressionSeries;
    private XYChart.Series<Number, Number> countSeries;
    private XYChart.Series<Number, Number> averageDecayFrequencySeries;
    private XYChart.Series<Number, Number> averageDecayRateSeries;
    private TextField id;
    private TextField location;
    private TextField origin;
    private TextField health;
    private TextField initialHealth;
    private TextField decay;
    private TextField sex;
    private TextField color;
    private TextField aggressive;
    private TextField tribe;
    private TextField mated;
    private TextField mate;
    private TextField pregnant;
    private TextField term;
    private TextField children;
    private TextField parents;
    private TextField childrenCount;
    private ListView<String> childrenIds;
    private TextField geneticCount;
    private ListView<String> geneticIds;
    private TextField separation;
    private TextField proximity;
    private TextField updates;
    private LineChart<Number, Number> countChart;

    public KritManager() {
//        JFrame frame = new JFrame();
//        JFXPanel panel = new JFXPanel();
//        frame.add(panel);
//
//        Platform.runLater(() -> panel.setScene(alternateStart()));
//        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private Node setupInspect() {
        GridPane inspect = new GridPane();
        inspectConfigureLabels(inspect);
        inspectConfigureInputs(inspect);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(20);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(80);

        inspect.getColumnConstraints().add(cc1);
        inspect.getColumnConstraints().add(cc2);

        ScrollPane sp = new ScrollPane(inspect);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return sp;
    }

    private void inspectConfigureLabels(GridPane p) {
        int y = 0;
        Label basicDetails = new Label("Basic Details");
        Label idLabel = new Label("ID: ");
        Label locationLabel = new Label("Location: ");
        Label originLabel = new Label("Origin: ");
        p.add(basicDetails, 0, y++, 2, 1);
        p.add(idLabel, 0, y++);
        p.add(locationLabel, 0, y++);
        p.add(originLabel, 0, y++);

        Label healthDetails = new Label("Health Details");
        Label healthLabel = new Label("Health: ");
        Label initialHealthLabel = new Label("Initial Health: ");
        Label decayLabel = new Label("Decay Rate: ");
        p.add(healthDetails, 0, y++, 2, 1);
        p.add(healthLabel, 0, y++);
        p.add(initialHealthLabel, 0, y++);
        p.add(decayLabel, 0, y++);

        Label miscDetails = new Label("Misc Details");
        Label sexLabel = new Label("Sex: ");
        Label colorLabel = new Label("Color: ");
        Label aggressiveLabel = new Label("Is Aggressive: ");
        Label tribeLabel = new Label("Tribe: ");
        p.add(miscDetails, 0, y++, 2, 1);
        p.add(sexLabel, 0, y++);
        p.add(colorLabel, 0, y++);
        p.add(aggressiveLabel, 0, y++);
        p.add(tribeLabel, 0, y++);

        Label familialDetails = new Label("Familial Details");
        Label matedLabel = new Label("Has Mated: ");
        Label mateLabel = new Label("Mate: ");
        Label pregnantLabel = new Label("Is Pregnant: ");
        Label termLabel = new Label("Term: ");
        Label childrenLabel = new Label("Children: ");
        Label parentsLabel = new Label("Parents: ");
        p.add(familialDetails, 0, y++, 2, 1);
        p.add(matedLabel, 0, y++);
        p.add(mateLabel, 0, y++);
        p.add(pregnantLabel, 0, y++);
        p.add(termLabel, 0, y++);
        p.add(childrenLabel, 0, y++);
        p.add(parentsLabel, 0, y++);

        Label childrenDetails = new Label("Children Details");
        Label childrenCountLabel = new Label("Count: ");
        Label childrenIdsLabel = new Label("Children: ");
        p.add(childrenDetails, 0, y++, 2, 1);
        p.add(childrenCountLabel, 0, y++);
        p.add(childrenIdsLabel, 0, y++);

        Label geneticDetails = new Label("Genetic Details");
        Label geneticCountLabel = new Label("Count: ");
        Label geneticIdsLabel = new Label("Genetics: ");
        p.add(geneticDetails, 0, y++, 2, 1);
        p.add(geneticCountLabel, 0, y++);
        p.add(geneticIdsLabel, 0, y++);

        Label otherDetails = new Label("Other Details");
        Label separationLabel = new Label("Separation: ");
        Label proximityLabel = new Label("Proximity: ");
        Label updatesLabel = new Label("Updates: ");
        p.add(otherDetails, 0, y++, 2, 1);
        p.add(separationLabel, 0, y++);
        p.add(proximityLabel, 0, y++);
        p.add(updatesLabel, 0, y);
    }

    private void inspectConfigureInputs(GridPane p) {
        int y = 1;

        id = new TextField();
        location = new TextField();
        origin = new TextField();
        id.setEditable(false);
        location.setEditable(false);
        origin.setEditable(false);
        p.add(id, 1, y++);
        p.add(location, 1, y++);
        p.add(origin, 1, y++);
        y++;

        health = new TextField();
        initialHealth = new TextField();
        decay = new TextField();
        health.setEditable(false);
        initialHealth.setEditable(false);
        decay.setEditable(false);
        p.add(health, 1, y++);
        p.add(initialHealth, 1, y++);
        p.add(decay, 1, y++);
        y++;

        sex = new TextField();
        color = new TextField();
        aggressive = new TextField();
        tribe = new TextField();
        sex.setEditable(false);
        color.setEditable(false);
        aggressive.setEditable(false);
        tribe.setEditable(false);
        p.add(sex, 1, y++);
        p.add(color, 1, y++);
        p.add(aggressive, 1, y++);
        p.add(tribe, 1, y++);
        y++;

        mated = new TextField();
        mate = new TextField();
        pregnant = new TextField();
        term = new TextField();
        children = new TextField();
        parents = new TextField();
        mated.setEditable(false);
        mate.setEditable(false);
        pregnant.setEditable(false);
        term.setEditable(false);
        children.setEditable(false);
        parents.setEditable(false);
        p.add(mated, 1, y++);
        p.add(mate, 1, y++);
        p.add(pregnant, 1, y++);
        p.add(term, 1, y++);
        p.add(children, 1, y++);
        p.add(parents, 1, y++);
        y++;

        childrenCount = new TextField();
        childrenIds = new ListView<>();
        childrenCount.setEditable(false);
        childrenIds.setEditable(false);
        p.add(childrenCount, 1, y++);
        p.add(childrenIds, 1, y++);
        y++;

        geneticCount = new TextField();
        geneticIds = new ListView<>();
        geneticCount.setEditable(false);
        geneticIds.setEditable(false);
        p.add(geneticCount, 1, y++);
        p.add(geneticIds, 1, y++);
        y++;

        separation = new TextField();
        proximity = new TextField();
        updates = new TextField();
        separation.setEditable(false);
        proximity.setEditable(false);
        updates.setEditable(false);
        p.add(separation, 1, y++);
        p.add(proximity, 1, y++);
        p.add(updates, 1, y);
    }

    private Node setupCharts() {
        VBox hBox = new VBox();

        hBox.getChildren().add(new Label("Count"));
        countChart = new LineChart<>(
                new NumberAxis(),
                new NumberAxis()
        );
        countSeries = new XYChart.Series<>();
        countChart.getData().add(countSeries);
        hBox.getChildren().add(countChart);

        hBox.getChildren().add(new Label("Aggression"));
        LineChart<Number, Number> aggressionChart = new LineChart<>(
                new NumberAxis(),
                new NumberAxis()
        );
        aggressionSeries = new XYChart.Series<>();
        aggressionChart.getData().add(aggressionSeries);
        hBox.getChildren().add(aggressionChart);

        hBox.getChildren().add(new Label("Average Decay Rate"));
        LineChart<Number, Number> averageDecayRateChart = new LineChart<>(
                new NumberAxis(),
                new NumberAxis()
        );
        averageDecayRateSeries = new XYChart.Series<>();
        averageDecayRateChart.getData().add(averageDecayRateSeries);
        hBox.getChildren().add(averageDecayRateChart);

        hBox.getChildren().add(new Label("Average Decay Frequency"));
        LineChart<Number, Number> averageDecayFrequencyChart = new LineChart<>(
                new NumberAxis(),
                new NumberAxis()
        );
        averageDecayFrequencySeries = new XYChart.Series<>();
        averageDecayFrequencyChart.getData().add(averageDecayFrequencySeries);
        hBox.getChildren().add(averageDecayFrequencyChart);

        ScrollPane sp = new ScrollPane(hBox);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return sp;
    }

    private Node setupManager() {
        VBox hBox = new VBox();

        Button reset = new Button("Reset");
        reset.setMaxWidth(Double.POSITIVE_INFINITY);
        reset.setOnAction(event -> resetTriggered = true);
        hBox.getChildren().add(reset);

        Button freeze = new Button("Freeze");
        freeze.setMaxWidth(Double.POSITIVE_INFINITY);
        freeze.setOnAction(event -> {
            frozen = !frozen;
            freeze.setText(frozen ? "Unfreeze" : "Freeze");
        });
        hBox.getChildren().add(freeze);

        ScrollPane sp = new ScrollPane(hBox);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return sp;
    }

    public void update(int aggressive, int count, double averageRate, double averageFrequency) {
        aggressionSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis() - start, aggressive));
        countSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis() - start, count));
        averageDecayFrequencySeries.getData().add(new XYChart.Data<>(System.currentTimeMillis() - start, averageRate));
        averageDecayRateSeries.getData().add(new XYChart.Data<>(System.currentTimeMillis() - start, averageFrequency));

        if (krit != null) {
            select(krit);
        }
    }

    public void select(Krit krit) {
        if (this.krit == null || this.krit != krit) this.krit = krit;

        id.setText(NameStorage.get(krit) + " - " + krit.getId());
        location.setText(krit.getLocation().toString());
        origin.setText(krit.getOrigin().toString());
        health.setText(krit.getHealth() + "");
        initialHealth.setText(krit.getMaxHealth() + "");
        decay.setText(krit.getDecayRate() + "/" + krit.getDecayFrequency());
        sex.setText(krit.getSex().toString());
        color.setText(krit.getColor().toString());
        aggressive.setText(krit.isAggressive() + "");
        tribe.setText(krit.getTribe().getId().toString());
        mated.setText(krit.getMate() == null ? "false" : "true");
        mate.setText(krit.getMate() == null ? "n/a" : krit.getMate().getId().toString());
        pregnant.setText(krit.isPregnant() + "");
        term.setText(krit.isPregnant() ? krit.getPregnancyTerm() + "" : "");
        children.setText(krit.isPregnant() ? krit.getChildrenCount() + "" : "");
        parents.setText((krit.getParents()[0] == null ? "" : krit.getParents()[0].getId().toString()) + " / " + (krit.getParents()[1] == null ? "" : krit.getParents()[1].getId().toString()));

        childrenCount.setText(krit.getChildren().size() + "");
        String[] childrenIdStrings = new String[krit.getChildren().size()];
        for (int i = 0; i < childrenIdStrings.length; i++) childrenIdStrings[i] = krit.getChildren().get(i).toString();
        childrenIds.setItems(new ObservableListWrapper<>(Arrays.asList(childrenIdStrings)));

        geneticCount.setText(krit.getGenetics().size() + "");
        String[] geneticIdStrings = new String[krit.getGenetics().size()];
        for (int i = 0; i < geneticIdStrings.length; i++) geneticIdStrings[i] = krit.getGenetics().get(i).toString();
        geneticIds.setItems(new ObservableListWrapper<>(Arrays.asList(geneticIdStrings)));

        separation.setText(krit.getSeparationTurns() + "");
        proximity.setText(krit.getProximityTurns() + "");
        updates.setText(krit.getUpdateCount() + "");
    }

    public boolean isResetTriggered() {
        return resetTriggered;
    }

    public boolean isFrozen() {
        return frozen;
    }


    public void resetClear(){
        aggressionSeries.getData().clear();
        averageDecayFrequencySeries.getData().clear();
        averageDecayRateSeries.getData().clear();
        countSeries.getData().clear();
        start = System.currentTimeMillis();
        Starter.initialize();
        resetTriggered = false;
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    public Scene alternateStart(){
        TabPane pane1 = new TabPane();
        Scene scene = new Scene(pane1);

        Tab tab = new Tab();
        tab.setText("Inspect");
        tab.setContent(setupInspect());
        tab.setClosable(false);
        tab.setId("inspect");
        pane1.getTabs().add(tab);

        Tab charts = new Tab();
        charts.setText("Charts");
        charts.setContent(setupCharts());
        charts.setClosable(false);
        charts.setId("charts");
        pane1.getTabs().add(charts);

        Tab manager = new Tab();
        manager.setText("Manager");
        manager.setContent(setupManager());
        manager.setClosable(false);
        manager.setId("manager");
        pane1.getTabs().add(manager);

        return scene;
    }

}
