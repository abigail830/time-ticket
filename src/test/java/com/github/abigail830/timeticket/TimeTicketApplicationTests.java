package com.github.abigail830.timeticket;

import com.github.abigail830.timeticket.util.DefaultEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeTicketApplicationTests {

	@Bean(name = "jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		return new DefaultEncryptor();
	}

	@Test
	public void contextLoads() {
	}

}
