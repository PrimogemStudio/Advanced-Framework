package com.primogemstudio.advancedfmk

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.apache.logging.log4j.LogManager

class RenderDocHook : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        try {
            System.load("C:/Program Files/RenderDoc/renderdoc.dll")
        } catch (e: Throwable) {
            LogManager.getLogger().error(e.message, e)
        }
    }
}