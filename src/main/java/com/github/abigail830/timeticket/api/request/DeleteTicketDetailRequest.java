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
@ApiModel(description = "删除时间小票请求")
public class DeleteTicketDetailRequest {

    @ApiModelProperty(value = "Ticket Index Id", example = "1")
    Integer ticketIndexId;

    @ApiModelProperty(value = "Ticket Id", example = "1")
    Integer ticketId;

}
