package br.com.intelligentclin.entity;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Idade implements Serializable {

    private static final long serialVersionUID = 1L;

    private int dias;
    private int meses;
    private int anos;
}
