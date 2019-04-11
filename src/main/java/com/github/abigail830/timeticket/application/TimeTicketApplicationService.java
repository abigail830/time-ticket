package com.github.abigail830.timeticket.application;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.ticket.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TimeTicketApplicationService {

    @Autowired
    TicketService ticketService;

    public List<TicketIndex> createTicketIndex(String ownerOpenId, String assigneeRole) {
        ticketService.addTicketIndex(ownerOpenId, assigneeRole);
        return ticketService.getTicketIndexListByOwner(ownerOpenId);
    }

    public List<TicketIndex> updateTicketIndex(Integer ticketIndexId, String ownerOpenId, String newAssigneeRole) {
        ticketService.updateTicketIndex(ticketIndexId, newAssigneeRole);
        return ticketService.getTicketIndexListByOwner(ownerOpenId);
    }

    public List<TicketIndex> deleteTicketIndex(Integer ticketIndexId, String ownerOpenId) {
        ticketService.deleteTicketIndex(ticketIndexId, ownerOpenId);
        return ticketService.getTicketIndexListByOwner(ownerOpenId);
    }

    public List<TicketIndex> getTicketIndexByOwner(String ownerOpenId) {
        return ticketService.getTicketIndexListByOwner(ownerOpenId);
    }

    public TicketIndex createTicketDetail(Integer timeIndexId, String event, Long duration) {
        ticketService.addTicketDetail(timeIndexId, event, duration);
        return ticketService.getTicketDetailListByIndexId(timeIndexId);
    }

    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}
