package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.inteligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.inteligentclin.entity.Dentista;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class DentistaModelMapperConverter extends GenericModelMapperConverter<Dentista, DentistaModelDTO,
        DentistaSummaryDTO> {


//    public DentistaModelDTO mapDentistaParaDentistaModelDTO(Dentista dentista) {
//        return modelMapper.map(dentista, DentistaModelDTO.class);
//    }
//
//    public Dentista mapDentistaModelDTOParaDentista(DentistaModelDTO dentistaModelDTO) {
//        return modelMapper.map(dentistaModelDTO, Dentista.class);
//    }
//
//    public DentistaSummaryDTO mapDentistaParaDentististaSummaryDTO(Dentista dentista) {
//        return modelMapper.map(dentista, DentistaSummaryDTO.class);
//    }
//
//    public List<DentistaSummaryDTO> convertListDentistasParaDentistasSummaryDTO(List<Dentista> dentistas) {
//
//        List<DentistaSummaryDTO> dentistasSummaryDTO = new ArrayList<>();
//
//        dentistas.stream().forEach(dentista -> {
//            DentistaSummaryDTO dentistaSummaryDTO = mapDentistaParaDentististaSummaryDTO(dentista);
//            dentistasSummaryDTO.add(dentistaSummaryDTO);
//        });
//        return dentistasSummaryDTO;
//    }
//
//    public Page<DentistaSummaryDTO> convertPageDentistasParaDentistasSummaryDTO(Page<Dentista> dentistasPage,
//                                                                                Pageable pageable) {
//        List<DentistaSummaryDTO> dentistasSummaryDTO =
//                convertListDentistasParaDentistasSummaryDTO(dentistasPage.getContent());
//
//        return new PageImpl<>(dentistasSummaryDTO, pageable, dentistasPage.getTotalElements());
//    }

//    public Page<DentistaSummaryDTO> convertPageDentistasParaDentistasSummuryDTO(Pageable pageable,
//                                                                                List<Dentista> dentistas) {
//        List<DentistaSummaryDTO> dentistasSummaryDTO =
//                    convertListDentistasParaDentistasSummuryDTO(dentistas);
//
//
//        return new PageImpl<>(dentistasSummaryDTO, pageable, dentistasPage.getTotalElements());
//    }

}
