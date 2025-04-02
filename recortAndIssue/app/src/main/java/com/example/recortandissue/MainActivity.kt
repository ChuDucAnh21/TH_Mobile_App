package com.example.recortandissue

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    // Mã yêu cầu quyền truy cập (được sử dụng trong onRequestPermissionsResult)
    private val REQUEST_CODE_PERMISSIONS = 200

    // Biến để quản lý việc phát lại âm thanh
    private var mediaPlayer: MediaPlayer? = null

    // Mảng các quyền cần thiết cho ứng dụng
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO // Yêu cầu quyền ghi âm
    )

    // Biến để quản lý việc ghi âm
    private var mediaRecorder: MediaRecorder? = null

    // Đường dẫn file ghi âm đầu ra
    private var outputFile: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // Đặt layout cho activity

        // Kiểm tra và yêu cầu quyền nếu cần thiết
        if (!checkPermissions()) {
            requestPermissions()
        }

        // Lấy tham chiếu đến các view từ layout
        val btnRecord = findViewById<Button>(R.id.btnRecord)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val listView = findViewById<ListView>(R.id.listView)

        // Thiết lập các trình xử lý sự kiện click cho các nút
        btnRecord.setOnClickListener { startRecording() }
        btnStop.setOnClickListener { stopRecording() }
        btnPlay.setOnClickListener { playRecording() }

        // Load danh sách các file âm thanh từ MediaStore và hiển thị chúng trong ListView
        val audioFiles = loadAudioFiles()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, audioFiles)
        listView.adapter = adapter
    }

    // Hàm kiểm tra xem tất cả các quyền cần thiết đã được cấp hay chưa
    private fun checkPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Hàm yêu cầu các quyền cần thiết từ người dùng
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
    }

    // Hàm bắt đầu ghi âm
    private fun startRecording() {
        // Đường dẫn file ghi âm đầu ra (lưu trong thư mục cache của ứng dụng)
        outputFile = "${externalCacheDir?.absolutePath}/recorded_audio.3gp"

        // Khởi tạo MediaRecorder và thiết lập các thông số
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC) // Nguồn âm thanh là microphone
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // Định dạng file đầu ra
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // Bộ mã hóa âm thanh
            setOutputFile(outputFile) // Đường dẫn file đầu ra
            try {
                prepare() // Chuẩn bị MediaRecorder
                start() // Bắt đầu ghi âm
                Toast.makeText(this@MainActivity, "Đang ghi âm...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Lỗi ghi âm: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm dừng ghi âm
    private fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop() // Dừng ghi âm
                release() // Giải phóng tài nguyên MediaRecorder
                saveRecordingToMediaStore() // Lưu file ghi âm vào MediaStore
                Toast.makeText(this@MainActivity, "Ghi âm xong!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Lỗi dừng ghi âm: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        mediaRecorder = null // Đặt mediaRecorder về null
    }

    // Hàm lưu file ghi âm vào MediaStore
    private fun saveRecordingToMediaStore() {
        val values = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, "recorded_audio.3gp") // Tên file hiển thị
            put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gp") // Loại MIME
            put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC) // Đường dẫn tương đối
        }

        // Thêm file vào MediaStore
        val uri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let { Toast.makeText(this@MainActivity, "Đã lưu vào MediaStore!", Toast.LENGTH_SHORT).show() }
    }

    // Hàm phát lại file ghi âm
    private fun playRecording() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(outputFile) // Đặt đường dẫn file nguồn
                prepare() // Chuẩn bị MediaPlayer
                start() // Bắt đầu phát
                Toast.makeText(this@MainActivity, "Đang phát...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Lỗi phát: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm load danh sách các file âm thanh từ MediaStore
    private fun loadAudioFiles(): List<String> {
        val audioList = mutableListOf<String>()
        // Các cột cần truy vấn
        val projection = arrayOf(MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA)

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, // URI của MediaStore
            projection, // Các cột cần truy vấn
            null, null, null
        )?.use { cursor -> // Sử dụng use để tự động đóng cursor
            // Lấy index của cột tên
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            // Lấy index của cột đường dẫn
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) { // Duyệt qua các hàng
                val name = cursor.getString(nameColumn) // Lấy tên file
                val path = cursor.getString(dataColumn) // Lấy đường dẫn file
                audioList.add("$name\n$path") // Thêm vào danh sách
            }
        }
        return audioList
    }
}