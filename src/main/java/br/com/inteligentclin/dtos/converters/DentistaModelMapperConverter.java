package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.dentistaDTO.DentistaModelDTO;
import br.com.inteligentclin.dtos.dentistaDTO.DentistaSummaryDTO;
import br.com.inteligentclin.entity.Dentista;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class DentistaModelMapperConverter {

    @Autowired
    private ModelMapper modelMapper;

    public DentistaModelDTO mapDentistaParaDentistaModelDTO(Dentista dentista) {
        return modelMapper.map(dentista, DentistaModelDTO.class);
    }

    public Dentista mapDentistaModelDTOParaDentista(DentistaModelDTO dentistaModelDTO) {
        return modelMapper.map(dentistaModelDTO, Dentista.class);
    }

    public DentistaSummaryDTO mapDentistaParaDentististaSummaryDTO(Dentista dentista) {
        return modelMapper.map(dentista, DentistaSummaryDTO.class);
    }

    public List<DentistaSummaryDTO> convertListDentistasParaDentistasSummaryDTO(List<Dentista> dentistas) {

        List<DentistaSummaryDTO> dentistasSummaryDTO = new ArrayList<>();

        dentistas.stream().forEach(dentista -> {
            DentistaSummaryDTO dentistaSummaryDTO = mapDentistaParaDentististaSummaryDTO(dentista);
            dentistasSummaryDTO.add(dentistaSummaryDTO);
        });
        return dentistasSummaryDTO;
    }

    public Page<DentistaSummaryDTO> convertPageDentistasParaDentistasSummaryDTO(Page<Dentista> dentistasPage,
                                                                                Pageable pageable) {
        List<DentistaSummaryDTO> dentistasSummaryDTO =
                convertListDentistasParaDentistasSummaryDTO(dentistasPage.getContent());

        return new PageImpl<>(dentistasSummaryDTO, pageable, dentistasPage.getTotalElements());
    }

//    public Page<DentistaSummaryDTO> convertPageDentistasParaDentistasSummuryDTO(Pageable pageable,
//                                                                                List<Dentista> dentistas) {
//        List<DentistaSummaryDTO> dentistasSummaryDTO =
//                    convertListDentistasParaDentistasSummuryDTO(dentistas);
//
//
//        return new PageImpl<>(dentistasSummaryDTO, pageable, dentistasPage.getTotalElements());
//    }

}
