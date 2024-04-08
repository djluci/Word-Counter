package PQ;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommonWordsFinder {

    /**
     * reads the contents of a word count file and reconstructs
     * the fields of the WordCount object, including the MapSet (BST or HashMap).
     *
     * @param filename, String, the filename to be loaded
     */
    public static void readWordCountFile(String filename, PQHeap<MapSet.KeyValuePair<String,Double>> pqHeap) throws FileNotFoundException {
        Integer wordCount;
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = br.readLine();
            String[] words = line.split(" : ");
            wordCount = Integer.parseInt(words[1]);
            line = br.readLine();
            // start a while loop that loops while line isn't null
            while (line != null) {
                // split line into words.
                words = line.split("[\s]");
                pqHeap.offer(new MapSet.KeyValuePair<>(words[0], (double) Integer.parseInt(words[1])/(double) wordCount));
                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        PQHeap<MapSet.KeyValuePair<String, Double>> pqHeap;
        MapSet.KeyValuePair<String, Double> kvp;

        int numOfWords = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            pqHeap = new PQHeap<>(16, new KeyValuePairComparatorByValue<>());
            readWordCountFile(args[i], pqHeap);
            System.out.println("" + ConsoleColors.ANSI_RED + "| " + args[i] + " |" + ConsoleColors.ANSI_RESET);
            for (int j = 0; j < numOfWords-1; j++) {
                kvp = pqHeap.poll();
                System.out.println("├->"+ ConsoleColors.ANSI_GREEN+"\t"+j+ ConsoleColors.ANSI_RESET+": < "+ ConsoleColors.ANSI_BLUE+kvp.getKey()+ ConsoleColors.ANSI_RESET+" , "+ ConsoleColors.ANSI_PURPLE+kvp.getValue()+ ConsoleColors.ANSI_RESET+" >");
            }
            kvp = pqHeap.poll();
            System.out.println("└->"+ ConsoleColors.ANSI_GREEN+"\t"+(numOfWords-1)+ ConsoleColors.ANSI_RESET+": < "+ ConsoleColors.ANSI_BLUE+kvp.getKey()+ ConsoleColors.ANSI_RESET+" , "+ ConsoleColors.ANSI_PURPLE+kvp.getValue()+ ConsoleColors.ANSI_RESET+" >");
        }
    }


}