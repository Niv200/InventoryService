package me.niv.inventoryservice.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.datain.BoxAction;
import me.niv.inventoryservice.datain.BoxItemRequest;
import me.niv.inventoryservice.datain.BoxRequest;
import me.niv.inventoryservice.dataout.BoxResponse;
import me.niv.inventoryservice.dataout.ItemResponse;
import me.niv.inventoryservice.entity.BoxEntity;
import me.niv.inventoryservice.entity.ItemEntity;
import me.niv.inventoryservice.exception.ApplicationException;
import me.niv.inventoryservice.repository.BoxRepository;
import me.niv.inventoryservice.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository repository;
    private final ItemRepository itemRepository;

    public List<BoxResponse> getAllBoxes(){
        List<BoxEntity> boxes = repository.findAll();

        return boxes.stream().map(box -> mapBoxResponse(box)).collect(toList());
    }

    public BoxResponse getBox(Long boxId){
        validateBoxId(boxId);
        BoxEntity box = repository.findById(boxId).get();

        return mapBoxResponse(box);
    }

    public void editBox(Long boxId, BoxRequest request){
        validateBoxId(boxId);
        BoxEntity boxEntity = repository.findById(boxId).get();

        boxEntity.setName(request.getName());
        boxEntity.setContents(request.getContents());
        boxEntity.setLocation(request.getLocation());
        boxEntity.setExtraInformation(request.getExtraInformation());
        boxEntity.setResourcePath(request.getResourcePath());

        repository.save(boxEntity);
    }

    public Long createBox(BoxRequest request){
    if(repository.existsByName(request.getName())){
        throw new ApplicationException(format("Box with name %s already exist", request.getName()), HttpStatus.CONFLICT);
    }
        BoxEntity boxEntity = BoxEntity.builder()
                .name(request.getName())
                .contents(request.getContents())
                .location(request.getLocation())
                .extraInformation(request.getExtraInformation())
                .resourcePath(request.getResourcePath())
                .build();

        return repository.save(boxEntity).getId();
    }

    public void handleItemUpdate(Long boxId, BoxItemRequest request){
        if(request.getAction() == null){
            throw new ApplicationException("No action provided", HttpStatus.BAD_REQUEST);
        }
        if(request.getAction() == BoxAction.ADD){
            addItemToBox(boxId, request.getItemId());
        }
        if(request.getAction() == BoxAction.REMOVE){
            removeItemFromBox(boxId, request.getItemId());
        }
    }

    public void removeItemFromBox(Long boxId, Long itemId){
        validateBoxId(boxId);
        validateItemId(itemId);

        BoxEntity box = repository.findById(boxId).get();
        ItemEntity item = itemRepository.findById(itemId).get();

        if(box.getItems() == null){
            box.setItems(Collections.emptySet());
        }

        if(!box.getItems().contains(item)){
            throw new ApplicationException(format("Box with id %s does not contain item id %s", boxId, itemId), HttpStatus.NOT_FOUND);
        }
        box.getItems().remove(item);
        repository.save(box);
    }

    public void removeItemFromBox(Long itemId){
        validateItemId(itemId);

        ItemEntity item = itemRepository.findById(itemId).get();

        List<BoxEntity> boxes = repository.findAll()
                .stream()
                .filter(box -> box.getItems().contains(item))
                .collect(toList());

        boxes.forEach(box -> box.getItems().remove(item));
        repository.saveAll(boxes);
    }

    public void addItemToBox(Long boxId, Long itemId){
        validateBoxId(boxId);
        validateItemId(itemId);

        BoxEntity box = repository.findById(boxId).get();
        ItemEntity item = itemRepository.findById(itemId).get();

        if(box.getItems() == null){
            box.setItems(Collections.emptySet());
        }

        if(box.getItems().contains(item)){
            throw new ApplicationException(format("Box with id %s already contains item id %s", boxId, itemId), HttpStatus.NOT_FOUND);
        }
        box.getItems().add(item);
        repository.save(box);
    }

    public void deleteBox(Long boxId){
        validateBoxId(boxId);

        repository.deleteById(boxId);
    }

    private void validateBoxId(Long id){
        if(!repository.findById(id).isPresent()){
            throw new ApplicationException("Box id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    private void validateItemId(Long id){
        if(!repository.findById(id).isPresent()){
            throw new ApplicationException("Item id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    private BoxResponse mapBoxResponse(BoxEntity entity){
        return BoxResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .contents(entity.getContents())
                .extraInformation(entity.getExtraInformation())
                .location(entity.getLocation())
                .resourcePath(entity.getResourcePath())
                .items(entity.getItems()
                        .stream()
                        .map(item -> ItemResponse.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .type(item.getType().getType())
                                .quantity(item.getQuantity())
                                .properties(item.getProperties())
                                .extraProperties(item.getExtraProperties())
                                .location(item.getLocation())
                                .resourcePath(item.getResourcePath())
                                .build()).collect(toList()))
                .build();
    }

}
