package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.request.CreateRoleRequest;
import com.github.abigail830.timeticket.api.request.DeleteRoleRequest;
import com.github.abigail830.timeticket.api.request.UpdateRoleRequest;
import com.github.abigail830.timeticket.api.response.TicketIndexResponse;
import com.github.abigail830.timeticket.application.TimeTicketApplicationService;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tickets/indexes")
@Slf4j
@Api(description = "首页角色列表相关操作API")
public class TicketIndexController {

    @Autowired
    TimeTicketApplicationService timeTicketApplicationService;

    @ApiOperation(value = "添加角色")
    @PostMapping
    public List<TicketIndexResponse> createTicketIndex(
            @RequestBody CreateRoleRequest createRoleRequest) {

        final List<TicketIndex> domainList = timeTicketApplicationService.createTicketIndex(
                createRoleRequest.getOwnerOpenId(),
                createRoleRequest.getAssigneeRole());

        return domainList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @ApiOperation(value = "修改角色")
    @PutMapping
    public List<TicketIndexResponse> updateTicketIndex(
            @RequestBody UpdateRoleRequest updateRoleRequest) {

        final List<TicketIndex> domainList = timeTicketApplicationService.updateTicketIndex(
                updateRoleRequest.getTicketIndexId(),
                updateRoleRequest.getOwnerOpenId(),
                updateRoleRequest.getNewAssigneeRole()
        );

        return domainList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping
    public List<TicketIndexResponse> deleteTicketIndex(
            @RequestBody DeleteRoleRequest deleteRoleRequest) {

        final List<TicketIndex> indexList = timeTicketApplicationService.deleteTicketIndex(
                deleteRoleRequest.getTicketIndexId(),
                deleteRoleRequest.getOwnerOpenId()
        );

        return indexList.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

    @ApiOperation(value = "首页角色列表查询")
    @GetMapping
    public List<TicketIndexResponse> getTicketIndexByOwner(
            @RequestParam String ownerOpenId) {

        final List<TicketIndex> ticketIndexByOwner = timeTicketApplicationService.getTicketIndexByOwner(ownerOpenId);
        return ticketIndexByOwner.stream().map(TicketIndexResponse::fromTicketIndex).collect(Collectors.toList());
    }

}
