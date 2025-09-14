package model.dto;

import lombok.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDetail {

    private String orderId;
    private String itemCode;
    private  int orderQty;
    private double discount;

}
