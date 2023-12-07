package com.primogemstudio.advancedui;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class AdvancedUI implements ModInitializer {
    public static final String MOD_ID = "advancedui";
    public static final ManagedShaderEffect ROUNDED_RECT = ShaderEffectManager.getInstance().manage(new ResourceLocation(MOD_ID, "shaders/post/rounded_rect.json"));
    public void onInitialize() {

    }
}
