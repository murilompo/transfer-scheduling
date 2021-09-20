package com.github.murilompo.transferscheduling.controller;

import com.github.murilompo.transferscheduling.dto.feesrules.CreateFeesRuleRequest;
import com.github.murilompo.transferscheduling.dto.feesrules.FeesRuleResponse;
import com.github.murilompo.transferscheduling.exception.FeesRulesCompositeKeyViolationException;
import com.github.murilompo.transferscheduling.exception.IdFeesRulesNotFoundException;
import com.github.murilompo.transferscheduling.usecase.CreateFessRuleUsecase;
import com.github.murilompo.transferscheduling.usecase.DeleteFeesRulesUsecase;
import com.github.murilompo.transferscheduling.usecase.ListAllFeesRulesUsecase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Api("Fees Rules")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/fees-rules")
public class FeesRulesController {

	private final CreateFessRuleUsecase createFessRuleUsecase;
	private final ListAllFeesRulesUsecase listAllFeesRulesUsecase;
	private final DeleteFeesRulesUsecase deleteFeesRulesUsecase;

	@GetMapping
	@ApiOperation("Lists all rate calculation rules")
	@ApiResponses({
		@ApiResponse(code = 200, message = "successfully listed rules"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<List<FeesRuleResponse>> listAllRules() {
		try {
			List<FeesRuleResponse> feesRules = listAllFeesRulesUsecase.execute();
			return ResponseEntity.ok(feesRules);

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping
	@ApiOperation("Register new calculation rule")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Rule successfully registered",
				response = Long.class, responseContainer = "Id of registered rule"),
		@ApiResponse(code = 400, message = "Error in formatting parameters"),
		@ApiResponse(code = 422, message = "Invalid data for inclusion of new fees rules"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<Long> createRule(@Valid @RequestBody CreateFeesRuleRequest feesRules) {
		try {
			Long ruleId = createFessRuleUsecase.execute(feesRules);
			return ResponseEntity.status(HttpStatus.CREATED).body(ruleId);

		} catch (FeesRulesCompositeKeyViolationException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"There is already a tariff rule registered with the same fields: 'priority', 'maximumDays' and 'minimumValue'");

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Delete a fees calculation rule")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Rule successfully deleted"),
		@ApiResponse(code = 404, message = "Id of fees rules not found"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<Void> deleteRule(@PathVariable("id") Long feesRulesId) {
		try {
			deleteFeesRulesUsecase.execute(feesRulesId);
			return ResponseEntity.noContent().build();

		} catch (IdFeesRulesNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}
