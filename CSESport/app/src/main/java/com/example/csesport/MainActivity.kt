package com.example.csesport

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.csesport.ui.theme.CSESportTheme
import com.example.csesport.ui.theme.Sleep_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         enableEdgeToEdge();
         setContentView(R.layout.main_activity)
        //Tham chieu cac phan tu tren Activity
       // val BottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav);
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav);
        val frameMain = findViewById<FrameLayout>(R.id.frame_main)

        //xu li su kien
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_running->{         // nếu ấn vào item có id = btn_running
                   loadFragment(Running_Fragment());
                    true
                }
                R.id.btn_recyline->{        // nếu ấn vào item có id = btn_recyline
                   loadFragment(Recycline_Fragment());
                    true
                }
                R.id.btn_sleep->{        // nếu ấn vào item có id = btn_recyline
                    loadFragment(Sleep_Fragment());
                    true
                }
            }
            true
        }

    }
    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main,fragment)
            .commit()
    }
}

