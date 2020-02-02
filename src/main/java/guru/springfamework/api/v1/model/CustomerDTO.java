package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @JsonProperty("custom_url")
    private String customUrl;
    @ApiModelProperty(value = "This is the first name")
    private String firstName;

    @ApiModelProperty(value = "This is the last name")
    private String lastName;
}
