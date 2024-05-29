package org.project.api.payloads.get_users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataItem {

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("email")
    private String email;

}