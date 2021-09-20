package com.github.murilompo.transferscheduling.usecase;

import com.github.murilompo.transferscheduling.dto.schedule.TransferScheduleResponse;
import java.util.List;

public interface ListAllScheduledTransfersUsecase {

	List<TransferScheduleResponse> execute();

}
