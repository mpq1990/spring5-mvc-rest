package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @JsonProperty("custom_url")
    private String customUrl;
    private String name;
}
