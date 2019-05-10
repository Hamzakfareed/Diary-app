package diary.sahak.com.diary.Diary;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import diary.sahak.com.diary.R;


public class DiaryActivity extends AppCompatActivity {
    private Database db= new Database(this);

    private ArrayList<Diary> diaryList =null;
    private DiaryListAdapter diaryListAdapter=null;
    private ListView listView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);


        addListView();

    }

    public void addListView() {

        diaryList =db.retrieveDiary();
        diaryListAdapter = new DiaryListAdapter(this,diaryList);

        listView = findViewById(R.id.diaryNoteList);

        listView.setAdapter(diaryListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Diary diary = diaryList.get(i);
                Intent intent = new Intent(DiaryActivity.this,DiaryNote.class);
                intent.putExtra("title",diary.getTitle());
                intent.putExtra("id",diary.getId());
                intent.putExtra("content",diary.getNote());

                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                final Dialog dialog = new Dialog(DiaryActivity.this);
                dialog.setContentView(R.layout.delete_diary_layout);
                Button deleteDiaryButton = (Button)dialog.findViewById(R.id.delteDiaryButton);
                deleteDiaryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Diary diary = diaryList.get(i);
                        db.deleteDiary(diary.getId());
                        diaryListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });



                dialog.show();
                return true;
            }
        });
    }

    public void addNote(View view) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.add_note_layout);

        final EditText noteTitle = (EditText)dialog.findViewById(R.id.noteTitle);
        Button addNoteTitle = (Button)dialog.findViewById(R.id.addNoteTitle);

        addNoteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = noteTitle.getText().toString();
                Intent intent = new Intent(DiaryActivity.this,DiaryNote.class);
                intent.putExtra("title",title);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}

