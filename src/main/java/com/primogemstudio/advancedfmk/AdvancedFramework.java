package com.primogemstudio.advancedfmk;

import com.primogemstudio.advancedfmk.mmd.entity.Entities;
import net.fabricmc.api.ModInitializer;

public class AdvancedFramework implements ModInitializer {
    public static final String MOD_ID = "advancedfmk";

    public void onInitialize() {
        Entities.INSTANCE.register();
    }
}
