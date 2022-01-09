package br.com.inteligentclin.entity.enums;

public enum Especialidade {

    ODONTOPEDIATRIA("odontopediatria", "ODT001"),
    IMPLANTODONTIA("implantodontia", "ODT002"),
    RADIOLOGIA("radiologia", "ODT003"),
    ORTODONTIA("ortodontia", "ODT004"),
    ENDODONTIA("endodontia", "ODT005"),
    DENTISTICA("dentistica", "ODT006"),
    CLINICO("clinico", "ODT007"),
    PROTESE("protese", "ODT008");

    private final String nome;
    private final String codigo;

    Especialidade(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }
}
