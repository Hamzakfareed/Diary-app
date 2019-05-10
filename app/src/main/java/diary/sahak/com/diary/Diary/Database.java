/**************************************************************************
 *
 * Copyright (C) 2012-2015 Alex Taradov <alex@taradov.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************/

package diary.sahak.com.diary.Diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Aladdin on 14-Dec-17.
 */

public class Database extends SQLiteOpenHelper {


  ///////////////////////////////////////////////////

  private static final String DIARY_TABLE="DIARY";
  private static final String DIARY_ID="ID";
  private static final String DIARY_TITLE="TITLE";
  private static final String DIARY_CONTENT = "CONTENT";


  private Context context;

  ///////////////////////////////////////////////////////////////////

  ////////////////////////////////////////////////////////////////
  public Database(Context context) {
    super(context, "diary.db", null, 1);
    this.context = context;


  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

    String dropDiaryTable = String.format("DROP TABLE IF EXISTS %s",DIARY_TABLE);
    sqLiteDatabase.execSQL(dropDiaryTable);


    String createDiaryTableSql = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(100) , %s VARCHAR(500))",DIARY_TABLE,DIARY_ID,DIARY_TITLE,DIARY_CONTENT);
    sqLiteDatabase.execSQL(createDiaryTableSql);






  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DIARY_TABLE);
    onCreate(sqLiteDatabase);

  }



  public void saveDiary(Diary diary){
    SQLiteDatabase db = getWritableDatabase();

    boolean exist = false;
    if(diary.getId()!=0) {
      exist = isExists(diary.getId());
    }

    if(exist) {
      updateDiary(diary);
      return;
    }
    ContentValues values = new ContentValues();


    values.put(DIARY_TITLE,diary.getTitle());
    values.put(DIARY_CONTENT,diary.getNote());


    Log.d("Save DB",""+diary);
    db.insert(DIARY_TABLE, null, values);



    db.close();
  }



  public boolean isExists(int id) {
    SQLiteDatabase db = getReadableDatabase();
    Diary diary = null;
    String sql = String.format("SELECT %s,%s,%s FROM %s WHERE %s=%d",DIARY_ID,DIARY_TITLE,DIARY_CONTENT,DIARY_TABLE,DIARY_ID,id);
    Cursor cursor = db.rawQuery(sql,null);



    Log.d("DB:","isexist db");

    return cursor.getCount()>0;
  }


  public ArrayList retrieveDiary() {
    SQLiteDatabase db = getReadableDatabase();
    ArrayList<Diary> diaryList = new ArrayList<>();
    Diary diary = null;
    String sql = String.format("SELECT %s,%s,%s FROM %s ORDER BY %s",DIARY_ID,DIARY_TITLE,DIARY_CONTENT,DIARY_TABLE,DIARY_ID);
    Cursor cursor = db.rawQuery(sql,null);
    while(cursor.moveToNext()) {
      diary = new Diary(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
      diaryList.add(diary);

    }
    db.close();
    return diaryList;
  }


  public void deleteDiary(int id) {
    SQLiteDatabase db = getWritableDatabase();

    Log.d("HSK","Delte :"+id);

    String sql = String.format("DELETE FROM %s WHERE %s = %d",DIARY_TABLE,DIARY_ID,id);
    db.execSQL(sql);


  }

  public void updateDiary(Diary diary) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(DIARY_ID,diary.getId());
    values.put(DIARY_TITLE,diary.getTitle());
    values.put(DIARY_CONTENT,diary.getNote());

    Log.d("Update DB",""+diary);
    db.update(DIARY_TABLE,values,DIARY_ID+"="+diary.getId(),null);
    db.close();
  }


}





