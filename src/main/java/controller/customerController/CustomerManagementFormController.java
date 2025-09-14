package controller.customerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {
    CustomerManagementService customerManagementService=new CustomerManagementController();
    ObservableList<Customer> customerDetails = FXCollections.observableArrayList();

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClearForm;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private ComboBox<String> cmdCustomerTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDoB;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private DatePicker doBBirthday;

    @FXML
    private TableView<Customer> tblCustomerView;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerCity;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerPostalCode;

    @FXML
    private TextField txtCustomerProvince;

    @FXML
    private TextField txtCustomerSalary;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        Customer customer=new Customer(
                txtCustomerId.getText(),
                cmdCustomerTitle.getValue(),
                txtCustomerName.getText(),
                doBBirthday.getValue(),
                Double.parseDouble(txtCustomerSalary.getText()),
                txtCustomerAddress.getText(),
                txtCustomerCity.getText(),
                txtCustomerProvince.getText(),
                txtCustomerPostalCode.getText()

        );
        customerManagementService.addCustomerDetails(customer);
        loadCustomerDetails();
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
    void btnDeleteCustomerOnAction(ActionEvent event) {
        customerManagementService.deleteCustomerDetails(txtCustomerId.getText());
        resetForm();
        loadCustomerDetails();

    }

    private void resetForm() {
        setCustomerID();
        cmdCustomerTitle.setValue("");
        txtCustomerName.setText("");
        doBBirthday.setValue(null);
        txtCustomerSalary.setText("");
        txtCustomerAddress.setText("");
        txtCustomerCity.setText("");
        txtCustomerProvince.setText("");
        txtCustomerPostalCode.setText("");

    }



    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {

        Customer customer=new Customer(
                txtCustomerId.getText(),
                cmdCustomerTitle.getValue(),
                txtCustomerName.getText(),
                doBBirthday.getValue(),
                Double.parseDouble(txtCustomerSalary.getText()),
                txtCustomerAddress.getText(),
                txtCustomerCity.getText(),
                txtCustomerProvince.getText(),
                txtCustomerPostalCode.getText()

        );
        customerManagementService.updateCustomerDetails(customer);
        loadCustomerDetails();
        resetForm();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustomerID();
        loadCustomerTitles();

        colId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDoB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        loadCustomerDetails();

        tblCustomerView.getSelectionModel().selectedItemProperty().addListener((ObservableValue,oldValue,newValue)->{
            if(newValue!=null){
                setSelectedValue(newValue);
            }

        });



    }

    private void loadCustomerTitles() {
        cmdCustomerTitle.getItems().addAll("Mr", "Mrs", "Ms", "Miss", "Dr", "Rev");

    }

    private void setSelectedValue(Customer newValue) {
        txtCustomerId.setText(newValue.getCustId());
        cmdCustomerTitle.setValue(newValue.getTitle());
        txtCustomerName.setText(newValue.getName());
        doBBirthday.setValue(newValue.getDob());
        txtCustomerSalary.setText(String.valueOf(newValue.getSalary()));
        txtCustomerAddress.setText(newValue.getAddress());
        txtCustomerCity.setText(newValue.getCity());
        txtCustomerProvince.setText(newValue.getProvince());
        txtCustomerPostalCode.setText(newValue.getPostalCode());


    }

    private void loadCustomerDetails() {
        customerDetails.clear();
        customerDetails= customerManagementService.getAllCustomers();
        tblCustomerView.setItems(customerDetails);


    }
    private void setCustomerID() {
        if(customerManagementService.getLastID()!=null){
            String lastId=customerManagementService.getLastID();
            lastId = lastId.split("[A-Z]")[1]; // C001==> 001
            lastId= String.format("C%03d",(Integer.parseInt(lastId)+1));
            txtCustomerId.setText(lastId);

        }
    }
}
