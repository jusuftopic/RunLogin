package sample.StatisticsMenu.History.Training4;

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
import sample.StatisticsMenu.History.HistoryController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Training4Controller {
    @FXML
    private Label username;
    @FXML
    private Button back;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField timePerKmField;
    @FXML
    private TextField caloreiField;


    //set Username
    public void setUsername(String name) {
        username.setText(name);
    }

    //set textfield with dates
    public void insertFields() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            String abfrage = "Select * from stat where username='" + username.getText() + "'";
            ResultSet resultSet = statement.executeQuery(abfrage);

            while (resultSet.next()) {
                if (resultSet.getRow() == 4) {
                    distanceField.setText(resultSet.getString(2));
                    timeField.setText(resultSet.getString(3));
                    timePerKmField.setText(resultSet.getString(4));
                    caloreiField.setText(resultSet.getString(5));
                }
            }
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }


    //go back to history controller
    public void backToHistoryMenu(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();

        Stage historyStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HistoryController.class.getResource("history.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            HistoryController historyController = fxmlLoader.getController();
            historyController.setUsername(username.getText());

            DbConnection dbConnection = new DbConnection();
            Connection connection = dbConnection.getConnection();

            try {
                Statement statement = connection.createStatement();
                String abfrage = "Select * from stat where username='" + username.getText() + "'";
                ResultSet resultSet = statement.executeQuery(abfrage);

                while (resultSet.next()) {
                    if (resultSet.getRow() == 1) {
                        historyController.setTraining1Visible();
                    } else if (resultSet.getRow() == 2) {
                        historyController.setTraining1Visible();
                        historyController.setTraining2Visible();
                    } else if (resultSet.getRow() == 3) {
                        historyController.setTraining1Visible();
                        historyController.setTraining2Visible();
                        historyController.setTraining3Visible();
                    } else if (resultSet.getRow() == 4) {
                        historyController.setTraining1Visible();
                        historyController.setTraining2Visible();
                        historyController.setTraining3Visible();
                        historyController.setTraining4Visible();
                    } else if (resultSet.getRow() == 5) {
                        historyController.setTraining1Visible();
                        historyController.setTraining2Visible();
                        historyController.setTraining3Visible();
                        historyController.setTraining4Visible();
                        historyController.setTraining5Visible();
                    }

                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace();
            }

            historyStage.setScene(new Scene(root, 364, 517));
            historyStage.initStyle(StageStyle.UNDECORATED);
            historyStage.show();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
