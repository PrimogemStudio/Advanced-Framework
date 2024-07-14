package com.primogemstudio.advancedfmk.client

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint

class AdvancedFrameworkFontEngineEarlyInit: PreLaunchEntrypoint {
    override fun onPreLaunch() {
        System.setProperty("org.lwjgl.harfbuzz.libname", "freetype")
    }
}