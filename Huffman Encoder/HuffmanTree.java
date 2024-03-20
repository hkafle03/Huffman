import java.util.*;

public class HuffmanTree {
    HuffmanNode root;

    public HuffmanTree(HuffmanNode huff) {
        this.root = huff;
    }

    public void printLegend() {
        printLegend(root, "");
    }

    private void printLegend(HuffmanNode node, String code) {
        if (node.letter.length() > 1) {
            printLegend(node.left, code + "0");
            printLegend(node.right, code + "1");
        } else if (!node.letter.equals("\n")) {
            System.out.println("'" + node.letter + "'" + "=" + code);
        } else {
            System.out.println("'\\n'" + "=" + code);
        }
    }

    public static BinaryHeap<HuffmanNode> legendToHeap(String legend) {
        BinaryHeap<HuffmanNode> heap = new BinaryHeap<>();
        List<String> tokenized = parse(legend.toCharArray());
        for (int i = 0; i < tokenized.size(); i += 2) {
            HuffmanNode huff = new HuffmanNode(tokenized.get(i), Double.valueOf(tokenized.get(i + 1)));
            heap.insert(huff);
        }
        return heap;
    }

    public static HuffmanTree createFromHeap(BinaryHeap<HuffmanNode> heap) {
        while (heap.getSize() > 1) {
            HuffmanNode node = new HuffmanNode(heap.deleteMin(), heap.deleteMin());
            heap.insert(node);
        }
        return new HuffmanTree(heap.deleteMin());
    }

    // Modified Helper Method from HW2
    private static List<String> parse(char[] input) {
        List<String> parsed = new ArrayList<>();
        for (int i = 0; i < input.length; ++i) {
            char c = input[i];
            if (Character.isDigit(c)) {
                String number = input[i] + "";
                for (int j = i + 1; j < input.length; ++j) {
                    if (Character.isDigit(input[j])) {
                        number += input[j];
                        i = j;
                    } else {
                        break;
                    }
                }
                parsed.add(number);
            } else {
                parsed.add(c + "");
            }
        }
        return parsed;
    }

}