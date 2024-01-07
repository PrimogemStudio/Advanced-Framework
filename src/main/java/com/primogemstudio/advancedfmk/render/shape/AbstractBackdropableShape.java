package com.primogemstudio.advancedfmk.render.shape;

import com.primogemstudio.advancedfmk.render.RenderQueue;
import com.primogemstudio.advancedfmk.render.Renderable;
import com.primogemstudio.advancedfmk.render.filter.FilterType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBackdropableShape extends AbstractWidget implements Renderable {
    private FilterType type;
    private final Map<String, Object> data = new HashMap<>();
    public AbstractBackdropableShape(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public AbstractBackdropableShape setType(FilterType type) {
        this.type = type;
        return this;
    }

    public AbstractBackdropableShape addData(String s, Object o) {
        data.put(s, o);
        return this;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        updateStack(guiGraphics.pose().last().pose());
        if (type == null) RenderQueue.draw(this);
        else {
            data.forEach((s, o) -> RenderQueue.setFilterArg(type, s, o));
            RenderQueue.draw(this, type);
        }
        RenderQueue.flush(partialTick);
    }

    protected abstract void updateStack(Matrix4f matrix);

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, getMessage());
    }
}
