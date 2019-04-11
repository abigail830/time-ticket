package com.github.abigail830.timeticket.api;

import com.github.abigail830.timeticket.api.response.TicketDetailResponse;
import com.github.abigail830.timeticket.application.TimeTicketApplicationService;
import com.github.abigail830.timeticket.domain.ticket.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 小程序相关登录和解密接口
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TimeTicketApplicationService timeTicketApplicationService;

    @GetMapping("/tickets")
    public List<TicketDetailResponse> getAllSimpleTicketDetail() {

        final List<Ticket> allTickets = timeTicketApplicationService.getAllTickets();
        return allTickets.stream().map(TicketDetailResponse::fromTicketDetail).collect(Collectors.toList());
    }

}
