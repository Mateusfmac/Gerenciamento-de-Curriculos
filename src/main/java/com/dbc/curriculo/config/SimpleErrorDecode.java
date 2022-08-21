package com.dbc.curriculo.config;


import com.dbc.curriculo.exceptions.ApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class SimpleErrorDecode implements ErrorDecoder{

    public Exception decode(String methodKey, Response response) {
        Response.Body body = response.body();
        if (body == null) {
            return new UnexpectedException("Erro inesperado api.");
        }

        try {
            String bodyString = IOUtils.toString(body.asInputStream());
            if (response.status() == 400) {
                return new ApiException(bodyString);
            }
            return new ApiException("Error interno - api.");
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        }
    }
}
