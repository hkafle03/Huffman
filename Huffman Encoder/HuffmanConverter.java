import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

//constructor
public class HuffmanConverter {

    // ASCII number of characters
    public static final int NUMBER_OF_CHARACTERS = 256;

    private final String contents;
    private HuffmanTree huffmanTree;
    private final int[] count;
    private final String[] code;

    // Construct using an input string.
    // Initialize count and code.
    public HuffmanConverter(String input) {
        this.contents = input;
        this.count = new int[NUMBER_OF_CHARACTERS];
        this.code = new String[NUMBER_OF_CHARACTERS];
    }

    // Record how often each character occurs and store in count.
    public void recordFrequencies() {
        char[] tokenized = contents.toCharArray();
        for (char c : tokenized) {
            count[c]++;
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0 && i != 10) {
                System.out.print("<" + (char) i + ", " + (double) count[i] + "> ");
            } else if (i == 10) {
                System.out.print("<\\n, " + (double) count[i] + "> ");
            }
        }
    }

    // Construct a Huffman tree from count and
    // store the tree in huffmanTree.
    public void frequenciesToTree() {
        StringBuilder legend = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                legend.append((char) i);
                legend.append(count[i]);
            }
        }
        BinaryHeap<HuffmanNode> heap = HuffmanTree.legendToHeap(legend.toString());
        huffmanTree = HuffmanTree.createFromHeap(heap);
    }

    // Construct code from huffmanTree.
    public void treeToCode() {
        Arrays.fill(code, "");
        treeToCode(huffmanTree.root, "");
        System.out.println("\n");
        huffmanTree.printLegend();
    }

    private void treeToCode(HuffmanNode t, String encoding) {
        if (t.letter.length() > 1 && !t.letter.equals("\\n")) {
            treeToCode(t.left, encoding + "0");
            treeToCode(t.right, encoding + "1");
        } else {
            code[t.letter.charAt(0)] = encoding;
        }
    }

    // Encode content using code.
    public String encodeMessage() {
        StringBuilder encoded = new StringBuilder();
        char[] tokenized = contents.toCharArray();
        for (char c : tokenized) {
            encoded.append(code[c]);
        }
        System.out.println("\nHuffman Encoding:");
        return encoded.toString();
    }

    // Decode a Huffman encoding.
    public String decodeMessage(String encodedStr) {
        StringBuilder decoded = new StringBuilder();
        char[] tokenized = encodedStr.toCharArray();
        HuffmanNode huff = huffmanTree.root;
        for (char c : tokenized) {
            if (c == '0') {
                huff = huff.left;
            } else if (c == '1') {
                huff = huff.right;
            }
            if (huff.letter.length() == 1) {
                decoded.append(huff.letter);
                huff = huffmanTree.root;
            }
        }
        return decoded.toString();
    }

    // Read an input file.
    public static String readContents(String filename) {
        StringBuilder temp = new StringBuilder();
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                temp.append(sc.nextLine());
                temp.append("\n");
            }
            sc.close();
            return temp.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        // String input = HuffmanConverter.readContents("/Users/hardikkafle/IdeaProjects/HW6/src/Homework6/love_poem_80.txt");
        String input = HuffmanConverter.readContents(args[0]);
        HuffmanConverter h = new HuffmanConverter(input);
        h.recordFrequencies();
        h.frequenciesToTree();
        h.treeToCode();
        String encoded = h.encodeMessage();
        System.out.println(encoded + "\n");
        System.out.println("Message size in ASCII encoding: " + h.contents.length() * 8);
        System.out.println("Message size in Huffman coding: " + encoded.length() + "\n");
        System.out.println(h.decodeMessage(encoded));
    }

}
