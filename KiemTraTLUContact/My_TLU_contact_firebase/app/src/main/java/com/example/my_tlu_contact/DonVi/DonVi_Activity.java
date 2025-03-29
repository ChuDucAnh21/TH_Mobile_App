package com.example.my_tlu_contact.DonVi;

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

import com.example.my_tlu_contact.Activate.activity_add_DV;
import com.example.my_tlu_contact.Adapter.dv_Adapter;
import com.example.my_tlu_contact.CBNV.CBNV;
import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.R;

public class DonVi_Activity extends AppCompatActivity {
   private RecyclerView rcv_donvi;
   private ImageButton btnback;
   private Button btnaddDV;
    private Context context;
   private dbSQLite db ;
   private Spinner spinerSortDV;
   private EditText edt_searchDV;
    private String checkSort = "Không";
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_vi);

        edt_searchDV = findViewById(R.id.edt_searchDV);
        btnaddDV = findViewById(R.id.btn_them_DV);
        btnback = findViewById(R.id.btn_arrowDv);
        spinerSortDV= findViewById(R.id.spin_sortDV);


        db = new dbSQLite(this);
        db.open();
        DonVi[] donvi=db.showDataDV();
        rcv_donvi = (RecyclerView) findViewById(R.id.rcv_dv);
        dv_Adapter dv_myAdapter = new dv_Adapter(donvi);
        rcv_donvi.setAdapter(dv_myAdapter);

        Intent intent = getIntent();
        String ROLE = intent.getStringExtra("ROLE_MAIN");
        if (ROLE != null) {
            if (ROLE.equals("USER")) {
                btnaddDV.setVisibility(View.GONE);
            } else if (ROLE.equals("ADMIN")) {

            }
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnaddDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến activity thêm đơn vị
                Intent intent = new Intent(DonVi_Activity.this, activity_add_DV.class);
                intent.putExtra("KEY_", "ADD_"); // Truyền id rỗng để thêm mới
                startActivity(intent);
            }
        });
        String[] items = {"Không", "A->Z", "Z->A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinerSortDV.setAdapter(adapter);
        // Xử lý sự kiện khi chọn item
       // Intent intent = getIntent();
        if (intent.hasExtra("SORT_ORDER")) {
            checkSort = intent.getStringExtra("SORT_ORDER");
        } else {
            checkSort = "Không"; // Mặc định nếu không có giá trị
        }
        // Đặt lại giá trị Spinner theo SORT_ORDER
        int spinnerPosition = adapter.getPosition(checkSort);
        spinerSortDV.setSelection(spinnerPosition);
        spinerSortDV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Không")) {
                    checkSort = "Không";
                    loadDataDV();
                } else if (selectedItem.equals("A->Z")) {
                    checkSort = "A->Z";
                    loadDataDV();
                } else if (selectedItem.equals("Z->A")) {
                    checkSort = "Z->A";
                    loadDataDV();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không chọn gì cả
            }


    }
        );
        edt_searchDV.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                db.open();
                DonVi[] donvi = db.searchDV(searchText);
                dv_Adapter dv_myAdapter = new dv_Adapter(donvi);
                rcv_donvi.setAdapter(dv_myAdapter);
                db.close();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Không cần làm gì ở đây
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDataDV(); // Gọi lại hàm load dữ liệu khi quay về Activity
    }

    public void loadDataDV() {
        db.open();
        DonVi[] dv;
        if (checkSort.equals("A->Z")) {
            dv =db.sortNameDV("ASC");
        } else if (checkSort.equals("Z->A")) {
            dv = db.sortNameDV("DESC");
        } else {
            dv = db.showDataDV();
        }
        dv_Adapter dvAdapter = new dv_Adapter(dv);
        rcv_donvi.setAdapter(dvAdapter);
        db.close();
    }

}