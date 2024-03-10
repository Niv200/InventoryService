package me.niv.inventoryservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.dto.request.ItemQuantityRequest;
import me.niv.inventoryservice.dto.request.ItemRequest;
import me.niv.inventoryservice.dto.response.ItemResponse;
import me.niv.inventoryservice.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService service;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems(){
        return ResponseEntity.ok(service.getAllItems(null, null, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long id){
        return ResponseEntity.ok(service.getItem(id));
    }

    @PostMapping
    public ResponseEntity<Void> postItem(@RequestBody ItemRequest request){
        service.createItem(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody ItemRequest request){
        service.editItem(id, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        service.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long id, @RequestBody ItemQuantityRequest request){
        service.manageQuantity(id, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
