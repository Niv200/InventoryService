package me.niv.inventoryservice.datain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoxItemRequest {

    private Long itemId;

    private BoxAction action;
}
