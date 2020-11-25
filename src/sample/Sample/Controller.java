package sample.Sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ChangePassword.ChangePasswordController;
import sample.DbConnection;
import sample.MainController.MainController;
import sample.RunLog.RunLogController;
import sample.SignUp.SignUpController;
import sample.StatisticsMenu.AverageScore.AverageScoreController;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView imageID;
    @FXML
    private Hyperlink exitAppID;
    @FXML
    public TextField userfieldID;
    @FXML
    private PasswordField passID;
    @FXML
    private Label leerTextID;
    @FXML
    private Button logID;
    @FXML
    private Button signID;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imageFile = new File("Images/download.jpg");
        Image image = new Image(imageFile.toURI().toString());
        imageID.setImage(image);
    }


    //Exit Aplication
    public void exitAppIDOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) exitAppID.getScene().getWindow();
        userfieldID.clear();
        passID.clear();
        stage.close();
    }

    //Login in database
    public void logIDOnAction(javafx.event.ActionEvent actionEvent) {
        if(userfieldID.getText().isBlank() || passID.getText().isBlank()){
        leerTextID.setText("Enter all data!");
    }
        else {
            logIn();
        }
    }

    //Connect with database
    public void logIn(){
        String username = userfieldID.getText();
        String password = passID.getText();
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();
        Stage mainstage = new Stage();
        try {
           PreparedStatement preparedStatement = connection.prepareStatement("select * from run where nameofuser = ? and passwordofuser=?");
           preparedStatement.setString(1,username);
           preparedStatement.setString(2,password);

           ResultSet resultSet = preparedStatement.executeQuery();

           if(resultSet.next()){
               if (resultSet.getRow()==1){
                   String weight=resultSet.getString(5);
                   String height = resultSet.getString(6);
                   int age= resultSet.getInt(7);
                   String email = resultSet.getString(3);

                   Stage stage = (Stage) logID.getScene().getWindow();
                   stage.close();

                   try {
                   FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
                   Parent root = (Parent) fxmlLoader.load();

                   MainController mainController = fxmlLoader.getController();
                   mainController.setNameID(username);
                   mainController.setWeightID(weight);
                   mainController.setHeightID(height);
                   mainController.setAgeID(age);
                   mainController.setEmail(email);
                   mainController.setPassword(password);


                   mainstage.setScene(new Scene(root,364,517));
                   mainstage.initStyle(StageStyle.UNDECORATED);
                   mainstage.show();}
                   catch (IOException ioe){
                       System.out.println(ioe.getMessage());
                       ioe.printStackTrace();
                   }


               }
           }
           else {
               leerTextID.setText("Wrong username or password!");
               userfieldID.clear();
               passID.clear();
           }
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

    }

    public void setOnEnter(KeyEvent keyEvent){
        if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
            if(userfieldID.getText().isEmpty() || passID.getText().isEmpty()){
                leerTextID.setText("Enter all data");
            }
            else{
                logIn();
            }
        }
    }

    //SignUp prooces
    public void signIDOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) signID.getScene().getWindow();
        stage.close();
        try {
            Stage signUpstage = new Stage();
            Parent root = FXMLLoader.load(SignUpController.class.getResource("signUp.fxml"));
            signUpstage.initStyle(StageStyle.UNDECORATED);
            signUpstage.setScene(new Scene(root,500,600));
            signUpstage.show();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



   public  String getUsername(){
        return userfieldID.getText();
   }
   public void setText(String text){
        userfieldID.setText(text);
   }


}
