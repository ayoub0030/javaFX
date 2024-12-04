package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField cinField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // Clear any previous messages
        messageLabel.setText("");
    }

    @FXML
    public void handleLogin() {
        String cin = cinField.getText();
        String password = passwordField.getText();

        // Simple validation
        if (cin == null || cin.trim().isEmpty()) {
            messageLabel.setText("Please enter your CIN");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            messageLabel.setText("Please enter your password");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Hardcoded credentials check (for demonstration)
        if (cin.equals("12345") && password.equals("password")) {
            messageLabel.setText("Login successful!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Invalid CIN or password");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            messageLabel.setText("Error loading registration page");
            messageLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
