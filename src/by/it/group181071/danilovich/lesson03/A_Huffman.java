package by.it.group181071.danilovich.lesson03;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Lesson 3. A_Huffman.
//Разработайте метод encode(File file) для кодирования строки (код Хаффмана)

// По данным файла (непустой строке ss длины не более 104104),
// состоящей из строчных букв латинского алфавита,
// постройте оптимальный по суммарной длине беспрефиксный код.

// Используйте Алгоритм Хаффмана — жадный алгоритм оптимального
// безпрефиксного кодирования алфавита с минимальной избыточностью.

// В первой строке выведите количество различных букв kk,
// встречающихся в строке, и размер получившейся закодированной строки.
// В следующих kk строках запишите коды букв в формате "letter: code".
// В последней строке выведите закодированную строку. Примеры ниже

//        Sample Input 1:
//        a
//
//        Sample Output 1:
//        1 1
//        a: 0
//        0

//        Sample Input 2:
//        abacabad
//
//        Sample Output 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111

public class A_Huffman
{

    //Изучите классы Node InternalNode LeafNode
    abstract class Node implements Comparable<Node>
    {
        //абстрактный класс элемент дерева
        //(сделан abstract, чтобы нельзя было использовать его напрямую)
        //а только через его версии InternalNode и LeafNode
        private final int frequence; //частота символов

        //генерация кодов (вызывается на корневом узле
        //один раз в конце, т.е. после построения дерева)
        abstract void fillCodes(String code);

        //конструктор по умолчанию
        private Node(int frequence) {
            this.frequence = frequence;
        }

        //метод нужен для корректной работы узла в приоритетной очереди
        //или для сортировок
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequence, o.frequence);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //расширение базового класса до внутреннего узла дерева
    private class InternalNode extends Node
    {

        Node left;
        Node right;


        InternalNode(Node left, Node right)
        {
            super(left.frequence + right.frequence);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code)
        {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    //расширение базового класса до листа дерева
    private class LeafNode extends Node
    {
        //лист
        char symbol;

        LeafNode(int frequence, char symbol)
        {
            super(frequence);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            A_Huffman.codes.put(this.symbol, code);
        }
    }

    //индекс данных из листьев
    static private Map<Character, String> codes = new TreeMap<>();


    //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    String encode(File file) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(file);
        String s = scanner.next();

        Map<Character, Integer> count = new HashMap<>();

        for (int i = 0; i < s.length(); i++)
        {
            if (!count.containsKey(s.toCharArray()[i]))
            {
                count.put(s.toCharArray()[i], 1);
            } else count.put(s.toCharArray()[i], count.get(s.toCharArray()[i]) + 1);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : count.entrySet())
        {
            LeafNode node = new LeafNode(entry.getValue(), entry.getKey());
            priorityQueue.add(node);
        }

        while (priorityQueue.size() > 1)
        {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            InternalNode node = new InternalNode(left, right);
            priorityQueue.add(node);
        }
        priorityQueue.poll().fillCodes("");


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
            sb.append(codes.get(s.toCharArray()[i]));
        }

        return sb.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println(result);
    }

}