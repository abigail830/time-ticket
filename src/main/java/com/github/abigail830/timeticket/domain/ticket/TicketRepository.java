package com.github.abigail830.timeticket.domain.ticket;

import java.util.List;

public interface TicketRepository {

    void addTicketIndex(TicketIndex ticketIndex);

    void updateAssigneeRoleOfTicketIndex(Integer id, String assigneeRole);

    void removeTicketIndex(Integer ticketIndexId);

    List<TicketIndex> getTicketIndexByOwnerOpenIdOrderBySumDuration(String ownerOpenId);

    void addTicketToIndex(Integer ticketIndexId, Ticket ticket);

    void updateTicket(Integer ticketId, Ticket ticket);

    void removeTicket(Integer ticketId);

    List<Ticket> getAllTickets();

    TicketIndex getTicketDetailListByIndexId(Integer timeIndexId);
}
