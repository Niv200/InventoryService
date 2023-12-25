package me.niv.inventoryservice.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.niv.inventoryservice.datain.TypeModifyRequest;
import me.niv.inventoryservice.datain.TypeRequest;
import me.niv.inventoryservice.dataout.TypeResponse;
import me.niv.inventoryservice.entity.TypeEntity;
import me.niv.inventoryservice.exception.ApplicationException;
import me.niv.inventoryservice.repository.TypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository repository;

    public Long createType(TypeRequest request) {
        if (repository.existsByType(request.getType())) {
            throw new ApplicationException("Type already exists.", HttpStatus.CONFLICT);
        }
        TypeEntity entity = TypeEntity.builder().type(request.getType()).build();

        Long id = repository.save(entity).getId();
        return id;
    }

    @Transactional
    public void deleteType(String type) {
        if (!repository.existsByType(type)) {
            throw new ApplicationException(format("Type %s does not exists.", type), HttpStatus.NOT_FOUND);
        }
        TypeEntity entity = repository.findByType(type).get();
        repository.delete(entity);
    }

    public List<TypeResponse> getAllTypes() {
        return repository.findAll().stream().map(entity ->
                        TypeResponse.builder().type(entity.getType()).id(entity.getId()).build()).collect(toList());
    }

    public Long editType(TypeModifyRequest request){
        Optional<TypeEntity> type = repository.findByType(request.getOldType());
        if(type.isEmpty()){
            throw new ApplicationException(format("Type %s does not exists.", request.getOldType()), HttpStatus.NOT_FOUND);
        }
        Optional<TypeEntity> newType = repository.findByType(request.getType());
        if(!newType.isEmpty()){
            throw new ApplicationException(format("Type %s already exists.", request.getType()), HttpStatus.CONFLICT);
        }
        TypeEntity entity = type.get();
        entity.setType(request.getType());
        return repository.save(entity).getId();
    }

}
