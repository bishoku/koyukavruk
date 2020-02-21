package com.bishoku.hashcode;

public class Book implements Comparable<Book> {

    public long index;
    public long score;


    @Override
    public int compareTo(Book o) {
        return Long.compare(o.score,score);
    }
}
