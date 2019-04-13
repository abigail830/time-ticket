package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.request.*;
import com.github.abigail830.timeticket.api.response.TicketDetailListResponse;
import com.github.abigail830.timeticket.api.response.TicketIndexResponse;
import com.github.abigail830.timeticket.application.TimeTicketApplicationService;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tickets")
@Slf4j
public class TicketController {

    @Autowired
    TimeTicketApplicationService timeTicketApplicationService;

    @PostMapping("/indexes")
    public List<TicketIndexResponse> createTicketIndex(
            @RequestBody CreateRoleRequest createRoleRequest) {

        final List<TicketIndex> domainList = timeTicketApplicationService.createTicketIndex(
                createRoleRequest.getOwnerOpenId(),
                createRoleRequest.getAssigneeRole());

        return domainList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @PutMapping("/indexes")
    public List<TicketIndexResponse> updateTicketIndex(
            @RequestBody UpdateRoleRequest updateRoleRequest) {

        final List<TicketIndex> domainList = timeTicketApplicationService.updateTicketIndex(
                updateRoleRequest.getTicketIndexId(),
                updateRoleRequest.getOwnerOpenId(),
                updateRoleRequest.getNewAssigneeRole()
        );

        return domainList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @DeleteMapping("/indexes")
    public List<TicketIndexResponse> deleteTicketIndex(
            @RequestBody DeleteRoleRequest deleteRoleRequest) {

        final List<TicketIndex> indexList = timeTicketApplicationService.deleteTicketIndex(
                deleteRoleRequest.getTicketIndexId(),
                deleteRoleRequest.getOwnerOpenId()
        );

        return indexList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @GetMapping("/indexes")
    public List<TicketIndexResponse> getTicketIndexByOwner(
            @RequestParam String ownerOpenId) {

        final List<TicketIndex> ticketIndexByOwner = timeTicketApplicationService.getTicketIndexByOwner(ownerOpenId);
        return ticketIndexByOwner.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @PostMapping("/detail")
    public TicketDetailListResponse createTicketDetail(
            @RequestBody CreateTicketDetailRequest createTicketDetailRequest) {

        final TicketIndex ticketIndex = timeTicketApplicationService.createTicketDetail(
                createTicketDetailRequest.getTimeIndexId(),
                createTicketDetailRequest.getEvent(),
                createTicketDetailRequest.getDuration());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @PutMapping("/detail")
    public TicketDetailListResponse updateTicketDetail(
            @RequestBody UpdateTicketDetailRequest updateTicketDetailRequest) {

        final TicketIndex ticketIndex = timeTicketApplicationService.updateTicketDetail(
                updateTicketDetailRequest.getTimeIndexId(),
                updateTicketDetailRequest.getTicketId(),
                updateTicketDetailRequest.getEvent(),
                updateTicketDetailRequest.getDuration());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @DeleteMapping("/detail")
    public TicketDetailListResponse deleteTicketDetail(
            @RequestBody DeleteTicketDetailRequest deleteTicketDetailRequest) {
        final TicketIndex ticketIndex = timeTicketApplicationService.deleteTicketDetail(
                deleteTicketDetailRequest.getTicketIndexId(),
                deleteTicketDetailRequest.getTicketId());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);

    }

}
