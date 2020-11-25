package sample.StatisticsMenu;

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
import sample.DbConnection;
import sample.MainController.MainController;
import sample.StatisticsMenu.AverageScore.AverageScoreController;
import sample.StatisticsMenu.History.HistoryController;
import sample.StatisticsMenu.TotalScore.TotalScoreController;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StatisticsMenu implements Initializable {
    @FXML
    private ImageView statisticsimageID;
    @FXML
    private Button totalscoreID;
    @FXML
    private Button averageScore;
    @FXML
    private Label user;
    @FXML
    private Button cancleStatisticsMenu;
    @FXML
    private Button historyPage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("Images/statistics.jpg");
        Image image = new Image(file.toURI().toString());
        statisticsimageID.setImage(image);
    }

    //set user not visible
    public void setVisible(){
        user.setVisible(false);
    }

    //Total score statistics
    public void totalscoreOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) totalscoreID.getScene().getWindow();
        stage.close();

        Stage totalScoreStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TotalScoreController.class.getResource("totalscore.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            TotalScoreController totalScoreController = fxmlLoader.getController();
            totalScoreController.setUser(user.getText());
            totalScoreController.setCalories();
            totalScoreController.setTotalKilometers();
            totalScoreController.setTotalTrainings();
            totalScoreController.setTotaltime();

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                String insert = "insert into totalscore(username,distance,timeofrace,calories,trainings) values (?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insert);

                preparedStatement.setString(1, user.getText());
                preparedStatement.setString(2, totalScoreController.getTotalkilometers().getText());
                preparedStatement.setString(3, totalScoreController.getTotaltime().getText());
                preparedStatement.setInt(4, Integer.parseInt(totalScoreController.getCalories().getText()));
                preparedStatement.setInt(5, Integer.parseInt(totalScoreController.getTotaltrainings().getText()));

                int getUpdate = preparedStatement.executeUpdate();
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
                sql.getCause();
            }


            totalScoreStage.setScene(new Scene(root, 364, 517));
            totalScoreStage.initStyle(StageStyle.UNDECORATED);
            totalScoreStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to site of average score
    public void averageScoreOnPoint(ActionEvent actionEvent) {
        Stage stage = (Stage) averageScore.getScene().getWindow();
        stage.close();

        Stage averageStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AverageScoreController.class.getResource("averagescore.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            AverageScoreController averageScoreController = fxmlLoader.getController();
            averageScoreController.setUserName(user.getText());
            averageScoreController.setAverageDistance();
            averageScoreController.setAverageTime();
            averageScoreController.setAverageCalories();
            averageScoreController.setAverageTimePerKilometer();

            averageStage.setScene(new Scene(root, 364, 517));
            averageStage.initStyle(StageStyle.UNDECORATED);
            averageStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //go to history page
    public void historyPage(ActionEvent actionEvent) {
        Stage stage = (Stage) historyPage.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HistoryController.class.getResource("history.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            HistoryController historyController = fxmlLoader.getController();
            historyController.setUsername(user.getText());
            historyController.chacekTrainings();

            Stage historyStage = new Stage();
            historyStage.setScene(new Scene(root, 364, 517));
            historyStage.initStyle(StageStyle.UNDECORATED);
            historyStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    //Exit menu
    public void cancelStatisticsMenuOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancleStatisticsMenu.getScene().getWindow();
        stage.close();

        Stage maincontrollerStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("maincontroller.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "select * from run where nameofuser = '" + user.getText() + "'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                if (resultSet.next()) {
                    if (resultSet.getRow() == 1) {
                        mainController.setNameID(user.getText());
                        mainController.setPassword(resultSet.getString(2));
                        mainController.setEmail(resultSet.getString(3));
                        mainController.setWeightID(resultSet.getString(5));
                        mainController.setHeightID(resultSet.getString(6));
                        mainController.setAgeID(resultSet.getInt(7));
                    }
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }
            maincontrollerStage.setScene(new Scene(root,364,517));
            maincontrollerStage.initStyle(StageStyle.UNDECORATED);
            maincontrollerStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }

    }

    //username set
    public void setUsername(String username) {
        user.setText(username);
    }
}
