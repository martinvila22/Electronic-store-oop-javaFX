package view;

import control.FirstPageController;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class HelloApplicationView {

    private Button userButton;
    private Button aboutButton;
    private Button welcomeButton;

    public void show(Stage primaryStage, FirstPageController controller) {

        Text welcomeText = new Text("Welcome to Our Electronic Store!");
        welcomeText.setFont(new Font(32));
        welcomeText.setStyle("-fx-font-weight: bold; -fx-fill: #2C3E50;");

        welcomeButton = new Button("Proceed");
        welcomeButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #8FBC8F; -fx-text-fill: white; " +
                        "-fx-padding: 12px 20px; -fx-border-radius: 30px; -fx-font-weight: bold;"
        );

        welcomeButton.setOnMouseEntered(e -> welcomeButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #6A9C70; -fx-text-fill: white; " +
                        "-fx-padding: 12px 20px; -fx-border-radius: 30px; -fx-font-weight: bold;"
        ));
        welcomeButton.setOnMouseExited(e -> welcomeButton.setStyle(
                "-fx-font-size: 16px; -fx-background-color: #8FBC8F; -fx-text-fill: white; " +
                        "-fx-padding: 12px 20px; -fx-border-radius: 30px; -fx-font-weight: bold;"
        ));

        VBox mainLayout = new VBox(30);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-padding: 50px;");

        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#ECF0F1"), CornerRadii.EMPTY, null)));
        mainLayout.getChildren().addAll(welcomeText, welcomeButton);

        welcomeButton.setOnAction(e -> {
            showMainMenu(primaryStage, controller);
        });

        Scene scene = new Scene(mainLayout, 600, 450);
        scene.setFill(Color.WHITE);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), mainLayout);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to Electronic Store");
        primaryStage.show();
    }

    private void showMainMenu(Stage primaryStage, FirstPageController controller) {

        userButton = new Button("User");
        userButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;");

        aboutButton = new Button("About");
        aboutButton.setStyle("-fx-font-size: 18px; -fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;");

        userButton.setOnMouseEntered(e -> userButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #388E3C; -fx-text-fill: white; " +
                        "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;"
        ));
        userButton.setOnMouseExited(e -> userButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white; " +
                        "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;"
        ));

        aboutButton.setOnMouseEntered(e -> aboutButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #1976D2; -fx-text-fill: white; " +
                        "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;"
        ));
        aboutButton.setOnMouseExited(e -> aboutButton.setStyle(
                "-fx-font-size: 18px; -fx-background-color: #2196F3; -fx-text-fill: white; " +
                        "-fx-padding: 15px 30px; -fx-font-weight: bold; -fx-background-radius: 30px;"
        ));

        VBox mainLayout = new VBox(25);
        mainLayout.getChildren().addAll(userButton, aboutButton);
        mainLayout.setStyle("-fx-padding: 60px;");
        mainLayout.setAlignment(Pos.CENTER);

        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F4F6F6"), CornerRadii.EMPTY, null)));

        userButton.setOnAction(e -> controller.handleUserSelection(primaryStage));
        aboutButton.setOnAction(e -> controller.handleAboutSelection(primaryStage));

        Scene scene = new Scene(mainLayout, 450, 350);
        scene.setFill(Color.WHITE);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), mainLayout);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu - Electronic Store");
        primaryStage.show();
    }
}
