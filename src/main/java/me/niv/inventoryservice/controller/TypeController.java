package me.niv.inventoryservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.dto.request.TypeModifyRequest;
import me.niv.inventoryservice.dto.request.TypeRequest;
import me.niv.inventoryservice.dto.response.TypeResponse;
import me.niv.inventoryservice.service.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/types")
public class TypeController {

    private final TypeService service;

    @GetMapping
    public ResponseEntity<List<TypeResponse>> getAllTypes(){
        return ResponseEntity.ok(service.getAllTypes());
    }

    @PostMapping
    public ResponseEntity<Void> createType(@RequestBody TypeRequest request){
        service.createType(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateType(@RequestBody TypeModifyRequest request){
        service.editType(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<Void> deleteType(@PathVariable String type){
        service.deleteType(type);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
