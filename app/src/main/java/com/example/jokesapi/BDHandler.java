package com.example.jokesapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class BDHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="jokes_db";
    private static final String TABLE_NAME = "jokes";
    private static final String JOKE_TEXT = "text";
    private static final String JOKE_NUMBER = "number";
    private static final String JOKE_ID = "id";

    public BDHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//create BD
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_JOKES_TABLE = "CREATE TABLE "+TABLE_NAME+"("+JOKE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+JOKE_TEXT+" TEXT, "+JOKE_NUMBER+" TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_JOKES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
//for add jokes to BD
    public void addJokes(ArrayList<OneJoke> jokeArrayList){
        SQLiteDatabase db = this.getWritableDatabase();
        for (OneJoke oneJoke:jokeArrayList){
            ContentValues cv = new ContentValues();
            cv.put(JOKE_TEXT,oneJoke.getTextOfJoke());
            cv.put(JOKE_NUMBER,oneJoke.getNumberOfJoke());
            db.insert(TABLE_NAME,null,cv);
        }
        db.close();
    }
//get all jokes
    public ArrayList<OneJoke> getAllJokes(){
        SQLiteDatabase db =this.getReadableDatabase();
        ArrayList<OneJoke> jokeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        while (cursor.moveToNext()){
            OneJoke oneJoke = new OneJoke();
            oneJoke.setId(cursor.getInt(0));
            oneJoke.setTextOfJoke(cursor.getString(1));
            oneJoke.setNumberOfJoke(cursor.getString(2));
            jokeList.add(oneJoke);
        }
        cursor.close();
        db.close();
        return jokeList;
    }
//for delet all jokes from table
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
        db.close();
    }
}