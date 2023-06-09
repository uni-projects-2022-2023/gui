package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.proxyUser.ProxyBorrower;
import com.example.project.proxyUser.ProxyUser;
import com.example.project.user.Borrower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookDetailController {
    @FXML
    private ImageView bookImage;
    @FXML
    private Text bookTitle;
    @FXML
    private Label label;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookCategory;
    @FXML
    private Text bookDescription;
    private ProxyUser proxyUser;
    private Iterator iterator;
    Database database;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Load the custom font
        Font font = Font.loadFont(getClass().getResourceAsStream("src/main/resources/fonts/Harlow Solid Italic Italic.ttf"), 20);
    }

    private Book book ;
    public void backHandler(ActionEvent actionEvent) throws IOException, SQLException {
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
    }
    public void setBook(Book book){this.book = book;}
    public void putBookDetails(){
        this.bookTitle.setText(book.getBookTitle());
        this.bookAuthor.setText(book.getBookAuthor());
        this.bookDescription.setText(book.getBookDesc());
        this.bookCategory.setText(book.getBookCat());
        Image image= new Image(getClass().getResourceAsStream("/"+book.getBookImage()));
        this.bookImage.setImage(image);

    }
    public void borrowHandler(ActionEvent actionEvent)throws SQLException,IOException{
        boolean flag =false;
        ResultSet resultSet = database.getBorrowedBooks(this.proxyUser.getRealUser());
        while(resultSet.next()){
            if(resultSet.getString("id").equals(book.getBookId())){
                flag= true;
                break;
            }
        }

        if(flag == false){
            Borrower borrower =(Borrower) this.proxyUser.getRealUser();
            if(borrower.borrowBook(book,borrower,"Start","End")){


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
            }
            else{
                System.out.println("Borrow Failed...!!");
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Book Already Borrowed!");
            alert.setHeaderText("Error!");
            alert.setContentText("Cannot Borrow This Book ... Already Borrowed");
            alert.show();
            return;
        }

    }
    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.label.setText(proxyUser.getRealUser().getEmail());
    }
}
