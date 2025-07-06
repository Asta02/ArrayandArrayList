package main.java.arrayandarraylist;
import java.util.ArrayList;
import java.util.Collections;

public class ArrayListOperations {

    public void traverse(ArrayList<Integer> list) {
        System.out.println("ArrayList Traversal: " + list);
    }

    public int search(ArrayList<Integer> list, int value) {
        return list.indexOf(value);
    }

    public void insert(ArrayList<Integer> list, int value, int index) {
        list.add(index, value);
    }

    public void delete(ArrayList<Integer> list, int index) {
        list.remove(index);
    }

    public void sort(ArrayList<Integer> list) {
        Collections.sort(list);
    }
}
