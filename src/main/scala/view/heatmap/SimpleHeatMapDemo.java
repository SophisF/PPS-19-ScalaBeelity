package view.heatmap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SimpleHeatMapDemo extends Application {
    private SimpleHeatMap heatMap;
    private StackPane pane;
    private EventHandler<ActionEvent> handler;


    // ******************** Initialization ************************************
    @Override
    public void init() {
        pane = new StackPane();
        heatMap = new SimpleHeatMap(400, 400, ColorMapping.BLACK_WHITE);

        registerListeners();
    }


    // ******************** Start *********************************************
    @Override
    public void start(Stage stage) {

        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);

        pane.getChildren().setAll(layout, heatMap.getHeatMapImage());
        Scene scene = new Scene(pane, 400, 400, Color.GRAY);

        stage.setTitle("JavaFX SimpleHeatMap Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    // ******************** Methods *******************************************
    private void registerListeners() {
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            double x = event.getX();
            double y = event.getY();
            if (x < heatMap.getEventRadius()) x = heatMap.getEventRadius();
            if (x > pane.getWidth() - heatMap.getEventRadius()) x = pane.getWidth() - heatMap.getEventRadius();
            if (y < heatMap.getEventRadius()) y = heatMap.getEventRadius();
            if (y > pane.getHeight() - heatMap.getEventRadius()) y = pane.getHeight() - heatMap.getEventRadius();

            heatMap.addEvent(x, y);
        });
        pane.widthProperty().addListener((ov, oldWidth, newWidth) -> heatMap.setSize(newWidth.doubleValue(), pane.getHeight()));
        pane.heightProperty().addListener((ov, oldHeight, newHeight) -> heatMap.setSize(pane.getWidth(), newHeight.doubleValue()));
    }
}





