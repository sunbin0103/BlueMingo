package com.top_adv.myapplication1.DataBaseManagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.top_adv.myapplication1.DataVO.AdvVO;
import com.top_adv.myapplication1.MainActivity;

import java.sql.Time;

/**
 * Created by sst on 2016-10-10.
 */
public class DBManagement extends SQLiteOpenHelper {
    private static String TAG = MainActivity.TAG;

    // Constructer, Input DB_Name, DB_Version Argument
    public DBManagement(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB Create
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE ADVERTISE (" +
                "  adv_key INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT," +
                "  adv_title VARCHAR(45) NULL," +
                "  adv_message VARCHAR(45) NULL," +
                "  adv_ticker VARCHAR(45) NULL," +
                "  adv_resource VARCHAR(45) NULL," +
                "  adv_time TIME NULL);" );
    }

    // DB Version 관리
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Web에서 가져와 Passing한 객체 AdvVO로 DB에 저장
     * @param advVO
     */
    public void create(AdvVO advVO) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" +
                advVO.getAdv_title() + "', " +
                advVO.getAdv_message() + ", '" +
                advVO.getAdv_ticker() + ", '" +
                advVO.getAdv_resource() + ", '" +
                advVO.getAdv_time() + "');");
        db.close();

    }

    public AdvVO read(Integer adv_key) {
        AdvVO advVO = new AdvVO();

        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // Cursor를 사용하여 여러 데이터 한번에 처리
        Cursor cursor = db.rawQuery("SELECT * FROM ADVERTISE WHERE adv_key ="+ adv_key +";", null);
        advVO.setAdv_key(cursor.getPosition());
        advVO.setAdv_title(cursor.getString(0));
        advVO.setAdv_message(cursor.getString(1));
        advVO.setAdv_ticker(cursor.getString(2));
        advVO.setAdv_resource(cursor.getString(3));

        return advVO;
    }

    /* Update는 아직 불필요한 듯 하니 봉인 아래 함수는 예시
    public void update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }
    */

    public void delete(AdvVO advVO) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM ADVERTISE WHERE adv_key='" + advVO.getAdv_key() + "';");
        db.close();
    }

    /* 전체 조회기능도 일단 봉인. 아래는 예시
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM ADVERTISE", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getInt(2)
                    + "원 "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }
    */

}
