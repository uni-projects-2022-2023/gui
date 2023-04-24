package com.example.project;

import com.example.project.proxyUser.ProxyAdmin;
import com.example.project.proxyUser.ProxyBorrower;
import com.example.project.proxyUser.ProxyLibrarian;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EntryController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button userbtn;

    @FXML
    private Button librarianbtn;

    @FXML
    private Button adminbtn;


    public void userHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.proxyUser = new ProxyBorrower();

        Scene scene = new Scene(root);
        Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage1.setTitle("Borrower Login");
        stage1.setWidth(1350);
        stage1.setHeight(810);

        stage1.setScene(scene);
        stage1.show();

    }
    public void librarianHandler(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            AnchorPane root = loader.load();

            LoginController loginController = loader.getController();
            loginController.proxyUser = new ProxyLibrarian();
            loginController.removeRegister();

            Scene scene = new Scene(root);
            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage1.setTitle("librarian Login");
            stage1.setWidth(1350);
            stage1.setHeight(810);

            stage1.setScene(scene);
            stage1.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void adminHandler(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.proxyUser = new ProxyAdmin();
        loginController.removeRegister();

        Scene scene = new Scene(root);
        Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage1.setTitle("admin Login");
        stage1.setWidth(1350);
        stage1.setHeight(810);

        stage1.setScene(scene);
        stage1.show();

    }

}