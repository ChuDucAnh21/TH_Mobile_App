package com.example.giatoc_app

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
import com.example.giatoc_app.ui.theme.GiaToc_appTheme
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var textX: TextView
    private lateinit var textY: TextView
    private lateinit var textZ: TextView
    private lateinit var imageView: ImageView
    private var posX = 0f
    private var posY = 0f
    private val factor = 5f  // Hệ số điều chỉnh tốc độ di chuyển

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Ánh xạ các thành phần giao diện
        textX = findViewById(R.id.textX)
        textY = findViewById(R.id.textY)
        textZ = findViewById(R.id.textZ)
        imageView = findViewById(R.id.imageView)

        // Lấy SensorManager từ hệ thống
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Lấy cảm biến gia tốc
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        // Đăng ký lắng nghe sự kiện từ cảm biến gia tốc
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        // Hủy đăng ký lắng nghe khi ứng dụng tạm dừng để tiết kiệm pin
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0] // Gia tốc theo trục X
            val y = it.values[1] // Gia tốc theo trục Y
            val z = it.values[2] // Gia tốc theo trục Z

            // Hiển thị giá trị gia tốc lên các TextView
            textX.text = "Gia tốc X: ${x} m/s²"
            textY.text = "Gia tốc Y: ${y} m/s²"
            textZ.text = "Gia tốc Z: ${z} m/s²"

            // Cập nhật vị trí của ảnh dựa trên gia tốc
            posX -= x * factor  // Dịch chuyển theo trục X
            posY += y * factor  // Dịch chuyển theo trục Y

            // Đảm bảo ảnh không ra khỏi màn hình
            val maxX = (imageView.parent as? ViewGroup)?.width?.toFloat() ?: 0f
            val maxY = (imageView.parent as? ViewGroup)?.height?.toFloat() ?: 0f
            posX = posX.coerceIn(0f, maxX - imageView.width)
            posY = posY.coerceIn(0f, maxY - imageView.height)

            // Cập nhật vị trí của hình ảnh
            imageView.translationX = posX
            imageView.translationY = posY
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Không cần xử lý trong ứng dụng này, nhưng có thể dùng nếu muốn thông báo thay đổi độ chính xác
    }
}
