package com.stock.bookinventory.constants;

public class AppConstants {

	public static final String PAGE_SIZE = "200";

	public static final String OFFSET = "0";

	public static final long EXPIRED_DATE = System.currentTimeMillis() + 24 * 60 * 60 * 1000; // 24 hours

	public static final int STOCK_LOW_THRESHOLD = 10;

	public final int MAX_AUDIT_RETURN_VALUE_LENGTH = 2000;

	public final int LOCK_TIMEOUT_SECONDS = 60;

	public final int LOCK_MAX_RETRIES = 10;

	public final int LOCK_RETRY_DELAY_MILLISECONDS = 100;
}
