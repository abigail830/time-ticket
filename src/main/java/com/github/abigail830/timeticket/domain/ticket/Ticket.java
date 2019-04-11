package com.github.abigail830.timeticket.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private Integer id;
    private Integer ticketIndexId;

    private Timestamp createTime;
    private Timestamp lastUpdateTime;

    private String eventStatus;
    private String event;
    private Long duration;

    public Ticket(Integer ticketIndexId, String event, Long duration) {
        this.ticketIndexId = ticketIndexId;
        this.event = event;
        this.eventStatus = TICKET_STATUS.NEW.name();
        this.duration = duration;
    }

    private enum TICKET_STATUS {
        NEW,
        CONFIRMED
    }


}
