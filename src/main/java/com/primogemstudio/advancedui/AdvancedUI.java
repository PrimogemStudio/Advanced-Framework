package com.primogemstudio.advancedui;

import com.primogemstudio.advancedui.mmd.entity.Entities;
import net.fabricmc.api.ModInitializer;

public class AdvancedUI implements ModInitializer {
    public static final String MOD_ID = "advancedui";

    public void onInitialize() {
        Entities.INSTANCE.register();
    }
}
