package com.github.abigail830.timeticket.infrastructure.repository.po;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TicketPO {

    private Integer id;
    private Integer ticketIndexId;

    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    private String eventStatus;
    private String event;
    private Long duration;


    public Ticket toDomain() {
        return new Ticket(id, ticketIndexId, createTime, lastUpdateTime, eventStatus, event, duration);
    }


}
