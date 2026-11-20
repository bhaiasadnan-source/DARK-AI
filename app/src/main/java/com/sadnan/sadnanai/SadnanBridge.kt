package com.sadnan.sadnanai

import android.webkit.WebView
import org.json.JSONObject
import java.lang.ref.WeakReference

object SadnanBridge {

    @Volatile
    var serviceReady: Boolean = false

    private var webViewRef: WeakReference<WebView>? = null

    fun attach(wv: WebView) {
        webViewRef = WeakReference(wv)
    }

    fun detach() {
        webViewRef = null
    }

    fun pushScreen(text: String, pkg: String) {
        val wv = webViewRef?.get() ?: return
        val t = JSONObject.quote(text)
        val p = JSONObject.quote(pkg)
        wv.post {
            wv.evaluateJavascript(
                "window.onScreenText && window.onScreenText($t, $p);",
                null
            )
        }
    }
}
