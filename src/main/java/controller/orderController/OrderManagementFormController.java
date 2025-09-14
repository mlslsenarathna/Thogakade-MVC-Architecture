package controller.orderController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.dto.Item;

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
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemDescription;

    @FXML
    private TableColumn<?, ?> colItemPackageSize;

    @FXML
    private TableColumn<?, ?> colItemQuantity;

    @FXML
    private TableColumn<?, ?> colPrice;

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
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnDeleteItemFromCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchCustIdOnAction(ActionEvent event) {

    }

    @FXML
    void btnSetDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateItemInCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOrdersHistoryOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrderId();
        setDate();
        
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
