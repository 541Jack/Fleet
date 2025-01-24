package com.fleet.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "entity for registration form")
public class RegisterFormDTO {
    @ApiModelProperty(value = "username", required = true)
    private String username;
    @ApiModelProperty(value = "password", required = true)
    private String password;
    @ApiModelProperty(value = "email", required = true)
    private String email;
    @ApiModelProperty(value = "university_id")
    private Long university_id;
}
