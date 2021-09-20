package com.github.murilompo.transferscheduling.controller;

import com.github.murilompo.transferscheduling.dto.schedule.CreateTransferScheduleRequest;
import static io.restassured.RestAssured.given;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransfersSchedulesControllerTest {

	private final LocalDate today
			= LocalDate.now(ZoneId.of("America/Sao_Paulo"));

	@LocalServerPort
	private int port;

	@Test
	public void transfersSchedulesAllTests() {

		testCreateTransferScheduleSucess("56.85", inDays(0), 4.7f);
		testCreateTransferScheduleSucess("56.85", inDays(1), 12f);
		testCreateTransferScheduleSucess("56.85", inDays(2), 24f);
		testCreateTransferScheduleSucess("876.54", inDays(9), 108f);
		testCreateTransferScheduleSucess("876.54", inDays(10), 120f);
		testCreateTransferScheduleSucess("876.54", inDays(11), 70.12f);
		testCreateTransferScheduleSucess("876.54", inDays(20), 70.12f);
		testCreateTransferScheduleSucess("876.54", inDays(21), 52.59f);
		testCreateTransferScheduleSucess("765.43", inDays(30), 45.92f);
		testCreateTransferScheduleSucess("765.43", inDays(31), 30.61f);
		testCreateTransferScheduleSucess("765.43", inDays(40), 30.61f);
		testCreateTransferScheduleSucess("100000.00", inDays(41), 2000f);
		testCreateTransferScheduleSucess("100000.01", inDays(41), 2000f);

		testCreateTransferScheduleError("ABC001", "ABC001", "56.85", inDays(0), 400);
		testCreateTransferScheduleError("ABC001", "ABC002", "56.85", inDays(358000), 400);
		testCreateTransferScheduleError("ABC001", "ABC002", "56.85", inDays(9), 422);
		testCreateTransferScheduleError("ABC001", "ABC002", "56.85", inDays(41), 422);

		testDeleteTransferSchedule(1, 422);
		testGetOneTransferSchedule(2, 200);
		testDeleteTransferSchedule(2, 204);
		testDeleteTransferSchedule(2, 404);
		testGetOneTransferSchedule(2, 404);

		testListAllTransferSchedule();
	}

	private void testCreateTransferScheduleSucess(
			String amount, LocalDate processingDate, float fee) {

		CreateTransferScheduleRequest scheduleRequest
				= new CreateTransferScheduleRequest();

		scheduleRequest.setAmount(new BigDecimal(amount));
		scheduleRequest.setProcessingDate(processingDate);
		scheduleRequest.setSourceAccount("ABC001");
		scheduleRequest.setTargetAccount("ABC002");

		given().port(port)
				.contentType("application/json")
				.body(scheduleRequest)
				.when()
				.post("/v1/transfers/schedules")
				.then()
				.statusCode(201)
				.body("fee", is(fee));
	}

	private void testCreateTransferScheduleError(String sourceAccount,
			String targetAccount, String amount, LocalDate processingDate,
			int statusCode) {

		CreateTransferScheduleRequest scheduleRequest
				= new CreateTransferScheduleRequest();

		scheduleRequest.setAmount(new BigDecimal(amount));
		scheduleRequest.setProcessingDate(processingDate);
		scheduleRequest.setSourceAccount(sourceAccount);
		scheduleRequest.setTargetAccount(targetAccount);

		given().port(port)
				.contentType("application/json")
				.body(scheduleRequest)
				.when()
				.post("/v1/transfers/schedules")
				.then()
				.statusCode(statusCode);
	}

	private void testListAllTransferSchedule() {
		given().port(port)
				.when()
				.get("/v1/transfers/schedules")
				.then()
				.statusCode(200);
	}

	private void testGetOneTransferSchedule(int schedulingId, int statusCode) {
		given().port(port)
				.when()
				.get("/v1/transfers/schedules/{id}", schedulingId)
				.then()
				.statusCode(statusCode);
	}

	private void testDeleteTransferSchedule(int schedulingId, int statusCode) {
		given().port(port)
				.when()
				.delete("/v1/transfers/schedules/{id}", schedulingId)
				.then()
				.statusCode(statusCode);
	}

	private LocalDate inDays(long daysToAdd) {
		return today.plusDays(daysToAdd);
	}

}
