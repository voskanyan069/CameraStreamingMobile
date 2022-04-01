package am.camerastreamingmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var launchServerBtn: Button
    private lateinit var connectToServerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchServerBtn = findViewById(R.id.main_btn_launch_server)
        connectToServerBtn = findViewById(R.id.main_btn_connect_to_server)
        onButtonsClick()
    }

    private fun onButtonsClick() {
        connectToServerBtn.setOnClickListener {
            val intent = Intent(this, ClientLoginActivity::class.java)
            startActivity(intent)
        }
    }
}