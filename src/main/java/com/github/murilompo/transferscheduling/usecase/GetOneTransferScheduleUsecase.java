package com.github.murilompo.transferscheduling.usecase;

import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;

public interface GetOneTransferScheduleUsecase {

	TransferScheduleResponse execute(Long transferSchedulingId);

}
