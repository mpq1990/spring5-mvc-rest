package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @JsonProperty("custom_url")
    private String customUrl;

    @ApiModelProperty(value = "This is the name")
    private String name;
}
