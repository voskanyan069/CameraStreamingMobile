package am.camerastreamingmobile

import android.accounts.NetworkErrorException
import android.util.Log
import java.io.InputStream
import java.net.Socket
import java.net.SocketException

class NetworkClient(val host: String, val port: Int) {
    private lateinit var socket: Socket
    private lateinit var socketInput: InputStream

    fun connectToHost(): Boolean {
        var rc = true
        val socketThread = Thread {
            try {
                socket = Socket(host, port)
                socketInput = socket.getInputStream()
            } catch (e: SocketException) {
                rc = false
            } catch (e: NetworkErrorException) {
                rc = false
            }
        }
        socketThread.start()
        socketThread.join()
        return rc
    }

    fun read(frameSize: Int, processData: (ByteArray) -> Unit) {
        val readerThread = Thread {
            while (socket.isConnected) {
                val buffer = receiveSocketData(frameSize)
                processData(buffer)
            }
            closeConnection()
        }
        readerThread.start()
    }

    private fun receiveSocketData(frameSize: Int): ByteArray {
        var totalRead = 0
        val buffer = ByteArray(frameSize)
        while (totalRead < frameSize) {
            val lastRead: Int = socketInput.read(
                buffer,
                totalRead,
                frameSize - totalRead
            )
            if (lastRead < 0) {
                Log.e("mLog", "not enough data in stream")
                closeConnection()
            }
            totalRead += lastRead
        }
        return buffer
    }

    fun closeConnection() {
        socketInput.close()
        socket.close()
    }
}