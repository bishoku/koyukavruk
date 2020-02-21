
package com.bishoku.hashcode;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //f_libraries_of_the_world.txt
        //a_example.txt
        File datasetb = new File(Paths.get("C:/devel/bishoku", "b_read_on.txt").toUri());
        File resultb = new File(Paths.get("c:/devel/bishoku", "b_read_on_result.txt").toUri());

        File datasetc = new File(Paths.get("C:/devel/bishoku", "c_incunabula.txt").toUri());
        File resultc = new File(Paths.get("c:/devel/bishoku", "c_incunabula_result.txt").toUri());

        File datasetd = new File(Paths.get("C:/devel/bishoku", "d_tough_choices.txt").toUri());
        File resultd = new File(Paths.get("c:/devel/bishoku", "d_tough_choices_result.txt").toUri());

        File datasete = new File(Paths.get("C:/devel/bishoku", "e_so_many_books.txt").toUri());
        File resulte = new File(Paths.get("c:/devel/bishoku", "e_so_many_books_result.txt").toUri());

        File datasetf = new File(Paths.get("C:/devel/bishoku", "f_libraries_of_the_world.txt").toUri());
        File resultf = new File(Paths.get("c:/devel/bishoku", "f_libraries_of_the_world_result.txt").toUri());


        process(datasetb,resultb);
//        process(datasetc,resultc);
//        process(datasetd,resultd);
//        process(datasete,resulte);
//        process(datasetf,resultf);



//        final Path resultPath = Paths.get("c:/devel/bishoku", "aresult.txt");



    }

    public static void process(File dataset,File result){
        try (BufferedReader reader = new BufferedReader(new FileReader(dataset))) {
            String line = reader.readLine();

            final String[] firstLine = line.split(" ");

            int totalLibCount = Integer.parseInt(firstLine[1]);
            long maxDay = Integer.parseInt(firstLine[2]);

            line = reader.readLine();

            final String[] bookScores = line.split(" ");

            List<Book> books = new ArrayList<>(bookScores.length);
            for (int i = 0; i < bookScores.length; i++) {
                Book book = new Book();
                book.index = i;
                book.score = Long.parseLong(bookScores[i]);
                books.add(book);
            }

            List<Library> libraries = new ArrayList<>(totalLibCount);
            for (long i = 0; i < totalLibCount; i++) {

                final Library lib = new Library(reader.readLine());

                final List<Book> booksInLib = Arrays.stream(reader.readLine().split(" ")).map(index -> books.get(Integer.parseInt(index))).collect(Collectors.toList());

                Collections.sort(booksInLib);
                lib.bookList = booksInLib;
                lib.index = i;
                lib.maxDay = maxDay;
                libraries.add(lib);
            }

            Collections.sort(libraries);


            long totalScanDay = 0;


            List<Library> singUpLibraries = new ArrayList<>();
            for (Library library : libraries) {
                totalScanDay += library.signupTime;
                if (maxDay > totalScanDay) {
                    singUpLibraries.add(library);
                } else {
                    totalScanDay -= library.signupTime;
                }
            }

            long dayCount = 0;
            for (Library library : singUpLibraries) {
                dayCount += library.signupTime;

                long remainigDay = maxDay - dayCount;

                long maxBookCapacity = library.velocity * remainigDay;

                long shipedBookCount = 0;
                for (long i = 0; i < maxBookCapacity; i++) {
                    if (library.bookList.isEmpty()) break;
                    library.scanBookList.add(library.bookList.remove(library.bookList.size() - 1));
                    shipedBookCount++;
                    if (maxBookCapacity == shipedBookCount) {
                        break;
                    }
                }
            }


            FileWriter myWriter = new FileWriter(result);
            myWriter.write(String.valueOf(singUpLibraries.size()));
            myWriter.write("\n");
            for (Library library : singUpLibraries) {
                myWriter.write(library.index + " " + library.scanBookList.size());
                myWriter.write("\n");
                for (Book book : library.scanBookList) {
                    myWriter.write(book.index + " ");
                }
                myWriter.write("\n");
            }
            myWriter.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

