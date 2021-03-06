package br.com.intelligentclin.entity;

import br.com.intelligentclin.entity.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PACIENTE")
@SequenceGenerator(name = "paciente", sequenceName = "SQ_TB_PACIENTE", allocationSize = 1)
public class Paciente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "paciente_id")
    @GeneratedValue(generator = "paciente", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Transient
    private Idade idade;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey = @ForeignKey(name = "fk_endereco"))
    private Endereco endereco;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Consulta> consultas = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "prontuario_id", foreignKey = @ForeignKey(name = "fk_prontuario_paciente"))
    private Prontuario prontuario;

    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;

}
