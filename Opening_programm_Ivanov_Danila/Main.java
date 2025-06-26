package practic.danila.list;

public class Main {
    public static void main(String[] args) {
        int N = 50;
        Integer[] data = new Integer[N];
        for (int i = 1; i <= N; i++)
            data[i - 1] = i;
        UnrolledLinkedList list = new UnrolledLinkedList(data);
        list.print_elements();
        System.out.println("================");

        System.out.println("Push 3 new elements:");
        list.push(97);
        list.push(98);
        list.push(99);
        list.print_elements();
        System.out.println("================");

        System.out.println("Insert 3 elements:");
        list.insert(222, 20);
        list.insert(333, 0);
        list.insert(444, 25);
        list.print_elements();
        System.out.println("================");

        System.out.println("Delete 3 elements:");
        list.delete_number(14);
        list.delete_number(50);
        list.delete_number(1);
        list.print_elements();
        System.out.println("================");
    }
}
