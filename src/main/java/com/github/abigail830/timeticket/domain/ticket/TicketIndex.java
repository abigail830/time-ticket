package com.github.abigail830.timeticket.domain.ticket;

import com.github.abigail830.timeticket.domain.user.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TicketIndex {

    private Integer id;
    private String ownerOpenId;
    private User assignee;
    private String assigneeRole;
    private Timestamp createTime;
    private Long sumDuration;
    private List<Ticket> ticketList = new ArrayList<>();

    public TicketIndex(String ownerOpenId, String assigneeRole) {
        this.ownerOpenId = ownerOpenId;
        this.assigneeRole = assigneeRole;
        this.sumDuration = 0L;
    }

    public TicketIndex(Integer id, String ownerOpenId, String assigneeRole, Long sumDuration) {
        this.id = id;
        this.ownerOpenId = ownerOpenId;
        this.assigneeRole = assigneeRole;
        this.sumDuration = sumDuration;
    }

    public TicketIndex(Integer id, String ownerOpenId, User assignee, String assigneeRole, Long sumDuration) {
        this.id = id;
        this.ownerOpenId = ownerOpenId;
        this.assignee = assignee;
        this.assigneeRole = assigneeRole;
        this.sumDuration = sumDuration;
    }

    public void addTicket(Ticket ticket) {
        this.ticketList.add(ticket);
    }

    public void addTickets(List<Ticket> tickets) {
        this.ticketList.addAll(tickets);
    }
}
