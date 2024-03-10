package me.niv.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemRequest {

    private String name;
    private String type;
    private Integer quantity;
    private String properties;
    private String location;
    private String extraProperties;
    private String resourcePath;


}
