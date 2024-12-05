package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RespuestaBase {
    private String status;
    private String message;
    private Object data;
}
