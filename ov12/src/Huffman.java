import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by joaki on 12.11.2017.
 */
public final class Huffman {

    private Huffman() {}

    public static double compress(File file) throws IOException, ClassNotFoundException{
        String sentence = getInputFromFile(file);
        long fileSize = file.length();
        final int[] frequencies = makeFrequencyArray(sentence);
        final HuffmanNode root = buildTree(frequencies);
        HuffmanNodePriQueue.getDepth(root, 0);
        final Map<Character, String> charCode = generateCodes(root);
        final String encodedMessage = encodeMessage(charCode, sentence);
        //System.out.println("encoded: " + encodedMessage);
        final byte[] encoded = convertToByte(encodedMessage);

        HuffmanNode.getLeafNodes(root);

        serializeTree(root);
        return (double)serializeMessage(encoded)/ (double)fileSize;
    }

    public static List<HuffmanNode> expand(File file) throws FileNotFoundException, ClassNotFoundException, IOException {
        final int[] frequencies = makeFrequencyArray(getInputFromFile(file));
        int chars = 0;
        for (int i = 0; i < frequencies.length; i++) {
            chars += frequencies[i];
        }
        final HuffmanNode root = deserializeTree();
        final String message = decodeMessage(root, chars);
        //System.out.println("decoded: " + message);
        writeToOutput(message);

        return HuffmanNode.getLeafNodes(root);
    }

