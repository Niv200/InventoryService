package me.niv.inventoryservice.datain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoxRequest {

    private String name;
    private String contents;
    private String location;
    private String extraInformation;
    private String resourcePath;

}
