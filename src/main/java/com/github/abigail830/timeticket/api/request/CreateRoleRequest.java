package com.github.abigail830.timeticket.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "创建角色请求")
public class CreateRoleRequest {

    @ApiModelProperty(value = "本尊OpenId", example = "orQ0R5dfGenexrRFU-74p_l3iXes")
    String ownerOpenId;

    @ApiModelProperty(value = "指派人角色", example = "老公")
    String assigneeRole;
}
