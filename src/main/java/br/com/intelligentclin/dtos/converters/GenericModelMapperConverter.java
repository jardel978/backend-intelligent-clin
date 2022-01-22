package br.com.intelligentclin.dtos.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenericModelMapperConverter<E, M, S> {
//E: Entity, M: ModelDTO, S: SummaryDTO

    @Autowired
    private ModelMapper modelMapper;


    public M mapEntityToModelDTO(E entity, Class<M> modelClassDestination) {
        return modelMapper.map(entity, modelClassDestination);
    }

    public E mapModelDTOToEntity(M modelDTO, Class<E> entityClassDestination) {
        return modelMapper.map(modelDTO, entityClassDestination);
    }

    public S mapEntityToSummaryDTO(E entity, Class<S> summaryClassDestination) {
        return modelMapper.map(entity, summaryClassDestination);
    }

    public List<M> convertListEntityToModelDTO(List<E> listEntity, Class<M> modelClassDestination) {

        List<M> listSummaryDTO = new ArrayList<>();

        listEntity.stream().forEach(entity -> {
            M modelDTO = mapEntityToModelDTO(entity, modelClassDestination);
            listSummaryDTO.add(modelDTO);
        });
        return listSummaryDTO;
    }

}
