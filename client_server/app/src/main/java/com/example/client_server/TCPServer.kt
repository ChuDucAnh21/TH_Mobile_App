package com.example.client_server

import java.io.*
import java.net.*

class TCPServer {
    fun main() {
        val port = 12345
        val serverSocket = ServerSocket(port)
        println("Server đang chạy trên cổng $port")

        while (true) {
            val clientSocket = serverSocket.accept()
            println("Client đã kết nối: ${clientSocket.inetAddress}")

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                    val writer = PrintWriter(clientSocket.getOutputStream(), true)

                    var message: String?
                    while (reader.readLine().also { message = it } != null) {
                        println("Client gửi: $message")
                        writer.println("Server nhận: $message")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    clientSocket.close()
                }
            }.start()
        }
    }
}