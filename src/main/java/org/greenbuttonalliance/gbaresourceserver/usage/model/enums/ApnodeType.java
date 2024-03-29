/*
 * Copyright (c) 2022-2024 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.gbaresourceserver.usage.model.enums;

import java.util.EnumSet;

public enum ApnodeType {
	AG("AG"),
	CPZ("CPZ"),
	DPZ("DPZ"),
	LAP("LAP"),
	TH("TH"),
	SYS("SYS"),
	CA("CA"),
	DCA("DCA"),
	GA("GA"),
	GH("GH"),
	EHV("EHV"),
	ZN("ZN"),
	INT("INT"),
	BUS("BUS");

	public final String schemaValue;

	ApnodeType(String schemaValue) {
		this.schemaValue = schemaValue;
	}

	public static ApnodeType getOAuthErrorFromSchemaValue(String schemaValue) {
		return EnumSet.allOf(ApnodeType.class).stream()
			.filter(qor -> qor.schemaValue.equals(schemaValue))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No " + ApnodeType.class.getCanonicalName() + " with schemaValue " + schemaValue));
	}
}
