package org.greenbuttonalliance.gbaresourceserver.customer.repository;

import com.github.f4b6a3.uuid.UuidCreator;
import org.greenbuttonalliance.gbaresourceserver.common.model.AcceptanceTest;
import org.greenbuttonalliance.gbaresourceserver.common.model.LifecycleDate;
import org.greenbuttonalliance.gbaresourceserver.common.model.PerCent;
import org.greenbuttonalliance.gbaresourceserver.customer.model.ElectronicAddress;
import org.greenbuttonalliance.gbaresourceserver.customer.model.EndDevice;
import org.greenbuttonalliance.gbaresourceserver.customer.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EndDeviceRepositoryTest {

	@Autowired
	private EndDeviceRepository endDeviceRepository;

	private static final String upLinkHref = "https://{domain}/espi/1_1/resource/EndDevice";

	// for testing findById
	private static final String PRESENT_SELF_LINK = "https://{domain}/espi/1_1/resource/EndDevice/174";

	private static final String NOT_PRESENT_SELF_LINK = "foobar";

	private static final DateTimeFormatter SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@BeforeEach
	public void initTestData() {
		endDeviceRepository.deleteAllInBatch();
		endDeviceRepository.saveAll(buildTestData());
	}

	@Test
	public void findByPresentId_returnsMatching() {
		UUID presentUuid = UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, PRESENT_SELF_LINK);
		UUID foundUuid = endDeviceRepository.findById(presentUuid).map(EndDevice::getUuid).orElse(null);

		Assertions.assertEquals(
			presentUuid,
			foundUuid,
			() -> String.format("findById with %s returns entity with ID %s", presentUuid, foundUuid)
		);
	}

	@Test
	public void findByNotPresentId_returnsEmpty() {
		UUID notPresentUuid = UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, NOT_PRESENT_SELF_LINK);
		Optional<EndDevice> endDevice = endDeviceRepository.findById(notPresentUuid);

		Assertions.assertTrue(
			endDevice.isEmpty(),
			() -> String.format("findById with %s returns entity with ID %s", notPresentUuid, endDevice.map(EndDevice::getUuid).orElse(null))
		);
	}

	@Test
	public void findAll_returnsAll() {
		int findByAllSize = endDeviceRepository.findAll().size();
		int testDataSize = buildTestData().size();

		Assertions.assertEquals(
			findByAllSize,
			testDataSize,
			() -> String.format("findByAll size of %s does not match test data size of %s", findByAllSize, testDataSize)
		);
	}

	@Test
	public void entityMappings_areNotNull() {
		EndDevice fullyMappedEndDevice = endDeviceRepository.findById(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, PRESENT_SELF_LINK)).orElse(null);
		Assumptions.assumeTrue(fullyMappedEndDevice != null);

		// first-level mappings; directly from fullyMappedEndDevice
		Function<EndDevice, Optional<ElectronicAddress>> endDeviceToElectronicAddress = ed -> Optional.ofNullable(ed.getElectronicAddress());
		Function<EndDevice, Optional<Status>> endDeviceToStatus = ed -> Optional.ofNullable(ed.getStatus());

		Assertions.assertAll(
			"Entity mapping failures for customer account " + fullyMappedEndDevice.getUuid(),
			Stream.of(endDeviceToElectronicAddress,
					endDeviceToStatus)
				.map(mappingFunc ->
					() -> Assertions.assertTrue(mappingFunc.apply(fullyMappedEndDevice).isPresent()))
		);
	}

	private static List<EndDevice> buildTestData() {
		List<EndDevice> endDevices = Arrays.asList(
			EndDevice.builder()
				.description("description")
				.published(LocalDateTime.parse("2022-03-01 05:00:00", SQL_FORMATTER))
				.selfLinkHref(PRESENT_SELF_LINK)
				.selfLinkRel("self")
				.upLinkHref(upLinkHref)
				.upLinkRel("up")
				.updated(LocalDateTime.parse("2022-03-01 05:00:00", SQL_FORMATTER))
				.type("type")
				.utcNumber("utcNumber")
				.serialNumber("serialNumber")
				.lotNumber("lotNumber")
				.purchasePrice(1L)
				.critical(true)
				.electronicAddress(ElectronicAddress.builder()
					.lan("lan")
					.build())
				.lifecycle(LifecycleDate.builder()
					.manufacturedDate(1L)
					.build())
				.acceptanceTest(AcceptanceTest.builder()
					.type("type")
					.build())
				.initialCondition("initialCondition")
				.initialLossOfLife(PerCent.builder()
					.percent(0)
					.build())
				.status(Status.builder()
					.value("value")
					.build())
				.isVirtual(true)
				.isPan(true)
				.installCode("installCode")
				.amrSystem("amrSystem")
				.build(),

			EndDevice.builder()
				.description("description")
				.published(LocalDateTime.parse("2022-03-02 05:00:00", SQL_FORMATTER))
				.selfLinkHref("https://{domain}/espi/1_1/resource/EndDevice/175")
				.selfLinkRel("self")
				.upLinkHref(upLinkHref)
				.upLinkRel("up")
				.updated(LocalDateTime.parse("2022-03-02 05:00:00", SQL_FORMATTER))
				.type("type")
				.utcNumber("utcNumber")
				.serialNumber("serialNumber")
				.lotNumber("lotNumber")
				.purchasePrice(1L)
				.critical(true)
				.electronicAddress(ElectronicAddress.builder()
					.lan("lan")
					.build())
				.lifecycle(LifecycleDate.builder()
					.manufacturedDate(1L)
					.build())
				.acceptanceTest(AcceptanceTest.builder()
					.type("type")
					.build())
				.initialCondition("initialCondition")
				.initialLossOfLife(PerCent.builder()
					.percent(0)
					.build())
				.status(Status.builder()
					.value("value")
					.build())
				.isVirtual(true)
				.isPan(true)
				.installCode("installCode")
				.amrSystem("amrSystem")
				.build(),

			EndDevice.builder()
				.description("description")
				.published(LocalDateTime.parse("2022-03-03 05:00:00", SQL_FORMATTER))
				.selfLinkHref("https://{domain}/espi/1_1/resource/EndDevice/176")
				.selfLinkRel("self")
				.upLinkHref(upLinkHref)
				.upLinkRel("up")
				.updated(LocalDateTime.parse("2022-03-03 05:00:00", SQL_FORMATTER))
				.type("type")
				.utcNumber("utcNumber")
				.serialNumber("serialNumber")
				.lotNumber("lotNumber")
				.purchasePrice(1L)
				.critical(true)
				.electronicAddress(ElectronicAddress.builder()
					.lan("lan")
					.build())
				.lifecycle(LifecycleDate.builder()
					.manufacturedDate(1L)
					.build())
				.acceptanceTest(AcceptanceTest.builder()
					.type("type")
					.build())
				.initialCondition("initialCondition")
				.initialLossOfLife(PerCent.builder()
					.percent(0)
					.build())
				.status(Status.builder()
					.value("value")
					.build())
				.isVirtual(true)
				.isPan(true)
				.installCode("installCode")
				.amrSystem("amrSystem")
				.build()
		);

		// hydrate UUIDs
		endDevices.forEach(ed -> ed.setUuid(UuidCreator.getNameBasedSha1(UuidCreator.NAMESPACE_URL, ed.getSelfLinkHref())));
		return endDevices;
	}
}
