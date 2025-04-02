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
import com.example.my_tlu_contact.Adapter.dv_Adapter;
import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;
import com.example.my_tlu_contact.R;
import com.example.my_tlu_contact.role_;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CBNV_Activity extends AppCompatActivity {
   private RecyclerView rcv_cbnv;
   private  ImageButton btnback;
    private Context context;
   private Button btnaddCB;
   private Spinner spinerSortCB;
   private String checkSort = "Không";
    private CBNV[] arraycb;
    private ArrayList<CBNV> arrlistCBNV= new ArrayList<>();
    private cbnv_Adapter cb_myAdapter;
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
        rcv_cbnv = findViewById(R.id.rcv_cbnv);

        Intent intent = getIntent();
        String ROLE = role_.getRole();
        if (ROLE != null) {
            if (ROLE.equals("USER")) {
                btnaddCB.setVisibility(View.GONE);
            } else if (ROLE.equals("ADMIN")) {

            }
        }


//        ArrayList<CBNV> arraydonvi = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dbReference = database.getReference();


        dbReference.child("canbo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if (!dataSnapshot.exists()) {
                    Toast.makeText(CBNV_Activity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                arrlistCBNV.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CBNV cbnv = snapshot.getValue(CBNV.class);
                    if (cbnv != null) {
                        arrlistCBNV.add(cbnv);
                    }
                }

                arraycb = arrlistCBNV.toArray(new CBNV[0]);
                cb_myAdapter = new cbnv_Adapter(arraycb);
                rcv_cbnv.setAdapter(cb_myAdapter);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
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

        edt_searchCBNV.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì cả
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                filterListCB(searchText);

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

    }
    private void sortData(String sortOrder) {
        if (sortOrder.equals("A->Z")) {
            Arrays.sort(arraycb, new Comparator<CBNV>() {
                @Override
                public int compare(CBNV cb1, CBNV cb2) {
                    return cb1.getTenCB().compareTo(cb2.getTenCB());
                }
            });
        } else if (sortOrder.equals("Z->A")) {
            Arrays.sort(arraycb, new Comparator<CBNV>() {
                @Override
                public int compare(CBNV cb1, CBNV cb2) {
                    return cb2.getTenCB().compareTo(cb1.getTenCB());
                }
            });
        }

        // ✅ Cập nhật dữ liệu trong Adapter
        if (cb_myAdapter != null) {
            cb_myAdapter.updateData(arraycb);
        }
    }
    private void filterListCB(String query) {
        ArrayList<CBNV> filteredList = new ArrayList<>();

        for (CBNV cb : arrlistCBNV) {
            if (cb.getTenCB().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(cb);
            }
        }

        cb_myAdapter.updateData_searchCB(filteredList);
    }

}