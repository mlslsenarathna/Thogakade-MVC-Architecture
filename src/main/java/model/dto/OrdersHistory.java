package model.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrdersHistory {
    private String customerId;
    private String orderId;
    private LocalDate orderDate;
    private String itemId;
    private String description;
    private String packageSize;
    private int orderQTY;
    private double price;

}
