package br.com.loboneto.mesamobile.util

fun String.serverFormat(): String {
    val split = this.split("/")
    return "${split[2]}-${split[1]}-${split[0]}"
}

fun String.verifyUrl(): String {
    return if (!this.startsWith("http://") && !this.startsWith("https://")) {
        "http://$this"
    } else {
        this
    }
}

