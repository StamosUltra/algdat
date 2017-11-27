import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaki on 12.11.2017.
 */
public class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public static List<HuffmanNode> getLeafNodes(HuffmanNode node) {
        ArrayList<HuffmanNode> nodes = new ArrayList<>();
        if (!(node.isLeaf())){
            nodes.addAll(getLeafNodes(node.left));
            nodes.addAll(getLeafNodes(node.right));
        }
        return nodes;
    }
}
