package com.github.abigail830.timeticket.domain.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public void addTicketIndex(String ownerOpenId, String assigneeRole) {
        final TicketIndex newIndex = TicketIndex.builder()
                .ownerOpenId(ownerOpenId)
                .assigneeRole(assigneeRole)
                .sumDuration(0L)
                .build();
        ticketRepository.addTicketIndex(newIndex);
    }

    public List<TicketIndex> getTicketIndexListByOwner(String ownerOpenId) {
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public void updateTicketIndex(Integer ticketIndexId, String newAssigneeRole) {
        ticketRepository.updateAssigneeRoleOfTicketIndex(ticketIndexId, newAssigneeRole);
    }

    public void deleteTicketIndex(Integer ticketIndexId, String ownerOpenId) {
        ticketRepository.removeTicketIndex(ticketIndexId);
    }

    public void addTicketDetail(Integer timeIndexId, String event, Long duration) {
        final Ticket newTicket = new Ticket(timeIndexId, event, duration);
        ticketRepository.addTicketToIndex(timeIndexId, newTicket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    public TicketIndex getTicketDetailListByIndexId(Integer timeIndexId) {
        return ticketRepository.getTicketDetailListByIndexId(timeIndexId);
    }
}
