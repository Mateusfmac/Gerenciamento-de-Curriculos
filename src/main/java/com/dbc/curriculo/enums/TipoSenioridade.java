package com.dbc.curriculo.enums;

public enum TipoSenioridade {
    JUNIOR("junior"),
    PLENO("pleno"),
    SENIOR("senior"),
    ESPECIALISTA("especilista");
    
    private String tipoSenioridade;
    
    TipoSenioridade(String tipoSenioridade) {
        this.tipoSenioridade = tipoSenioridade;
    }

    public String getTipoDeMensagem() {
        return tipoSenioridade;
    }
}
