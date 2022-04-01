package am.camerastreamingmobile

object HostData {
    private var host: String = ""
    private var port: Int = 0

    fun setHostInfo(host: String, port: Int) {
        this.host = host
        this.port = port
    }

    fun getHost(): String {
        return host
    }

    fun getPort(): Int {
        return port
    }
}