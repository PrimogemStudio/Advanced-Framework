package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.GlobalData;
import com.primogemstudio.advancedfmk.kui.KUITest;
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TransferableSelectionList.PackEntry.class)
public class PackEntryMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "render")
    public void renderIcon(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        KUITest.Companion.getRes().subElement("icon").getPos().set((float) x, (float) y);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).getSize().set((float) width, (float) height);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).setTexturePath(atlasLocation);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).getColor().set(1f);
        KUITest.Companion.getRes().render(GlobalData.genData(instance, 0f));
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    public void renderSelection(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int color) {
        KUITest.Companion.getRes().subElement("icon").getPos().set((float) minX, (float) minY);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).getSize().set((float) maxX - minX, (float) maxY - minY);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).setTexturePath(null);
        ((RectangleElement) KUITest.Companion.getRes().subElement("icon")).getColor().set(
                FastColor.ARGB32.red(color) / 255f,
                FastColor.ARGB32.green(color) / 255f,
                FastColor.ARGB32.blue(color) / 255f,
                FastColor.ARGB32.alpha(color) / 255f
        );

        KUITest.Companion.getRes().render(GlobalData.genData(instance, 0f));
    }
}
