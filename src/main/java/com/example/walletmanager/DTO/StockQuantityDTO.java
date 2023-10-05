package com.example.walletmanager.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Getter
@Setter
public class StockQuantityDTO {
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity should be greater than 0")
    private Integer quantity;

}
