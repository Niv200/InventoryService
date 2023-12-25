package me.niv.inventoryservice.dataout;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeResponse {

    Long id;
    String type;

}
