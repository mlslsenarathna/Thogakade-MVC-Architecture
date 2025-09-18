package controller.orderController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Item;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderManagementFormController implements Initializable {
    OrderManagementService orderManagementService=new OrderManagementController();

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteItemFromCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnResetCart;

    @FXML
    private Button btnSearchCustId;

    @FXML
    private Button btnSetDiscount;

    @FXML
    private Button btnUpdateItemInCart;

    @FXML
    private Button btnViewOrdersHistory;

    @FXML
    private ComboBox<String> cmdItemId;

    @FXML
    private TableColumn<Item, String> colItemCode;

    @FXML
    private TableColumn<Item, String> colItemDescription;

    @FXML
    private TableColumn<Item, String> colItemPackageSize;

    @FXML
    private TableColumn<Item, Integer> colItemQuantity;

    @FXML
    private TableColumn<Item, Double> colPrice;

    @FXML
    private TableView<Item> tblItemCartVeiw;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTotalCount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDiscountRate;

    @FXML
    private TextField txtInStock;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPackageSize;

    @FXML
    private TextField txtOrderQTY;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String itemID=cmdItemId.getSelectionModel().getSelectedItem();
        Item item=orderManagementService.getItem(itemID);
        double itemsValue=item.getUnitPrice()*Integer.parseInt(txtOrderQTY.getText());

        for(Item existing:tblItemCartVeiw.getItems()){
            if(existing.getItemCode().equals(item.getItemCode())){
                int newItenQTY= Integer.parseInt(txtOrderQTY.getText())+existing.getQtyOnHand();
                existing.setQtyOnHand(newItenQTY);
                existing.setUnitPrice((item.getUnitPrice() * newItenQTY));
                tblItemCartVeiw.refresh();
                calculateOrderTotal();
                txtOrderQTY.clear();
                return;

            }
        }
        tblItemCartVeiw.getItems().add(
                new Item(item.getItemCode(),
                        item.getDescription(),
                        item.getPackSize(),
                        itemsValue,
                        Integer.parseInt(txtOrderQTY.getText())));
        calculateOrderTotal();
        txtOrderQTY.setText("");






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
    void btnDeleteItemFromCartOnAction(ActionEvent event) {
        Item selectedItem=tblItemCartVeiw.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            tblItemCartVeiw.getItems().remove(selectedItem);
            calculateOrderTotal();
        }else {
            JOptionPane.showMessageDialog(null,"No item Selected..");
        }

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        ObservableList<Item> itemsList=tblItemCartVeiw.getItems();
        for (Item item:itemsList){
            if(txtOrderQTY!=null){
                orderManagementService.updatStockCount(item.getItemCode(),item.getQtyOnHand());

            }else{
                JOptionPane.showMessageDialog(null,"Please Select items");
            }



        }
        tblItemCartVeiw.getItems().clear();
        calculateOrderTotal();
        setItems();



    }

    @FXML
    void btnResetCartOnAction(ActionEvent event) {
        tblItemCartVeiw.getItems().clear();
        calculateOrderTotal();

    }

    @FXML
    void btnSearchCustIdOnAction(ActionEvent event) {
        double Totalprice=orderManagementService.customerTotalValue(txtCustomerId.getText());
        txtCustomerTotalCount.setText(String.valueOf(Totalprice));
        txtCustomerAddress.setText(orderManagementService.getCustomer(txtCustomerId.getText()).getAddress());
        txtCustomerName.setText(orderManagementService.getCustomer(txtCustomerId.getText()).getName());


    }

    @FXML
    void btnSetDiscountOnAction(ActionEvent event) {


    }

    @FXML
    void btnUpdateItemInCartOnAction(ActionEvent event) {
        String itemID=cmdItemId.getSelectionModel().getSelectedItem();
        Item item=orderManagementService.getItem(itemID);
        double itemsValue=item.getUnitPrice()*Integer.parseInt(txtOrderQTY.getText());

        for(Item existing:tblItemCartVeiw.getItems()){
            if(existing.getItemCode().equals(item.getItemCode())){
                int newItenQTY= Integer.parseInt(txtOrderQTY.getText());
                existing.setQtyOnHand(newItenQTY);
                existing.setUnitPrice((item.getUnitPrice() * newItenQTY));
                tblItemCartVeiw.refresh();
                calculateOrderTotal();
                txtOrderQTY.clear();
                return;

            }
        }
        tblItemCartVeiw.getItems().add(
                new Item(item.getItemCode(),
                        item.getDescription(),
                        item.getPackSize(),
                        itemsValue,
                        Integer.parseInt(txtOrderQTY.getText())));
        calculateOrderTotal();
        txtOrderQTY.setText("");



    }

    @FXML
    void btnViewOrdersHistoryOnAction(ActionEvent event) {
        Stage stage= (Stage) btnViewOrdersHistory.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ViewOrdersHistory.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrderId();
        setDate();
        setItems();
        calculateOrderTotal();


        colItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        colItemPackageSize.setCellValueFactory(new PropertyValueFactory<Item,String>("packSize"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Item,Double>("unitPrice"));
        colItemQuantity.setCellValueFactory(new PropertyValueFactory<Item,Integer>("qtyOnHand"));

        cmdItemId.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Item item=orderManagementService.getItem(newVal);
                txtDescription.setText(item.getDescription());
                txtPackageSize.setText(item.getPackSize());
                txtInStock.setText(String.valueOf(item.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));

            }
        });

    }
    private double  calculateOrderTotal(){
        double total=tblItemCartVeiw.getItems().stream().mapToDouble(Item::getUnitPrice).sum();
        txtTotalPrice.setText("Rs."+total+"0");
       return total;
    }

    private void setItems() {
        cmdItemId.setItems(orderManagementService.getItems());

    }

    private void setDate() {
        txtDate.setText(LocalDate.now().toString());
    }

    private void setOrderId() {
        if(orderManagementService.getLastID()!=null){
            String lastId=orderManagementService.getLastID();
            lastId = lastId.split("[A-Z]")[1]; // D001==> 001
            lastId= String.format("D%03d",(Integer.parseInt(lastId)+1));
            txtOrderId.setText(lastId);

        }
    }
}
