package me.niv.inventoryservice.dataout;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxResponse {

    private Long id;
    private String name;
    private String contents;
    private String location;
    private String extraInformation;
    private String resourcePath;
    private List<ItemResponse> items;

}
