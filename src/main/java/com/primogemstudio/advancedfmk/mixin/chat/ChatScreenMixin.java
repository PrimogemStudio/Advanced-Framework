package com.primogemstudio.advancedfmk.mixin.chat;

import com.primogemstudio.advancedfmk.UITestKt;
import com.primogemstudio.advancedfmk.render.ColorComponent;
import com.primogemstudio.advancedfmk.render.ColorIntExtentionsKt;
import com.primogemstudio.advancedfmk.render.FilterTypes;
import com.primogemstudio.advancedfmk.render.shape.AbstractBackdropableShape;
import com.primogemstudio.advancedfmk.render.shape.RoundedRectangle;
import com.primogemstudio.advancedfmk.render.uiframework.ComposedShape;
import kotlin.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    @Unique
    private AbstractBackdropableShape rect;
    @Unique
    private Pair<ComposedShape, Consumer<Map<String, Float>>> ui;
    /*@Unique
    private AbstractBackdropableShape micro$oftLogo = new RoundedRectangleTex(
            30, 30, 32, 32, Component.empty(), new BaseTexture(NativeImage.read(Files.newInputStream(Path.of("D:\\mods\\Advanced-UI\\out.png"))))
    )
            .thickness(0f)
            .smoothedge(0.001f)
            .color(1f, 1f, 1f, 1f)
            .radius(10f);*/

    public ChatScreenMixin() throws IOException {
        super(Component.empty());
    }
    @Inject(at = @At("HEAD"), method = "init")
    public void init(CallbackInfo ci) {
        ui = UITestKt.load();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    public void renderWidget(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int c) {
        if (rect == null) rect = new RoundedRectangle(minX, minY, maxX - minX, maxY - minY, Component.empty())
                .thickness(0f)
                .smoothedge(0.001f)
                .setType(FilterTypes.GAUSSIAN_BLUR)
                .addData("Radius", 20)
                .addData("EnableFrostGrass", false);
        ((RoundedRectangle) rect).resize(minX, minY, maxX - minX, maxY - minY)
                .color(
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.R),
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.G),
                        ColorIntExtentionsKt.colorFetchM(c, ColorComponent.B),
                        Math.max(1f - ColorIntExtentionsKt.colorFetchM(c, ColorComponent.A), 0.1f))
                .radius(Math.min(maxX - minX, maxY - minY) / 2f)
                .renderWidget(instance, 0, 0, 0f);

        // RenderQueue.draw(micro$oftLogo);
        ui.component2().accept(Map.of(
                "screen_width", (float) width,
                "screen_height", (float) height
        ));
        ui.component1().renderWidget(instance, 0, 0, 0f);
    }
}
