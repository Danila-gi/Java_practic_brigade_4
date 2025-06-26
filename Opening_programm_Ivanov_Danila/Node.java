package practic.danila.list;

/**
 * Class include structure of node for unrolled linked list
 * array - array of objects in this node
 * array_lenght - number of filled cells in the array
 * capacity - max number of elements
 * next - pointer to the next node in the list
 */

public class Node {
    public Object[] array;
    public int array_lenght;
    public int capacity;
    public Node next;

    public Node(Object[] array, int max_lenght){
        this.array = new Object[max_lenght];
        this.array_lenght = 0;
        for (int i = 0; i < array.length; i++){
            if (array[i] == null)
                break;
            this.array[i] = array[i];
            this.array_lenght++;
        }
        this.next = null;
        this.capacity = max_lenght;
    }

    public void push(Object element) {
        /* Function of push one element to the node */
        this.array[array_lenght++] = element;
    }

    public Node separate(){
        /* Function of separate filled array into two equal parts */
        Object[] first_arr = new Object[this.array_lenght];
        Object[] second_arr = new Object[this.array_lenght];
        for (int i = 0; i < this.array_lenght; i++){
            if (i < this.array_lenght / 2)
                first_arr[i] = array[i];
            else
                second_arr[i - this.array_lenght / 2] = array[i];
        }
        this.array = first_arr.clone();
        this.array_lenght = this.array_lenght / 2;
        return new Node(second_arr, this.capacity);
    }

    public void insert(Object element, int index){
        /* Function of push one element to the node with some index */
        Object[] new_arr = new Object[this.capacity];
        int k = 0;
        int i = 0;
        while (i < this.array_lenght){
            if (k == index){
                new_arr[k] = element;
                k++;
                continue;
            }
            new_arr[k] = this.array[i];
            i++;
            k++;
        }
        this.array = new_arr;
        this.array_lenght++;
    }

    public void delete(int index){
        /* Function of delete one element from the node with some index */
        Object[] new_arr = new Object[this.capacity];
        int k = 0;
        int i = 0;
        boolean flag = true;
        while (i < this.array_lenght){
            if (k == index && flag){
                i++;
                flag = false;
                continue;
            }
            new_arr[k] = this.array[i];
            i++;
            k++;
        }
        this.array = new_arr;
        this.array_lenght--;
    }

    public int find(Object element){
        /* Function of find element in the node */
        for (int i = 0; i < this.array_lenght; i++){
            if (this.array[i].equals(element))
                return i;
        }
        return -1;
    }
}
