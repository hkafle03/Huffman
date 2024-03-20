public class HuffmanNode implements Comparable<HuffmanNode> {
    public String letter;
    public Double frequency;
    public HuffmanNode left, right;

    public HuffmanNode(String letter, Double frequency) {
        this.letter = letter;
        this.frequency = frequency;
        left = null;
        right = null;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        letter = left.letter + right.letter;
        frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    public int compareTo(HuffmanNode huff) {
        return this.frequency.compareTo(huff.frequency);
    }

    public String toString() {
        return "<" + letter + ", " + frequency + ">";
    }
}