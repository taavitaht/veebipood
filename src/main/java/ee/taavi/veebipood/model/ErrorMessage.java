package ee.taavi.veebipood.model;

import lombok.Data;

import java.util.Date;

@Data // @Getter, @Setter, @Noargsconstructor included
public class ErrorMessage {
    private String message;
    private int status;
    private Date timestamp;
}
