package com.bishoku.hashcode;


import java.util.ArrayList;
import java.util.List;

public class Library implements Comparable<Library>{

    /**
     * Library 0 has 5 books, the signup process takes 2 days, and the library
     * can ship 2 books per day.
     */

    public Library(String line) {
        final String[] lineArray = line.split(" ");

        bookCount = Integer.parseInt(lineArray[0]);
        signupTime = Integer.parseInt(lineArray[1]);
        velocity = Integer.parseInt(lineArray[2]);
    }

    public long index;
    public long bookCount;
    public long signupTime;
    public long velocity;
    public List<Book> bookList;
    public List<Book> scanBookList = new ArrayList<>();
    public long maxDay;

    public long getTotalScore() {
        return bookList.stream().map(b -> b.score).reduce(0L, (a, b) -> a + b);
    }

    @Override
    public int compareTo(Library o) {

        return  this.calculateTotalScore(this) - o.calculateTotalScore(o);
    }

    private int calculateTotalScore(Library lib){
        int totalBooksInThis = (int)((lib.maxDay -lib.signupTime)*lib.velocity);

        long totalScore = 0;

        for(int i = 0; i< totalBooksInThis && i< lib.bookList.size();i++){
            totalScore += lib.bookList.get(i).score;
        }
      return (int)totalScore;

    }
}
