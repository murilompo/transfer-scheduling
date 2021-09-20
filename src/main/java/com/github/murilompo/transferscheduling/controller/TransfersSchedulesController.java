package com.github.murilompo.transferscheduling.controller;

import com.github.murilompo.transferscheduling.dto.schedule.CreateTransferScheduleRequest;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import com.github.murilompo.transferscheduling.exception.CancelTransferProcessedException;
import com.github.murilompo.transferscheduling.exception.FeeGreaterThanAmountException;
import com.github.murilompo.transferscheduling.exception.IdTransferSchedulingNotFoundException;
import com.github.murilompo.transferscheduling.exception.MillenarySchedulingException;
import com.github.murilompo.transferscheduling.exception.NoApplicableFeeRuleException;
import com.github.murilompo.transferscheduling.exception.TargetAndSourceEqualsException;
import com.github.murilompo.transferscheduling.usecase.CreateTransferScheduleUsecase;
import com.github.murilompo.transferscheduling.usecase.DeleteTransferScheduleUsecase;
import com.github.murilompo.transferscheduling.usecase.GetOneTransferScheduleUsecase;
import com.github.murilompo.transferscheduling.usecase.ListAllScheduledTransfersUsecase;
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

@Api("Transfer schedules")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transfers/schedules")
public class TransfersSchedulesController {

	private final ListAllScheduledTransfersUsecase listAllScheduledTransfersUsecase;
	private final GetOneTransferScheduleUsecase getOneTransferScheduleUsecase;
	private final CreateTransferScheduleUsecase createTransferScheduleUsecase;
	private final DeleteTransferScheduleUsecase deleteTransferScheduleUsecase;

	@GetMapping
	@ApiOperation("List all scheduled transfers")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Successfully listed scheduled transfers"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<List<TransferScheduleResponse>> listAllTransferSchedule() {

		try {
			List<TransferScheduleResponse> scheduledTransfers
					= listAllScheduledTransfersUsecase.execute();
			return ResponseEntity.ok(scheduledTransfers);

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}")
	@ApiOperation("Consult a transfer schedule")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Transfer schedule found successfully"),
		@ApiResponse(code = 404, message = "Id of transfer schedule not found"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<TransferScheduleResponse> getOneTransferSchedule(
			@PathVariable("id") Long transferSchedulingId) {

		try {
			TransferScheduleResponse transferSchedule
					= getOneTransferScheduleUsecase.execute(transferSchedulingId);
			return ResponseEntity.ok(transferSchedule);

		} catch (IdTransferSchedulingNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping
	@ApiOperation("Schedule a new transfer")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Successful scheduled transfer"),
		@ApiResponse(code = 400, message = "Error in formatting parameters"),
		@ApiResponse(code = 422, message = "Invalid data to schedule a new transfer"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<TransferScheduleResponse> createTransferSchedule(
			@Valid @RequestBody CreateTransferScheduleRequest transferScheduleRequest) {

		try {
			TransferScheduleResponse scheduleCreated
					= createTransferScheduleUsecase.execute(transferScheduleRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(scheduleCreated);

		} catch (TargetAndSourceEqualsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Source account and destination account cannot be the same");

		} catch (MillenarySchedulingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"The transfer must take place in this millennium");

		} catch (NoApplicableFeeRuleException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"There is no applicable fee rule for scheduling");

		} catch (FeeGreaterThanAmountException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"The fee would be greater than or equal to the amount of the schedule");

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Cancel a transfer schedule")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Transfer schedule successfully canceled"),
		@ApiResponse(code = 404, message = "Id of transfer schedule not found"),
		@ApiResponse(code = 422, message = "Transfer schedule cannot be canceled"),
		@ApiResponse(code = 500, message = "An unexpected error has occurred")})
	public ResponseEntity<TransferScheduleResponse> deleteTransferSchedule(
			@PathVariable("id") Long transferSchedulingId) {

		try {
			deleteTransferScheduleUsecase.execute(transferSchedulingId);
			return ResponseEntity.noContent().build();

		} catch (IdTransferSchedulingNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (CancelTransferProcessedException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Canceling a transfer schedule with a processing date less than or equal to today is not allowed");

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}
