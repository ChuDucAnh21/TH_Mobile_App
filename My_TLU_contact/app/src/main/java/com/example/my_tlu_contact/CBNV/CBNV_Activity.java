package com.example.my_tlu_contact.CBNV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.Activate.activity_add_CB;
import com.example.my_tlu_contact.Adapter.cbnv_Adapter;
import com.example.my_tlu_contact.R;
import com.example.my_tlu_contact.Database.dbSQLite;

public class CBNV_Activity extends AppCompatActivity {
   private RecyclerView rcv_cbnv;
   private  ImageButton btnback;
    private Context context;
   private dbSQLite db ;
   private Button btnaddCB;
   private Spinner spinerSortCB;
   private String checkSort = "Không";

private EditText edt_searchCBNV;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbnv);



        btnback = findViewById(R.id.btn_arrowCBNV);
        btnaddCB=findViewById(R.id.btn_themCB);
        spinerSortCB = findViewById(R.id.spin_sortCB);
        edt_searchCBNV = findViewById(R.id.edt_searchCB);
        rcv_cbnv = (RecyclerView) findViewById(R.id.rcv_dv);

        Intent intent = getIntent();
        String ROLE = intent.getStringExtra("ROLE_MAIN");
        if (ROLE != null) {
            if (ROLE.equals("USER")) {
                btnaddCB.setVisibility(View.GONE);
            } else if (ROLE.equals("ADMIN")) {

            }
        }



        db = new dbSQLite(this);
        db.open();
        CBNV[] cbnv = db.showDataCBNV();// Lấy dữ liệu từ cơ sở dữ liệu
        cbnv_Adapter cbnv_myAdapter = new cbnv_Adapter(cbnv);
        rcv_cbnv.setAdapter(cbnv_myAdapter);



        btnaddCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CBNV_Activity.this, activity_add_CB.class);
                intent.putExtra("KEY_UI", "ADD"); // Truyền id rỗng để thêm mới
                startActivity(intent);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String[] items = {"Không", "A->Z", "Z->A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinerSortCB.setAdapter(adapter);
        // Xử lý sự kiện khi chọn item
       // Intent intent = getIntent();
        if (intent.hasExtra("SORT_ORDER")) {
            checkSort = intent.getStringExtra("SORT_ORDER");
        } else {
            checkSort = "Không"; // Mặc định nếu không có giá trị
        }
        // Đặt lại giá trị Spinner theo SORT_ORDER
        int spinnerPosition = adapter.getPosition(checkSort);
        spinerSortCB.setSelection(spinnerPosition);
        spinerSortCB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Không")) {
                    checkSort = "Không";
                    loadDataCBNV();
                } else if (selectedItem.equals("A->Z")) {
                    checkSort = "A->Z";
                    loadDataCBNV();
                } else if (selectedItem.equals("Z->A")) {
                    checkSort = "Z->A";
                    loadDataCBNV();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không chọn gì cả
            }
        });

        edt_searchCBNV.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì cả
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                db.open();
                CBNV[] cbnv = db.searchCBNV(searchText);
                cbnv_Adapter cbnvAdapter = new cbnv_Adapter(cbnv);
                rcv_cbnv.setAdapter(cbnvAdapter);
                db.close();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Không làm gì cả
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadDataCBNV(); // Gọi lại hàm load dữ liệu khi quay về Activity
    }

    public void loadDataCBNV() {
        db.open();
        CBNV[] cb;
        if (checkSort.equals("A->Z")) {
            cb =db.sortNameCBNV("ASC");
        } else if (checkSort.equals("Z->A")) {
            cb = db.sortNameCBNV("DESC");
        } else {
            cb = db.showDataCBNV();
        }
        cbnv_Adapter cbnvAdapter = new cbnv_Adapter(cb);
        rcv_cbnv.setAdapter(cbnvAdapter);
        db.close();
    }
}