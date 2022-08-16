package com.dbc.curriculo.enums;

import java.util.Arrays;

public enum StatusVagasEnum {

    RASCUNHO("Rascunho"),
    ABERTA("Aberta"),
    EM_ANDAMENTO("Em Andamento"),
    FINALIZADA("Finalizada"),
    SUSPENSA("Suspensa"),
    CANCELADA("Cancelada");

    private String status;

    StatusVagasEnum(String status) {
        this.status = status;
    }

    public static StatusVagasEnum offValue (String status){
        return Arrays.stream(StatusVagasEnum.values())
                .filter(tp -> tp.getStatus().equals(status))
                .findFirst()
                .get();
    }

    public String getStatus() {
        return status;
    }

}
