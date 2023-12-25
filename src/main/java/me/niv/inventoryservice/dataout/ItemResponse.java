package me.niv.inventoryservice.dataout;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {

    private Long id;
    private String name;
    private String type;
    private Integer quantity;
    private String properties;
    private String location;
    private String extraProperties;
    private String resourcePath;

}
