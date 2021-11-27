package com.clinicasorridente.pifinalbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIO")
@SequenceGenerator(name = "usuario", sequenceName = "SQ_TB_USUARIO", allocationSize = 1)
public class Usuario implements Serializable {

    @Id
    @Column(name = "usuario_id")
@GeneratedValue(generator = "usuario", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "usuario_nome")
    @NotNull(message = "Informe o nome do usuário a ser cadastrado.")
    private String nome;

    @Column(name = "usuario_email")
    @NotNull(message = "É necessário informar o email do novo usuário para o cadastro.")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message =
            "Formato de email inválido.")
    private String email;

    @Column(name = "usuario_senha")
    @Size(min = 6, max = 15, message = "Sua senha deve ter no mínimo 6 dígitos.")
    private String senha;

    @Column(name = "usuario_acesso")
    @Enumerated(value = EnumType.STRING)
    private CategoriaUsuario acesso;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Consulta> consultas = new HashSet<>();

}
