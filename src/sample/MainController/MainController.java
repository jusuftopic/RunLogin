package sample.MainController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ChangePassword.ChangePasswordController;
import sample.FindFriends.FindFriendsController;
import sample.Program.ProgrammController;
import sample.Sample.Controller;
import sample.RunLog.RunLogController;
import sample.StatisticsMenu.StatisticsMenu;
import sample.WantChangePass.WantToChangePassword;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ImageView runnerID;
    @FXML
    private TextField nameID;
    @FXML
    private TextField weightID;
    @FXML
    private TextField heightID;
    @FXML
    private TextField ageID;
    @FXML
    private TextField imeID;
    @FXML
    private TextField lozinkaID;
    @FXML
    private Hyperlink exitButtonID;
    @FXML
    private Button newlogID;
    @FXML
    private Hyperlink anotherpasswordID;
    @FXML
    private Button switchtostatistics;
    @FXML
    private Button switchToProgrammMenu;
    @FXML
    private Button findFriendsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File imagefile = new File("Images/110982549-runner-jogging-sprinter-athlete-vector-train-running-running-status-running-man-live-running-status.jpg");
        Image runnerimage = new Image(imagefile.toURI().toString());
        runnerID.setImage(runnerimage);
    }

    //Exit main page
    public void exitButtonIDOnAction(ActionEvent actionEvent) {
        Controller controller = new Controller();
        Stage stage = (Stage) exitButtonID.getScene().getWindow();
        stage.close();
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 364, 517));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //Stage to insert your run log
    public void newlogIDOnAction(ActionEvent actionEvent) {
        loadRunLog();
    }

    public void loadRunLog() {
        Stage runLogStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RunLogController.class.getResource("runlog.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            RunLogController runLogController = (RunLogController) fxmlLoader.getController();
            runLogController.transferMessage(nameID.getText());
            runLogController.setBlankUsername(nameID.getText());
            runLogController.setVisible();

            runLogStage.setScene(new Scene(root, 364, 517));
            runLogStage.initStyle(StageStyle.UNDECORATED);
            runLogStage.show();


        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //switch to "change password" page
    public void anotherpasswordIDOnAction(ActionEvent actionEvent) {
        Stage another = (Stage) anotherpasswordID.getScene().getWindow();
        another.close();

        Stage changepasswordWindow = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChangePasswordController.class.getResource("changepassword.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ChangePasswordController changePasswordController = fxmlLoader.getController();
            changePasswordController.setName(nameID.getText());
            changePasswordController.setOldpassword(lozinkaID.getText());

            changepasswordWindow.setScene(new Scene(root, 480, 350));
            changepasswordWindow.initStyle(StageStyle.UNDECORATED);
            changepasswordWindow.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //Switch on your statistics
    public void switchonstatisticsOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) switchtostatistics.getScene().getWindow();
        stage.close();

        Stage statisticsMenu = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StatisticsMenu.class.getResource("statisticsmenu..fxml"));
            Parent root = (Parent) fxmlLoader.load();

            StatisticsMenu statisticsMenu1 = fxmlLoader.getController();
            statisticsMenu1.setUsername(nameID.getText());
            statisticsMenu1.setVisible();

            statisticsMenu.setScene(new Scene(root, 364, 517));
            statisticsMenu.initStyle(StageStyle.UNDECORATED);
            statisticsMenu.show();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    public void switchToProgrammChoose(ActionEvent actionEvent) {
        Stage closeMain = (Stage) switchToProgrammMenu.getScene().getWindow();
        closeMain.close();
        Stage chooseProgramStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ProgrammController.class.getResource("programcontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            ProgrammController programmController = fxmlLoader.getController();
            programmController.setUsername(nameID.getText());


            chooseProgramStage.setScene(new Scene(root, 364, 517));
            chooseProgramStage.initStyle(StageStyle.UNDECORATED);
            chooseProgramStage.show();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to page to find your friends
    public void findFriendsPage(ActionEvent actionEvent) {
        Stage stage = (Stage) findFriendsButton.getScene().getWindow();
        stage.close();

        Stage friendsfinderStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FindFriendsController.class.getResource("findfriends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            FindFriendsController findFriendsController = fxmlLoader.getController();
            findFriendsController.setUsername(nameID.getText());

            friendsfinderStage.setScene(new Scene(root,364,517));
            friendsfinderStage.initStyle(StageStyle.UNDECORATED);
            friendsfinderStage.show();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //set name
    public void setNameID(String name){
        nameID.setText(name);
    }
    //set weight
    public void setWeightID(String weight){
        weightID.setText(weight);
    }
    //set height
    public void setHeightID(String height){
        heightID.setText(height);
    }
    //set age
    public void setAgeID(int age){
        ageID.setText(Integer.toString(age));
    }
    //set email
    public void setEmail(String email){
        imeID.setText(email);
    }
    //set passwor
    public void setPassword(String pass){
        lozinkaID.setText(pass);

    }


}

