package nl.thom.TDDns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrajectPrijsServiceTest {

    String ams = "AMS";
    String utr = "UTR";
    String dhg = "DHG";

    @Mock
    private ZoneService zoneService;
    @Mock
    private PrijsService prijsService;
    @InjectMocks
    private TrajectPrijsService trajectPrijsService;

    @Test
    public void aantalZonesAmsUtrReturnsVierTest() {
        String from = ams;
        String to = utr;
        when(zoneService.getAantalZones(from, to)).thenReturn(4);
        int aantalZones = trajectPrijsService.getAantalZones(ams, utr);
        assertThat(4).isEqualTo(aantalZones);
    }

    @Test
    public void aantalZonesAmsDhgReturnsVijfTest() {
        String from = ams;
        String to = dhg;
        when(zoneService.getAantalZones(from, to)).thenReturn(5);
        int aantalZones = trajectPrijsService.getAantalZones(ams, dhg);
        assertThat(5).isEqualTo(aantalZones);
    }

    @ParameterizedTest
    @CsvSource(value = {"APD:AMS", "EWR:REW"}, delimiter = ':')
    public void aantalZonesAnderAnderReturnsTienTest(String from, String to) {
        when(zoneService.getAantalZones(from, to)).thenReturn(10);
        int aantalZones = trajectPrijsService.getAantalZones(from, to);
        assertThat(10).isEqualTo(aantalZones);
    }

    @Test
    public void vierZonesKostZesPerZoneTest() {
        when(prijsService.getZoneprijs(4)).thenReturn(6);
        int zonePrijs = trajectPrijsService.getZoneprijs(4);
        assertThat(6).isEqualTo(zonePrijs);
    }

    @Test
    public void VijfZonesKostVijfPerZoneTest() {
        when(prijsService.getZoneprijs(5)).thenReturn(5);
        int zonePrijs = trajectPrijsService.getZoneprijs(5);
        assertThat(5).isEqualTo(zonePrijs);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8, 100, 500, Integer.MAX_VALUE})
    public void BovenVijfZonesKostDriePerZoneTest(int zones) {
        when(prijsService.getZoneprijs(zones)).thenReturn(3);
        int zonePrijs = trajectPrijsService.getZoneprijs(zones);
        assertThat(3).isEqualTo(zonePrijs);
    }

    @ParameterizedTest
    @CsvSource(value = {"4:6", "5:5"}, delimiter = ':')
    public void multiValuesTest(String i, String j) {
        int zones = Integer.parseInt(i);
        int expected = Integer.parseInt(j);
        when(prijsService.getZoneprijs(zones)).thenReturn(expected);
        int zonePrijs = trajectPrijsService.getZoneprijs(zones);
        assertThat(expected).isEqualTo(zonePrijs);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    public void OnderDrieZonesKostTienVoorTrajectTest(int zones) {
        int zonePrijs = trajectPrijsService.getZoneprijs(zones);
        assertThat(2).isEqualTo(zonePrijs);
    }

    @Test
    public void getThatException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    trajectPrijsService.getAantalZones("TELANG", "OKE");
                });
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    trajectPrijsService.getAantalZones("YES", "NO");
                });
    }


}