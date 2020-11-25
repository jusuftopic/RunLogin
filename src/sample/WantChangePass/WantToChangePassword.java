package sample.WantChangePass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ChangePassword.ChangePasswordController;
import sample.MainController.MainController;

import java.io.IOException;
import java.sql.Statement;

public class WantToChangePassword {
    @FXML
    private Button noID;
    @FXML
    private Button yesID;

    public void noIDOnAction(ActionEvent actionEvent){
        Stage stage = (Stage) noID.getScene().getWindow();
        stage.close();

        Stage mainAgain = new Stage();
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
        try {
            Parent root = (Parent) loader.load();

            mainAgain.setScene(new Scene(root,364,517));
            mainAgain.initStyle(StageStyle.UNDECORATED);
            mainAgain.show();

        }
        catch (IOException i){
            System.out.println(i.getMessage());
        }
    }

    public void yesIDOnAction(ActionEvent actionEvent){
        Stage stage = (Stage) yesID.getScene().getWindow();
        stage.close();

        Stage changePasswordStage = new Stage();
        try {

            FXMLLoader loader  = new FXMLLoader(ChangePasswordController.class.getResource("changepassword.fxml"));
            Parent parent = (Parent) loader.load();


            changePasswordStage.setScene(new Scene(parent,480,350));
            changePasswordStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }




}
