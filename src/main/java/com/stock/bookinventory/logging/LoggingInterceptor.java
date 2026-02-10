package com.stock.bookinventory.logging;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private static final String START_TIME_ATTRIBUTE = "startTime";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME_ATTRIBUTE, startTime);

		String clientIp = getClientIpAddress(request);
		String queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";

		log.info(">>> Incoming Request: Method={}, URI={}{}, ClientIP={}, UserAgent={}", request.getMethod(),
				request.getRequestURI(), queryString, clientIp, request.getHeader("User-Agent"));

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// Log response details after handler execution but before view rendering
		log.debug("Handler execution completed for: Method={}, URI={}", request.getMethod(), request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);

		if (startTime != null) {
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;

			String clientIp = getClientIpAddress(request);
			int statusCode = response.getStatus();
			String statusMessage = getStatusMessage(statusCode);

			if (ex != null) {
				log.error(
						"<<< Request Failed: Method={}, URI={}, ClientIP={}, Status={} ({}), Duration={}ms, Exception={}",
						request.getMethod(), request.getRequestURI(), clientIp, statusCode, statusMessage, duration,
						ex.getMessage(), ex);
			} else {
				log.info("<<< Response Sent: Method={}, URI={}, ClientIP={}, Status={} ({}), Duration={}ms",
						request.getMethod(), request.getRequestURI(), clientIp, statusCode, statusMessage, duration);
			}
		}
	}

	/**
	 * Get the real client IP address considering proxy headers
	 */
	private String getClientIpAddress(@NotNull HttpServletRequest request) {
		String[] headers = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
				"HTTP_X_FORWARDED_FOR"};

		for (String header : headers) {
			String clientIp = request.getHeader(header);
			if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
				// Handle multiple IPs in X-Forwarded-For header (take the first one)
				return clientIp.contains(",") ? clientIp.split(",")[0].trim() : clientIp;
			}
		}

		return request.getRemoteAddr();
	}

	/**
	 * Get HTTP status message for the given status code
	 */
	private String getStatusMessage(int statusCode) {
		try {
			HttpStatus status = HttpStatus.valueOf(statusCode);
			return status.getReasonPhrase();
		} catch (IllegalArgumentException e) {
			return "Unknown Status";
		}
	}
}
