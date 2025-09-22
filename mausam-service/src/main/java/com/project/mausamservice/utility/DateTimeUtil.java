package com.project.mausamservice.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {

	public LocalDateTime getDateTimeFromUnixUtc(final long UnixDateTime, final ZoneId zoneId) {
		return Instant.ofEpochSecond(UnixDateTime).atZone(zoneId).toLocalDateTime();
	}

	public ZoneId getZoneIdFromUnixTimeZoneUtc(final int unixTimeZone) {
		ZoneOffset offset = ZoneOffset.ofTotalSeconds(unixTimeZone);
		return ZoneId.ofOffset("UTC", offset);
	}
	
	public LocalDateTime toUTC(final String localTime, final ZoneOffset offset, final DateTimeFormatter formatter) {
		final LocalDateTime localDateTime = LocalDateTime.parse(localTime, formatter);
		final OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);
		return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
	}

	public LocalDateTime getZonedDateTime(final String localTime, final DateTimeFormatter formatter, final String timeZone) {
		final LocalDateTime localDateTime = LocalDateTime.parse(localTime, formatter);
		final ZoneId zoneId = ZoneId.of(timeZone);
		final ZonedDateTime zoneDateTime = localDateTime.atZone(zoneId);
		return zoneDateTime.toLocalDateTime();
	}

	public ZoneOffset getZoneOffset(double offsetHours) {
		int totalSeconds = (int) (offsetHours * 3600);
		ZoneOffset offset = ZoneOffset.ofTotalSeconds(totalSeconds);
		return offset;
	}

}
