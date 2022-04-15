package am.camerastreamingmobile

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvException
import org.opencv.core.CvType.CV_8UC1
import org.opencv.core.Mat
import org.opencv.core.Size

class ClientViewerActivity : AppCompatActivity() {
    private lateinit var frame: Mat

    private lateinit var bitmap: Bitmap
    private lateinit var frameImg: ImageView
    private lateinit var closeSocketBtn: ImageView

    private lateinit var networkClient: NetworkClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_viewer)
        frameImg = findViewById(R.id.client_viewer_img_frame)
        closeSocketBtn = findViewById(R.id.client_viewer_btn_close)
        OpenCVLoader.initDebug()
        initFrame()
        connectToSocket()
        readSocket()
        closeOnClick()
    }

    private fun initFrame() {
        frame = Mat.zeros(Size(FrameData.getWidth(), FrameData.getHeight()), CV_8UC1)
        bitmap = Bitmap.createBitmap(frame.width(), frame.height(), Bitmap.Config.ARGB_8888)
    }

    private fun connectToSocket() {
        val host: String = HostData.getHost()
        val port: Int = HostData.getPort()
        Log.i("mLog", "connecting to the $host:$port")
        networkClient = NetworkClient(host, port)
        if (!networkClient.connectToHost()) {
            Log.e("mLog", "failed to connect socket")
            Toast.makeText(
                this,
                "Failed to connect socket",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        } else {
            Toast.makeText(
                this,
                "Connected to the $host:$port",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun readSocket() {
        networkClient.read(FrameData.getFrameSize()) {
            Log.i("mLog", "received frame size: ${it.size}")
            showFrame(it)
        }
    }

    private fun showFrame(data: ByteArray) {
        frame.put(0, 0, data)
        try {
            Utils.matToBitmap(frame, bitmap)
        } catch (e: CvException) {
            Log.i("mLog", "error: ${e.message}")
            return
        }
        runOnUiThread {
            frameImg.setImageBitmap(bitmap)
        }
    }

    private fun closeOnClick() {
        closeSocketBtn.setOnClickListener {
            Log.i("mLog", "closing connection")
            networkClient.closeConnection()
            Toast.makeText(
                this,
                "Connected closed",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}