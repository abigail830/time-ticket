package com.github.abigail830.timeticket.infrastructure.repository.po;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.user.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TicketIndexPO {

    private Integer id;
    private String ownerOpenId;
    private String assigneeOpenId;
    private String assigneeRole;
    private Timestamp createTime;
    private Long sumDuration;

    public TicketIndex toDomain() {
        User assignee = User.builder().openId(assigneeOpenId).build();
        return new TicketIndex(id, ownerOpenId, assignee, assigneeRole, createTime, sumDuration, new ArrayList<>());
    }

}
