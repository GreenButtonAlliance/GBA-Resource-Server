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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.greenbuttonalliance.gbaresourceserver.common.model.IdentifiedObject;

@Entity
@Table(name = "subscription", schema = "usage") // the table name is plural?
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
//@RequiredArgsConstructor
public class Subscription extends IdentifiedObject {

	@Column(name = "hashed_id")
	private String hashedId;

//	@Column(name = "last_update")
//	private LocalDateTime lastUpdate;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "application_information_id", nullable = false)
	private ApplicationInformation applicationInformation;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn (name = "authorization_id")
	private Authorization authorization;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "retail_customer_id", nullable = false)
	private RetailCustomer retailCustomer;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "usage_point_id", nullable = false)
	private UsagePoint usagePoint;

	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "time_configuration_id", nullable = false)
	private TimeConfiguration timeConfiguration;
}
