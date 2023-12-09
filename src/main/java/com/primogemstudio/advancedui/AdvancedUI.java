package com.primogemstudio.advancedui;

import com.primogemstudio.advancedui.render.Shaders;
import net.fabricmc.api.ModInitializer;

public class AdvancedUI implements ModInitializer {
    public static final String MOD_ID = "advancedui";

    public void onInitialize() {
        Shaders.init();
    }
}
