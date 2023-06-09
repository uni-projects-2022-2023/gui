package com.example.project;

import com.example.project.proxyUser.*;
import com.example.project.user.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {


    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @javafx.fxml.FXML
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public Button register;
    @FXML
    public Button login;

    @javafx.fxml.FXML
    @FXML
    private Label errorMessage;

    private String type ;

    @javafx.fxml.FXML
    @FXML
    private VBox Vbox;

    protected ProxyUser proxyUser;
    public void login(ActionEvent actionEvent) throws SQLException, IOException {

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(Email.isEmpty() || Password.isEmpty()){
            errorMessage.setText("Please fill the empty fields ... ! ");
            return ;
        }

        proxyUser.login(Email,Password);

        if(this.proxyUser.getRealUser()==null){
            errorMessage.setText("Invalid username or password ... !");
            return ;
        }
        else{
            if(this.proxyUser instanceof ProxyBorrower){
                errorMessage.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
                AnchorPane root = fxmlLoader.load();


                AllBooksController allBooksController = fxmlLoader.getController();
                allBooksController.setProxyUser(this.proxyUser);
                allBooksController.start();


                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                Screen screen= Screen.getPrimary();
                Rectangle2D bounds= screen.getVisualBounds();
                root.setPrefSize(bounds.getWidth(), bounds.getHeight());
                stage.setWidth(bounds.getWidth());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
                return ;
            }
            if(this.proxyUser instanceof ProxyLibrarian){
                errorMessage.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Librarian.fxml"));
                AnchorPane root = fxmlLoader.load();

                LibrarianController librarianController = fxmlLoader.getController();
                librarianController.setProxyUser((ProxyLibrarian) this.proxyUser);

                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                Screen screen= Screen.getPrimary();
                Rectangle2D bounds= screen.getVisualBounds();
                root.setPrefSize(bounds.getWidth(), bounds.getHeight());
                stage.setWidth(bounds.getWidth());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            }
            if(this.proxyUser instanceof ProxyAdmin){
                errorMessage.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                AnchorPane root = fxmlLoader.load();
                AdminController adminController = fxmlLoader.getController();
                adminController.setProxyUser((ProxyAdmin)this.proxyUser);
                adminController.start();

                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                Screen screen= Screen.getPrimary();
                Rectangle2D bounds= screen.getVisualBounds();
                root.setPrefSize(bounds.getWidth(), bounds.getHeight());
                stage.setWidth(bounds.getWidth());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            }

        }

    }

    public void register(ActionEvent actionEvent)throws SQLException ,IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
        AnchorPane root = fxmlLoader.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Screen screen= Screen.getPrimary();
        Rectangle2D bounds= screen.getVisualBounds();
        root.setPrefSize(bounds.getWidth(), bounds.getHeight());
        stage.setWidth(bounds.getWidth());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void backHandler(ActionEvent actionEvent)throws SQLException,IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Entry.fxml"));
        AnchorPane root = fxmlLoader.load();


        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Screen screen= Screen.getPrimary();
        Rectangle2D bounds= screen.getVisualBounds();
        root.setPrefSize(bounds.getWidth(), bounds.getHeight());
        stage.setWidth(bounds.getWidth());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void removeRegister(){
        Vbox.getChildren().remove(register);
    }
}
