package com.primogemstudio.advancedfmk.mixin

import com.primogemstudio.advancedfmk.fontengine.BufferManager.renderTextNormal
import com.primogemstudio.advancedfmk.fontengine.BufferManager.updateBufferColor
import com.primogemstudio.advancedfmk.fontengine.ComposedFont
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.TitleScreen
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(TitleScreen::class)
class TitleScreenFontEngineMixin {
    @Inject(at = [At("RETURN")], method = ["render"])
    fun render(graphics: GuiGraphics?, mx: Int, my: Int, partialTick: Float, ci: CallbackInfo?) {
        ci?: return
        updateBufferColor(0xffffffff.toInt())
        renderTextNormal({ vertexConsumer, poseStack ->
            font.drawWrapText(
                vertexConsumer, poseStack, "测试abcd？?!", 200 + mx - mx, 200 + my - my, 9, 25, 0xffffffff.toInt()
            )
        }, graphics!!, partialTick)
    }

    private companion object {
        @Unique
        private val font = ComposedFont()
    }
}
