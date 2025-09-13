package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnItemsManager;

    @FXML
    private Button btnOrderManagement;

    @FXML
    void btnCustomerManagementOnAction(ActionEvent event) {
        Stage stage= (Stage) btnCustomerManagement.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerManagment.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnExitOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemsManagerOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderManagementOnAction(ActionEvent event) {

    }

}
