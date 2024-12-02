package co.edu.ufps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaBase {
    private String status;
    private String message;
    private Object data;

    public RespuestaBase(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
