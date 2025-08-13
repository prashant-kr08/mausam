package com.project.mausam.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class MausamCommonHelper {

	public LocalDateTime getDateTimeFromUnixUtc(final long UnixDateTime, final ZoneId zoneId) {
		return Instant.ofEpochSecond(UnixDateTime).atZone(zoneId).toLocalDateTime();
	}

	public ZoneId getZoneIdFromUnixTimeZoneUtc(final int unixTimeZone) {
		ZoneOffset offset = ZoneOffset.ofTotalSeconds(unixTimeZone);
		return ZoneId.ofOffset("UTC", offset);
	}

}
