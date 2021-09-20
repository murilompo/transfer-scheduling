package com.github.murilompo.transferscheduling.controller;

import com.github.murilompo.transferscheduling.dto.feesrules.CreateFeesRuleRequest;
import static io.restassured.RestAssured.given;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeesRulesControllerTest {

	@LocalServerPort
	private int port;

	@Test
	public void feesRulesAllTests() {

		testListAllRules();

		testCreateRule(50L, 10, BigDecimal.ZERO, 201);
		testCreateRule(50L, 10, BigDecimal.ZERO, 422);

		testDeleteRule(7, 204);
		testDeleteRule(7, 404);
	}

	private void testListAllRules() {
		given().port(port)
				.when()
				.get("/v1/fees-rules")
				.then()
				.statusCode(200);
	}

	private void testCreateRule(Long priority, Integer maximumDays,
			BigDecimal minimumValue, int statusCode) {

		CreateFeesRuleRequest ruleRequest = new CreateFeesRuleRequest();

		ruleRequest.setPriority(priority);
		ruleRequest.setMaximumDays(maximumDays);
		ruleRequest.setMinimumValue(minimumValue);

		given().port(port)
				.contentType("application/json")
				.body(ruleRequest)
				.when()
				.post("/v1/fees-rules")
				.then()
				.statusCode(statusCode);

	}

	private void testDeleteRule(int schedulingId, int statusCode) {
		given().port(port)
				.when()
				.delete("/v1/fees-rules/{id}", schedulingId)
				.then()
				.statusCode(statusCode);
	}

}
