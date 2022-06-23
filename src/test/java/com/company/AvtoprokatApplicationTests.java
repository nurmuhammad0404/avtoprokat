package com.company;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@SpringBootTest
class AvtoprokatApplicationTests {

	@Test
	void contextLoads() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		LocalDate localDate = LocalDate.parse(LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getMonthValue() + "/"
				+ LocalDateTime.now().getYear(), formatter);
		System.out.println(localDate.toString());

	}

}
