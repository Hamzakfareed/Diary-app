package diary.sahak.com.diary.Diary;

import java.io.Serializable;

/**
 * Created by Aladdin on 10-Mar-18.
 */

public class Diary implements Serializable {
    private  int id;
    private String title;
    private String note;


    public Diary () {

    }


    public Diary(int id, String title , String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
