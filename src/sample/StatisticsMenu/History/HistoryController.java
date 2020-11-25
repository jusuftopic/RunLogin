package sample.StatisticsMenu.History;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbConnection;
import sample.StatisticsMenu.History.Training1.Training1Controller;
import sample.StatisticsMenu.History.Training2.Training2Controller;
import sample.StatisticsMenu.History.Training3.Training3Controller;
import sample.StatisticsMenu.History.Training4.Training4Controller;
import sample.StatisticsMenu.History.Training5.Training5Controller;
import sample.StatisticsMenu.StatisticsMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private ImageView photo;
    @FXML
    private Button backbutton;
    @FXML
    private Label username;
    @FXML
    private Button training1;
    @FXML
    private Button training2;
    @FXML
    private Button training3;
    @FXML
    private Button training4;
    @FXML
    private Button training5;
    @FXML
    private Label blank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/pastruns.jpg");
        Image image = new Image(file.toURI().toString());
        photo.setImage(image);
    }

    //set username
    public void setUsername(String name) {
        username.setText(name);
    }

    //check quantity of trainigs
    public void chacekTrainings() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select * from stat where username='" + username.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);
            while (resultSet.next()) {
                if (resultSet.getRow() == 1) {
                    training1.setVisible(true);
                } else if (resultSet.getRow() == 2) {
                    training1.setVisible(true);
                    training2.setVisible(true);
                } else if (resultSet.getRow() == 3) {
                    training1.setVisible(true);
                    training2.setVisible(true);
                    training3.setVisible(true);
                } else if (resultSet.getRow() == 4) {
                    training1.setVisible(true);
                    training2.setVisible(true);
                    training3.setVisible(true);
                    training4.setVisible(true);
                } else if (resultSet.getRow() == 5) {
                    training1.setVisible(true);
                    training2.setVisible(true);
                    training3.setVisible(true);
                    training4.setVisible(true);
                    training5.setVisible(true);
                } else {
                    blank.setText("Error with a trainings");
                }
            }
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }

    //Methods set visible training when go back to history controller
    public void setTraining1Visible(){
        training1.setVisible(true);
    }
    public void setTraining2Visible(){
        training2.setVisible(true);
    }
    public void setTraining3Visible(){
        training3.setVisible(true);
    }
    public void setTraining4Visible(){
        training4.setVisible(true);
    }
    public void setTraining5Visible(){
        training5.setVisible(true);
    }

    //go to first training
    public void trainings1controller(ActionEvent actionevent) {
        if (training1.isVisible()) {
            Stage stage = (Stage) training1.getScene().getWindow();
            stage.close();

            Stage trainigs1Stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Training1Controller.class.getResource("trainings1.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                Training1Controller training1Controller = fxmlLoader.getController();
                training1Controller.setUsername(username.getText());
                training1Controller.setFields();

                trainigs1Stage.setScene(new Scene(root, 364, 517));
                trainigs1Stage.initStyle(StageStyle.UNDECORATED);
                trainigs1Stage.show();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                ioe.printStackTrace();
            }
        }
    }

    //go to training 2
    public void training2controller(ActionEvent actionEvent) {
        Stage stage = (Stage) training2.getScene().getWindow();
        stage.close();

        Stage training2Stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Training2Controller.class.getResource("training2.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Training2Controller training2Controller = fxmlLoader.getController();
            training2Controller.setUsername(username.getText());
            training2Controller.setFields();

            training2Stage.setScene(new Scene(root, 364, 517));
            training2Stage.initStyle(StageStyle.UNDECORATED);
            training2Stage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to training 3
    public void training3controller(ActionEvent actionEvent) {
        Stage stage = (Stage) training3.getScene().getWindow();
        stage.close();

        Stage training3stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Training3Controller.class.getResource("training3.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Training3Controller training3Controller = fxmlLoader.getController();
            training3Controller.setUser(username.getText());
            training3Controller.setFields();

            training3stage.setScene(new Scene(root, 364, 517));
            training3stage.initStyle(StageStyle.UNDECORATED);
            training3stage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }

    }

    //go to training 4
    public void training4controller(ActionEvent actionEvent){
        Stage stage = (Stage) training4.getScene().getWindow();
        stage.close();

        Stage training4stage = new Stage();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Training4Controller.class.getResource("training4.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Training4Controller training4Controller = fxmlLoader.getController();
            training4Controller.setUsername(username.getText());
            training4Controller.insertFields();

            training4stage.setScene(new Scene(root,364,517));
            training4stage.initStyle(StageStyle.UNDECORATED);
            training4stage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to training 5
    public void training5controller(ActionEvent actionEvent){
        Stage stage = (Stage) training5.getScene().getWindow();
        stage.close();

        Stage training5stage = new Stage();

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Training5Controller.class.getResource("training5.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Training5Controller training5Controller = fxmlLoader.getController();
            training5Controller.setUsername(username.getText());
            training5Controller.insertFields();

            training5stage.setScene(new Scene(root,364,517));
            training5stage.initStyle(StageStyle.UNDECORATED);
            training5stage.show();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }


    //go back to statistics menu
    public void backToStatistics(ActionEvent actionEvent) {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        stage.close();

        Stage statisticsStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StatisticsMenu.class.getResource("statisticsmenu..fxml"));
            Parent root = (Parent) fxmlLoader.load();

            StatisticsMenu statisticsMenu = fxmlLoader.getController();
            statisticsMenu.setUsername(username.getText());


            statisticsStage.setScene(new Scene(root, 364, 517));
            statisticsStage.initStyle(StageStyle.UNDECORATED);
            statisticsStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
