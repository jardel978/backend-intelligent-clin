package br.com.intelligentclin.entity;

import br.com.intelligentclin.entity.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIO")
@SequenceGenerator(name = "usuario", sequenceName = "SQ_TB_USUARIO", allocationSize = 1)
public class Usuario extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(generator = "usuario", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//ignorar senha no retorno
    private String senha;

    //    @JsonManagedReference
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Consulta> consultas = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Cargo cargo;
}
