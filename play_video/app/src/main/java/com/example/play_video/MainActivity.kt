package com.example.play_video

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    // Biến dùng để tham chiếu đến VideoView và MediaController
    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController

    // Hằng số đại diện cho mã yêu cầu quyền đọc bộ nhớ
    private val REQUEST_VIDEO_PERMISSION = 101

    // ActivityResultLauncher để chọn video từ thiết bị
    private val videoPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedVideoUri = result.data?.data
            selectedVideoUri?.let { playVideo(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // Đảm bảo tên layout đúng

        // Khởi tạo VideoView và MediaController
        videoView = findViewById(R.id.videoView)
        mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

        // Khởi tạo các nút bấm
        val btnPickVideo = findViewById<Button>(R.id.btnPickVideo)
        val btnPlayUrl = findViewById<Button>(R.id.btnPlayUrl)

        // Sự kiện khi bấm nút chọn video từ thiết bị
        btnPickVideo.setOnClickListener {
            if (checkPermissions()) {
                pickVideoFromDevice()
            } else {
                requestPermissions()
            }
        }

        // Sự kiện khi bấm nút phát video từ URL
        btnPlayUrl.setOnClickListener { playVideoFromUrl() }
    }

    /**
     * Kiểm tra xem ứng dụng đã có quyền đọc bộ nhớ chưa
     * Trả về `true` nếu đã có quyền, `false` nếu chưa
     */
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
    }

    /**
     * Yêu cầu quyền truy cập bộ nhớ nếu chưa có
     */
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_VIDEO_PERMISSION
        )
    }

    /**
     * Nhận kết quả khi người dùng chấp nhận hoặc từ chối quyền
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_VIDEO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền được cấp.", Toast.LENGTH_SHORT).show()
                pickVideoFromDevice() // Nếu cấp quyền, tiếp tục chọn video
            } else {
                Toast.makeText(this, "Quyền bị từ chối.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    
    /**
     * Mở trình chọn video từ thư viện thiết bị
     */
    private fun pickVideoFromDevice() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        videoPickerLauncher.launch(intent)
    }

    /**
     * Phát video từ một URI (đường dẫn của video)
     */
    private fun playVideo(videoUri: Uri) {
        try {
            videoView.setVideoURI(videoUri)
            videoView.start()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi phát video: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * Phát video từ URL do người dùng nhập
     */
    private fun playVideoFromUrl() {
        val videoUrl = findViewById<EditText>(R.id.edtVideoUrl).text.toString()
        if (videoUrl.isNotEmpty()) {
            try {
                videoView.setVideoURI(Uri.parse(videoUrl))
                videoView.start()
            } catch (e: Exception) {
                Toast.makeText(this, "Lỗi phát video: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập URL hợp lệ!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Khi Activity bị tạm dừng, tạm dừng video để tránh lỗi
     */
    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    /**
     * Khi Activity bị hủy, giải phóng tài nguyên của VideoView
     */
    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}
