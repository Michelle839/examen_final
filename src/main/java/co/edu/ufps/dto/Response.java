// Response.java
package co.edu.ufps.dto;

public class Response {
    private String status;
    private String message;
    private Object data;

    // Constructor, Getters y Setters
    public Response(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
