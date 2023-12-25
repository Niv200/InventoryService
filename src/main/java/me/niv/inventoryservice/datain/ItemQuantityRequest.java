package me.niv.inventoryservice.datain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemQuantityRequest {

    private long id;
    private QuantityChange change;
    private Integer number;
}
