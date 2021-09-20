package com.github.murilompo.transferscheduling.usecase;

import com.github.murilompo.transferscheduling.dto.schedule.CreateTransferScheduleRequest;
import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;

public interface CreateTransferScheduleUsecase {

	TransferScheduleResponse execute(
			CreateTransferScheduleRequest transferScheduleRequest);

}
