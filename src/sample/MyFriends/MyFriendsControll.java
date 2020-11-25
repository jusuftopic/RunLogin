package sample.MyFriends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.FindFriends.FindFriendsController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MyFriendsControll implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Button back;
    @FXML
    private ImageView imageOfFriends;
    @FXML
    private Button friend1;
    @FXML
    private Button friend2;
    @FXML
    private Button friend3;
    @FXML
    private Button friend4;
    @FXML
    private Button friend5;
    @FXML
    private Hyperlink moreFriends;
    @FXML
    private Button continueButton;


    //set friend 1 button
    public void setFriend1(String friendusername){
        friend1.setVisible(true);
        friend1.setText(friendusername);
    }

    //set friend 2 button
    public void setFriend2(String friendusername){
        friend2.setVisible(true);
        friend2.setText(friendusername);
    }

    //set friend 3 button
    public void setFriend3(String friendsusername){
        friend3.setVisible(true);
        friend3.setText(friendsusername);
    }

    //set friend 4 button
    public void setFriend4(String friendsusername){
        friend4.setVisible(true);
        friend4.setText(friendsusername);
    }

    //set friend 5 button
    public void setFriend5(String friendusername){
        friend5.setVisible(true);
        friend5.setText(friendusername);
    }

    //get hyperlink for more than 5 friends
    public Hyperlink getMoreFriends(){
        return moreFriends;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/frend.jpg");
        Image image = new Image(file.toURI().toString());
        imageOfFriends.setImage(image);
    }

    public void setUsername(String name) {
        username.setText(name);
    }

    //go to more friends
    public void listOfMoreFriends(ActionEvent actionEvent){
        Stage stage = (Stage) moreFriends.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MoreFriendsController.class.getResource("moreFriendsController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MoreFriendsController moreFriendsController = fxmlLoader.getController();
            moreFriendsController.setUsername(username.getText());

            Stage morefriendStage = new Stage();
            morefriendStage.setScene(new Scene(root,380,600));
            morefriendStage.initStyle(StageStyle.UNDECORATED);
            morefriendStage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to "make workout or see profile controller1
    public void goToProfileOrWorkoutController1(ActionEvent actionEvent){
        Stage stage = (Stage) friend1.getScene().getWindow();
        stage.close();

        Stage doWorkoutOrVisitProfileStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TrainingOrProfileController.class.getResource("trainingOrProfileController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TrainingOrProfileController trainingOrProfileController = fxmlLoader.getController();
            trainingOrProfileController.setFriendsusername(friend1.getText());
            trainingOrProfileController.setMyusername(username.getText());

            doWorkoutOrVisitProfileStage.setScene(new Scene(root,470,250));
            doWorkoutOrVisitProfileStage.initStyle(StageStyle.UNDECORATED);
            doWorkoutOrVisitProfileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    //go to "make workout or see profile controller 2
    public void goToProfileOrWorkoutController2(ActionEvent actionEvent){
        Stage stage = (Stage) friend2.getScene().getWindow();
        stage.close();

        Stage doWorkoutOrVisitProfileStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TrainingOrProfileController.class.getResource("trainingOrProfileController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TrainingOrProfileController trainingOrProfileController = fxmlLoader.getController();
            trainingOrProfileController.setFriendsusername(friend2.getText());
            trainingOrProfileController.setMyusername(username.getText());

            doWorkoutOrVisitProfileStage.setScene(new Scene(root,470,250));
            doWorkoutOrVisitProfileStage.initStyle(StageStyle.UNDECORATED);
            doWorkoutOrVisitProfileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    //go to "make workout or see profile controller 3
    public void goToProfileOrWorkoutController3(ActionEvent actionEvent){
        Stage stage = (Stage) friend3.getScene().getWindow();
        stage.close();

        Stage doWorkoutOrVisitProfileStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TrainingOrProfileController.class.getResource("trainingOrProfileController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TrainingOrProfileController trainingOrProfileController = fxmlLoader.getController();
            trainingOrProfileController.setFriendsusername(friend3.getText());
            trainingOrProfileController.setMyusername(username.getText());

            doWorkoutOrVisitProfileStage.setScene(new Scene(root,470,250));
            doWorkoutOrVisitProfileStage.initStyle(StageStyle.UNDECORATED);
            doWorkoutOrVisitProfileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to "make workout or see profile controller 4
    public void goToProfileOrWorkoutController4(ActionEvent actionEvent){
        Stage stage = (Stage) friend4.getScene().getWindow();
        stage.close();

        Stage doWorkoutOrVisitProfileStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TrainingOrProfileController.class.getResource("trainingOrProfileController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TrainingOrProfileController trainingOrProfileController = fxmlLoader.getController();
            trainingOrProfileController.setFriendsusername(friend4.getText());
            trainingOrProfileController.setMyusername(username.getText());

            doWorkoutOrVisitProfileStage.setScene(new Scene(root,470,250));
            doWorkoutOrVisitProfileStage.initStyle(StageStyle.UNDECORATED);
            doWorkoutOrVisitProfileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    //go to "make workout or see profile controller 5
    public void goToProfileOrWorkoutController5(ActionEvent actionEvent){
        Stage stage = (Stage) friend5.getScene().getWindow();
        stage.close();

        Stage doWorkoutOrVisitProfileStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TrainingOrProfileController.class.getResource("trainingOrProfileController.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TrainingOrProfileController trainingOrProfileController = fxmlLoader.getController();
            trainingOrProfileController.setFriendsusername(friend5.getText());
            trainingOrProfileController.setMyusername(username.getText());

            doWorkoutOrVisitProfileStage.setScene(new Scene(root,470,250));
            doWorkoutOrVisitProfileStage.initStyle(StageStyle.UNDECORATED);
            doWorkoutOrVisitProfileStage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }






    //back to menu
    public void backToFindFrienMend(ActionEvent actionEvent){
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        Stage findfriendstage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FindFriendsController.class.getResource("findfriends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            FindFriendsController findFriendsController = fxmlLoader.getController();
            findFriendsController.setUsername(username.getText());

            findfriendstage.setScene(new Scene(root,364,517));
            findfriendstage.initStyle(StageStyle.UNDECORATED);
            findfriendstage.show();
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
