package ru.nsu.fit.oop.maximov;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Notebook {
    private final ArrayList<Note> notes = new ArrayList<Note>();

    public void add(Note note){
        notes.add(note);
    }

    public void rm(String title) {
        notes.removeIf(note -> title.equals(note.title));
    }

    public void show(BufferedWriter writer) throws IOException {
        for (var note : notes) {
            note.print(writer);
        }
    }

    private boolean isInInterval(Date curr, Date begin, Date end) {
        return curr.after(begin) && curr.before(end);
    }

    public void show(BufferedWriter writer, Date begin, Date end, String keyword) throws IOException {
        for (var note : notes) {
            // check conditions
            if (isInInterval(note.time, begin, end) && note.title.contains(keyword)) {
                note.print(writer);
            }
        }
    }
}
