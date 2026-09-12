package com.atom.firefly;

import com.atom.firefly.config.FireflyConfig;
import com.atom.firefly.platform.Services;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("Initializing {} on {} ({})", Constants.MOD_NAME, Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        FireflyConfig.get();
    }
}