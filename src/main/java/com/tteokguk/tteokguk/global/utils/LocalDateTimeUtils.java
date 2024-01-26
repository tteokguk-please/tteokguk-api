package com.tteokguk.tteokguk.global.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtils {
	public static LocalDateTime convertBy(Long timestamp) {
		return LocalDateTime.ofInstant(
			Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()
		);
	}
}
