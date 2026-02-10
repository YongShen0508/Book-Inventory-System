package com.stock.bookinventory.acpect;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.stock.bookinventory.controller..*(..))")
	public void controllerMethod() {
	}

	@Pointcut("execution(* com.stock.bookinventory.service..*(..))")
	public void serviceMethod() {
	}

	@Pointcut("execution(* com.stock.bookinventory.repository..*(..))")
	public void repositoryMethod() {
	}

	@Around("controllerMethod() || serviceMethod() || repositoryMethod()")
	public Object logAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		String layer = getLayer(joinPoint);
		String methodName = joinPoint.getSignature().toShortString();
		String args = formatArgs(joinPoint.getArgs());

		logger.info("[{}] Executing: {} with args: {}", layer, methodName, args);

		long startTime = System.currentTimeMillis();

		try {
			Object result = joinPoint.proceed();
			long executionTime = System.currentTimeMillis() - startTime;

			logger.info("[{}] Successfully executed: {} in {}ms - Result: {}", layer, methodName, executionTime,
					formatResult(result));

			return result;
		} catch (Exception e) {
			long executionTime = System.currentTimeMillis() - startTime;

			logger.error("[{}] Exception in: {} after {}ms - Error: {}", layer, methodName, executionTime,
					e.getMessage(), e);

			throw e;
		}
	}

	private String getLayer(ProceedingJoinPoint joinPoint) {
		String className = joinPoint.getSignature().getDeclaringTypeName();

		if (className.contains(".controller.")) {
			return "CONTROLLER";
		} else if (className.contains(".service.")) {
			return "SERVICE";
		} else if (className.contains(".repository.")) {
			return "REPOSITORY";
		}

		return "UNKNOWN";
	}

	private String formatArgs(Object[] args) {
		if (args == null || args.length == 0) {
			return "[]";
		}

		return Arrays.stream(args).map(arg -> {
			if (arg == null) {
				return "null";
			}
			String argStr = arg.toString();
			return argStr.length() > 100 ? argStr.substring(0, 100) + "..." : argStr;
		}).collect(Collectors.joining(", ", "[", "]"));
	}

	private String formatResult(Object result) {
		if (result == null) {
			return "null";
		}

		String resultStr = result.toString();
		return resultStr.length() > 200 ? resultStr.substring(0, 200) + "..." : resultStr;
	}
}
