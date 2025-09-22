package view;

import control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class ShowEmployeeLoginView {
    private Stage stage;
    private TextField idField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label messageLabel = new Label();

    public void showLogin(Stage stage, showEmployeeLoginController controller) {
        idField.setPromptText("Enter your Employee ID");
        passwordField.setPromptText("Enter your Password");

        VBox employeeLoginLayout = new VBox(15);
        employeeLoginLayout.setAlignment(Pos.CENTER);
        employeeLoginLayout.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 20;");

        HBox idBox = new HBox(10, new Label("Employee ID:"), idField);
        HBox passwordBox = new HBox(10, new Label("Password:"), passwordField);

        idBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);

        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
        loginButton.setMaxWidth(200);

        messageLabel.setFont(new Font("Arial", 12));
        messageLabel.setStyle("-fx-text-fill: red;");
        loginButton.setOnAction(e -> controller.handleLogin(stage, this));

        employeeLoginLayout.getChildren().addAll(idBox, passwordBox, loginButton, messageLabel);

        Scene scene = new Scene(employeeLoginLayout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Employee Login");
        stage.show();
    }
    public TextField getIdField() {
        return idField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
} 