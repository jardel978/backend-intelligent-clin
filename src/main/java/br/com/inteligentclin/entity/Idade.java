package br.com.inteligentclin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
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
