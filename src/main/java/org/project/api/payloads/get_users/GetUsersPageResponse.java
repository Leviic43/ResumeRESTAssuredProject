package org.project.api.payloads.get_users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUsersPageResponse {

    @JsonProperty("per_page")
    private Integer perPage;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("data")
    private List<DataItem> data;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("support")
    private Support support;
}