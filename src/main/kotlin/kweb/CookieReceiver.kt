package kweb

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import kweb.util.KWebDSL
import kweb.util.json
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture

@ImplicitReflectionSerializer
@KWebDSL
class CookieReceiver(val receiver: WebBrowser) {
    fun set(name: String, value: Any, expires: Duration? = null, path: String? = null, domain: String? = null) {
        setString(name, json.stringify(value), expires, path, domain)
    }

    fun setString(name: String, value: String, expires: Duration? = null, path: String? = null, domain: String? = null) {
        val arguments = LinkedList<String>()
        arguments.add(json.stringify(name))
        arguments.add(json.stringify(value))
        if (expires != null) {
            arguments.add(expires.seconds.toString())
        }
        if (path != null) {
            arguments.add(path)
        }
        if (domain != null) {
            arguments.add(domain)
        }

        receiver.execute("docCookies.setItem(${arguments.joinToString(separator = ", ")});")
    }

    inline fun <reified V : Any> get(name: String): CompletableFuture<V?> = getString(name).thenApply {
        when (it) {
            null -> null
            else -> json.parse(it)
        }
    }

    fun getString(name: String): CompletableFuture<String?> {
        return receiver.evaluate("docCookies.getItem(${json.stringify(name)});")
                .thenApply {
                    if (it == "__COOKIE_NOT_FOUND_TOKEN__") {
                        null
                    } else {
                        it.toString()
                    }
                }
    }

    fun remove(name: String) {
        receiver.execute("docCookies.removeItem(${json.stringify(name)};")
    }
}