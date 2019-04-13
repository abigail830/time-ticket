package com.github.abigail830.timeticket.infrastructure.ticket;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;

import java.util.List;

public interface RawTicketIndexRepository {
    void addTicketIndex(TicketIndex ticketIndex);

    void updateAssigneeRoleOfTicketIndex(Integer id, String assigneeRole);

    void removeTicketIndex(Integer ticketIndexId);

    List<TicketIndex> getTicketIndexByOwnerOpenIdOrderBySumDuration(String ownerOpenId);
}
