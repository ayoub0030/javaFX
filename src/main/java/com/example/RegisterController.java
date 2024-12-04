package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField cinField;
    @FXML private DatePicker birthdayPicker;
    @FXML private ComboBox<String> sexComboBox;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> insuranceComboBox;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button togglePassword;
    @FXML private Button toggleConfirmPassword;
    @FXML private Label messageLabel;

    private TextField visiblePasswordField;
    private TextField visibleConfirmPasswordField;
    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;

    @FXML
    public void initialize() {
        // Initialize ComboBox options
        sexComboBox.getItems().addAll("Male", "Female");
        cityComboBox.getItems().addAll("Casablanca", "Rabat", "Marrakech", "Fes", "Tangier", "Agadir");
        insuranceComboBox.getItems().addAll("CNSS", "CNOPS", "RAMED", "Other");
        
        messageLabel.setText("");

        // Create visible text fields
        visiblePasswordField = new TextField();
        visiblePasswordField.setPromptText("Password");
        visiblePasswordField.setStyle(passwordField.getStyle());
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);

        visibleConfirmPasswordField = new TextField();
        visibleConfirmPasswordField.setPromptText("Confirm Password");
        visibleConfirmPasswordField.setStyle(confirmPasswordField.getStyle());
        visibleConfirmPasswordField.setVisible(false);
        visibleConfirmPasswordField.setManaged(false);

        // Add visible fields to StackPane
        StackPane passwordStack = (StackPane) passwordField.getParent();
        StackPane confirmStack = (StackPane) confirmPasswordField.getParent();
        
        passwordStack.getChildren().add(1, visiblePasswordField);
        confirmStack.getChildren().add(1, visibleConfirmPasswordField);

        // Bind text properties
        bindPasswordFields(passwordField, visiblePasswordField);
        bindPasswordFields(confirmPasswordField, visibleConfirmPasswordField);
    }

    private void bindPasswordFields(PasswordField passwordField, TextField textField) {
        // Bidirectional binding of text properties
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!textField.getText().equals(newValue)) {
                textField.setText(newValue);
            }
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!passwordField.getText().equals(newValue)) {
                passwordField.setText(newValue);
            }
        });
    }

    @FXML
    public void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        visiblePasswordField.setVisible(passwordVisible);
        visiblePasswordField.setManaged(passwordVisible);
        passwordField.setVisible(!passwordVisible);
        passwordField.setManaged(!passwordVisible);

        // Update eye icon
        ImageView imageView = (ImageView) togglePassword.getGraphic();
        imageView.setImage(new Image(getClass().getResourceAsStream(
            passwordVisible ? "/eye-slash.png" : "/eye.png"
        )));
    }

    @FXML
    public void toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible;
        visibleConfirmPasswordField.setVisible(confirmPasswordVisible);
        visibleConfirmPasswordField.setManaged(confirmPasswordVisible);
        confirmPasswordField.setVisible(!confirmPasswordVisible);
        confirmPasswordField.setManaged(!confirmPasswordVisible);

        // Update eye icon
        ImageView imageView = (ImageView) toggleConfirmPassword.getGraphic();
        imageView.setImage(new Image(getClass().getResourceAsStream(
            confirmPasswordVisible ? "/eye-slash.png" : "/eye.png"
        )));
    }

    @FXML
    public void handleRegister() {
        if (isAnyFieldEmpty()) {
            messageLabel.setText("Please fill in all fields");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // TODO: Add actual registration logic here
        messageLabel.setText("Account created successfully!");
        messageLabel.setStyle("-fx-text-fill: green;");
    }

    private boolean isAnyFieldEmpty() {
        return firstNameField.getText().trim().isEmpty() ||
               lastNameField.getText().trim().isEmpty() ||
               cinField.getText().trim().isEmpty() ||
               birthdayPicker.getValue() == null ||
               sexComboBox.getValue() == null ||
               phoneField.getText().trim().isEmpty() ||
               addressField.getText().trim().isEmpty() ||
               cityComboBox.getValue() == null ||
               insuranceComboBox.getValue() == null ||
               passwordField.getText().trim().isEmpty() ||
               confirmPasswordField.getText().trim().isEmpty();
    }

    @FXML
    public void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Error loading login page");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
