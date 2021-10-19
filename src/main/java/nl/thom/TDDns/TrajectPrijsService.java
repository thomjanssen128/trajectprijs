package nl.thom.TDDns;


public class TrajectPrijsService {

    private final ZoneService zoneService;
    private final PrijsService prijsService;

    public TrajectPrijsService(ZoneService zoneService, PrijsService prijsService) {
        this.zoneService = zoneService;
        this.prijsService = prijsService;
    }

    public int getZoneprijs(int aantalZones) {
        if (aantalZones <= 2) {
            return 2;
        } else {
            return prijsService.getZoneprijs(aantalZones);
        }
    }

    public int getAantalZones(String vertrek, String bestemming) {
        if (validateInput(vertrek) && validateInput(bestemming)) {
            return zoneService.getAantalZones(vertrek, bestemming);
        } else {
            throw new IllegalArgumentException("Stations hebben drie characters; AMS, UTR, etc.");
        }
    }

    private boolean validateInput(String input) {
        return input.length() == 3;
    }
}
