package com.dbc.curriculo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageDTO<T> {
    private Long total;
    private Integer paginas;
    private Integer pagina;
    private Integer quantidade;
    private List<T> content;
}