    private static String getInputFromFile(File file) throws FileNotFoundException, IOException{
        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            byte[] bytes = new byte[(int)file.length()];
            inputStream.readFully(bytes);
            return new String(bytes, Charset.forName("UTF-8"));
        } catch (EOFException e) {
            return null;
        }
    }

    private static String convertToString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
            /*
            else {
                //System.out.println("ignored: " + (char)bytes[i]);
                try {
                    specialCase[0] = bytes[i];
                    specialCase[1] = bytes[++i];
                    String s = new String(bytes, Charset.forName("UTF-8"));
                    c = s.charAt(0);
                    stringBuilder.append(c); // merges two bytes to one char;
                    c = (int)0;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("ArrayIndexOutOfBounds");
                }
            }
            */
        }
        return stringBuilder.toString();
    }

    private static int[] makeFrequencyArray (String s) {
        int ignored = 0;
        int[] frequencies = new int[256];
        for (int i = 0; i < s.length(); i++) {
            int c = (int) s.charAt(i);
            if (c >= 0 && c < 256) {
                frequencies[c]++;
            } else ignored++;
        }
        System.out.println("chars: " + s.length());
        System.out.println("ignored: " + ignored);
        return frequencies;
    }

    private static HuffmanNode buildTree(int[] frequencies) {
        /*
        ArrayList<HuffmanNode> list = new ArrayList<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                list.add(new HuffmanNode((char)i, frequencies[i], null, null));
            }
        }
        final PriorityQueue<HuffmanNode> queue = new PriorityQueue(list);
        */

        HuffmanNodePriQueue queue = new HuffmanNodePriQueue(frequencies);
        while (queue.length > 1) {
            HuffmanNode left = queue.getMin();
            HuffmanNode right = queue.getMin();
            int llFreq = 0;
            if (left != null) llFreq = left.frequency;
            int rFreq = 0;
            if (right != null) rFreq = right.frequency;
            HuffmanNode parent = new HuffmanNode('\0', llFreq + rFreq, left, right);
            queue.insert(parent);
        }
        return queue.getMin();
    }

    private static Map<Character, String> generateCodes(HuffmanNode root) {
        final Map<Character, String> map = new HashMap<>();
        doGenerateCode(map, root, "");
        return map;
    }

    private static void doGenerateCode(Map<Character, String> map, HuffmanNode node, String s) {
        if (node.isLeaf()) {
            map.put(node.character, s);
        }
        else {
            doGenerateCode(map, node.left, s + '0');
            doGenerateCode(map, node.right, s + '1');
        }
    }

    private static String encodeMessage(Map<Character, String> map, String sentence) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            try {
                stringBuilder.append(map.get(sentence.charAt(i)));
            } catch (OutOfMemoryError e) {
                System.out.println("Error: Out of memory");
                return stringBuilder.toString();
            }
        }

        return stringBuilder.toString();
    }

    private static void serializeTree(HuffmanNode node) throws FileNotFoundException, IOException {
        final BitSet bitSet = new BitSet();
        try (ObjectOutputStream oosTree = new ObjectOutputStream(new FileOutputStream("src\\resources\\tree"))) {
            try (ObjectOutputStream oosChar = new ObjectOutputStream(new FileOutputStream("src\\resources\\char"))) {
                IntObject o = new IntObject();
                preOrder(node, oosChar, bitSet, o);
                bitSet.set(o.bitPosition, true); // padded to mark end of bit set relevant for deserialization.
                oosTree.writeObject(bitSet);
            }
        }
    }

    private static void preOrder(HuffmanNode node, ObjectOutputStream oosChar, BitSet bitSet, IntObject intObject) throws IOException {
        if (node.isLeaf()) {
            bitSet.set(intObject.bitPosition++, false);  // register branch in bitset
            oosChar.writeChar(node.character);
            return;                                  // DONT take the branch.
        }
        bitSet.set(intObject.bitPosition++, true);           // register branch in bitset
        preOrder(node.left, oosChar, bitSet, intObject); // take the branch.

        bitSet.set(intObject.bitPosition++, true);               // register branch in bitset
        preOrder(node.right, oosChar, bitSet, intObject);    // take the branch.
    }

    private static HuffmanNode preOrder(BitSet bitSet, ObjectInputStream oisChar, IntObject o) throws IOException {
        // created the node before reading whats registered.
        final HuffmanNode node = new HuffmanNode('\0', 0, null, null);

        // reading whats registered and determining if created node is the leaf or non-leaf.
        if (!bitSet.get(o.bitPosition)) {
            o.bitPosition++;              // feed the next position to the next stack frame by doing computation before preOrder is called.
            node.character = oisChar.readChar();
            return node;
        }

        o.bitPosition = o.bitPosition + 1;  // feed the next position to the next stack frame by doing computation before preOrder is called.
        node.left = preOrder(bitSet, oisChar, o);

        o.bitPosition = o.bitPosition + 1; // feed the next position to the next stack frame by doing computation before preOrder is called.
        node.right = preOrder(bitSet, oisChar, o);

        return node;
    }

    private static void serializeMessage(String message) throws IOException {
        final BitSet bitSet = getBitSet(message);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src\\encoded\\encodedMessage"))){

            oos.writeObject(bitSet);
        }
    }
    private static long serializeMessage(byte[] message) throws IOException {
        File file = new File("src\\encoded\\encodedBytes");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))){
            dos.write(message, 0, message.length);
        }
        return file.length();
    }

    private static BitSet getBitSet(String message) {
        final BitSet bitSet = new BitSet();
        int i = 0;
        for (i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '0') {
                bitSet.set(i, false);
            } else {
                bitSet.set(i, true);
            }
        }
        bitSet.set(i, true); // dummy bit set to know the length
        return bitSet;
    }

    private static HuffmanNode deserializeTree() throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectInputStream oisBranch = new ObjectInputStream(new FileInputStream("src\\resources\\tree"))) {
            try (ObjectInputStream oisChar = new ObjectInputStream(new FileInputStream("src\\resources\\char"))) {
                final BitSet bitSet = (BitSet) oisBranch.readObject();
                return preOrder(bitSet, oisChar, new IntObject());
            }
        }
    }

    private static String decodeMessage(HuffmanNode node, int chars) throws FileNotFoundException, IOException {
        File file = new File("src\\encoded\\encodedBytes");
        String bits;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[(int)file.length()];
            dis.readFully(bytes);
            bits = convertToString(bytes);
            //System.out.println("biiiits: " + bits);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            HuffmanNode temp = node;
            while (temp.left != null) {
                if (bits.charAt(i) == '0') {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
                i = i + 1;
            }
            stringBuilder.append(temp.character);
            if (stringBuilder.length() == chars) return stringBuilder.toString();
            i--;
        }
        return stringBuilder.toString();
        /*
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\encoded\\encodedMessage"))) {
            final BitSet bitSet = (BitSet) ois.readObject();
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (bitSet.length() - 1);) {
                HuffmanNode temp = node;
                // since huffman code generates full binary tree, temp.right is certainly null if temp.left is null.
                while (temp.left != null) {
                    if (!bitSet.get(i)) {
                        temp = temp.left;
                    } else {
                        temp = temp.right;
                    }
                    i = i + 1;
                }
                stringBuilder.append(temp.character);
            }
            System.out.println("chars, decoded: " + stringBuilder.length());
            return stringBuilder.toString();
        }
        */
    }
    private static void writeToOutput(String decodedMessage) throws FileNotFoundException, IOException{
        PrintWriter out = new PrintWriter("src\\decoded\\message");
        out.print(decodedMessage);
    }

    private static byte[] convertToByte(String sentence) {
        ArrayList<Byte> bytes = new ArrayList<>();
        int i = 0;
        String sub;
        while (i < sentence.length()) {
            try {
                sub = sentence.substring(i, i + 8);
                bytes.add((byte) Integer.parseInt(sub, 2));
                i += 8;
            } catch (StringIndexOutOfBoundsException e) {
                sub = sentence.substring(i, sentence.length());
                for (int j = sub.length(); j < 8; j++) {
                    sub = sub + '0';
                }
                bytes.add((byte) Integer.parseInt(sub, 2));
                i = sentence.length();
            }
        }
        byte[] out = new byte[bytes.size()];
        for (int j = 0; j < bytes.size(); j++) {
            out[j] = bytes.get(j);
        }
        return out;
    }

    public static void cutFile(File file, int start, int end) throws IOException{
        String input;
        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            byte[] bytes = new byte[(int)file.length()];
            inputStream.readFully(bytes);
            input = new String(bytes, Charset.forName("UTF-8"));
        } catch (EOFException e) {
            return;
        }

        input = input.substring(start, end);

        PrintWriter out = new PrintWriter(file);
        out.print(input);
    }
}
