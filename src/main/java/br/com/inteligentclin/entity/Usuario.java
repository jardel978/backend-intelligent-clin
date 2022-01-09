package com.clinicasorridente.pifinalbackend.entity;

import com.clinicasorridente.pifinalbackend.entity.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "É necessário informar um login para o novo usuário.")
    @Size(min = 8, max = 15, message = "Seu endereço de login deve ter no mínimo 8 dígitos.")
    private String login;

    @Size(min = 6, max = 15, message = "Sua senha deve ter no mínimo 6 dígitos.")
    private String senha;

//    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Consulta> consultas = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Cargo cargo;
}
