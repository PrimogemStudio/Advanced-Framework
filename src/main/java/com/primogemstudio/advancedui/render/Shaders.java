package com.primogemstudio.advancedui.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.resources.ResourceLocation;

import static com.primogemstudio.advancedui.AdvancedUI.MOD_ID;

public class Shaders {
    public static final ManagedCoreShader ROUNDED_RECT = ShaderEffectManager.getInstance().manageCoreShader(new ResourceLocation(MOD_ID, "rounded_rect"), DefaultVertexFormat.POSITION_COLOR);
    public static final ManagedShaderEffect FAST_GAUSSIAN_BLUR = ShaderEffectManager.getInstance().manage(new ResourceLocation(MOD_ID, "shaders/filter/fast_gaussian_blur.json"));
    public static final ManagedShaderEffect GAUSSIAN_BLUR = ShaderEffectManager.getInstance().manage(new ResourceLocation(MOD_ID, "shaders/filter/gaussian_blur.json"));
    public static void init() {
    }
}
