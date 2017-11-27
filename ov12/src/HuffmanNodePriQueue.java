import java.util.HashMap;
import java.util.Map;

/**
 * Created by joaki on 12.11.2017.
 */
public class HuffmanNodePriQueue {
    int length;
    HuffmanNode[] huffmanNodes;

    public HuffmanNodePriQueue (int[] frequencies){
        int length = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) length++;
        }
        this.length = length;
        huffmanNodes = new HuffmanNode[length];
        int index = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                huffmanNodes[index] = new HuffmanNode((char)i, frequencies[i], null, null);
                index++;
            }
        }
        createHeap();
    }

    public void fixHeap(int i){
        int leftChild = (i<<1) + 1;
        if (leftChild < length) {
            int rightChild = leftChild + 1;
            if (rightChild < length && huffmanNodes[rightChild].frequency < huffmanNodes[leftChild].frequency) leftChild = rightChild;
            if (huffmanNodes[leftChild].frequency < huffmanNodes[i].frequency) {
                HuffmanNode help = huffmanNodes[leftChild];
                huffmanNodes[leftChild] = huffmanNodes[i];
                huffmanNodes[i] = help;
                fixHeap(leftChild);
            }
        }
    }

    public void createHeap() {
        int i = length/2;
        while (i-- > 0) fixHeap(i);
    }

    public boolean checkHeap() {
        for (int i = 0; i < huffmanNodes.length; i++) {
            int left = (i << 1) + 1;
            int right = left + 1;
            if (left < length && huffmanNodes[left].frequency < huffmanNodes[i].frequency) {
                System.out.println("left: " + left + " i " + i);
                return false;
            }
            if (right < length && huffmanNodes[right].frequency < huffmanNodes[i].frequency) {
                System.out.println("right: " + right + " i: " + i);
                return false;
            }
        }
        return true;
    }

    public HuffmanNode getMin() {
        try {
            HuffmanNode min = huffmanNodes[0];
            huffmanNodes[0] = huffmanNodes[--length];
            fixHeap(0);
            return min;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void priUp(int i, int pri) {
        int f = 0;
        huffmanNodes[i].frequency += pri;
        while(i>0 && huffmanNodes[i].frequency < huffmanNodes[f=((i-1)>>1)].frequency){
            HuffmanNode help = huffmanNodes[i];
            huffmanNodes[i] = huffmanNodes[f];
            huffmanNodes[f] = help;
            i = f;
        }
    }

    public void insert(HuffmanNode node) {
        int i = length++;
        this.huffmanNodes[i] = node;
        priUp(i, 0);
    }

    public Map getLeafs (HuffmanNode n) {
        HashMap map = new HashMap();
        if (n.isLeaf()) {
            map.put(n.character, n);
        } else {
            map.putAll(getLeafs(n.left));
            map.putAll(getLeafs(n.right));
        }
        return map;
    }

    public static void getDepth(HuffmanNode n, int i) {
        if(!n.isLeaf()) {
            getDepth(n.left, ++i);
            getDepth(n.right, ++i);
        }
        //System.out.println("depth: " + i);
    }

}
