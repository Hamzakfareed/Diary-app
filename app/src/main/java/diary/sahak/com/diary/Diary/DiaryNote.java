package diary.sahak.com.diary.Diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import diary.sahak.com.diary.R;


public class DiaryNote extends AppCompatActivity {

    TextView diaryDetail =null;
    private Database db = null;
    Diary diary = null;
    int id;
    String title;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_note);
        db = new Database(this);
        diaryDetail = (EditText)findViewById(R.id.diaryNoteDetail);

        Bundle bundle = getIntent().getExtras();
        id= bundle.getInt("id");
        title =  bundle.getString("title");
         content = bundle.getString("content");

       if(content !=null) {
           diaryDetail.setText(content);
       }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveNote:
                if(content !=null && title!= null) {
                    diary = new Diary(id,title,diaryDetail.getText().toString());
                }else if(content ==null && title!=null) {
                    diary = new Diary();
                    diary.setTitle(title);
                    diary.setNote(diaryDetail.getText().toString());
                }
                String t[];
                   t= title.split("e:");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE yyyy.MMMM.dd hh:mm aaa");

                boolean isDate = false;
                simpleDateFormat.setLenient(false);



                try {
                    if(t!=null) {
                        if (t.length > 1) {
                            simpleDateFormat.parse(t[1].trim());
                            isDate = true;
                        }
                    }
                }catch(ParseException e) {
                    isDate=false;
                }

                if( isDate || title.length()==0) {
                    diary.setTitle("Note:"+simpleDateFormat.format(new Date()));
                }

                
                db.saveDiary(diary);
                Intent intent =new Intent(this,DiaryActivity.class);
                startActivity(intent);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
