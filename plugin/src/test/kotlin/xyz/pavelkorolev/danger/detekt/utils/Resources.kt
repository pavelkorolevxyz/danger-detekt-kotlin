package xyz.pavelkorolev.danger.detekt.utils

import java.io.File

/**
 * Resources utilities
 */
internal object Resources {

    /**
     * Gets file reference from resources by name
     */
    fun getFile(name: String): File {
        val url = ClassLoader.getSystemResource(name)
        return File(url.toURI())
    }
}
