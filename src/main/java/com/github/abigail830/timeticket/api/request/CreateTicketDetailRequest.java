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
@ApiModel("创建时间事件请求")
public class CreateTicketDetailRequest {

    @ApiModelProperty(value = "Time Index Id", example = "1")
    Integer timeIndexId;

    @ApiModelProperty(value = "event", example = "陪我吃饭")
    String event;

    @ApiModelProperty(value = "时间小票时间(ms)", example = "36000000")
    private Long duration;

}
