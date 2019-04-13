package com.github.abigail830.timeticket.domain.ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    void addTicketIndex(TicketIndex ticketIndex);

    void updateAssigneeRoleOfTicketIndex(Integer id, String assigneeRole);

    void removeTicketIndex(Integer ticketIndexId);

    List<TicketIndex> getTicketIndexByOwnerOpenIdOrderBySumDuration(String ownerOpenId);

    void addTicketToIndex(Ticket ticket);

    void updateTicket(Ticket ticket);

    void removeTicket(Integer ticketId);

    List<Ticket> getAllTickets();

    TicketIndex getTicketDetailByIndexId(Integer timeIndexId);

    Optional<Ticket> getTicketById(Integer ticketId);
}
