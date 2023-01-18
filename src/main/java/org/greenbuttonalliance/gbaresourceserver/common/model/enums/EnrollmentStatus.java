/*
 * Copyright (c) 2022 Green Button Alliance, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.greenbuttonalliance.gbaresourceserver.common.model.enums;

import java.util.EnumSet;

public enum EnrollmentStatus {
	UNENROLLED("unenrolled"),
	ENROLLED("enrolled"),
	ENROLLED_PENDING("enrolledPending");

	public final String schemaValue;

	EnrollmentStatus(String schemaValue) {
		this.schemaValue = schemaValue;
	}

	public static EnrollmentStatus getEnrollmentStatusFromSchemaValue(String schemaValue) {
		return EnumSet.allOf(EnrollmentStatus.class).stream()
			.filter(es -> es.schemaValue.equals(schemaValue))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No " + EnrollmentStatus.class.getCanonicalName() + " with schemaValue " + schemaValue));
	}
}