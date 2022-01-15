package br.com.inteligentclin.entity.enums;

public enum Cargo {

    ESTAGIARIO("estagiario", 1),
    ATENDENTE("atendente", 2),
    GERENTE("gerente", 3),
    DIRETOR("diretor", 4);

    private final String name;
    private final int codigo;

    Cargo(String name, int codigo) {
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
