package com.primogemstudio.advancedfmk.mmd;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTextureAtlas;
import com.primogemstudio.advancedfmk.mmd.renderer.TextureManager;

import java.io.File;
import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.lang.NullPointerException;

import static org.lwjgl.opengl.GL15.*;

public class PMXModel implements AutoCloseable {
    @AccessFromNative
    private long ptr;
    @AccessFromNative
    private final Cleaner.Cleanable cleaner;
    private static int ID = 0;
    private final int id = ++ID;
    private final String texture = "mmd_texture" + id;
    @AccessFromNative
    private final MMDTextureAtlas atlas;
    public final TextureManager textureManager;
    public final int vertexCount, indexCount;
    public final Animation animation;
    private final int ibo;

    public PMXModel(File file) {
        if (file.getParentFile() == null) {
            throw new NullPointerException("Invaild path!");
        }
        load(file);
        vertexCount = getVertexCount();
        indexCount = getIndexCount();
        atlas = Loader.createAtlas(getTextures());
        textureManager = new TextureManager(atlas);
        animation = new Animation(this);
        textureManager.register(texture);
        mappingVertices();
        ibo = GlStateManager._glGenBuffers();
        var p = ptr;
        var tm = textureManager;
        var t_ibo = ibo;
        cleaner = SabaNative.cleaner.register(this, () -> {
            release(p);
            tm.release();
            RenderSystem.glDeleteBuffers(t_ibo);
        });
    }

    private native void load(File file);

    public native void render(long pointer, ByteBuffer constants);

    private native ByteBuffer getIndices();

    private native int getVertexCount();

    private native int getIndexCount();

    private native List<File> getTextures();

    private native void mappingVertices();

    private static native void release(long ptr);

    public void bindIndices() {
        GlStateManager._glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        RenderSystem.glBufferData(GL_ELEMENT_ARRAY_BUFFER, getIndices(), GL_STATIC_DRAW);
    }

    private float lastTime = System.nanoTime() / 1000000000f;
    @AccessFromNative
    private float animationTime = 0;
    private static final float RefreshRate = 1 / 30f;

    public void updateAnimation() {
        var time = System.nanoTime() / 1000000000f;
        var elapsed = time - lastTime;
        if (elapsed > RefreshRate) elapsed = RefreshRate;
        lastTime = time;
        animationTime += elapsed;
        var frame = animationTime / RefreshRate;
        if (frame > animation.maxFrame) animationTime = 0;
        animation.updateAnimation(frame, elapsed);
    }

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
    }

    public static class Animation extends ArrayList<File> {
        @AccessFromNative
        private final long ptr;
        @AccessFromNative
        private final PMXModel model;
        @AccessFromNative
        private int maxFrame;

        private Animation(PMXModel m) {
            ptr = m.ptr;
            model = m;
        }

        public final native void setupAnimation();

        private native void updateAnimation(float frame, float elapsed);
    }
}

