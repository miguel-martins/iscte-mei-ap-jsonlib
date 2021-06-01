package plugins

import viewer.JSONViewer

interface Plugin {
    fun execute(viewer: JSONViewer)
}