package diary.sahak.com.diary.Diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import diary.sahak.com.diary.R;


/**
 * Created by Aladdin on 10-Mar-18.
 */

public class DiaryListAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Diary> diaryArrayList;
    private Database db;
    public DiaryListAdapter(Context context, ArrayList<Diary> diaryArrayList) {
        this.context = context;
        db = new Database(context);
        this.diaryArrayList = db.retrieveDiary();
    }
    @Override
    public int getCount() {
        return diaryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return diaryArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return diaryArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.diary_list_item,null);

        Diary diary = diaryArrayList.get(i);

        TextView textView = v.findViewById(R.id.note);

        TextView diaryIcon = v.findViewById(R.id.diaryListImageNoteImg);



        char a = diary.getTitle().toUpperCase().charAt(0);

        diaryIcon.setText(""+a);
        textView.setText(diary.getTitle());
        return v;
    }

}
