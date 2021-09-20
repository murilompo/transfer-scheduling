package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.FeesRulesEntity;
import com.github.murilompo.transferscheduling.domain.TransferSchedulingEntity;
import com.github.murilompo.transferscheduling.dto.schedule.CreateTransferScheduleRequest;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import com.github.murilompo.transferscheduling.exception.FeeGreaterThanAmountException;
import com.github.murilompo.transferscheduling.exception.MillenarySchedulingException;
import com.github.murilompo.transferscheduling.exception.NoApplicableFeeRuleException;
import com.github.murilompo.transferscheduling.exception.TargetAndSourceEqualsException;
import com.github.murilompo.transferscheduling.mapper.TransferSchedulingMapper;
import com.github.murilompo.transferscheduling.repository.FeesRulesRepository;
import com.github.murilompo.transferscheduling.repository.TransferSchedulingRepository;
import com.github.murilompo.transferscheduling.usecase.CreateTransferScheduleUsecase;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import static java.util.Objects.nonNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTransferScheduleUsecaseImpl
		implements CreateTransferScheduleUsecase {

	private static final ZoneId REFERENCE_TIME_ZONE
			= ZoneId.of("America/Sao_Paulo");

	private final TransferSchedulingMapper mapper;
	private final FeesRulesRepository feesRulesRepository;
	private final TransferSchedulingRepository transferSchedulingRepository;

	@Override
	public TransferScheduleResponse execute(
			CreateTransferScheduleRequest transferScheduleRequest) {

		initialValidation(transferScheduleRequest);

		TransferSchedulingEntity schedulingEntity
				= mapper.toEntity(transferScheduleRequest);

		schedulingEntity.setSchedulingDate(LocalDate.now(REFERENCE_TIME_ZONE));

		Integer daysUntilProcessing = (int) ChronoUnit.DAYS.between(
				schedulingEntity.getSchedulingDate(),
				schedulingEntity.getProcessingDate());

		Optional<FeesRulesEntity> feesRulesEntityOp = feesRulesRepository
				.findTop1ByMaximumDaysGreaterThanEqualAndMinimumValueLessThanEqualOrderByPriorityAscMaximumDaysAscMinimumValueDesc(
						daysUntilProcessing, schedulingEntity.getAmount());

		if (feesRulesEntityOp.isEmpty()) {
			throw new NoApplicableFeeRuleException();
		}

		calculateFee(schedulingEntity, feesRulesEntityOp.get(), daysUntilProcessing);
		integrityValidations(schedulingEntity);

		transferSchedulingRepository.save(schedulingEntity);
		return mapper.toDTO(schedulingEntity);
	}

	private void calculateFee(TransferSchedulingEntity schedulingEntity,
			FeesRulesEntity feesRules, Integer daysUntilProcessing) {

		BigDecimal fee = Objects.requireNonNullElse(
				feesRules.getSingleFixedFee(), BigDecimal.ZERO);

		if (nonNull(feesRules.getRecurringFixedFee())) {
			fee = fee.add(feesRules.getRecurringFixedFee()
					.multiply(BigDecimal.valueOf(daysUntilProcessing)));
		}

		if (nonNull(feesRules.getPercentageFee())) {
			fee = fee.add(feesRules.getPercentageFee()
					.multiply(schedulingEntity.getAmount()));
		}

		schedulingEntity.setFee(fee.setScale(2, RoundingMode.DOWN));
	}

	private void initialValidation(CreateTransferScheduleRequest transferScheduleRequest) {
		if (transferScheduleRequest.getSourceAccount()
				.equalsIgnoreCase(transferScheduleRequest.getTargetAccount())) {
			throw new TargetAndSourceEqualsException();
		}

		if (transferScheduleRequest.getProcessingDate().getYear() > 2999) {
			throw new MillenarySchedulingException();
		}
	}

	private void integrityValidations(TransferSchedulingEntity schedulingEntity) {
		if (schedulingEntity.getFee().compareTo(schedulingEntity.getAmount()) > -1) {
			throw new FeeGreaterThanAmountException();
		}
	}

}
