package com.linkdevelopment.walid44443.conf


class SharedCodeConfiguration {
    companion object {
        val url: String
            get() {
                return "https://newsapi.org/"
            }
        val apiKey: String
            get() {
                return "533af958594143758318137469b41ba9"
            }
        val targetSource: String
            get() {
                return "the-next-web"
            }
        val connectTimeoutMillis: Long
            get() {
                return 60000
            }
        val requestTimeoutMillis: Long
            get() {
                return 60000
            }
    }
}