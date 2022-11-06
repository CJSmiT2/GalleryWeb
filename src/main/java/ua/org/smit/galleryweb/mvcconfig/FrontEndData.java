package ua.org.smit.galleryweb.mvcconfig;

import java.util.Properties;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.getPropperties;

public class FrontEndData {

    private final String siteName;

    FrontEndData(Properties propperties) {
        siteName = getPropperties().getProperty("siteName");
    }

    public String getSiteName() {
        return siteName;
    }

}
