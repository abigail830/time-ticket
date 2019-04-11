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
@ApiModel("创建角色请求")
public class CreateRoleRequest {

    @ApiModelProperty(value = "本尊OpenId", example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    String ownerOpenId;

    @ApiModelProperty(value = "指派人角色", example = "老公")
    String assigneeRole;
}
