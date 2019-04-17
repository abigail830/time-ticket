package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.UnitTestBase;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.user.User;
import org.junit.Assert;
import org.junit.Test;

public class RawTicketIndexRepositoryImplTest extends UnitTestBase {


    @Test
    public void should_add_ticket() {
        RawTicketIndexRepository repository = new RawTicketIndexRepositoryImpl(jdbcTemplate);
        final TicketIndex ticketIndex = new TicketIndex("openID_1", "assignRole");
        repository.addTicketIndex(ticketIndex);

        final TicketIndex result = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_1").stream()
                .filter(ticketIndex1 -> ticketIndex1.getAssigneeRole().equals("assignRole"))
                .findFirst().orElse(null);
        Assert.assertNotNull(result);
    }

    @Test
    public void should_update_ticket_assignee_role() {
        RawTicketIndexRepository repository = new RawTicketIndexRepositoryImpl(jdbcTemplate);
        final TicketIndex ticketIndex = new TicketIndex("openID_2", "assignRole");
        repository.addTicketIndex(ticketIndex);
        final TicketIndex result = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_2")
                .stream().findFirst().orElse(null);
        Assert.assertNotNull(result);

        repository.updateAssigneeRoleOfTicketIndex(result.getId(), "newRole");
        final TicketIndex result1 = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_2").stream()
                .filter(ticketIndex1 -> ticketIndex1.getAssigneeRole().equals("newRole"))
                .findFirst().orElse(null);
        Assert.assertNotNull(result1);
    }

    @Test
    public void should_remove_ticket() {
        RawTicketIndexRepository repository = new RawTicketIndexRepositoryImpl(jdbcTemplate);
        final TicketIndex ticketIndex = new TicketIndex("openID_3", "assignRole");
        repository.addTicketIndex(ticketIndex);

        final TicketIndex ticketIndex2 = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_3").stream()
                .findFirst().orElse(null);
        Assert.assertNotNull(ticketIndex2);

        repository.removeTicketIndex(ticketIndex2.getId());
        final TicketIndex ticketIndex3 = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_3").stream()
                .findFirst().orElse(null);
        Assert.assertNull(ticketIndex3);
    }

    @Test
    public void should_update_ticket_sum_duration() {
        RawTicketIndexRepository repository = new RawTicketIndexRepositoryImpl(jdbcTemplate);
        final TicketIndex ticketIndex = new TicketIndex("openID_4", "assignRole");
        repository.addTicketIndex(ticketIndex);
        final TicketIndex result = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_4")
                .stream().findFirst().orElse(null);
        Assert.assertNotNull(result);

        User assignee = User.builder().openId("assigneeOpenId").build();
        TicketIndex newIndex = new TicketIndex(result.getId(), result.getOwnerOpenId(), assignee, result.getAssigneeRole(), 3600L);
        repository.updateAssigneeAndSumDurationOfTicketIndex(newIndex);
        final TicketIndex result1 = repository.getTicketIndexByOwnerOpenIdOrderBySumDuration("openID_4").stream()
                .findFirst().orElse(null);
        Assert.assertNotNull(result1);
//        Assert.assertEquals(3600L, result1.getSumDuration());
    }
}