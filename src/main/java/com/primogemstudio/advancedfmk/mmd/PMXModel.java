package com.primogemstudio.advancedfmk.mmd;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTextureAtlas;
import com.primogemstudio.advancedfmk.mmd.renderer.TextureManager;

import java.io.File;
import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PMXModel implements AutoCloseable {
    @AccessFromNative
    private long ptr;
    @AccessFromNative
    private Cleaner.Cleanable cleaner;
    private static int ID = 0;
    private final int id = ++ID;
    private final String texture = "mmd_texture" + id;
    private final MMDTextureAtlas atlas;
    public final TextureManager textureManager;
    public final int vertexCount;
    public final Animation animation;

    public PMXModel(File file) {
        load(file);
        vertexCount = getVertexCount();
        atlas = Loader.createAtlas(getTextures());
        textureManager = new TextureManager(atlas);
        animation = new Animation(this);
        textureManager.register(texture);
        mappingVertices();
        var p = ptr;
        var tm = textureManager;
        cleaner = SabaNative.cleaner.register(this, () -> {
            SabaNative.release(PMXModel.class, p);
            tm.release();
        });
    }

    private native void load(File file);

    public native void render(ByteBuffer buff, ByteBuffer constants);

    private native int getVertexCount();

    private native List<File> getTextures();

    private native void mappingVertices();

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

