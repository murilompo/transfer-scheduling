package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.TransferSchedulingEntity;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import com.github.murilompo.transferscheduling.exception.IdTransferSchedulingNotFoundException;
import com.github.murilompo.transferscheduling.mapper.TransferSchedulingMapper;
import com.github.murilompo.transferscheduling.repository.TransferSchedulingRepository;
import com.github.murilompo.transferscheduling.usecase.GetOneTransferScheduleUsecase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOneTransferScheduleUsecaseImpl implements GetOneTransferScheduleUsecase {

	private final TransferSchedulingMapper mapper;
	private final TransferSchedulingRepository transferSchedulingRepository;

	@Override
	public TransferScheduleResponse execute(Long transferSchedulingId) {

		Optional<TransferSchedulingEntity> transferSchedulingOp
				= transferSchedulingRepository.findById(transferSchedulingId);

		if (transferSchedulingOp.isEmpty()) {
			throw new IdTransferSchedulingNotFoundException();
		}

		return mapper.toDTO(transferSchedulingOp.get());
	}

}
