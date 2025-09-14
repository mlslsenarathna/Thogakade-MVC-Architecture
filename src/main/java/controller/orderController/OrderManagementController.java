package controller.orderController;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementController implements OrderManagementService {
    @Override
    public String getLastID() {
        try {
            Connection connection  = DBConnection.getInstance().getConnection();

            String SQL="SELECT OrderID FROM orders ORDER BY OrderID  DESC LIMIT 1;";
            PreparedStatement preparedStatement= connection.prepareStatement(SQL);
            ResultSet resultset= preparedStatement.executeQuery();
            return resultset.next() ? resultset.getString("OrderID") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
