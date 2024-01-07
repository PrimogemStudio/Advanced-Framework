package com.primogemstudio.advancedfmk.client;

import com.primogemstudio.advancedfmk.render.Shaders;
import net.fabricmc.api.ClientModInitializer;

public class AdvancedFrameworkClient implements ClientModInitializer {
    public void onInitializeClient() {
        Shaders.init();
    }
}
