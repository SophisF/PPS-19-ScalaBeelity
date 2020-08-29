package view.heatmap;


import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;


public class HeatMapViewer extends Application {
    private static final Dimension2D SIZE = new Dimension2D(1024, 640);
    private AnchorPane pane;
    private ImageView backgroundImage;
    private ImageView heatMapImage;
    private Button buttonLoadBackgroundImage;
    private Button buttonLoadHeatMapImage;
    private ToggleButton toggleButtonShowHeatMap;


    @Override
    public void init() {
        backgroundImage = new ImageView();
        heatMapImage = new ImageView();
        buttonLoadBackgroundImage = new Button("BackgroundImage");
        buttonLoadHeatMapImage = new Button("HeatmapImage");
        toggleButtonShowHeatMap = new ToggleButton("hide");
        toggleButtonShowHeatMap.setSelected(true);

        AnchorPane.setLeftAnchor(buttonLoadBackgroundImage, 10d);
        AnchorPane.setTopAnchor(buttonLoadBackgroundImage, 10d);

        AnchorPane.setRightAnchor(buttonLoadHeatMapImage, 10d);
        AnchorPane.setTopAnchor(buttonLoadHeatMapImage, 10d);

        AnchorPane.setRightAnchor(toggleButtonShowHeatMap, 10d);
        AnchorPane.setTopAnchor(toggleButtonShowHeatMap, 40d);

        pane = new AnchorPane(backgroundImage, heatMapImage, buttonLoadBackgroundImage, buttonLoadHeatMapImage, toggleButtonShowHeatMap);

        registerListeners();
    }

    private void registerListeners() {
        buttonLoadBackgroundImage.setOnAction(observable -> handleControlPropertyChanged("LOAD_BACKGROUND"));
        buttonLoadHeatMapImage.setOnAction(observable -> handleControlPropertyChanged("LOAD_HEATMAP"));
        toggleButtonShowHeatMap.setOnAction(observable -> handleControlPropertyChanged("TOGGLE_HEATMAP"));
    }

    private void handleControlPropertyChanged(final String PROPERTY) {
        if ("LOAD_BACKGROUND".equals(PROPERTY)) {
            Image image = getImage(buttonLoadBackgroundImage.getScene().getWindow());
            if (null == image) return;
            backgroundImage.setImage(image);
            backgroundImage.toBack();
        } else if ("LOAD_HEATMAP".equals(PROPERTY)) {
            Image image = getImage(buttonLoadHeatMapImage.getScene().getWindow());
            if (null == image) return;
            heatMapImage.setImage(image);
            toggleButtonShowHeatMap.setSelected(true);
        } else if ("TOGGLE_HEATMAP".equals(PROPERTY)) {
            toggleButtonShowHeatMap.setText(toggleButtonShowHeatMap.isSelected() ? "show" : "hide");
            heatMapImage.setVisible(!toggleButtonShowHeatMap.isSelected());
        }
    }

    private Image getImage(final Window WINDOW) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        final File SELECTED_FILE = fileChooser.showOpenDialog(WINDOW);

        return null == SELECTED_FILE ? null : new Image("file:" + SELECTED_FILE.getAbsolutePath(), pane.getWidth(), 0, true, true);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(pane, SIZE.getWidth(), SIZE.getHeight());

        stage.setTitle("HeatMap Viewer");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}