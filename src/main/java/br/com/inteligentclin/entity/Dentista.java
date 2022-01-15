package br.com.inteligentclin.entity;

import br.com.inteligentclin.entity.enums.Especialidade;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_DENTISTA")
@SequenceGenerator(name = "dentista", sequenceName = "SQ_TB_DENTISTA", allocationSize = 1)
public class Dentista extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dentista_id")
    @GeneratedValue(generator = "dentista", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String matricula;

    //    @JsonManagedReference
//    @JsonIgnore
    @OneToMany(mappedBy = "dentista")
    @LazyCollection (LazyCollectionOption.FALSE)
    private Set<Consulta> consultas = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Especialidade.class)
    @Column(name = "dentista_especialidades")
    @JoinTable(name = "dentista_especialidades")
    @Enumerated(value = EnumType.STRING)
    @LazyCollection (LazyCollectionOption.FALSE)
    private List<Especialidade> especialidades;

    //    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "dentista")
    private Set<Prontuario> prontuarios = new HashSet<>();

}
