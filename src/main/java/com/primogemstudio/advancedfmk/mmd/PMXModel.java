package com.primogemstudio.advancedfmk.mmd;

import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;
import com.primogemstudio.advancedfmk.mmd.renderer.MMDTextureAtlas;
import com.primogemstudio.advancedfmk.mmd.renderer.TextureManager;

import java.io.File;
import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;
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

    public PMXModel(File file) {
        load(file);
        atlas = Loader.createAtlas(getTextures());
        textureManager = new TextureManager(atlas);
        textureManager.register(texture);
        mappingVertices();
    }

    private native void load(File file);

    public native int render(ByteBuffer buff, ByteBuffer matrix);

    private native List<File> getTextures();

    private native void mappingVertices();

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
        textureManager.release();
    }
}

