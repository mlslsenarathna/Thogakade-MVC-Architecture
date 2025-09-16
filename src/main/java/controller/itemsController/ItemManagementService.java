package controller.itemsController;

import javafx.collections.ObservableList;
import model.dto.Item;


public interface ItemManagementService {
    void addItemDetails(Item item);
    void updateItemDetails(Item item);
    ObservableList<Item> getAllItems();
    void deleteItemDetails(String custId);
    String getLastID();


}
