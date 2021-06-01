package injector

import generator.Inject
import generator.InjectAdd
import plugins.action.ActionPlugin
import viewer.JSONViewer
import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

class Injector {
    companion object {
        private var PLUGIN_NAME_SEPARATOR: String = "="
        private var PLUGIN_VALUES_SEPARATOR: String = ","
        private var PLUGINS_LOCATION: String = "./src/main/di.properties"
        private val map: MutableMap<String, List<KClass<*>>> = mutableMapOf()

        private fun init() {
            val scanner = Scanner(File(PLUGINS_LOCATION))
            while(scanner.hasNextLine()) {
                val line = scanner.nextLine()
                val parts = line.split(PLUGIN_NAME_SEPARATOR)
                val key = parts[0]
                val values = parts[1].split(PLUGIN_VALUES_SEPARATOR).map { Class.forName(it).kotlin }
                map[key] = values
            }
            scanner.close()
        }

        fun  create(viewer: JSONViewer) : JSONViewer {
            init()
            val clazz =  viewer::class

            clazz.declaredMemberProperties.forEach {

                //Checking for presentation plugin
                if(it.hasAnnotation<Inject>()) {
                    it.isAccessible = true
                    val key = clazz.simpleName + "." + it.name
                    if(map.containsKey(key)) {
                        // Will only ever parse one presentation plugin
                        val obj = map[key]!!.first().createInstance()
                        (it as KMutableProperty<*>).setter.call(viewer, obj)
                    }
                }

                //Checking for actions plugins
                if(it.hasAnnotation<InjectAdd>()) {
                    it.isAccessible = true
                    val key = clazz.simpleName + "." + it.name
                    if(map.containsKey(key)){
                        var actions = mutableListOf<ActionPlugin>()
                        map[key]!!.forEach { actionPlugin -> actions.add(actionPlugin.createInstance() as ActionPlugin)  }
                        (it as KMutableProperty<*>).setter.call(viewer, actions)
                    }
                }
            }

            return viewer
        }

        fun setPluginNameSeparator(separator: String): Companion {
            PLUGIN_NAME_SEPARATOR = separator
            return this
        }

        fun setPluginValuesSeparator(separator: String): Companion {
            PLUGIN_VALUES_SEPARATOR = separator
            return this
        }

        fun setPluginsLocation(location: String): Companion {
            PLUGINS_LOCATION = location
            return this
        }
    }
}