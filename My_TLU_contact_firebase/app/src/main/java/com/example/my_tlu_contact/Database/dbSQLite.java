//package com.example.my_tlu_contact.Database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.example.my_tlu_contact.CBNV.CBNV;
//import com.example.my_tlu_contact.DonVi.DonVi;
//import com.example.my_tlu_contact.User_Login.User;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class dbSQLite extends SQLiteOpenHelper {
//
//    private static final String TABLE_USERS = "users";
//    private static final String COLUMN_ID = "id";
//    private static final String COLUMN_USERNAME = "username";
//    private static final String COLUMN_PASSWORD = "password";
//    private static final String COLUMN_ROLE = "role";
//    private static final String COLUMN_TEN = "ten";
//    private static final String COLUMN_SDT = "sdt";
//    private static final String COLUMN_NGAYSINH = "ngaysinh";
//    private static final String COLUMN_DIACHI= "diachi";
//    public dbSQLite(Context context) {
//       super(context, "contactTLU.db", null, 6);
//    }
//
//    public SQLiteDatabase open() {
//        return this.getWritableDatabase();
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        String tbDonVi = "CREATE TABLE IF NOT EXISTS tb_DonVi(ma_DV TEXT PRIMARY KEY, ten_DV TEXT, diachi_DV TEXT, sdt_DV TEXT, email_DV TEXT, website_DV TEXT)";
//        String tbCBNV = "CREATE TABLE IF NOT EXISTS tb_CBNV(ma_cb TEXT PRIMARY KEY, ten_cb TEXT, ngaysinh_cb TEXT, sdt_cb TEXT, diachi_cb TEXT)";
//        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
//                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TEN + " TEXT, " +
//                COLUMN_SDT + " TEXT, " +
//                COLUMN_NGAYSINH + " TEXT, " +
//                COLUMN_DIACHI + " TEXT, " +
//                COLUMN_USERNAME + " TEXT UNIQUE, " +
//                COLUMN_PASSWORD + " TEXT, " +
//                COLUMN_ROLE + " TEXT)";
//        db.execSQL(tbDonVi);
//        db.execSQL(tbCBNV);
//        db.execSQL(createUserTable);
//        // Chèn dữ liệu mẫu cho bảng TB_DonVi
//        db.execSQL("INSERT INTO tb_DonVi VALUES ('DV001', 'Phòng Kế Toán', 'Tầng 2, Nhà A', '0123456789', 'ke.toan@tlu.edu.vn', 'ke.toan.tlu.edu.vn')");
//        db.execSQL("INSERT INTO tb_DonVi VALUES ('DV002', 'Phòng Đào Tạo', 'Tầng 3, Nhà B', '0987654321', 'dao.tao@tlu.edu.vn', 'daotao.tlu.edu.vn')");
//        db.execSQL("INSERT INTO tb_DonVi VALUES ('DV003', 'Phòng Công Tác SV', 'Tầng 1, Nhà C', '0912345678', 'congtac.sv@tlu.edu.vn', 'ctsv.tlu.edu.vn')");
//
//        // Chèn dữ liệu mẫu cho bảng TB_CBNV
//        db.execSQL("INSERT INTO tb_CBNV VALUES ('CB001', 'Nguyễn Văn A', '1980-05-15', '0901234567', 'Hà Nội')");
//        db.execSQL("INSERT INTO tb_CBNV VALUES ('CB002', 'Trần Thị B', '1990-07-20', '0912345678', 'TP.HCM')");
//        db.execSQL("INSERT INTO tb_CBNV VALUES ('CB003', 'Lê Văn C', '1985-10-10', '0923456789', 'Đà Nẵng')");
//
//
////        // Tạo bảng người dùng
//
//
//        // Thêm admin mặc định nếu chưa có
//        db.execSQL("INSERT INTO users (ten,sdt,ngaysinh,diachi,username, password, role) VALUES ('ten_admin','sdt_admin','ngaysinh_admin','diachi_admin','admin@example.com', 'admin123', 'admin')");
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Handle database upgrade if needed
//        db.execSQL("DROP TABLE IF EXISTS TB_DonVi");
//        db.execSQL("DROP TABLE IF EXISTS TB_CBNV");
//        db.execSQL("DROP TABLE IF EXISTS users");
//        onCreate(db);
//    }
//
//    public CBNV[] showDataCBNV() {
//        ArrayList<CBNV> cbnvList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_CBNV", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String maCB = cursor.getString(0);
//                String tenCB = cursor.getString(1);
//                String ngaysinhCB = cursor.getString(2);
//                String sdtCB = cursor.getString(3);
//                String diachiCB = cursor.getString(4);
//
//                // Thêm vào danh sách
//                cbnvList.add(new CBNV(maCB, tenCB, ngaysinhCB, sdtCB, diachiCB));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return cbnvList.toArray(new CBNV[0]);
//    }
//    public DonVi[] showDataDV() {
//        ArrayList<DonVi> dvList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_DonVi", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String maDV = cursor.getString(0);
//                String tenDV = cursor.getString(1);
//                String diachiDV = cursor.getString(2);
//                String sdtDV = cursor.getString(3);
//                String emailDV = cursor.getString(4);
//                String websiteDV = cursor.getString(5);
//
//                // Thêm vào danh sách
//                dvList.add(new DonVi(maDV, tenDV, sdtDV, diachiDV, emailDV, websiteDV));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return dvList.toArray(new DonVi[0]);
//    }
//
//    public void addCBNV(String maCB, String tenCB, String ngaysinhCB, String sdtCB, String diachiCB) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("INSERT INTO TB_CBNV VALUES (?, ?, ?, ?, ?)", new Object[]{maCB, tenCB, ngaysinhCB, sdtCB, diachiCB});
//        db.close();
//    }
//    public void addDV(String maDV, String tenDV, String diachiDV, String sdtDV, String emailDV, String websiteDV) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("INSERT INTO TB_DonVi VALUES (?, ?, ?, ?, ?, ?)", new Object[]{maDV, tenDV, diachiDV, sdtDV, emailDV, websiteDV});
//        db.close();
//    }
//
//    public void updateCBNV(String maCB, String tenCB, String ngaysinhCB, String sdtCB, String diachiCB) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE TB_CBNV SET  ten_cb = ?, ngaysinh_cb = ?, sdt_cb = ?, diachi_cb = ? WHERE ma_cb = ?", new Object[]{tenCB, ngaysinhCB, sdtCB, diachiCB, maCB});
//        db.close();
//    }
//    public void updateDV(String maDV, String tenDV, String diachiDV, String sdtDV, String emailDV, String websiteDV) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE TB_DonVi SET ten_DV = ?, diachi_DV = ?, sdt_DV = ?, email_DV = ?, website_DV = ? WHERE ma_DV = ?", new Object[]{tenDV, diachiDV, sdtDV, emailDV, websiteDV, maDV});
//        db.close();
//    }
//
//    public void deleteCBNV(String maCB) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM TB_CBNV WHERE ma_cb = ?", new Object[]{maCB});
//        db.close();
//    }
//    public void deleteDV(String maDV) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM TB_DonVi WHERE ma_DV = ?", new Object[]{maDV});
//        db.close();
//    }
//
//    public  Boolean checkMaCB(String maCB) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_CBNV WHERE ma_cb = ?", new String[]{maCB});
//        boolean exists = cursor.getCount() > 0;
//        cursor.close();
//        return exists;
//    }
//    public Boolean checkMaDV(String maDV) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_DonVi WHERE ma_DV = ?", new String[]{maDV});
//        boolean exists = cursor.getCount() > 0;
//        cursor.close();
//        return exists;
//    }
//
//    public CBNV[] sortNameCBNV(String sortOrder) {
//
//
//            ArrayList<CBNV> cbnvList = new ArrayList<>();
//            SQLiteDatabase db = this.getReadableDatabase();
//
//      Cursor  cursor = db.rawQuery("SELECT * FROM TB_CBNV ORDER BY ten_cb "+sortOrder+";", null);
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String maCB = cursor.getString(0);
//                    String tenCB = cursor.getString(1);
//                    String ngaysinhCB = cursor.getString(2);
//                    String sdtCB = cursor.getString(3);
//                    String diachiCB = cursor.getString(4);
//
//                    // Thêm vào danh sách
//                    cbnvList.add(new CBNV(maCB, tenCB, ngaysinhCB, sdtCB, diachiCB));
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//
//            return cbnvList.toArray(new CBNV[0]);
//
//    }
//    public DonVi[] sortNameDV(String sortOrder) {
//        ArrayList<DonVi> dvList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_DonVi ORDER BY ten_DV " + sortOrder + ";", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String maDV = cursor.getString(0);
//                String tenDV = cursor.getString(1);
//                String diachiDV = cursor.getString(2);
//                String sdtDV = cursor.getString(3);
//                String emailDV = cursor.getString(4);
//                String websiteDV = cursor.getString(5);
//
//                // Thêm vào danh sách
//                dvList.add(new DonVi(maDV, tenDV, sdtDV, diachiDV, emailDV, websiteDV));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return dvList.toArray(new DonVi[0]);
//    }
//
//    public CBNV[] searchCBNV(String searchText) {
//        ArrayList<CBNV> cbnvList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_CBNV WHERE ten_cb LIKE ?", new String[]{"%" + searchText + "%"});
//
//        if (cursor.moveToFirst()) {
//            do {
//                String maCB = cursor.getString(0);
//                String tenCB = cursor.getString(1);
//                String ngaysinhCB = cursor.getString(2);
//                String sdtCB = cursor.getString(3);
//                String diachiCB = cursor.getString(4);
//
//                // Thêm vào danh sách
//                cbnvList.add(new CBNV(maCB, tenCB, ngaysinhCB, sdtCB, diachiCB));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return cbnvList.toArray(new CBNV[0]);
//    }
//
//    public DonVi[] searchDV(String searchText) {
//        ArrayList<DonVi> dvList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM TB_DonVi WHERE ten_DV LIKE ?", new String[]{"%" + searchText + "%"});
//
//        if (cursor.moveToFirst()) {
//            do {
//                String maDV = cursor.getString(0);
//                String tenDV = cursor.getString(1);
//                String diachiDV = cursor.getString(2);
//                String sdtDV = cursor.getString(3);
//                String emailDV = cursor.getString(4);
//                String websiteDV = cursor.getString(5);
//
//                // Thêm vào danh sách
//                dvList.add(new DonVi(maDV, tenDV, sdtDV, diachiDV, emailDV, websiteDV));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return dvList.toArray(new DonVi[0]);
//    }
//
//    // Thêm tài khoản mới
////    public boolean registerUser(String username, String password, String role) {
////        SQLiteDatabase db = this.getWritableDatabase();
////
////        // Kiểm tra xem username đã tồn tại chưa
////        Cursor cursor = db.rawQuery("SELECT * FROM  users WHERE username = ?", new String[]{username});
////        boolean userExists = cursor.getCount() > 0;
////        cursor.close();
////
////        if (userExists) {
////            db.close();
////            return false; // Username đã tồn tại, không thêm mới
////        }
////
////        // Nếu chưa có, thêm tài khoản mới vào database
////        ContentValues values = new ContentValues();
////        values.put(COLUMN_USERNAME, username);
////        values.put(COLUMN_PASSWORD, password);
////        values.put(COLUMN_ROLE, role);
////
////        long result = db.insert(TABLE_USERS, null, values);
////        db.close();
////        return result != -1;
////    }
//    public boolean registerUser(String ten, String sdt, String ngaySinh, String diaChi,
//                                String username, String password, String role) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Kiểm tra xem username đã tồn tại chưa
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
//        boolean userExists = cursor.getCount() > 0;
//        cursor.close();
//
//        if (userExists) {
//            db.close();
//            return false; // Username đã tồn tại, không thêm mới
//        }
//
//        // Nếu chưa có, thêm tài khoản mới vào database
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TEN, ten);
//        values.put(COLUMN_SDT, sdt);
//        values.put(COLUMN_NGAYSINH, ngaySinh);
//        values.put(COLUMN_DIACHI, diaChi);
//        values.put(COLUMN_USERNAME, username);
//        values.put(COLUMN_PASSWORD, password);
//        values.put(COLUMN_ROLE, role);
//
//        long result = db.insert(TABLE_USERS, null, values);
//        db.close();
//        return result != -1;
//    }
//
//    // Kiểm tra đăng nhập
//    public User loginUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
//                new String[]{username, password});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
//            cursor.close();
//            db.close();
//            return user;
//        }
//        return null;
//    }
//    public User getUserByEmail(String email) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        User user = null;
//
//        // Truy vấn database
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{email});
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                // Kiểm tra cột trước khi truy xuất dữ liệu
//                int idIndex = cursor.getColumnIndex(COLUMN_ID);
//                int tenIndex = cursor.getColumnIndex(COLUMN_TEN);
//                int sdtIndex = cursor.getColumnIndex(COLUMN_SDT);
//                int ngaysinhIndex = cursor.getColumnIndex(COLUMN_NGAYSINH);
//                int diachiIndex = cursor.getColumnIndex(COLUMN_DIACHI);
//                int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
//                int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
//                int roleIndex = cursor.getColumnIndex(COLUMN_ROLE);
//
//                // Nếu tất cả các cột đều hợp lệ
//                if (idIndex != -1 && tenIndex != -1 && sdtIndex != -1 &&
//                        ngaysinhIndex != -1 && diachiIndex != -1 &&
//                        usernameIndex != -1 && passwordIndex != -1 && roleIndex != -1) {
//
//                    user = new User(
//                            cursor.getInt(idIndex),
//                            cursor.getString(tenIndex),
//                            cursor.getString(sdtIndex),
//                            cursor.getString(ngaysinhIndex),
//                            cursor.getString(diachiIndex),
//                            cursor.getString(usernameIndex),
//                            cursor.getString(passwordIndex),
//                            cursor.getString(roleIndex)
//                    );
//                } else {
//                    Log.e("DB_ERROR", "Một hoặc nhiều cột không tồn tại trong bảng users!");
//                }
//            }
//            cursor.close();
//        }
//        db.close();
//        return user;
//    }
//
//
//}
