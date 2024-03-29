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

package org.greenbuttonalliance.gbaresourceserver.common.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.greenbuttonalliance.gbaresourceserver.common.model.enums.UnitMultiplierKind;
import org.greenbuttonalliance.gbaresourceserver.common.model.enums.UnitSymbolKind;
import org.hibernate.annotations.ColumnTransformer;

@Embeddable
@Getter
@Setter
@Accessors(chain = true)
public class SummaryMeasurement {

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)")
	private UnitMultiplierKind powerOfTenMultiplier;

	private Long timeStamp; //in epoch-seconds

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)")
	private UnitSymbolKind uom;

	private Long value;

	private String readingTypeRef;

}
