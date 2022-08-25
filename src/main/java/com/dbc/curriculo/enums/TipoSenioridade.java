package com.dbc.curriculo.enums;

import java.util.Arrays;

public enum TipoSenioridade {
    JUNIOR("junior"),
    PLENO("pleno"),
    SENIOR("senior"),
    ESPECIALISTA("especilista");
    
    private String tipoSenioridade;
    
    TipoSenioridade(String tipoSenioridade) {
        this.tipoSenioridade = tipoSenioridade;
    }

    // FIXME metodo nao usado... e sem sentido esse nome para metodos
    public static TipoSenioridade tipo (String tipoMensagem){
        return Arrays.stream(TipoSenioridade.values())
                .filter(tp -> tp.getTipoDeMensagem().equals(tipoMensagem))
                .findFirst()
                .get();
    }
    
    public String getTipoDeMensagem() {
        return tipoSenioridade;
    }
}
