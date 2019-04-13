package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;

public interface ComplexTicketIndexRepository {

    TicketIndex getTicketDetailListByIndexId(Integer timeIndexId);
}
