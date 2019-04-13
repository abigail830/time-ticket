package com.github.abigail830.timeticket.api.response;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "TimeDetail列表")
public class TicketDetailListResponse {

    @ApiModelProperty(value = "Ticket Index Id", example = "1")
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

    private List<TicketDetailResponse> ticketDetailResponseList;

    public static TicketDetailListResponse fromTicketIndex(TicketIndex ticketIndex) {

        if (ticketIndex == null)
            return new TicketDetailListResponse();

        List<TicketDetailResponse> ticketResponses = new ArrayList<>();
        if (ticketIndex.getTicketList() != null && !ticketIndex.getTicketList().isEmpty()) {
            ticketResponses = ticketIndex.getTicketList().stream()
                    .map(ticket -> TicketDetailResponse.fromTicketDetail(ticket))
                    .collect(Collectors.toList());
        }

        return TicketDetailListResponse.builder()
                .id(ticketIndex.getId())
                .ownerOpenId(ticketIndex.getOwnerOpenId())
                .assigneeRole(ticketIndex.getAssigneeRole())
                .assigneeOpenId(ticketIndex.getAssignee() == null ? "" : ticketIndex.getAssignee().getOpenId())
                .assigneeAvatarUrl(ticketIndex.getAssignee() == null ? "" : ticketIndex.getAssignee().getAvatarUrl())
                .sumDuration(ticketIndex.getSumDuration())
                .ticketDetailResponseList(ticketResponses)
                .build();
    }

}
