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

package org.greenbuttonalliance.gbaresourceserver.usage.repository;

import com.github.f4b6a3.uuid.UuidCreator;
import lombok.RequiredArgsConstructor;
import org.greenbuttonalliance.gbaresourceserver.usage.model.ApplicationInformation;
import org.greenbuttonalliance.gbaresourceserver.usage.model.Authorization;
import org.greenbuttonalliance.gbaresourceserver.usage.model.RetailCustomer;
import org.greenbuttonalliance.gbaresourceserver.usage.model.Subscription;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.DataCustodianApplicationStatus;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.GrantType;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.OAuthError;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.ResponseType;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.ThirdPartyApplicationStatus;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.ThirdPartyApplicationType;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.ThirdPartyApplicationUse;
import org.greenbuttonalliance.gbaresourceserver.usage.model.enums.TokenEndpointMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationInformationRepositoryTest {
	private final ApplicationInformationRepository applicationInformationRepository;

	private static final String PRESENT_SELF_LINK = "https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation/1";
	private static final String NOT_PRESENT_SELF_LINK = "foobar";
	private static final String DUMMY_STRING = "test1";
	private static final DateTimeFormatter SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@BeforeEach
	public void initTestData() {
		applicationInformationRepository.deleteAllInBatch();
		applicationInformationRepository.saveAll(buildTestData());
	}

	@Test
	public void findByPresentId_returnsMatching() {
		UUID presentUuid = UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, PRESENT_SELF_LINK);
		UUID foundUuid = applicationInformationRepository.findById(presentUuid).map(ApplicationInformation::getUuid).orElse(null);

		Assertions.assertEquals(
			presentUuid,
			foundUuid,
			() -> String.format("findById with %s returns entity with ID %s", presentUuid, foundUuid)
		);
	}

	@Test
	public void findByNotPresentId_returnsEmpty() {
		UUID notPresentUuid = UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, NOT_PRESENT_SELF_LINK);
		Optional<ApplicationInformation> applicationInformation = applicationInformationRepository.findById(notPresentUuid);

		Assertions.assertTrue(
			applicationInformation.isEmpty(),
			() -> String.format("findById with %s returns entity with ID %s", notPresentUuid, applicationInformation.map(ApplicationInformation::getUuid).orElse(null))
		);
	}

	@Test
	public void findAll_returnsAll() {
		int findByAllSize = applicationInformationRepository.findAll().size();
		int testDataSize = buildTestData().size();

		Assertions.assertEquals(
			findByAllSize,
			testDataSize,
			() -> String.format("findByAll size of %s does not match test data size of %s", findByAllSize, testDataSize)
		);
	}

	@Test
	public void entityMappings_areNotNull() {
		ApplicationInformation fullyMappedApplicationInformation = applicationInformationRepository.findById(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, PRESENT_SELF_LINK)).orElse(null);
		Assumptions.assumeTrue(fullyMappedApplicationInformation != null);

		Function<ApplicationInformation, Optional<Set<Subscription>>> applicationInformationToSubscription = ai -> Optional.ofNullable(ai.getSubscriptions());
		// TODO test mapping to UsagePoint once entity is available

		Assertions.assertAll(
			"Entity mapping failures for applicationInformation " + fullyMappedApplicationInformation.getUuid(),
			Stream.of(applicationInformationToSubscription)
				.map(mappingFunc ->
					() -> Assertions.assertTrue(mappingFunc.apply(fullyMappedApplicationInformation).isPresent()))
		);
	}


	private static List<ApplicationInformation> buildTestData() {
		List<ApplicationInformation> applicationInformations = Arrays.asList(
			ApplicationInformation.builder()
				.description("Green Button Alliance Data Custodian Admin Application")
				.published(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
				.selfLinkHref(PRESENT_SELF_LINK)
				.selfLinkRel("self")
				.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation")
				.upLinkRel("up")
				.updated(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
				.authorizationServerAuthorizationEndpoint(DUMMY_STRING)
				.authorizationServerRegistrationEndpoint(null)
				.authorizationServerTokenEndpoint(DUMMY_STRING)
				.authorizationServerUri(null)
				.clientId(DUMMY_STRING)
				.clientIdIssuedAt(1403190000L)
				.clientName(DUMMY_STRING)
				.clientSecret(DUMMY_STRING)
				.clientSecretExpiresAt(0L)
				.clientUri(null)
				.contacts(new HashSet<>(Arrays.asList(
					"Contact1",
					"Contact2"
				)))
				.dataCustodianApplicationStatus(DataCustodianApplicationStatus.ON_HOLD)
				.dataCustodianBulkRequestUri(DUMMY_STRING)
				.dataCustodianId(DUMMY_STRING)
				.dataCustodianResourceEndpoint(DUMMY_STRING)
				.thirdPartyScopeSelectionScreenUri(null)
				.thirdPartyUserPortalScreenUri(null)
				.logoUri(null)
				.policyUri(null)
				.thirdPartyApplicationDescription(null)
				.thirdPartyApplicationStatus(ThirdPartyApplicationStatus.PRODUCTION)
				.thirdPartyApplicationType(ThirdPartyApplicationType.DEVICE)
				.thirdPartyApplicationUse(ThirdPartyApplicationUse.COMPARISONS)
				.thirdPartyPhone(null)
				.thirdPartyNotifyUri(DUMMY_STRING)
				.redirectUris(new HashSet<>(Arrays.asList(
					"Redirect1"
				)))
				.tosUri(null)
				.softwareId(DUMMY_STRING)
				.softwareVersion(DUMMY_STRING)
				.tokenEndpointAuthMethod(TokenEndpointMethod.BASIC)
				.responseType(ResponseType.CODE)
				.registrationClientUri(DUMMY_STRING)
				.registrationAccessToken(DUMMY_STRING)
				.thirdPartyScopeSelectionScreenUri(null)
				.dataCustodianScopeSelectionScreenUri(null)
				.grantTypes(new HashSet<>(Arrays.asList(
					GrantType.AUTHORIZATION_CODE
				)))
				.scopes(new HashSet<>(Arrays.asList(
					"Scope1",
					"Scope2",
					"Scope3"
				)))
				.subscriptions(new HashSet<>(
					Collections.singletonList(
						Subscription.builder()
							.description("description")
							.published(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
							.selfLinkHref("https://{domain}/espi/1_1/resource/Subscription/176")
							.selfLinkRel("self")
							.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/Subscription")
							.upLinkRel("up")
							.updated(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
							.hashedId("hashedId")
							.lastUpdate(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
							.authorization(
								Authorization.builder()
									.uuid(UUID.randomUUID())
									.description("Green Button Alliance Data Custodian Authorization")
									.published(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
									.selfLinkHref(PRESENT_SELF_LINK)
									.selfLinkRel("self")
									.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation")
									.upLinkRel("up")
									.updated(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
									.accessToken("accessToken")
									.authorizationUri(null)
									.apDuration(BigInteger.valueOf(21))
									.apStart(BigInteger.valueOf(654))
									.code("code")
									.error(OAuthError.INVALID_CLIENT)
									.errorDescription("errorDescription")
									.errorUri("errorUri")
									.expiresIn(BigInteger.valueOf(32164))
									.grantType(1)
									.ppDuration(BigInteger.valueOf(23123))
									.ppStart(BigInteger.valueOf(3241654))
									.refreshToken("23123124646")
									.resourceUri("resourceUri")
									.responseType(24)
									.scope("scope")
									.state("state")
									.thirdParty("thirdParty")
									.tokenType(54)
									.applicationInformationId(UUID.randomUUID())
									.retailCustomerId(UUID.randomUUID())
									.subscription(Subscription.builder().build())
									.build()
							)
							.retailCustomer(RetailCustomer.builder()
								.description("Type of Meter Reading Data")
								.published(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
								.selfLinkHref(PRESENT_SELF_LINK)
								.selfLinkRel("self")
								.upLinkHref("https://{domain}/espi/1_1/resource/RetailCustomer")
								.upLinkRel("up")
								.updated(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
								.enabled(Boolean.TRUE)
								.firstName("hello")
								.lastName("last")
								.password("password")
								.role("whatever")
								.username("Username")
								.build())
//				.usagePointId(1)
							.build()
					))
				)
				.build(),
			ApplicationInformation.builder()
				.description(DUMMY_STRING)
				.published(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
				.selfLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation/2")
				.selfLinkRel("self")
				.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation")
				.upLinkRel("up")
				.updated(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
				.authorizationServerAuthorizationEndpoint(DUMMY_STRING)
				.authorizationServerRegistrationEndpoint(null)
				.authorizationServerTokenEndpoint(DUMMY_STRING)
				.authorizationServerUri(null)
				.clientId(DUMMY_STRING)
				.clientIdIssuedAt(1403190000L)
				.clientName(DUMMY_STRING)
				.clientSecret(DUMMY_STRING)
				.clientSecretExpiresAt(0L)
				.clientUri(null)
				.contacts(new HashSet<>(Arrays.asList(
					"Contact1",
					"Contact3"
				)))
				.dataCustodianApplicationStatus(DataCustodianApplicationStatus.ON_HOLD)
				.dataCustodianBulkRequestUri(DUMMY_STRING)
				.dataCustodianId(DUMMY_STRING)
				.dataCustodianResourceEndpoint(DUMMY_STRING)
				.thirdPartyScopeSelectionScreenUri(null)
				.thirdPartyUserPortalScreenUri(null)
				.logoUri(null)
				.policyUri(null)
				.thirdPartyApplicationDescription(null)
				.thirdPartyApplicationStatus(ThirdPartyApplicationStatus.PRODUCTION)
				.thirdPartyApplicationType(ThirdPartyApplicationType.DEVICE)
				.thirdPartyApplicationUse(ThirdPartyApplicationUse.COMPARISONS)
				.thirdPartyPhone(null)
				.thirdPartyNotifyUri(DUMMY_STRING)
				.redirectUris(new HashSet<>(Arrays.asList(
					"Redirect2",
					"Redirect3"
				)))
				.tosUri(null)
				.softwareId(DUMMY_STRING)
				.softwareVersion(DUMMY_STRING)
				.tokenEndpointAuthMethod(TokenEndpointMethod.BASIC)
				.responseType(ResponseType.CODE)
				.registrationClientUri(DUMMY_STRING)
				.registrationAccessToken(DUMMY_STRING)
				.thirdPartyScopeSelectionScreenUri(null)
				.dataCustodianScopeSelectionScreenUri(null)
				.grantTypes(new HashSet<>(Arrays.asList(
					GrantType.AUTHORIZATION_CODE
				)))
				.scopes(new HashSet<>(Arrays.asList(
					"Scope4"
				)))
				.subscriptions(new HashSet<>(
						Collections.singletonList(
							Subscription.builder()
								.description("description")
								.published(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
								.selfLinkHref("https://{domain}/espi/1_1/resource/Subscription/176")
								.selfLinkRel("self")
								.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/Subscription")
								.upLinkRel("up")
								.updated(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
								.hashedId("hashedId")
								.lastUpdate(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
								.authorization(
									Authorization.builder()
										.uuid(UUID.randomUUID())
										.description("Green Button Alliance Data Custodian Authorization")
										.published(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
										.selfLinkHref(PRESENT_SELF_LINK)
										.selfLinkRel("self")
										.upLinkHref("https://{domain}/DataCustodian/espi/1_1/resource/ApplicationInformation")
										.upLinkRel("up")
										.updated(LocalDateTime.parse("2014-01-02 05:00:00", SQL_FORMATTER))
										.accessToken("accessToken")
										.authorizationUri(null)
										.apDuration(BigInteger.valueOf(21))
										.apStart(BigInteger.valueOf(654))
										.code("code")
										.error(OAuthError.INVALID_CLIENT)
										.errorDescription("errorDescription")
										.errorUri("errorUri")
										.expiresIn(BigInteger.valueOf(32164))
										.grantType(1)
										.ppDuration(BigInteger.valueOf(23123))
										.ppStart(BigInteger.valueOf(3241654))
										.refreshToken("23123124646")
										.resourceUri("resourceUri")
										.responseType(24)
										.scope("scope")
										.state("state")
										.thirdParty("thirdParty")
										.tokenType(54)
										.applicationInformationId(UUID.randomUUID())
										.retailCustomerId(UUID.randomUUID())
										.subscription(Subscription.builder().build())
										.build()
								)
								.retailCustomer(RetailCustomer.builder()
									.description("Type of Meter Reading Data")
									.published(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
									.selfLinkHref(PRESENT_SELF_LINK)
									.selfLinkRel("self")
									.upLinkHref("https://{domain}/espi/1_1/resource/RetailCustomer")
									.upLinkRel("up")
									.updated(LocalDateTime.parse("2012-10-24 04:00:00", SQL_FORMATTER))
									.enabled(Boolean.TRUE)
									.firstName("hello")
									.lastName("last")
									.password("password")
									.role("whatever")
									.username("Username")
									.build())
//				.usagePointId(1)
								.build()
						))
				)
				.build()
		);

		// hydrate UUIDs and entity mappings
		applicationInformations.forEach(ai -> {
			ai.setUuid(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, ai.getSelfLinkHref()));

			ai.getSubscriptions().forEach(sub -> {
				sub.setUuid(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, sub.getSelfLinkHref()));
				sub.setApplicationInformation(ai);
				sub.setAuthorization(Authorization.builder().build());
				sub.getAuthorization().setUuid(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, sub.getSelfLinkHref()));
				sub.setRetailCustomer(RetailCustomer.builder().build());
				sub.getRetailCustomer().setUuid(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, sub.getSelfLinkHref()));
			});

			// TODO hydrate UsagePoint reference once entity is available
		});
		return applicationInformations;
	}
}
