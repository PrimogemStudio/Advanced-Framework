package com.primogemstudio.advancedui.client;

import com.primogemstudio.advancedui.render.Shaders;
import net.fabricmc.api.ClientModInitializer;

public class AdvancedUIClient implements ClientModInitializer {
    public void onInitializeClient() {
        Shaders.init();
    }
}
