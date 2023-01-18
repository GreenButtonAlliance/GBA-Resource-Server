/*
 * Copyright (c) 2022-2023 Green Button Alliance, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greenbuttonalliance.gbaresourceserver.usage.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.greenbuttonalliance.gbaresourceserver.usage.model.MeterReading;

import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
public class MeterReadingDto extends IdentifiedObjectDto {
//	private DateTimeIntervalDto interval;
//	private Set<IntervalBlockDto> IntervalReading = new HashSet<>(); // unusual naming convention to match NAESB schema


	public static MeterReadingDto fromMeterReading(MeterReading meterReading) {
		return Optional.ofNullable(meterReading)
			.map(ib -> new IdentifiedObjectDtoSubclassFactory<>(MeterReadingDto::new).create(ib))
//				.setInterval(DateTimeIntervalDto.fromDateTimeInterval(ib.getInterval()))
//				.setIntervalReading(ib.getIntervalReadings().stream()
//					.map(IntervalReadingDto::fromIntervalReading)
//					.collect(Collectors.toSet())))
			.orElse(null);
	}
}
