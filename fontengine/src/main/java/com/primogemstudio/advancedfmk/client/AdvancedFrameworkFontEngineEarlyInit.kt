package com.primogemstudio.advancedfmk.client

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.lwjgl.system.Configuration
import org.lwjgl.util.freetype.FreeType

class AdvancedFrameworkFontEngineEarlyInit : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        Configuration.HARFBUZZ_LIBRARY_NAME.set(FreeType.getLibrary());
    }
}