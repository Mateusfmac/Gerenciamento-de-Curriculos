package com.dbc.curriculo.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultError {

    @Schema(example = "2022-08-25T04:03:52.398+0000")
    private Date timestamp;
    @Schema(example = "400 || 401 || 404 || 500")
    private Integer status;

    private List<String> errors;

}