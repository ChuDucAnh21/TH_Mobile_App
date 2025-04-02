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
import com.example.my_tlu_contact.R;
import com.example.my_tlu_contact.role_;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DonVi_Activity extends AppCompatActivity {
    private RecyclerView rcv_donvi;
    private ImageButton btnback;
    private Button btnaddDV;
    private Context context;
//    private dbSQLite db;
    private Spinner spinerSortDV;
    private EditText edt_searchDV;
    private String checkSort = "Không";
    private ArrayList<DonVi> arrlistDV = new ArrayList<>();
    private DonVi[] arraydv;
    private dv_Adapter dv_myAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_vi);

        edt_searchDV = findViewById(R.id.edt_searchDV);
        btnaddDV = findViewById(R.id.btn_them_DV);
        btnback = findViewById(R.id.btn_arrowDv);
        spinerSortDV = findViewById(R.id.spin_sortDV);
        rcv_donvi = (RecyclerView) findViewById(R.id.rcv_dv);


       FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dbReference = database.getReference();


        dbReference.child("donvi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if (!dataSnapshot.exists()) {
                    Toast.makeText(DonVi_Activity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                arrlistDV.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonVi donVi = snapshot.getValue(DonVi.class);
                    if (donVi != null) {
                        arrlistDV.add(donVi);
                    }
                }

                arraydv = arrlistDV.toArray(new DonVi[0]);
               dv_myAdapter = new dv_Adapter(arraydv);
                rcv_donvi.setAdapter(dv_myAdapter);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

        String ROLE = role_.getRole();
        if (ROLE != null) {
            if (ROLE.equals("USER")) {
                btnaddDV.setVisibility(View.GONE);
            } else if (ROLE.equals("ADMIN")) {

            }
        }

        Intent intent = getIntent();
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
                } else if (selectedItem.equals("A->Z")) {
                    checkSort = "A->Z";
                } else if (selectedItem.equals("Z->A")) {
                    checkSort = "Z->A";
                }
                sortData(checkSort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không chọn gì cả
            }
        });
        edt_searchDV.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì cả
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                filterListDV(searchText);

            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Không làm gì cả
            }
        });






    }
    private void sortData(String sortOrder) {
        if (sortOrder.equals("A->Z")) {
            Arrays.sort(arraydv, new Comparator<DonVi>() {
                @Override
                public int compare(DonVi donVi1, DonVi donVi2) {
                    return donVi1.getNameDV().compareTo(donVi2.getNameDV());
                }
            });
        } else if (sortOrder.equals("Z->A")) {
            Arrays.sort(arraydv, new Comparator<DonVi>() {
                @Override
                public int compare(DonVi donVi1, DonVi donVi2) {
                    return donVi2.getNameDV().compareTo(donVi1.getNameDV());
                }
            });
        }

        // ✅ Cập nhật dữ liệu trong Adapter
        if (dv_myAdapter != null) {
            dv_myAdapter.updateData(arraydv);
        }
    }
    private void filterListDV(String query) {
        ArrayList<DonVi> filteredList = new ArrayList<>();

        for (DonVi dv : arrlistDV) {
            if (dv.getNameDV().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(dv);
            }
        }

        dv_myAdapter.updateData_searchDV(filteredList);
    }

}