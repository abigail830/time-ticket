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
@ApiModel(description = "删除角色请求")
public class DeleteRoleRequest {

    @ApiModelProperty(value = "Ticket Index Id", example = "1")
    Integer ticketIndexId;

    @ApiModelProperty(value = "本尊OpenId", example = "orQ0R5dfGenexrRFU-74p_l3iXes")
    String ownerOpenId;

}
