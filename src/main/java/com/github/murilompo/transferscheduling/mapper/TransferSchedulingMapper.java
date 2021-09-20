package com.github.murilompo.transferscheduling.mapper;

import com.github.murilompo.transferscheduling.domain.TransferSchedulingEntity;
import com.github.murilompo.transferscheduling.dto.schedule.CreateTransferScheduleRequest;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransferSchedulingMapper {

	TransferSchedulingEntity toEntity(
			CreateTransferScheduleRequest transferScheduleRequest);

	TransferScheduleResponse toDTO(TransferSchedulingEntity transferScheduling);

	List<TransferScheduleResponse> toDTO(
			Iterable<TransferSchedulingEntity> transferSchedulingList);

}
