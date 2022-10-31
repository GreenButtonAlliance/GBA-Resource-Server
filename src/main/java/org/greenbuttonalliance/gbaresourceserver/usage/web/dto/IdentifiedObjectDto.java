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

package org.greenbuttonalliance.gbaresourceserver.usage.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Just a starting point for the API team, feel free to modify/delete as needed
 */
@Getter
@Setter
@Accessors(chain = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class IdentifiedObjectDto implements Serializable {
	@XmlElement
	private UUID uuid;
	private String description;
	private LocalDateTime published;
	private String selfLinkHref;
	private String selfLinkRel;
	private String upLinkHref;
	private String upLinkRel;
	private LocalDateTime updated;
}
