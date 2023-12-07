package com.primogemstudio.advancedui;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class AdvancedUI implements ModInitializer {
    public static final String MOD_ID = "advancedui";
    public static final ManagedCoreShader ROUNDED_RECT = ShaderEffectManager.getInstance().manageCoreShader(new ResourceLocation(MOD_ID, "rounded_rect"), DefaultVertexFormat.POSITION_COLOR);

    public void onInitialize() {
    }
}
