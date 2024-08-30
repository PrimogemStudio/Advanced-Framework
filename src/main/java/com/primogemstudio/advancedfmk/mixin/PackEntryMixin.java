package com.primogemstudio.advancedfmk.mixin;

import com.primogemstudio.advancedfmk.kui.GlobalData;
import com.primogemstudio.advancedfmk.kui.elements.RectangleElement;
import com.primogemstudio.advancedfmk.kui.yaml.YamlParser;
import com.primogemstudio.advancedfmk.kui.yaml.jvm.YamlCompiler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;

@Mixin(TransferableSelectionList.PackEntry.class)
public class PackEntryMixin {
    @Unique
    private static RectangleElement ui;

    static {
        try {
            ui = ((RectangleElement) new YamlCompiler(YamlParser.INSTANCE.parse(new String(PackEntryMixin.class.getClassLoader().getResourceAsStream("assets/advancedfmk/ui/resourcepack_icon.yaml").readAllBytes()))).build());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "render")
    public void renderIcon(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        ui.getPos().set((float) x, (float) y);
        ui.getSize().set((float) width, (float) height);
        ui.setTexturePath(atlasLocation);
        ui.getTextureUV().set(0, 1, 0, 1);
        ui.getColor().set(1f, 1f, 1f, 1f);
        ui.render(GlobalData.genData(instance, 0f));
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    public void renderBg(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int color) {
        ui.getPos().set((float) minX, (float) minY);
        ui.getSize().set((float) maxX - minX, (float) maxY - minY);
        ui.setTexturePath(null);
        ui.getTextureUV().set(0, 1, 0, 1);
        ui.getColor().set(
                FastColor.ARGB32.red(color) / 255f,
                FastColor.ARGB32.green(color) / 255f,
                FastColor.ARGB32.blue(color) / 255f,
                FastColor.ARGB32.alpha(color) / 255f
        );
        ui.render(GlobalData.genData(instance, 0f));
    }
}
