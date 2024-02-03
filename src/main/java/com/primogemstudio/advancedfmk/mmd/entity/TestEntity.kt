package com.primogemstudio.advancedfmk.mmd.entity

import com.primogemstudio.advancedfmk.AdvancedFramework.Companion.MOD_ID
import com.primogemstudio.advancedfmk.mmd.Loader
import com.primogemstudio.advancedfmk.mmd.renderer.CustomRenderType
import com.primogemstudio.advancedfmk.network.UpdatePacket
import com.primogemstudio.mmdbase.io.PMXFile
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.renderer.RenderType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

class TestEntity(entityType: EntityType<out Entity>, level: Level) : Entity(entityType, level) {
    @Environment(EnvType.CLIENT)
    @JvmField
    var model: PMXFile? = null
    @Environment(EnvType.CLIENT)
    var renderType: RenderType? = null
    @Environment(EnvType.CLIENT)
    private var processed: Array<FloatArray>? = null
    private var mp = ""

    @Environment(EnvType.CLIENT)
    @JvmField
    var enable_pipeline = false
    private var modelName = ""

    @Environment(EnvType.CLIENT)
    fun getProcessed(): Array<FloatArray> {
        if (processed == null) rebuildBuffer()
        return processed!!
    }

    @Environment(EnvType.CLIENT)
    private fun rebuildBuffer() {
        if (model == null) return
        processed = Array(model!!.m_faces.size * 3) { floatArrayOf() }
        for (i in model!!.m_faces.indices) {
            for (it in model!!.m_faces[i].m_vertices.indices) {
                val vtx = model!!.m_vertices[model!!.m_faces[i].m_vertices[it]]
                val v = vtx.m_position
                val uv = vtx.m_uv
                processed!![i * 3 + it] = floatArrayOf(v.x, v.y, v.z, uv.x, uv.y, v.x / 16, v.y / 16, v.z / 16)
            }
        }
    }

    @Environment(EnvType.CLIENT)
    fun setModel(path: String) {
        if (Files.exists(Path(path))) {
            val file = File(path)
            modelName = file.name.replace(Regex("[^a-zA-Z0-9]+"), "_")
            reinitRenderLayer()
            model = Loader.load(file.absoluteFile.parent, file.name, modelName)
            processed = null
        }
    }

    @Environment(EnvType.CLIENT)
    fun reinitRenderLayer() {
        enable_pipeline = TestEntityRenderer.enable_pipeline
        renderType = CustomRenderType.mmd(
            ResourceLocation(MOD_ID, "mmd_$modelName"), enable_pipeline
        )
    }

    override fun defineSynchedData() {
    }

    override fun readAdditionalSaveData(compound: CompoundTag) {
        val path = compound.getString("Model")
        mp = path
    }

    override fun addAdditionalSaveData(compound: CompoundTag) {
        compound.putString("Model", mp)
    }

    fun updateModel(path: String) {
        mp = path
        UpdatePacket.send(this, path)
    }

    companion object {
        @Environment(EnvType.CLIENT)
        fun registerPacket() {
            ClientPlayNetworking.registerGlobalReceiver(
                ResourceLocation(
                    MOD_ID, "test_entity_add"
                )
            ) { client, handler, buf, _ ->
                val p = ClientboundAddEntityPacket(buf)
                val path = buf.readUtf()
                client.execute {
                    p.handle(handler)
                    val te = client.level!!.getEntity(p.id) as TestEntity
                    te.setModel(path)
                }
            }
        }
    }

    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> {
        val buf = PacketByteBufs.create()
        ClientboundAddEntityPacket(this).write(buf)
        buf.writeUtf(mp)
        return ServerPlayNetworking.createS2CPacket(ResourceLocation(MOD_ID, "test_entity_add"), buf)
    }
}