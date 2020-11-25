package sample.BackToLogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Sample.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BackToLogin implements Initializable {
    @FXML
    private ImageView rightID;
    @FXML
    private Button backToLoginID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/right.jpg");
        Image image = new Image(file.toURI().toString());
        rightID.setImage(image);
    }

    public void backToLoginIDOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) backToLoginID.getScene().getWindow();
        stage.close();

        try {
            Stage loginStage = new Stage();
            Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
            loginStage.setScene(new Scene(root,364,517));
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}