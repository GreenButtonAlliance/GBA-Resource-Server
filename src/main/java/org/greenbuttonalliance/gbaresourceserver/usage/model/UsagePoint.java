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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.greenbuttonalliance.gbaresourceserver.common.model.IdentifiedObject;
import org.greenbuttonalliance.gbaresourceserver.common.model.SummaryMeasurement;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.AmiBillingReadyKind;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.PhaseCodeKind;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.ServiceKind;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.UsagePointConnectedKind;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usage_point", schema = "usage")
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
public class UsagePoint extends IdentifiedObject {
	@Column(name = "role_flags")
	private byte[] roleFlags;

	@Column(name = "service_category", nullable = false)
	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.service_kind)", read = "service_category::TEXT")
	private ServiceKind serviceCategory;

	@Column
	private Short status;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "service_delivery_point_uuid", nullable = false)
	private ServiceDeliveryPoint serviceDeliveryPoint;

	@Column(name = "ami_billing_ready")
	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.ami_billing_ready_kind)", read = "ami_billing_ready::TEXT")
	private AmiBillingReadyKind amiBillingReady;

	@Column(name = "check_billing")
	private Boolean checkBilling;

	@Column(name = "connection_state", nullable = false)
	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.usage_point_connected_kind)", read = "connection_state::TEXT")
	private UsagePointConnectedKind connectionState;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "estimated_load_power_of_ten_multiplier")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "estimated_load_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "estimated_load_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "estimated_load_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "estimated_load_reading_type_ref"))
	})
	private SummaryMeasurement estimatedLoad;

	@Column
	private Boolean grounded;

	@Column(name = "is_sdp")
	private Boolean isSdp;

	@Column(name = "is_virtual")
	private Boolean isVirtual;

	@Column(name = "minimal_usage_expected")
	private Boolean minimalUsageExpected;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "nominal_service_voltage_power_of_ten_multiplier")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "nominal_service_voltage_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "nominal_service_voltage_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "nominal_service_voltage_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "nominal_service_voltage_reading_type_ref"))
	})
	private SummaryMeasurement nominalServiceVoltage;

	@Column(name = "outage_region")
	private String outageRegion;

	@Column(name = "phase_code", nullable = false)
	@Enumerated(EnumType.STRING)
	@ColumnTransformer(write = "CAST(? AS usage.phase_code_kind)", read = "phase_code::TEXT")
	private PhaseCodeKind phaseCode;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "rated_current_power_of_ten_multiplier")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "rated_current_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "rated_current_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "rated_current_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "rated_current_reading_type_ref"))
	})
	private SummaryMeasurement ratedCurrent;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride( name = "powerOfTenMultiplier", column = @Column(name = "rated_power_power_of_ten_multiplier")),
		@AttributeOverride( name = "timeStamp", column = @Column(name = "rated_power_time_stamp")),
		@AttributeOverride( name = "uom", column = @Column(name = "rated_power_uom")),
		@AttributeOverride( name = "value", column = @Column(name = "rated_power_value")),
		@AttributeOverride( name = "readingTypeRef", column = @Column(name = "rated_power_reading_type_ref"))
	})
	private SummaryMeasurement ratedPower;

	@Column(name = "read_cycle")
	private String readCycle;

	@Column(name = "read_route")
	private String readRoute;

	@Column(name="service_delivery_remark")
	private String serviceDeliveryRemark;

	@Column(name = "service_priority")
	private String servicePriority;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usage_point_pnode_ref", schema = "usage",
		joinColumns = {@JoinColumn(name = "usage_point_uuid", nullable = false)},
		inverseJoinColumns = {@JoinColumn(name = "pnode_ref_id", nullable = false)})
	private Set<PnodeRef> pnodeRefs = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usage_point_aggregate_node_ref", schema = "usage",
		joinColumns = {@JoinColumn(name = "usage_point_uuid", nullable = false)},
		inverseJoinColumns = {@JoinColumn(name = "aggregate_node_ref_id", nullable = false)})
	private Set<AggregateNodeRef> aggregateNodeRefs = new HashSet<>();

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "time_configuration_uuid", nullable = false)
	private TimeConfiguration timeConfiguration;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "retail_customer_uuid", nullable = false)
	private RetailCustomer retailCustomer;

	@OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL)
	private Set<MeterReading> meterReadings = new HashSet<>();

	@OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL)
	private Set<ElectricPowerQualitySummary> electricPowerQualitySummaries = new HashSet<>();

	@OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL)
	private Set<UsageSummary> usageSummaries = new HashSet<>();

	@OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL)
	private Set<Subscription> subscriptions = new HashSet<>();
}
