package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.TransferSchedulingEntity;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import com.github.murilompo.transferscheduling.mapper.TransferSchedulingMapper;
import com.github.murilompo.transferscheduling.repository.TransferSchedulingRepository;
import com.github.murilompo.transferscheduling.usecase.ListAllScheduledTransfersUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListAllScheduledTransfersUsecaseImpl implements ListAllScheduledTransfersUsecase {

	private final TransferSchedulingMapper mapper;
	private final TransferSchedulingRepository transferSchedulingRepository;

	@Override
	public List<TransferScheduleResponse> execute() {

		Iterable<TransferSchedulingEntity> entities
				= transferSchedulingRepository.findAll();

		return mapper.toDTO(entities);
	}

}
