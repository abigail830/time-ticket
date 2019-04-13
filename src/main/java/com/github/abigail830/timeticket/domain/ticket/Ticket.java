package com.github.abigail830.timeticket.domain.ticket;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
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

    public Ticket(Integer ticketIndexId, Integer id, String event, Long duration) {
        this.ticketIndexId = ticketIndexId;
        this.id = id;
        this.event = event;
        this.duration = duration;
    }

    public boolean isAllowUpdate() {
        if (this.eventStatus.equals(Ticket.TICKET_STATUS.NEW.name())) {
            return true;
        } else {
            return false;
        }
    }

    public enum TICKET_STATUS {
        NEW,
        CONFIRMED
    }


}
