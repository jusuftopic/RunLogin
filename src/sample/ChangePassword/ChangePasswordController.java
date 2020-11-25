package sample.ChangePassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;
import sample.MainController.MainController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePasswordController {
    @FXML
    private Label blankfieldID;
    @FXML
    private TextField oldPasswordID;
    @FXML
    private TextField newPasswordID;
    @FXML
    private TextField newPasswordAgainID;
    @FXML
    private Button CancleID;
    @FXML
    private Button confirmID;
    @FXML
    private Label welcome;
    @FXML
    private Label korisnik;
    @FXML
    private TextField warningID;
    private String oldpassword;

    public void setOldpassword(String password){
        oldpassword=password;
    }



    //Check that the entered password is correct
    public boolean isSamePassword(){
       if(oldPasswordID.getText().equals(oldpassword)){
           return true;
       }
       else{
           return false;
       }
    }

    //Change password
    public void confirmIDOnAction(){
      if(!isSamePassword()){
          blankfieldID.setText("You didn't type your old password correctly");
      }
        else if(!newPasswordID.getText().equals(newPasswordAgainID.getText())){
            blankfieldID.setText("Your conformational password is incorrect");
        }
        else if(correctPassword()){
            Stage stage = (Stage) confirmID.getScene().getWindow();
            stage.close();

            Stage mainStage=new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                MainController mainController = fxmlLoader.getController();

                try {
                    DbConnection dbConnection = new DbConnection();
                    Connection connection = dbConnection.getConnection();
                    String user=korisnik.getText();
                    String newpassword = newPasswordID.getText();

                    Statement statement = connection.createStatement();
                    String abfrage = "UPDATE run set passwordofuser='"+newpassword+"' where nameofuser ='"+ user+"'";
                    statement.executeUpdate(abfrage);

                    String back= "Select * from run where nameofuser='"+user+"'";
                    ResultSet resultSet = statement.executeQuery(back);

                    while (resultSet.next()){
                        if (resultSet.getRow()==1){
                            String weight=resultSet.getString(5);
                            String height = resultSet.getString(6);
                            int age= resultSet.getInt(7);
                            String email = resultSet.getString(3);

                       //     mainController.setFields(user,weight,height,age,email,newpassword);
                        }
                    }


                }
                catch (SQLException sql){
                    System.out.println(sql.getMessage());
                    sql.printStackTrace();
                }

                mainStage.setScene(new Scene(root,364,517));
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.show();
            }
            catch (IOException ioe){
                System.out.println(ioe.getMessage());
                ioe.printStackTrace();
            }
      }
      else if(!correctPassword()){
          blankfieldID.setText("Password does not contain letters (AaBb...), numbers (1,2,3,4...) and characters (_,.,!,#,...)");
      }


    }


    //Cancle with same password
    public void cancleIDOnAction(ActionEvent actionEvent){
       Stage close = (Stage) CancleID.getScene().getWindow();
       close.close();
        Stage main=new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();


            try {
                DbConnection dbConnection = new DbConnection();
                Connection connection = dbConnection.getConnection();
                String username = korisnik.getText();

                Statement statement = connection.createStatement();
                String abfrage = "Select * from run where nameofuser='"+username+"'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while(resultSet.next()){
                    if (resultSet.getRow()==1){
                        String weight=resultSet.getString(5);
                        String height = resultSet.getString(6);
                        int age= resultSet.getInt(7);
                        String email = resultSet.getString(3);
                        String password= resultSet.getString(2);

                     mainController.setNameID(username);
                     mainController.setWeightID(weight);
                     mainController.setHeightID(height);
                     mainController.setAgeID(age);
                     mainController.setEmail(email);
                     mainController.setPassword(password);
                    }
                }
            }
            catch (SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

            main.setScene(new Scene(root,364,517));
            main.initStyle(StageStyle.UNDECORATED);
            main.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }

    //check if password letters, numbers and character has
   public boolean correctPassword(){
        String password = newPasswordID.getText();
        int letter=0;
        int nummber = 0;
        int character=0;

        for(int i = 0; i < password.length(); i++){
            if (Character.isLetter(password.charAt(i))){
                letter++;
            }
            else if(Character.isDigit(password.charAt(i))){
                nummber++;
            }
            else if(password.charAt(i)>=(char)33 && password.charAt(i)<=(char)47){
                character++;
            }
            else if(password.charAt(i)>=(char)58 && password.charAt(i)<=(char)64){
                character++;
            }
            else if(password.charAt(i)>=(char)91 && password.charAt(i)<=(char)96){
                character++;
            }
            else if(password.charAt(i)>=(char)123 && password.charAt(i)<=(char)128){
                character++;
            }
        }
        if (character>0 && letter>0 && nummber>0){
            return true;
        }
        else{
            return false;
        }

    }

    public void setName(String name){
        this.korisnik.setText(name);
    }

    }



