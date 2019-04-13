package com.github.abigail830.timeticket.api.response;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Time Detail")
public class TicketDetailResponse {

    private Integer id;
    private Integer ticket_index_id;
    private Timestamp createTime;
    private String status;
    private String event;
    private Long duration;

    public static TicketDetailResponse fromTicketDetail(Ticket ticket) {
        return TicketDetailResponse.builder()
                .createTime(ticket.getCreateTime())
                .status(ticket.getEventStatus())
                .event(ticket.getEvent())
                .duration(ticket.getDuration())
                .id(ticket.getId())
                .ticket_index_id(ticket.getTicketIndexId())
                .build();
    }
}
