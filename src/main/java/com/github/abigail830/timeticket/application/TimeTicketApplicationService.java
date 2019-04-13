package com.github.abigail830.timeticket.application;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.ticket.TicketRepository;
import com.github.abigail830.timeticket.domain.ticket.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class TimeTicketApplicationService {

    private TicketService ticketService;
    private TicketRepository ticketRepository;

    @Autowired
    public TimeTicketApplicationService(TicketRepository ticketRepository) {
        this.ticketService = new TicketService(ticketRepository);
        this.ticketRepository = ticketRepository;
    }

    public List<TicketIndex> createTicketIndex(String ownerOpenId, String assigneeRole) {
        ticketService.addTicketIndex(ownerOpenId, assigneeRole);
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public List<TicketIndex> updateTicketIndex(Integer ticketIndexId, String ownerOpenId, String newAssigneeRole) {
        ticketService.updateTicketIndex(ticketIndexId, newAssigneeRole);
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public List<TicketIndex> deleteTicketIndex(Integer ticketIndexId, String ownerOpenId) {
        ticketService.deleteTicketIndex(ticketIndexId);
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public List<TicketIndex> getTicketIndexByOwner(String ownerOpenId) {
        return ticketRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    public TicketIndex createTicketDetail(Integer timeIndexId, String event, Long duration) {
        ticketService.addTicketDetail(timeIndexId, event, duration);
        return ticketRepository.getTicketDetailListByIndexId(timeIndexId);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

    public TicketIndex updateTicketDetail(Integer ticketIndexId, Integer ticketId, String event, Long duration) {
        if (!ticketService.isTicketAcceptUpdate(ticketId)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ticket is not allowed for update");
        } else {
            ticketService.updateTicketDetail(ticketIndexId, ticketId, event, duration);
            return ticketRepository.getTicketDetailListByIndexId(ticketIndexId);
        }
    }

    public TicketIndex deleteTicketDetail(Integer ticketIndexId, Integer ticketId) {
        ticketService.deleteTicket(ticketId);
        return ticketRepository.getTicketDetailListByIndexId(ticketIndexId);
    }
}
