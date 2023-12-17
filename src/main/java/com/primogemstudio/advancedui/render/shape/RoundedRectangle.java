package com.primogemstudio.advancedui.render.shape;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedui.render.RenderResource;
import com.primogemstudio.advancedui.render.Renderable;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static com.primogemstudio.advancedui.render.Shaders.ROUNDED_RECT;

public class RoundedRectangle implements Renderable {
    private final Matrix4f matrix;
    private final Vector2f center = new Vector2f();
    private final Vector2f size = new Vector2f();
    private final Vector4f color = new Vector4f();
    private final float radius;
    private final float thickness;

    public RoundedRectangle(Matrix4f matrix, Vector2f center, Vector2f size, Vector4f color, float radius, float thickness) {
        this.matrix = matrix;
        this.radius = radius;
        this.thickness = thickness;
        this.center.set(center);
        this.size.set(size);
        this.color.set(color);
    }

    public RoundedRectangle(Matrix4f matrix, float radius, float thickness) {
        this.matrix = matrix;
        this.radius = radius;
        this.thickness = thickness;
    }

    public RoundedRectangle resize(float x, float y, float w, float h) {
        size.set(w, h);
        center.set(x + w / 2, y + h / 2);
        return this;
    }

    public RoundedRectangle color(float r, float g, float b, float a) {
        color.set(r, g, b, a);
        return this;
    }

    @Override
    public void render(RenderResource res) {
        RenderSystem.setShader(ROUNDED_RECT::getProgram);
        ROUNDED_RECT.findUniform2f("Resolution").set(res.screenWidth, res.screenHeight);
        ROUNDED_RECT.findUniform2f("Center").set(center);
        ROUNDED_RECT.findUniform1f("Radius").set(radius);
        ROUNDED_RECT.findUniform1f("Thickness").set(thickness);
        ROUNDED_RECT.findUniform2f("Size").set(size);
        var x1 = center.x - size.x / 2;
        var x2 = center.x + size.x / 2;
        var y1 = center.y - size.y / 2;
        var y2 = center.y + size.y / 2;
        var buff = Tesselator.getInstance().getBuilder();
        buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buff.vertex(matrix, x1, y1, 0).color(color.x, color.y, color.z, color.w).endVertex();
        buff.vertex(matrix, x1, y2, 0).color(color.x, color.y, color.z, color.w).endVertex();
        buff.vertex(matrix, x2, y2, 0).color(color.x, color.y, color.z, color.w).endVertex();
        buff.vertex(matrix, x2, y1, 0).color(color.x, color.y, color.z, color.w).endVertex();
        RenderSystem.enableBlend();
        BufferUploader.drawWithShader(buff.end());
        RenderSystem.disableBlend();
    }
}
