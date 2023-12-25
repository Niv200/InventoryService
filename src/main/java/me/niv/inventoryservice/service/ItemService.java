package me.niv.inventoryservice.service;

import static java.util.stream.Collectors.toList;
import static me.niv.inventoryservice.datain.QuantityChange.ADD;
import static me.niv.inventoryservice.datain.QuantityChange.REMOVE;
import static me.niv.inventoryservice.datain.QuantityChange.SET;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.datain.ItemQuantityRequest;
import me.niv.inventoryservice.datain.ItemRequest;
import me.niv.inventoryservice.dataout.ItemResponse;
import me.niv.inventoryservice.entity.ItemEntity;
import me.niv.inventoryservice.entity.TypeEntity;
import me.niv.inventoryservice.exception.ApplicationException;
import me.niv.inventoryservice.repository.ItemRepository;
import me.niv.inventoryservice.repository.TypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;
    private final BoxService boxService;
    private final TypeRepository typeRepository;

    public List<ItemResponse> getAllItems(String name, String properties, String type){
        List<ItemEntity> items = repository.findAll();
        if(name != null){
            items = items.stream().filter(item -> item.getName().contains(name)).collect(toList());
        }
        if(properties != null){
            items = items.stream().filter(item -> item.getProperties().contains(properties)).collect(toList());
        }
        if(type != null){
            items = items.stream().filter(item -> item.getType().getType().contains(type)).collect(toList());
        }
        return items.stream().map(item -> ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .type(item.getType().getType())
                .quantity(item.getQuantity())
                .properties(item.getProperties())
                .extraProperties(item.getExtraProperties())
                .location(item.getLocation())
                .resourcePath(item.getResourcePath())
                .build()).collect(toList());
    }

    public ItemResponse getItem(Long id){
        validateId(id);
        ItemEntity entity = repository.findById(id).get();

        return ItemResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getType())
                .quantity(entity.getQuantity())
                .properties(entity.getProperties())
                .extraProperties(entity.getExtraProperties())
                .location(entity.getLocation())
                .resourcePath(entity.getResourcePath())
                .build();
    }

    public Long createItem(ItemRequest request){
        if(repository.existsByNameAndProperties(request.getName(), request.getProperties())){
            throw new ApplicationException("Item name and properties already exist.", HttpStatus.CONFLICT);
        }
        if(!typeRepository.existsByType(request.getType())){
            throw new ApplicationException("Item type does not exist in types table", HttpStatus.NOT_FOUND);
        }
        if(request.getQuantity() != null && request.getQuantity() < 0){
            throw new ApplicationException("Item count cannot be below 0", HttpStatus.BAD_REQUEST);
        }
        TypeEntity typeEntity = typeRepository.findByType(request.getType()).get();
        ItemEntity entity = ItemEntity.builder()
                .name(request.getName())
                .type(typeEntity)
                .quantity(request.getQuantity())
                .properties(request.getProperties())
                .location(request.getLocation())
                .extraProperties(request.getExtraProperties())
                .resourcePath(request.getResourcePath())
                .build();
        return repository.save(entity).getId();
    }

    public void editItem(Long id, ItemRequest request){
        validateId(id);
        Optional<TypeEntity> typeEntity = typeRepository.findByType(request.getType());
        if(!typeEntity.isPresent()){
            throw new ApplicationException("Item type does not exist", HttpStatus.NOT_FOUND);
        }
        ItemEntity entity = repository.findById(id).get();
        entity.setQuantity(request.getQuantity());
        entity.setName(request.getName());
        entity.setType(typeEntity.get());
        entity.setLocation(request.getLocation());
        entity.setProperties(request.getProperties());
        entity.setExtraProperties(request.getExtraProperties());
        entity.setResourcePath(request.getResourcePath());
    }

    public void deleteItem(Long id){
        validateId(id);
        repository.deleteById(id);
        boxService.removeItemFromBox(id);
    }

    public void manageQuantity(Long id, ItemQuantityRequest request){
        if(request.getChange() == null){
            throw new ApplicationException("change type must not be null", HttpStatus.BAD_REQUEST);
        }
        if(request.getNumber() == null){
            throw new ApplicationException("Quantity change number must not be null", HttpStatus.BAD_REQUEST);
        }
        validateId(id);
        if(request.getChange() == ADD) addQuantity(id, request.getNumber());
        if(request.getChange() == REMOVE) removeQuantity(id, request.getNumber());
        if(request.getChange() == SET) setQuantity(id, request.getNumber());
    }

    private void addQuantity(Long id, Integer add){
        if(add < 0){
            throw new ApplicationException("Quantity to add must be bigger than 0", HttpStatus.BAD_REQUEST);
        }
        ItemEntity entity = repository.findById(id).get();
        entity.setQuantity(entity.getQuantity() + add);
        repository.save(entity);
    }

    private void removeQuantity(Long id, Integer remove){
        if(remove < 0){
            throw new ApplicationException("Quantity to remove must be bigger than 0", HttpStatus.BAD_REQUEST);
        }
        ItemEntity entity = repository.findById(id).get();
        if(entity.getQuantity() - remove < 0){
            throw new ApplicationException("Item count cannot be below 0", HttpStatus.BAD_REQUEST);
        }
        entity.setQuantity(entity.getQuantity() - remove);
        repository.save(entity);
    }

    private void setQuantity(long id, Integer newQuantity){
        if(newQuantity < 0){
            throw new ApplicationException("Quantity must be bigger than 0", HttpStatus.BAD_REQUEST);
        }
        ItemEntity entity = repository.findById(id).get();
        entity.setQuantity(newQuantity);
        repository.save(entity);
    }

    private void validateId(Long id){
        if(!repository.findById(id).isPresent()){
            throw new ApplicationException("Item id does not exist", HttpStatus.NOT_FOUND);
        }
    }

}
