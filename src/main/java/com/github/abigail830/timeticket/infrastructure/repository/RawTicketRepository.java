package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.Ticket;

import java.util.List;
import java.util.Optional;

public interface RawTicketRepository {

    List<Ticket> getAllTickets();

    Optional<Ticket> getTicketById(Integer ticketId);

    void addTicketToIndex(Ticket ticket);

    void updateTicket(Ticket ticket);

    void removeTicket(Integer ticketId);

    void removeTicketByIndexId(Integer ticketIndexId);

    void updateStatus(Integer ticketId, String status);
}
