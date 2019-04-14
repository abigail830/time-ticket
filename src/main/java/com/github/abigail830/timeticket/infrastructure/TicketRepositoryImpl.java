package com.github.abigail830.timeticket.infrastructure;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.ticket.TicketRepository;
import com.github.abigail830.timeticket.infrastructure.repository.ComplexTicketIndexRepository;
import com.github.abigail830.timeticket.infrastructure.repository.RawTicketIndexRepository;
import com.github.abigail830.timeticket.infrastructure.repository.RawTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketRepositoryImpl implements TicketRepository {

    private RawTicketRepository rawTicketRepository;
    private RawTicketIndexRepository rawTicketIndexRepository;
    private ComplexTicketIndexRepository complexTicketIndexRepository;

    @Autowired
    public TicketRepositoryImpl(RawTicketRepository rawTicketRepository,
                                RawTicketIndexRepository rawTicketIndexRepository,
                                ComplexTicketIndexRepository complexTicketIndexRepository) {
        this.rawTicketRepository = rawTicketRepository;
        this.rawTicketIndexRepository = rawTicketIndexRepository;
        this.complexTicketIndexRepository = complexTicketIndexRepository;
    }


    @Override
    public void addTicketIndex(TicketIndex ticketIndex) {
        rawTicketIndexRepository.addTicketIndex(ticketIndex);
    }

    @Override
    public void updateAssigneeRoleOfTicketIndex(Integer id, String assigneeRole) {
        rawTicketIndexRepository.updateAssigneeRoleOfTicketIndex(id, assigneeRole);
    }

    @Override
    public void removeTicketIndex(Integer ticketIndexId) {
        rawTicketRepository.removeTicketByIndexId(ticketIndexId);
        rawTicketIndexRepository.removeTicketIndex(ticketIndexId);
    }

    @Override
    public List<TicketIndex> getTicketIndexByOwnerOpenIdOrderBySumDuration(String ownerOpenId) {
        return rawTicketIndexRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration(ownerOpenId);
    }

    @Override
    public void updateAssigneeAndSumDurationOfTicketIndex(TicketIndex updatedTicket) {
        rawTicketIndexRepository.updateAssigneeAndSumDurationOfTicketIndex(updatedTicket);
    }

    @Override
    public void addTicketToIndex(Ticket ticket) {
        rawTicketRepository.addTicketToIndex(ticket);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        rawTicketRepository.updateTicket(ticket);
    }

    @Override
    public void removeTicket(Integer ticketId) {
        rawTicketRepository.removeTicket(ticketId);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return rawTicketRepository.getAllTickets();
    }


    @Override
    public Optional<Ticket> getTicketById(Integer ticketId) {
        return rawTicketRepository.getTicketById(ticketId);
    }

    @Override
    public void updateTicketStatus(Integer ticketId, String status) {
        rawTicketRepository.updateStatus(ticketId, status);
    }

    @Override
    public TicketIndex getTicketIndexByIndexId(Integer ticketIndexId) {
        return rawTicketIndexRepository.getTicketIndexByIndexId(ticketIndexId);
    }

    @Override
    public TicketIndex getTicketDetailByIndexId(Integer ticketIndexId) {
        return complexTicketIndexRepository.getTicketDetailListByIndexId(ticketIndexId);
    }


}
