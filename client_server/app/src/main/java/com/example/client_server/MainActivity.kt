package com.example.client_server

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.client_server.ui.theme.Client_serverTheme

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.net.*

class MainActivity : AppCompatActivity() {
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var textViewResponse: TextView

    private val serverIP = "10.0.2.2" // Thay bằng IP của Server
    private val serverPort = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        textViewResponse = findViewById(R.id.textViewResponse)

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                Thread {
                    val response = sendMessageToServer(message)
                    runOnUiThread {
                        textViewResponse.text = "Phản hồi từ Server: $response"
                    }
                }.start()
            }
        }
    }

    private fun sendMessageToServer(message: String): String {
        return try {
            val socket = Socket(serverIP, serverPort)
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

            writer.println(message)
            val response = reader.readLine()

            socket.close()
            response ?: "Không nhận được phản hồi"
        } catch (e: Exception) {
            "Lỗi: ${e.message}"
        }
    }
}