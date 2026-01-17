package com.stock.bookinventory.dto.audit;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class AuditLogEntryDTO {
    String timestamp;
    String userId;
    String action;
    String targetEntity;
    String targetId;
    String outcome; // success or fail
    String source;
    Long executionTimeMs;
    String errorMessage;
    String returnValue;
}
