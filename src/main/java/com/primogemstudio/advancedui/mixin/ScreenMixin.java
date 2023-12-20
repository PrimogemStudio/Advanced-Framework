package com.primogemstudio.advancedui.mixin;

import com.primogemstudio.advancedui.render.FilterTypes;
import com.primogemstudio.advancedui.render.RenderQueue;
import com.primogemstudio.advancedui.render.shape.RoundedRectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.primogemstudio.advancedui.test.ImguiRender.*;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "render", at = @At(value = "RETURN"))
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderQueue.draw(new RoundedRectangle(graphics.pose().last().pose(), Radius, Thickness).resize(Pos[0], Pos[1], Size[0], Size[1]).color(Color).smoothedge(SmoothEdge), FastBlur ? FilterTypes.FAST_GAUSSIAN_BLUR : FilterTypes.GAUSSIAN_BLUR);
        RenderQueue.setFilterArg(FilterTypes.GAUSSIAN_BLUR, "Radius", BlurRadius);
        RenderQueue.setFilterArg(FilterTypes.GAUSSIAN_BLUR, "EnableFrostGrass", FrostGrass);
        RenderQueue.flush(partialTick);
    }
}
