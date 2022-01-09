package br.com.inteligentclin.dtos.converters;

import br.com.inteligentclin.dtos.pacienteDTO.PacienteModelDTO;
import br.com.inteligentclin.entity.Paciente;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class PacienteModelMapperConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PacienteModelDTO mapPacienteParaPacienteModelDTO(Paciente paciente) {
        return modelMapper.map(paciente, PacienteModelDTO.class);
    }

    public Paciente mapPacienteModelDTOParaPaciente(PacienteModelDTO pacienteDTO) {
        return modelMapper.map(pacienteDTO, Paciente.class);
    }

//    public Paciente mapPacienteModelDTOParaPacienteSkippingIdade(PacienteModelDTO pacienteDTO) {
//        return modelMapper.typeMap(PacienteModelDTO.class, Paciente.class)
//                .addMappings(mappr -> mappr.skip(Paciente::setIdade)).map(pacienteDTO);
//    }
}
