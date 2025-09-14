package model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Order {

    private String orderId;
    private LocalDate orderDate;
    private String custId;
}
