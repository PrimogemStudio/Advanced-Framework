package com.primogemstudio.advancedfmk.render.shape;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.primogemstudio.advancedfmk.render.RenderResource;
import com.primogemstudio.advancedfmk.render.Shaders;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class RoundedRectangle extends AbstractBackdropableShape {
    private Matrix4f matrix = new Matrix4f();
    private final Vector2f center = new Vector2f();
    private final Vector2f size = new Vector2f();
    private final Vector4f color = new Vector4f();
    private float radius = 10F;
    private float thickness = 0.00F;
    private float smoothedge = 0.001F;

    public RoundedRectangle(float x, float y, float w, float h, Component message) {
        super((int) x, (int) y, (int) w, (int) h, message);
        resize(x, y, w, h);
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
    public RoundedRectangle radius(float rad) {
        this.radius = rad;
        return this;
    }
    public RoundedRectangle thickness(float thi) {
        this.thickness = thi;
        return this;
    }
    public RoundedRectangle smoothedge(float edg) {
        this.smoothedge = edg;
        return this;
    }

    public RoundedRectangle color(float[] col) {
        color.set(col);
        return this;
    }

    @Override
    public void render(RenderResource res) {
        RenderSystem.setShader(Shaders.ROUNDED_RECT::getProgram);
        Shaders.ROUNDED_RECT.findUniform2f("Resolution").set(res.getWidth(), res.getHeight());
        Shaders.ROUNDED_RECT.findUniform2f("Center").set(center);
        Shaders.ROUNDED_RECT.findUniform1f("Radius").set(radius);
        Shaders.ROUNDED_RECT.findUniform1f("Thickness").set(thickness);
        Shaders.ROUNDED_RECT.findUniform1f("SmoothEdge").set(smoothedge);
        Shaders.ROUNDED_RECT.findUniform2f("Size").set(size);
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

    protected void updateStack(Matrix4f matrix) {
        this.matrix = matrix;
    }
}
