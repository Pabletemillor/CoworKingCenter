package p7;

public class OfficeConf extends Office {
    private ConfService confService;

    public OfficeConf(OfficeLocator officeLocator) {
        super(officeLocator);
        this.confService = new ConfService();
    }

    public OfficeConf(OfficeLocator officeLocator, String idCoworker) {
        super(officeLocator, idCoworker);
        this.confService = new ConfService();
    }

    public OfficeConf(OfficeLocator officeLocator, String idCoworker, ConfService confService) {
        super(officeLocator, idCoworker);
        setConfService(confService);
    }

    public ConfService getConfService() {
        return confService;
    }

    public void setConfService(ConfService confService) {
        this.confService = confService;
    }

    public void activateService() {
        confService.activate();
    }

    public void deactivateService() {
        confService.deactivate();
    }

    @Override
    public boolean hasConfService() {
        return confService != null && confService.isActivated();
    }

    @Override
    public String toString() {
        String conf = confService.isActivated() ? "Y" : "N";
        return super.getIdCoworker() == null ? super.getOfficeLocator().toString() 
            : super.getOfficeLocator().toString() + ";" + super.getIdCoworker() + ";" + conf + ";" + super.getEntryDate();
    }
}