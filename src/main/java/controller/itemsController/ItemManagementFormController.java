package controller.itemsController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemManagementFormController implements Initializable {
    ItemManagementService itemManagementService=new ItemManagementController();
    ObservableList<Item> itemList= FXCollections.observableArrayList();

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearForm;

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnUpdateItem;


    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colPackageSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItemsView;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtPackageSize;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        Item item=new Item(
                txtItemId.getText(),
                txtDescription.getText(),
                txtPackageSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQuantityOnHand.getText())
        );
        itemManagementService.addItemDetails(item);
        loadItemDetails();
        resetForm();

    }



    @FXML
    void btnBack(ActionEvent event) {
        Stage stage= (Stage) btnBack.getScene().getWindow();

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearForm(ActionEvent event) {
        resetForm();

    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        itemManagementService.deleteItemDetails(txtItemId.getText());
        loadItemDetails();
        resetForm();


    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        Item item=new Item(
                txtItemId.getText(),
                txtDescription.getText(),
                txtPackageSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQuantityOnHand.getText())
        );
        itemManagementService.updateItemDetails(item);
        loadItemDetails();
        resetForm();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setItemID();

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackageSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        loadItemDetails();
        tblItemsView.getSelectionModel().selectedItemProperty().addListener((ObservaalbleValue,oldValue,newValue)->{
            if(newValue!=null){
                setSelectedValue(newValue);

            }

        });






    }
    private void resetForm() {
        setItemID();
        txtDescription.setText("");
        txtPackageSize.setText("");
        txtUnitPrice.setText("");
        txtQuantityOnHand.setText("");
    }
    private void loadItemDetails() {
        itemList.clear();
        itemList=itemManagementService.getAllItems();
        tblItemsView.setItems(itemList);
    }

    private void setSelectedValue(Item newValue) {
        txtItemId.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackageSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQuantityOnHand.setText(String.valueOf(newValue.getQtyOnHand()));

    }

    private void setItemID() {
        if(itemManagementService.getLastID()!=null){
            String lastId=itemManagementService.getLastID();
            lastId = lastId.split("[A-Z]")[1]; // C001==> 001
            lastId= String.format("P%03d",(Integer.parseInt(lastId)+1));
            txtItemId.setText(lastId);

        }
    }
}
