package br.com.intelligentclin.entity.enums;

public enum Sexo {

    MASCULINO("masculino", 1),
    FEMININO("feminino", 2),
    OUTRO("outro", 3),
    NAO_INFORMADO("nao informado", 4);

    private final String name;
    private final int codigo;

    Sexo(String name, int codigo) {
        this.name = name;
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public int getCodigo() {
        return codigo;
    }
}
