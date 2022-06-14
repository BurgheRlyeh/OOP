package ru.nsu.fit.oop.maximov;

import java.io.*;
import java.util.Date;

public final class Note {
    public Date time;
    public String title;
    public String content;

    public Note(Date timestamp, String title, String text) {
        this.time = timestamp;
        this.title = title;
        this.content = text;
    }

    public void print() throws IOException {
        print(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    public void print(BufferedWriter writer) throws IOException {
        // time
        writer.write(time.toString());
        writer.newLine();

        // title
        writer.write(title);
        writer.newLine();

        // content
        writer.write(content);
        writer.newLine();
    }
}
