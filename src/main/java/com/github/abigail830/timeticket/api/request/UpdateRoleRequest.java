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
@ApiModel("更新角色请求")
public class UpdateRoleRequest {

    @ApiModelProperty(value = "Ticket Index Id", example = "1")
    Integer ticketIndexId;

    @ApiModelProperty(value = "本尊OpenId", example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    String ownerOpenId;

    @ApiModelProperty(value = "更新的指派人角色", example = "老公")
    String newAssigneeRole;
}
