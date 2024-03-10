package me.niv.inventoryservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeResponse {

    Long id;
    String type;

}
