package am.camerastreamingmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ClientLoginActivity : AppCompatActivity() {
    private lateinit var hostInput: EditText
    private lateinit var portInput: EditText
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_login)

        hostInput = findViewById(R.id.client_login_input_host)
        portInput = findViewById(R.id.client_login_input_port)
        submitBtn = findViewById(R.id.client_login_btn_submit)
        onSubmit()
    }

    private fun onSubmit() {
        submitBtn.setOnClickListener {
            val host: String = hostInput.text.toString()
            val port: Int = portInput.text.toString().toInt()

            if (host.isNotEmpty() && port > 0) {
                HostData.setHostInfo(host, port)
                intent = Intent(this, ClientViewerActivity::class.java)
                startActivity(intent)
            }
        }
    }
}