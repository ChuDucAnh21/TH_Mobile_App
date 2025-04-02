package com.example.count_time

import android.annotation.SuppressLint
import android.os.Bundle

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var timeTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

     private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0
    private var isRunning = false

  // Runnable để cập nhật thời gian
    private val timeRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                seconds++
                updateTimeDisplay()
                // Lập lịch chạy lại sau 1 giây
                handler.postDelayed(this, 1000)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Khởi tạo các thành phần giao diện
        timeTextView = findViewById(R.id.txt_time)
        startButton = findViewById(R.id.buttonStart)
        stopButton = findViewById(R.id.buttonStop)


        // Thiết lập sự kiện cho nút Bắt đầu
        startButton.setOnClickListener {
            startTimer()
        }

        // Thiết lập sự kiện cho nút Dừng
        stopButton.setOnClickListener {
            stopTimer()
        }
        // Hiển thị thời gian ban đầu
        updateTimeDisplay()
    }
    private fun startTimer() {
        if (!isRunning) {
            isRunning = true
            // Bắt đầu đếm thời gian
            handler.post(timeRunnable)
            // Cập nhật trạng thái các nút
            startButton.isEnabled = false
            stopButton.isEnabled = true

        }
    }
    private fun stopTimer() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(timeRunnable) // Dừng đếm thời gian
            startButton.isEnabled = true // Cập nhật trạng thái các nút
            stopButton.isEnabled = false
        }
    }

    private fun updateTimeDisplay() {
        // Chuyển đổi tổng số giây thành giờ phút: giây
        val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
        val minutes = TimeUnit.SECONDS.toMinutes(seconds.toLong()) % 60
        val secs = seconds % 60

        // Hiển thị thời gian với định dạng HH:MM:SS
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, secs)
        timeTextView.text = timeString
    }
    override fun onDestroy() {
        super.onDestroy()
        // Đảm bảo dừng handler khi activity bị hủy
        handler.removeCallbacks(timeRunnable)
    }


}
