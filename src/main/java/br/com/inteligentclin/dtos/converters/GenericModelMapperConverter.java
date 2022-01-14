package br.com.inteligentclin.dtos.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

//    public List<M> convertListEntityToModelDTO(List<E> listEntity, Class<M> modelClassDestination) {
//
//        List<M> listSummaryDTO = new ArrayList<>();
//
//        listEntity.stream().forEach(entity -> {
//            M modelDTO = mapEntityToModelDTO(entity, modelClassDestination);
//            listSummaryDTO.add(modelDTO);
//        });
//        return listSummaryDTO;
//    }

//    public List<S> convertListEntityToSummaryDTO(List<E> listEntity, Class<S> summaryClassDestination) {
//
//        List<S> listSummaryDTO = new ArrayList<>();
//
//        listEntity.stream().forEach(entity -> {
//            S summaryDTO = mapEntityToSummaryDTO(entity, summaryClassDestination);
//            listSummaryDTO.add(summaryDTO);
//        });
//        return listSummaryDTO;
//    }


//    public Page<S> convertPageEntityToSummaryDTO(Page<E> entityPage, Pageable pageable, Class<S> summaryClassDestination) {
//        List<S> listSummaryDTO =
//                convertListEntityToSummaryDTO(entityPage.getContent(), summaryClassDestination);
//
//        return new PageImpl<>(listSummaryDTO, pageable, entityPage.getTotalElements());
//    }

}
