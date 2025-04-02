package com.example.sharepreference_th1

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException

class PreferenceHelper( context: Context) {
    private val prefName = "USER_LOGIN"
  private val sharedPreferences : SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    fun addUser(value:String) {

        val editor = sharedPreferences.edit()

        // Kiểm tra nếu dữ liệu chưa đúng định dạng JSON thì khởi tạo một JSONArray mới
        val jsonString = sharedPreferences.getString("INFOR", "[]") ?: "[]"

        val jsonArray: JSONArray = try {
            JSONArray(jsonString) // ✅ Nếu jsonString hợp lệ, chuyển thành JSONArray
        } catch (e: JSONException) {
            JSONArray() // ✅ Nếu lỗi, tạo một mảng JSON mới
        }

        // Thêm dữ liệu mới vào danh sách
        jsonArray.put(value)

        // Lưu lại danh sách mới dưới dạng JSON
        editor.putString("INFOR", jsonArray.toString())
        editor.apply()
    }


    fun deleteUser() {
        sharedPreferences.edit().clear().apply()
    }
    fun showUser():String {
        val jsonString = sharedPreferences.getString("INFOR", "[]") ?: "[]"
        var result = "";
        try {
            val jsonArray = JSONArray(jsonString)
            val builder = StringBuilder()

            // Duyệt từng phần tử và xuống dòng mỗi dòng
            for (i in 0 until jsonArray.length()) {
                builder.append(jsonArray.getString(i)).append("\n") // Thêm dữ liệu và xuống dòng
            }

           result = builder.toString() // Hiển thị trong TextView

        } catch (e: JSONException) {
            e.printStackTrace()
           result = "Lỗi đọc dữ liệu!"
        }
        return result
    }
}