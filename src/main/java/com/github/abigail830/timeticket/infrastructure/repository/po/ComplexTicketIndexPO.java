package com.github.abigail830.timeticket.infrastructure.repository.po;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.user.User;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ComplexTicketIndexPO {

    private Integer index_id;
    private String ownerOpenId;
    private String assigneeOpenId;
    private String assigneeRole;
    private Timestamp indexCreateTime;
    private Long sumDuration;

    private Integer ticket_id;
    private Integer ticketIndexId;
    private Timestamp ticketCreateTime;
    private Timestamp ticketLastUpdateTime;
    private String eventStatus;
    private String event;
    private Long duration;

    private Integer userId;
    private String openId;
    private String avatarUrl;

    public Ticket toTicketDomain() {
        return new Ticket(ticket_id, ticketIndexId, ticketCreateTime, ticketLastUpdateTime, eventStatus, event, duration);
    }

    public TicketIndex toRawTicketIndexDomain() {
        User assignee = User.builder().id(userId).openId(openId).avatarUrl(avatarUrl).build();
        return new TicketIndex(index_id, ownerOpenId, assignee, assigneeRole, sumDuration);
    }
}
