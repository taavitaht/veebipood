package ee.taavi.veebipood.dto;
import lombok.Data;

// Data Transfer Object - entity muudetud kujul
// Model - päringutes kasutatav mudel

@Data
public class PersonDTO {
    private String firstName;
    private String lastName;
}
