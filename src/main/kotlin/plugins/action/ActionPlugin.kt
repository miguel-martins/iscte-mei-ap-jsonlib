package plugins.action

import plugins.Plugin

interface ActionPlugin: Plugin {
    val name: String
}