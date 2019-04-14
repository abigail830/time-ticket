package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.request.CreateTicketDetailRequest;
import com.github.abigail830.timeticket.api.request.DeleteTicketDetailRequest;
import com.github.abigail830.timeticket.api.request.UpdateTicketDetailRequest;
import com.github.abigail830.timeticket.api.response.TicketDetailListResponse;
import com.github.abigail830.timeticket.api.response.TicketDetailResponse;
import com.github.abigail830.timeticket.application.TimeTicketApplicationService;
import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tickets/detail")
@Slf4j
@Api(description = "某角色时间小票列表页面相关操作API")
public class TicketDetailController {

    @Autowired
    TimeTicketApplicationService timeTicketApplicationService;

    @ApiOperation(value = "添加时间小票")
    @PostMapping
    public TicketDetailListResponse createTicketDetail(
            @RequestBody CreateTicketDetailRequest createTicketDetailRequest) {

        final TicketIndex ticketIndex = timeTicketApplicationService.createTicketDetail(
                createTicketDetailRequest.getTimeIndexId(),
                createTicketDetailRequest.getEvent(),
                createTicketDetailRequest.getDuration());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @ApiOperation(value = "修改时间小票")
    @PutMapping
    public TicketDetailListResponse updateTicketDetail(
            @RequestBody UpdateTicketDetailRequest updateTicketDetailRequest) {

        final TicketIndex ticketIndex = timeTicketApplicationService.updateTicketDetail(
                updateTicketDetailRequest.getTimeIndexId(),
                updateTicketDetailRequest.getTicketId(),
                updateTicketDetailRequest.getEvent(),
                updateTicketDetailRequest.getDuration());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @ApiOperation(value = "删除时间小票")
    @DeleteMapping
    public TicketDetailListResponse deleteTicketDetail(
            @RequestBody DeleteTicketDetailRequest deleteTicketDetailRequest) {
        final TicketIndex ticketIndex = timeTicketApplicationService.deleteTicketDetail(
                deleteTicketDetailRequest.getTicketIndexId(),
                deleteTicketDetailRequest.getTicketId());

        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @ApiOperation(value = "查询角色对应所有时间小票列表")
    @GetMapping
    public TicketDetailListResponse getTicketDetailByIndexId(@RequestParam Integer ticketIndexId) {
        final TicketIndex ticketIndex = timeTicketApplicationService.getTicketDetailByTicketIndexId(ticketIndexId);
        return TicketDetailListResponse.fromTicketIndex(ticketIndex);
    }

    @PostMapping("/confirm")
    public void confirmTicket(@RequestParam Integer ticketId,
                              @RequestParam Integer ticketIndexId,
                              @RequestParam String assigneeOpenId) {
        timeTicketApplicationService.confirmTicket(ticketId, ticketIndexId, assigneeOpenId);
    }

    @ApiOperation(value = "【测试用】获取所有时间小票")
    @GetMapping("/all")
    public List<TicketDetailResponse> getAllSimpleTicketDetail() {

        final List<Ticket> allTickets = timeTicketApplicationService.getAllTickets();
        return allTickets.stream().map(TicketDetailResponse::fromTicketDetail).collect(Collectors.toList());
    }


}
