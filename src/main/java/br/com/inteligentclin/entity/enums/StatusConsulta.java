package br.com.inteligentclin.entity.enums;

public enum StatusConsulta {

    PENDENTE("pendente", 001),
    CONFIRMADA("confirmada", 002),
    REALIZADA("realizada", 003),
    CANCELADA("cancelada", 004);

    private String status;
    private int codigo;

    StatusConsulta(String status, int codigo) {
        this.status = status;
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public int getCodigo() {
        return codigo;
    }
}
