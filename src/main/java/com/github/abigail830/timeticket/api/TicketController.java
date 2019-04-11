package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.request.CreateRoleRequest;
import com.github.abigail830.timeticket.api.request.CreateTicketDetailRequest;
import com.github.abigail830.timeticket.api.request.DeleteRoleRequest;
import com.github.abigail830.timeticket.api.request.UpdateRoleRequest;
import com.github.abigail830.timeticket.api.response.TicketDetailListResponse;
import com.github.abigail830.timeticket.api.response.TicketIndexResponse;
import com.github.abigail830.timeticket.application.TimeTicketApplicationService;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 小程序相关登录和解密接口
 */
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

}
