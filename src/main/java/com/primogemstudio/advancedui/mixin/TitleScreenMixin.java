package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.render.FilterTypes;
import com.primogemstudio.advancedui.render.RenderQueue;
import com.primogemstudio.advancedui.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.primogemstudio.advancedui.test.ImguiRender.*;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Inject(method = "render", at = @At(value = "RETURN"))
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderQueue.draw(new RoundedRectangle(graphics.pose().last().pose(), Radius, Thickness).resize(Pos[0], Pos[1], Size[0], Size[1]).color(Color), FilterTypes.GAUSSIAN_BLUR);
        RenderQueue.setFilterArg(FilterTypes.GAUSSIAN_BLUR, "Radius", Radius);
        RenderQueue.setFilterArg(FilterTypes.GAUSSIAN_BLUR, "EnableFrostGrass", FrostGrass);
        RenderQueue.flush(partialTick);
    }
}
