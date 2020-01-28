package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @JsonProperty("custom_url")
    private String customUrl;
    private String firstName;
    private String lastName;
}
