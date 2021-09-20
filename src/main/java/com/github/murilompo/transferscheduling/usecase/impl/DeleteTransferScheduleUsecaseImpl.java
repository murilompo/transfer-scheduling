package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.TransferSchedulingEntity;
import com.github.murilompo.transferscheduling.exception.CancelTransferProcessedException;
import com.github.murilompo.transferscheduling.exception.IdTransferSchedulingNotFoundException;
import com.github.murilompo.transferscheduling.repository.TransferSchedulingRepository;
import com.github.murilompo.transferscheduling.usecase.DeleteTransferScheduleUsecase;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTransferScheduleUsecaseImpl implements DeleteTransferScheduleUsecase {

	private static final ZoneId REFERENCE_TIME_ZONE
			= ZoneId.of("America/Sao_Paulo");

	private final TransferSchedulingRepository transferSchedulingRepository;

	@Override
	public void execute(Long transferSchedulingId) {

		Optional<TransferSchedulingEntity> transferSchedulingOp
				= transferSchedulingRepository.findById(transferSchedulingId);

		if (transferSchedulingOp.isEmpty()) {
			throw new IdTransferSchedulingNotFoundException();
		}

		if (LocalDate.now(REFERENCE_TIME_ZONE).compareTo(
				transferSchedulingOp.get().getProcessingDate()) > -1) {
			throw new CancelTransferProcessedException();
		}

		transferSchedulingRepository.delete(transferSchedulingOp.get());
	}

}
