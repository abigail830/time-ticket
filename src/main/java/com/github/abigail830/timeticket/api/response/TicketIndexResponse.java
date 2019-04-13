package com.github.abigail830.timeticket.api.response;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "TimeIndex首页列表")
public class TicketIndexResponse {

    @ApiModelProperty(value = "Time Index Id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "本尊OpenId", example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String ownerOpenId;

    @ApiModelProperty(value = "指派人角色", example = "老公")
    private String assigneeRole;

    @ApiModelProperty(value = "指派人OpenId", example = "oEmJ75YWmBSDgyz4KLi_yGL8MBV4ue")
    private String assigneeOpenId;

    private String assigneeAvatarUrl;

    @ApiModelProperty(value = "时间小票累计时间(ms)", example = "36000000")
    private Long sumDuration;


    public static TicketIndexResponse fromTicketIndex(TicketIndex ticketIndex) {

        return TicketIndexResponse.builder()
                .id(ticketIndex.getId())
                .ownerOpenId(ticketIndex.getOwnerOpenId())
                .assigneeRole(ticketIndex.getAssigneeRole())
                .assigneeOpenId(ticketIndex.getAssignee() == null ? "" : ticketIndex.getAssignee().getOpenId())
                .assigneeAvatarUrl(ticketIndex.getAssignee() == null ? "" : ticketIndex.getAssignee().getAvatarUrl())
                .sumDuration(ticketIndex.getSumDuration())
                .build();
    }

}
