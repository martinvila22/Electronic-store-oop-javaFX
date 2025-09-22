package control;

import view.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class FirstPageController {

    public void handleUserSelection(Stage stage) {
       ShowEmployeeLoginView view=new ShowEmployeeLoginView();
       showEmployeeLoginController control=new showEmployeeLoginController();
        view.showLogin(stage, control);
    }


	public void handleAboutSelection(Stage stage) {
        Stage aboutStage = new Stage();
        VBox aboutPane = new VBox(20);
        aboutPane.setAlignment(Pos.CENTER);
        aboutPane.setStyle("-fx-background-color: beige; -fx-padding: 30px; -fx-border-radius: 15px; -fx-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);");

        Label aboutLabel = new Label("Welcome to the Electronic Management System!");
        aboutLabel.setWrapText(true);
        aboutLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-padding: 10px;");

        Label anotherLabel = new Label(
                "This application helps manage operations for an electronic store, providing functions for clients and employees.\n\n" +
                        "Features:\n" +
                        "- Job applications (Cashier, Manager)\n" +
                        "- View products with details and pricing\n" +
                        "- Client and employee views\n" +
                        "- Version 1.0.2\n\n" +
                        "Developed by: Entea Bakiasi, Martin Vila, Mikael Xhangolli"
        );
        anotherLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666; -fx-padding: 15px;");

        Label otherLabel = new Label("Version 1.0.2");
        otherLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888888; -fx-font-style: italic;");

        aboutPane.getChildren().addAll(aboutLabel, anotherLabel, otherLabel);

        Scene aboutScene = new Scene(aboutPane, 800, 600);
        aboutStage.setTitle("Electronic Store - About");
        aboutStage.setScene(aboutScene);
        aboutStage.show();
    }

    public void handleJobSelection(Stage stage) {
        Stage jobStage = new Stage();
        VBox jobSelectionLayout = new VBox(25);
        jobSelectionLayout.setAlignment(Pos.CENTER);
        jobSelectionLayout.setStyle("-fx-padding: 20px; -fx-background-color: #f9f9f9;");

        jobSelectionLayout.getChildren().addAll(
                createJobCard("Cashier 1 - $30/hour", "9:00 AM - 3:00 PM", "Sunday Off", "Cashier 1", jobStage),
                createJobCard("Cashier 2 - $30/hour", "9:00 AM - 3:00 PM", "Sunday Off", "Cashier 2", jobStage),
                createJobCard("Manager - $70/hour", "10:00 AM - 6:00 PM", "Sunday Off", "Manager", jobStage)
        );

        Scene scene = new Scene(jobSelectionLayout, 650, 500);
        jobStage.setTitle("Job Selection");
        jobStage.setScene(scene);
        jobStage.show();
    }

    private HBox createJobCard(String jobTitle, String workingHours, String dayOff, String jobType, Stage jobStage) {
        HBox jobCard = new HBox(20);
        jobCard.setAlignment(Pos.CENTER);
        jobCard.setStyle("-fx-background-color: #8FBC8F; -fx-padding: 15px; -fx-border-radius: 12px; -fx-shadow: 5px 5px 10px rgba(0, 0, 0, 0.1);");

        VBox jobInfo = new VBox(12);
        jobInfo.setAlignment(Pos.CENTER_LEFT);
        jobInfo.setStyle("-fx-padding: 15px;");

        Label titleLabel = new Label(jobTitle);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        Label hoursLabel = new Label("Working Hours: " + workingHours);
        Label dayOffLabel = new Label("Day Off: " + dayOff);

        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-font-size: 14px; -fx-background-color: #fff8e1; -fx-text-fill: #4CAF50; -fx-padding: 10px; -fx-border-radius: 5px; -fx-font-weight: bold;");
        applyButton.setOnAction(e -> {
            System.out.println("Applying for: " + jobType);
            showJobApplicationForm(jobStage, jobType);
        });

        jobInfo.getChildren().addAll(titleLabel, hoursLabel, dayOffLabel, applyButton);
        jobCard.getChildren().add(jobInfo);

        return jobCard;
    }

    private void showJobApplicationForm(Stage stage, String jobTitle) {
        Stage applicationStage = new Stage();
        VBox applicationFormLayout = new VBox(20);
        applicationFormLayout.setAlignment(Pos.CENTER);
        applicationFormLayout.setStyle("-fx-padding: 25px; -fx-background-color: #f4f4f4;");

        Label titleLabel = new Label("Apply for " + jobTitle);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your Name");
        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter your Surname");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your Phone Number");

        Button submitButton = new Button("Submit Application");
        submitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 12px 20px; -fx-border-radius: 5px;");

        submitButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Job Application");
            alert.setHeaderText("Your application for " + jobTitle + " has been submitted!");
            alert.setContentText("The administrator will be notified for this job application. You will be contacted soon for an interview.");
            alert.showAndWait();
        });

        applicationFormLayout.getChildren().addAll(titleLabel, nameField, surnameField, phoneField, submitButton);

        Scene applicationScene = new Scene(applicationFormLayout, 400, 300);
        applicationStage.setScene(applicationScene);
        applicationStage.setTitle("Job Application");
        applicationStage.initModality(Modality.APPLICATION_MODAL);
        applicationStage.show();
    }
}