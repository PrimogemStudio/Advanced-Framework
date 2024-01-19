package com.primogemstudio.advancedfmk

import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.entity.TestEntityRenderer
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
import org.apache.logging.log4j.LogManager
import javax.swing.JFileChooser

class RenderDocHook : PreLaunchEntrypoint {
    override fun onPreLaunch() {
        try {
            System.load("C:/Program Files/RenderDoc/renderdoc.dll")
        } catch (e: Throwable) {
            LogManager.getLogger().error(e.message, e)
        }
    }
}