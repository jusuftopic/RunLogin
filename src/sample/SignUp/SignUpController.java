package sample.SignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.BackToLogin.BackToLogin;
import sample.DbConnection;
import sample.Sample.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private ImageView runnerphotoID;
    @FXML
    private TextField nameofuserID;
    @FXML
    private TextField passwordofuserID;
    @FXML
    private TextField password2ofuserID;
    @FXML
    private TextField ageofuserID;
    @FXML
    private TextField weightofuserID;
    @FXML
    private TextField emailofuserID;
    @FXML
    private TextField numberofuserID;
    @FXML
    private Button registrationID;
    @FXML
    private Button memberID;
    @FXML
    private TextField heightofuserID;
    @FXML
    private Label blankID;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File photofile = new File("Images/attachment_118012231.jpg");
        Image photo = new Image(photofile.toURI().toString());
        runnerphotoID.setImage(photo);
    }

    public void registrationIDOnAction(javafx.event.ActionEvent actionEvent) {
        if (nameofuserID.getText().isBlank() || passwordofuserID.getText().isBlank() || password2ofuserID.getText().isBlank() || emailofuserID.getText().isBlank() || heightofuserID.getText().isBlank() || ageofuserID.getText().isBlank() || weightofuserID.getText().isBlank()) {
            blankID.setText("Please enter all required fields. ");
        } else {
            if(!rightPassword()){
                blankID.setText("Password does not contain letters (AaBb...), numbers (1,2,3,4...) and characters (_,.,!,#,...)");
            }
          else if  (!(passwordofuserID.getText().equals(password2ofuserID.getText()))) {
                blankID.setText("Wrong confirmation password");
            } else {
                DbConnection dbConnection = new DbConnection();
                Connection connection = dbConnection.getConnection();
                String username = nameofuserID.getText();

                try {
                    Statement statement = connection.createStatement();
                    String abfrage = "select count(*) from run where nameofuser='" + username + "'";
                    ResultSet resultSet = statement.executeQuery(abfrage);

                    while (resultSet.next()) {
                        if (resultSet.getInt(1) == 0) {
                            insertMember();
                        } else {
                            blankID.setText("User already exists");
                        }
                    }
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace();
                }
            }
        }
    }

    //Insert new member
    public void insertMember() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();
        String username = nameofuserID.getText();
        String password = passwordofuserID.getText();
        String email = emailofuserID.getText();
        String number = numberofuserID.getText();
        String height = heightofuserID.getText();
        String weight = weightofuserID.getText();
        String age = ageofuserID.getText();
        try {
            Statement statement = connection.createStatement();
            String insert = "Insert into run(nameofuser,passwordofuser,emailofuser,numberofuser,weight,height,age) values ('" + username + "', '" + password + "', '" + email + "', '" + number + "', '" + weight + "', '" + height + "', '" + Integer.parseInt(age) + "')";
            statement.executeUpdate(insert);
            blankID.setText("You have successfully registered");

            //open welcome page
            Stage registrationStage = (Stage) registrationID.getScene().getWindow();
            registrationStage.close();
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(BackToLogin.class.getResource("backToLogin.fxml"));
                stage.setScene(new Scene(root, 380, 300));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    public void memberIDOnAction(ActionEvent actionEvent) {
        Stage signUp = (Stage) memberID.getScene().getWindow();
        signUp.close();

        Stage login = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(Controller.class.getResource("sample.fxml"));
            Parent root = (Parent)loader.load();
            login.setScene(new Scene(root, 364, 517));
            login.initStyle(StageStyle.UNDECORATED);
            login.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    //chech correct password
    public boolean rightPassword(){
        String password = passwordofuserID.getText();
        int letter= 0;
        int digit = 0;
        int character=0;

        for(int i = 0; i < password.length(); i++){
            if(Character.isDigit(password.charAt(i))){
                digit++;
            }
            else if (Character.isLetter(password.charAt(i))){
                letter++;
            }
            else if(!Character.isLetter(password.charAt(i)) && !Character.isDigit(password.charAt(i))){
                character++;
            }
        }

        if(character>0 && letter>0 && digit>0){
            return true;
        }
        else{
            return false;
        }
    }

}
