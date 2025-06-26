package practic.danila.list;

import java.util.Arrays;

/**
 * Class include structure of unrolled linked list
 * head - the first element of list
 * lenght - the count of linked nodes
 * len_of_node_array - capacity of every node
 */
public class UnrolledLinkedList {
    public Node head;
    public int length;
    private final int len_of_node_array;

    private int calculate_optimal_node_size(int num_elements) {
        /* Function of find node size */
        double memory = num_elements * 4;
        return (int) Math.round(memory / 64.0) + 1;
    }

    public UnrolledLinkedList(Object[] array) {
        this.length = 0;
        this.len_of_node_array = calculate_optimal_node_size(array.length);

        make_linked_list(array);
    }

    private void make_linked_list(Object[] array) {
        /* Function of make linked list with initialization */
        if (array.length == 0)
            return;

        int k = 0;
        Object[] arrayNode = new Object[this.len_of_node_array];
        int index = 0;
        boolean flag_head = false;
        Node el = this.head;
        while (index < array.length) {
            if (k < this.len_of_node_array) {
                arrayNode[k] = array[index];
                k++;
                index++;
            }
            if (k >= this.len_of_node_array) {
                if (!flag_head) {
                    this.head = new Node(arrayNode, this.len_of_node_array);
                    this.length++;
                    flag_head = true;
                    el = this.head;
                } else {
                    el.next = new Node(arrayNode, this.len_of_node_array);
                    this.length++;
                    el = el.next;
                }
                arrayNode = new Object[this.len_of_node_array];
                k = 0;
            }
        }

        if (k != 0) {
            if (!flag_head) {
                this.head = new Node(arrayNode, this.len_of_node_array);
                this.length++;
                return;
            }

            el.next = new Node(arrayNode, this.len_of_node_array);
            this.length++;
        }
    }

    public boolean push(Object element) {
        /* Function of push new element to the end */
        if (this.head == null) {
            this.head = new Node(new Object[]{element}, this.len_of_node_array);
            return true;
        }
        Node el = this.head;
        while (el.next != null)
            el = el.next;

        if (el.array_lenght + 1 > this.len_of_node_array) {
            el.next = el.separate();
            el.next.push(element);
            this.length++;
            return true;
        }

        el.push(element);
        return true;
    }

    public boolean insert(Object element, int index) {
        /* Function of insert element with some index */
        if (index < 0)
            return false;

        Node el = this.head;
        int start_index = 0;
        while (start_index + el.array_lenght <= index) {
            start_index += el.array_lenght;
            el = el.next;
            if (el == null)
                return false;
        }
        if (el == null)
            return false;

        if (el.array_lenght + 1 > this.len_of_node_array) {
            Node tmp = el.next;
            el.next = el.separate();
            if ((index - start_index) < el.array_lenght)
                el.insert(element, index - start_index);
            else
                el.next.insert(element, index - start_index - el.array_lenght);
            el.next.next = tmp;
            this.length++;
            return true;
        }

        el.insert(element, index - start_index);
        return true;
    }

    public boolean delete_number(int index) {
        /* Function of delete element with some index */
        int index_node = 0;
        if (index < 0)
            return false;
        Node el = this.head;
        int k = el.array_lenght;
        while (k <= index) {
            el = el.next;
            index_node++;
            if (el == null)
                return false;
            k += el.array_lenght;
        }
        int start_index = k - el.array_lenght;
        int index_node_next = index_node + 1;
        el.delete(index - start_index);
        if (el.array_lenght == 0) {
            this.delete_arr(index_node);
            return true;
        }
        if (el.next != null) {
            if (el.next.array_lenght + el.array_lenght <= this.len_of_node_array) {
                for (int i = 0; i < el.next.array_lenght; i++) {
                    el.push(el.next.array[i]);
                }
                this.delete_arr(index_node_next);
            }
        }
        return true;
    }

    public void delete_arr(int index) {
        /* Function of delete some empty node from the list */
        if (index == 0) {
            this.head = this.head.next;
            this.length--;
            return;
        }
        Node el = this.head;
        int k = 0;
        while (k < index - 1 && el.next != null) {
            el = el.next;
            k++;
        }

        el.next = el.next.next;
        this.length--;
    }

    public void print_elements() {
        /* Function of print list */
        Node current = this.head;
        while (current != null) {
            System.out.print(Arrays.toString(current.array));
            System.out.println(current.array_lenght);
            current = current.next;
        }
    }

    public int find_index_of_object(Object element) {
        /* Function of find number index in all list */
        int index = 0;
        Node el = this.head;
        while (el != null) {
            if (el.find(element) != -1) {
                return index + el.find(element);
            }
            index += el.array_lenght;
            el = el.next;
        }
        return -1;
    }
}
