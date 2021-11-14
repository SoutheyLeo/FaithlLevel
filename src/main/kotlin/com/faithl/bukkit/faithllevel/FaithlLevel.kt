package com.faithl.bukkit.faithllevel

import com.alibaba.fastjson.JSONObject
import com.faithl.bukkit.faithllevel.internal.conf.Loader
import com.faithl.bukkit.faithllevel.util.JsonUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.env.RuntimeDependency
import taboolib.common.io.newFile
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.*
import taboolib.common.util.Version
import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin
import taboolib.platform.util.sendLang

@RuntimeDependency(value = "com.alibaba:fastjson:1.2.78")
object FaithlLevel:Plugin() {

    @Config("settings.yml", migrate = true,autoReload = true)
    lateinit var setting: SecuredFile

    val plugin by lazy { BukkitPlugin.getInstance() }
    var isOutDate = false
    var ap = false

    override fun onLoad() {
        Metrics(13122, pluginVersion, runningPlatform)
    }

    override fun onEnable() {
        init()
        checkUpdate()
        Loader.loadLevels()
        console().sendLang("Plugin-Enabled", pluginVersion,KotlinVersion.CURRENT.toString())
    }

    fun init(){
        if (Bukkit.getPluginManager().isPluginEnabled("AttributePlus")) {
            ap = true
            console().sendLang("Plugin-Hooked","AttributePlus")
        }
    }


    /**
     * Check update
     *
     * @param sender
     */
    fun checkUpdate(sender: Player? = null){
        if(!setting.getBoolean("Options.Check-Update"))
            return
        val json = JsonUtil.loadJson("https://api.faithl.com/version.php?plugin=FaithlLevel")
        val `object` = JSONObject.parseObject(json)
        val version = Version(`object`.getString("version"))
        if (version > Version(pluginVersion)){
            isOutDate = true
            if (sender == null)
                console().sendLang("Plugin-Update",pluginVersion,version)
            else
                sender.sendLang("Plugin-Update",pluginVersion,version.source,"https://www.mcbbs.net/forum.php?mod=viewthread&tid=1273118")
        }
    }

}