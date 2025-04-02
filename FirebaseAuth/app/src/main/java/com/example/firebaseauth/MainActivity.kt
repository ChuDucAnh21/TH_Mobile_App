package com.example.firebaseauth
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var showDataButton: Button
    private lateinit var dataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Khởi tạo Firebase Database
        database = FirebaseDatabase.getInstance()
        // Ánh xạ các thành phần UI
        emailEditText = findViewById(R.id.edtemail)
        passwordEditText = findViewById(R.id.edtpasswd)
        loginButton = findViewById(R.id.btn_login)
        registerButton = findViewById(R.id.btn_register)
        showDataButton = findViewById(R.id.btn_showdata)
        dataTextView = findViewById(R.id.txt_show)

        // Thiết lập các sự kiện click
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        // Kiểm tra nếu người dùng đã đăng nhập
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun setupClickListeners() {
        // Xử lý sự kiện đăng ký
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Hiển thị trạng thái đang xử lý
            showLoading(true)
            // Thực hiện đăng ký
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    showLoading(false)
                    if (task.isSuccessful) {
                        // Đăng ký thành công
                        val user = auth.currentUser
                        Toast.makeText(this, "Đăng ký thành công: ${user?.email}", Toast.LENGTH_SHORT).show()
                        // Lưu thông tin người dùng vào Realtime Database
                        saveUserToDatabase(user?.uid, email)
                        updateUI(user)
                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(this, "Đăng ký thất bại: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Xử lý sự kiện đăng nhập
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Hiển thị trạng thái đang xử lý
            showLoading(true)
            // Thực hiện đăng nhập
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    showLoading(false)
                    if (task.isSuccessful) {
                        // Đăng nhập thành công
                        val user = auth.currentUser
                        Toast.makeText(this, "Đăng nhập thành công: ${user?.email}", Toast.LENGTH_SHORT).show()

                        // Cập nhật thời gian đăng nhập cuối
                        updateLastLogin(user?.uid)
                        updateUI(user)
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(this, "Đăng nhập thất bại: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Xử lý sự kiện hiển thị dữ liệu
        showDataButton.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(this, "Vui lòng đăng nhập trước", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hiển thị dữ liệu của người dùng hiện tại
            showUserData(currentUser.uid)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        // Thực hiện hiển thị loading (có thể thêm ProgressBar trong layout)
        loginButton.isEnabled = !isLoading
        registerButton.isEnabled = !isLoading
        showDataButton.isEnabled = !isLoading
    }

    private fun saveUserToDatabase(userId: String?, email: String) {
        userId?.let {
            val userRef = database.getReference("users").child(it)
            val userData = HashMap<String, Any>()
            userData["email"] = email
            userData["createdAt"] = System.currentTimeMillis()

            userRef.setValue(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Lưu thông tin người dùng thành công", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi lưu thông tin: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateLastLogin(userId: String?) {
        userId?.let {
            val userRef = database.getReference("users").child(it)
            val updates = HashMap<String, Any>()
            updates["lastLogin"] = System.currentTimeMillis()

            userRef.updateChildren(updates)
                .addOnSuccessListener {
                    // Cập nhật thành công
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi cập nhật: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showUserData(userId: String) {
        val userRef = database.getReference("users").child(userId)

        dataTextView.text = "Đang tải dữ liệu..."

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userData = snapshot.value as Map<*, *>?
                    val stringBuilder = StringBuilder()

                    userData?.forEach { (key, value) ->
                        // Định dạng thời gian nếu là timestamp
                        if (key == "createdAt" || key == "lastLogin") {
                            val timestamp = value as Long
                            val date = java.util.Date(timestamp)
                            val formattedDate = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                                java.util.Locale.getDefault()).format(date)
                            stringBuilder.append("$key: $formattedDate\n")
                        } else {
                            stringBuilder.append("$key: $value\n")
                        }
                    }

                    dataTextView.text = stringBuilder.toString()
                } else {
                    dataTextView.text = "Không tìm thấy dữ liệu cho người dùng này."
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataTextView.text = "Lỗi khi tải dữ liệu: ${error.message}"
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Người dùng đã đăng nhập
            // Có thể cập nhật UI để hiển thị trạng thái đăng nhập
            emailEditText.hint = "Đã đăng nhập: ${user.email}"
        } else {
            // Người dùng chưa đăng nhập
            emailEditText.hint = "Email"
            dataTextView.text = "Vui lòng đăng nhập để xem dữ liệu."
        }
    }
}
