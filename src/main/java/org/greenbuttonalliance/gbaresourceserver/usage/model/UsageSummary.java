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

package org.greenbuttonalliance.gbaresourceserver.usage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.greenbuttonalliance.gbaresourceserver.common.model.DateTimeInterval;
import org.greenbuttonalliance.gbaresourceserver.common.model.IdentifiedObject;
import org.greenbuttonalliance.gbaresourceserver.common.model.SummaryMeasurement;
import org.greenbuttonalliance.gbaresourceserver.common.model.enums.Currency;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.CommodityKind;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.QualityOfReading;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usage_summaries", schema = "usage")
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@RequiredArgsConstructor
public class UsageSummary extends IdentifiedObject {

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "start", column = @Column(name = "billing_period_start")),
		@AttributeOverride( name = "duration", column = @Column(name = "billing_period_duration")),
	})
	private DateTimeInterval billingPeriod;

	@Column(name = "bill_last_period")
	private Long billLastPeriod;

	@Column(name = "bill_to_date")
	private Long billToDate;

	@Column(name = "cost_additional_last_period")
	private Long costAdditionalLastPeriod;

	@OneToMany(mappedBy = "usageSummary", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineItem> lineItems = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS public.currency)", read = "currency::TEXT")
	@Column
	private Currency currency;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "overall_consumption_last_period_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "overall_consumption_last_period_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "overall_consumption_last_period_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "overall_consumption_last_period_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "overall_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "overall_consumption_last_period_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "overall_consumption_last_period_uom::TEXT")
	private SummaryMeasurement overallConsumptionLastPeriod;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "current_billing_period_overall_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "current_billing_period_overall_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "current_billing_period_overall_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "current_billing_period_overall_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "current_billing_period_overall_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "current_billing_period_overall_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "current_billing_period_overall_consumption_uom::TEXT")
	private SummaryMeasurement currentBillingPeriodOverAllConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "current_day_last_year_net_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "current_day_last_year_net_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "current_day_last_year_net_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "current_day_last_year_net_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "current_day_last_year_net_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "current_day_last_year_net_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "current_day_last_year_net_consumption_uom::TEXT")
	private SummaryMeasurement currentDayLastYearNetConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "current_day_net_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "current_day_net_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "current_day_net_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "current_day_net_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "current_day_net_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "current_day_net_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "current_day_net_consumption_uom::TEXT")
	private SummaryMeasurement currentDayNetConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "current_day_overall_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "current_day_overall_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "current_day_overall_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "current_day_overall_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "current_day_overall_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "current_day_overall_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "current_day_overall_consumption_uom::TEXT")
	private SummaryMeasurement currentDayOverallConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "peak_demand_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "peak_demand_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "peak_demand_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "peak_demand_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "peak_demand_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "peak_demand_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "peak_demand_uom::TEXT")
	private SummaryMeasurement peakDemand;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "previous_day_last_year_overall_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "previous_day_last_year_overall_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "previous_day_last_year_overall_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "previous_day_last_year_overall_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "previous_day_last_year_overall_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "previous_day_last_year_overall_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "previous_day_last_year_overall_consumption_uom::TEXT")
	private SummaryMeasurement previousDayLastYearOverallConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "previous_day_net_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "previous_day_net_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "previous_day_net_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "previous_day_net_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "previous_day_net_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "previous_day_net_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "previous_day_net_consumption_uom::TEXT")
	private SummaryMeasurement previousDayNetConsumption;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "previous_day_overall_consumption_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "previous_day_overall_consumption_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "previous_day_overall_consumption_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "previous_day_overall_consumption_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "previous_day_overall_consumption_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "previous_day_overall_consumption_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "previous_day_overall_consumption_uom::TEXT")
	private SummaryMeasurement previousDayOverallConsumption;

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.quality_of_reading)", read = "quality_of_reading::TEXT")
	@Column(name = "quality_of_reading")
	private QualityOfReading qualityOfReading;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "ratchet_demand_potm")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "ratchet_demand_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "ratchet_demand_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "ratchet_demand_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "ratchet_demand_reading_type_ref"))
	})
	@ColumnTransformer(write = "CAST(? AS public.unit_multiplier_kind)", read = "ratchet_demand_potm::TEXT")
	@ColumnTransformer(write = "CAST(? AS public.unit_symbol_kind)", read = "ratchet_demand_uom::TEXT")
	private SummaryMeasurement ratchetDemand;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "start", column = @Column(name = "ratchet_demand_period_start")),
		@AttributeOverride( name = "duration", column = @Column(name = "ratchet_demand_period_duration")),
	})
	private DateTimeInterval ratchetDemandPeriod;

	@NonNull
	@Column(name = "status_time_stamp", nullable = false)
	private Long statusTimeStamp; //in epoch-seconds

	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.commodity_kind)", read = "commodity::TEXT")
	@Column(name = "commodity")
	private CommodityKind commodity;

	@Column(name = "tariff_profile")
	private String tariffProfile;

	@Column(name = "read_cycle")
	private String readCycle;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usage_summaries_tariff_rider_ref", schema = "usage",
		joinColumns = {@JoinColumn(name = "usage_summary_uuid", nullable = false)},
		inverseJoinColumns = {@JoinColumn(name = "tariff_rider_ref_id", nullable = false)})
	private Set<TariffRiderRef> tariffRiderRefs = new HashSet<>();

	@Embedded
	private BillingChargeSource billingChargeSource;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "usage_point_uuid", nullable = false)
	private UsagePoint usagePoint;
}
