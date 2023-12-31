package com.primogemstudio.advancedui;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;

public class EarlyInitializer implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        try {
            System.load("C:/Program Files/RenderDoc/renderdoc.dll");
        } catch (Throwable e) {
            LogManager.getLogger().error(e.getMessage(), e);
        }
    }
}
