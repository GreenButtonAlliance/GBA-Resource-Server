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

package org.greenbuttonalliance.gbaresourceserver.usage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
public abstract class IdentifiedObject {

	@Id
	private UUID uuid;

	@Column
	private String description;

	@Column
	private LocalDateTime published;

	@Column(name = "self_link_href")
	private String selfLinkHref;

	@Column(name = "self_link_rel")
	private String selfLinkRel;

	@Column(name = "up_link_href")
	private String upLinkHref;

	@Column(name = "up_link_rel")
	private String upLinkRel;

	@Column
	private LocalDateTime updated;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
			return false;
		}

		IdentifiedObject iObj = (IdentifiedObject)obj;
		return Objects.equals(uuid, iObj.uuid);
	}

	public abstract String getDataCustodianBulkRequestURI();

	public abstract String getThirdPartyScopeSelectionURI();

	public abstract String getThirdPartyUserPortalScreenURI();

	public abstract String getClient_secret();

	public abstract String getLogo_uri();

	public abstract String getClient_name();

	public abstract String getClient_uri();

	public abstract String getRedirect_uri();

	public abstract String getClient_id();

	public abstract String getTos_uri();

	public abstract String getPolicy_uri();

	public abstract String getSoftware_id();
}
