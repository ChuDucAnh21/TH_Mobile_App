package com.example.picturefrominternet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
import com.example.picturefrominternet.ui.theme.PicturefromInternetTheme
import java.io.InputStream
import java.net.URL

class MainActivity : ComponentActivity() {

    private lateinit var editTextUrl: EditText
    private lateinit var buttonDownload : Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Khởi tạo các thành phần giao diện
        editTextUrl = findViewById(R.id.txt_linkimg)
        buttonDownload = findViewById(R.id.btn_layimg)
        imageView = findViewById(R.id.imageView2)


        // Thêm URL mẫu để học sinh dễ thử nghiệm
        editTextUrl.setText("https://picsum.photos/800/600")

        // Xử lý sự kiện khi nhấn nút tải
        buttonDownload.setOnClickListener {
            val url = editTextUrl.text.toString()

            if (url.isNotEmpty()) {
              Toast.makeText(this, "Đang tải ảnh...", Toast.LENGTH_SHORT).show()
                // Tải ảnh từ URL

            } else {
                Toast.makeText(this, "Vui lòng nhập URL ảnh", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private inner class ImageDownloader : AsyncTask<String, Void, Bitmap?>() {

        override fun onPreExecute() {
            Toast.makeText(this@MainActivity, "Đang tải ảnh...", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            var bitmap: Bitmap? = null

            try {
                // Tải ảnh từ URL
                val inputStream: InputStream = URL(imageUrl).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }
    }
    fun onPostExecute(result: Bitmap?) {


        if (result != null) {
            // Hiển thị ảnh đã tải
            imageView.setImageBitmap(result)
            Toast.makeText(this@MainActivity, "Tải ảnh thành công!", Toast.LENGTH_SHORT).show()
        } else {
            // Thông báo lỗi nếu tải thất bại
            Toast.makeText(this@MainActivity, "Không thể tải ảnh. Kiểm tra URL và kết nối internet.", Toast.LENGTH_SHORT).show()
        }
    }

}

