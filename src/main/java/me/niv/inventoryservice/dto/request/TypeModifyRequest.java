package me.niv.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TypeModifyRequest {

    private String oldType;
    private String type;
}
