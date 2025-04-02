package com.example.send_sms

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
import com.example.send_sms.ui.theme.Send_smsTheme
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    private lateinit var editTextMessage: EditText
    private lateinit var editTextIP: EditText
    private lateinit var buttonSend: Button
    private lateinit var textViewReceived: TextView

    private val PORT = 9876 // Cổng UDP mặc định
    private var isReceiving = true // Kiểm soát luồng nhận tin nhắn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        editTextMessage = findViewById(R.id.editTextMessage)
        editTextIP = findViewById(R.id.editTextIP)
        buttonSend = findViewById(R.id.buttonSend)
        textViewReceived = findViewById(R.id.textViewReceived)

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            val ipAddress = editTextIP.text.toString()

            if (message.isNotEmpty() && ipAddress.isNotEmpty()) {
                Thread {
                    sendUDPMessage(message, ipAddress, PORT)
                }.start()
            }
        }
        Thread { receiveUDPMessage(PORT) }.start() // Bắt đầu lắng nghe tin nhắn UDP
    }

    private fun sendUDPMessage(message: String, ipAddress: String, port: Int) {
        try {
            val socket = DatagramSocket()
            val address = InetAddress.getByName(ipAddress)
            val buffer = message.toByteArray()
            val packet = DatagramPacket(buffer, buffer.size, address, port)
            socket.send(packet)
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun receiveUDPMessage(port: Int) {
        try {
            val socket = DatagramSocket(port)
            val buffer = ByteArray(1024)

            while (isReceiving) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val message = String(packet.data, 0, packet.length)

                runOnUiThread {
                    textViewReceived.text = "Tin nhắn nhận được: $message"
                }
            }

            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isReceiving = false // Dừng nhận tin nhắn khi thoát ứng dụng
    }
}

