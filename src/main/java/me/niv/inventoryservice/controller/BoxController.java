package me.niv.inventoryservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.datain.BoxItemRequest;
import me.niv.inventoryservice.datain.BoxRequest;
import me.niv.inventoryservice.dataout.BoxResponse;
import me.niv.inventoryservice.service.BoxService;
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
@RequestMapping(path = "/box")
public class BoxController {

    private final BoxService service;

    @GetMapping
    public ResponseEntity<List<BoxResponse>> getAllBoxes(){
        return ResponseEntity.ok(service.getAllBoxes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoxResponse> getBox(@PathVariable Long id){
        return ResponseEntity.ok(service.getBox(id));
    }

    @PostMapping
    public ResponseEntity<Void> createBox(@RequestBody BoxRequest request){
        service.createBox(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id){
        service.deleteBox(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBox(@PathVariable Long id, @RequestBody BoxRequest request){
        service.editBox(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> manageItems(@PathVariable Long id, @RequestBody BoxItemRequest request){
        service.handleItemUpdate(id, request);
        return ResponseEntity.ok().build();
    }


}
