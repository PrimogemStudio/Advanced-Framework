package com.primogemstudio.advancedfmk.live2d;

import com.mojang.blaze3d.platform.NativeImage;
import com.primogemstudio.advancedfmk.interfaces.AccessFromNative;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Live2DModel implements AutoCloseable {
    private static Random random = new Random();
    @AccessFromNative
    private long ptr;
    private Cleaner.Cleanable cleaner;
    public List<ResourceLocation> registeredTextures = new ArrayList<>();
    private boolean inited = false;

    public Live2DModel(String name, String path) {
        load(name, path);
        long ptr_i = ptr;

        cleaner = Live2DNative.cleaner.register(this, () -> release(ptr_i));
    }

    public void registerTextures() {
        if (inited) return;
        inited = true;
        getTextures().forEach(file -> {
            System.out.println("test1");
            var l = ResourceLocation.fromNamespaceAndPath("advancedfmk_animerenderer", String.valueOf(random.nextLong()));
            System.out.println("test2");
            try {
                Minecraft.getInstance().getTextureManager().register(l, new Live2DTexture(NativeImage.read(Files.newInputStream(file.toPath()))));
                System.out.println("test3");
            }
            catch (IOException e) {
                LogManager.getLogger("Loader").error("Error while loading texture", e);
            }
            System.out.println("test4");
            registeredTextures.add(l);
            System.out.println("test5");
        });
    }

    @Override
    public void close() {
        if (cleaner != null) cleaner.clean();
    }

    public static native void setGlfwGetTimeHandle(long handle);
    private native void load(String name, String path);
    public native List<File> getTextures();
    public native List<Float> getVertices(int texture_idx);
    public native void update();
    private static native void release(long ptr);
}
