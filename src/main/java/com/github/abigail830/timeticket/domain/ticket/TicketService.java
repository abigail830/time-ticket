package com.github.abigail830.timeticket.domain.ticket;

import com.github.abigail830.timeticket.infrastructure.InfraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


public class TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public void addTicketIndex(String ownerOpenId, String assigneeRole) {
        final TicketIndex newIndex = new TicketIndex(ownerOpenId, assigneeRole);
        ticketRepository.addTicketIndex(newIndex);
    }

    public List<TicketIndex> getTicketIndexListByOwner(String ownerOpenId) {
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public void updateTicketIndex(Integer ticketIndexId, String newAssigneeRole) {
        ticketRepository.updateAssigneeRoleOfTicketIndex(ticketIndexId, newAssigneeRole);
    }

    public void deleteTicketIndex(Integer ticketIndexId) {
        ticketRepository.removeTicketIndex(ticketIndexId);
    }

    public void addTicketDetail(Integer timeIndexId, String event, Long duration) {
        final Ticket newTicket = new Ticket(timeIndexId, event, duration);
        try {
            ticketRepository.addTicketToIndex(newTicket);
        } catch (InfraException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Exception when repository processing: " + ex.getMessage());
        }
    }

    public void updateTicketDetail(Integer timeIndexId, Integer ticketId, String event, Long duration) {
        final Ticket newTicket = new Ticket(timeIndexId, ticketId, event, duration);
        ticketRepository.updateTicket(newTicket);
    }

    public boolean isTicketAcceptUpdate(Integer ticketId) {
        final Optional<Ticket> ticketById = ticketRepository.getTicketById(ticketId);
        if (ticketById.isPresent() && ticketById.get().isAllowUpdate())
            return true;
        else
            return false;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    public TicketIndex getTicketDetailListByIndexId(Integer timeIndexId) {
        return ticketRepository.getTicketDetailListByIndexId(timeIndexId);
    }

    public void deleteTicket(Integer ticketId) {
        ticketRepository.removeTicket(ticketId);
    }


}
