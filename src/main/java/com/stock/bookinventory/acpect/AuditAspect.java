package com.stock.bookinventory.acpect;

import java.time.Instant;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.stock.bookinventory.dto.audit.AuditLogEntryDTO;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuditAspect {

	@Around("execution(public * com.stock.bookinventory.service..*(..))")
	public Object logAuditTrail(ProceedingJoinPoint joinPoint) throws Throwable {
		// Capture start state
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		Object[] methodArgs = joinPoint.getArgs();
		long startTime = System.currentTimeMillis();

		// Get current user ID (from security context or request header)
		String userId = getCurrentUserId();

		// Extract target ID from method arguments
		String targetId = extractTargetId(methodArgs);

		// Determine target entity from class name
		String targetEntity = extractTargetEntity(className);

		// Build action string
		String action = String.format("%s.%s", className.substring(className.lastIndexOf('.') + 1), methodName);

		log.info("AUDIT START - User: {}, Action: {}, Target: {} (ID: {}), Arguments: {}, Timestamp: {}", userId,
				action, targetEntity, targetId, Arrays.toString(methodArgs), startTime);

		AuditLogEntryDTO.AuditLogEntryDTOBuilder auditBuilder = AuditLogEntryDTO.builder()
				.timestamp(Instant.ofEpochMilli(startTime).toString()).userId(userId).action(action)
				.targetEntity(targetEntity).targetId(targetId).source(className);

		try {
			// Execute method
			Object result = joinPoint.proceed();

			// Build success log entry
			long executionTime = System.currentTimeMillis() - startTime;

			AuditLogEntryDTO auditLog = auditBuilder.outcome("SUCCESS").executionTimeMs(executionTime)
					.returnValue(result != null ? result.toString() : "null").build();

			log.info("AUDIT SUCCESS - {}", auditLog);

			return result;

		} catch (Exception ex) {
			// Handle exception and build failure log entry
			long executionTime = System.currentTimeMillis() - startTime;

			AuditLogEntryDTO auditLog = auditBuilder.outcome("FAILURE").executionTimeMs(executionTime)
					.errorMessage(String.format("%s: %s", ex.getClass().getSimpleName(), ex.getMessage())).build();

			log.error("AUDIT FAILURE - {}", auditLog);

			throw ex;
		}
	}

	/**
	 * Get current user ID from security context or request headers For now, returns
	 * "system" as default. Implement with Spring Security if needed.
	 */
	private String getCurrentUserId() {
		// TODO: Implement with Spring Security
		// SecurityContext context = SecurityContextHolder.getContext();
		// Authentication authentication = context.getAuthentication();
		// if (authentication != null && authentication.isAuthenticated()) {
		// return authentication.getName();
		// }
		return "system";
	}

	/**
	 * Extract target ID from method arguments Looks for Long/Integer IDs or objects
	 * with getId() method
	 */
	private String extractTargetId(Object[] args) {
		if (args == null || args.length == 0) {
			return "N/A";
		}

		// Check first argument for ID
		Object firstArg = args[0];
		if (firstArg instanceof Long || firstArg instanceof Integer) {
			return firstArg.toString();
		}

		// Try to extract ID from object using reflection
		try {
			if (firstArg != null) {
				var idMethod = firstArg.getClass().getMethod("getId");
				Object id = idMethod.invoke(firstArg);
				return id != null ? id.toString() : "N/A";
			}
		} catch (Exception e) {
			// No getId() method or reflection failed
		}

		return "N/A";
	}

	/**
	 * Extract target entity name from class name
	 */
	private String extractTargetEntity(String className) {
		// Extract service name and remove "Service" suffix
		String serviceName = className.substring(className.lastIndexOf('.') + 1);
		if (serviceName.endsWith("Service")) {
			serviceName = serviceName.substring(0, serviceName.length() - 7);
		}
		return serviceName;
	}

}
