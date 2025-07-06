package main.java.arrayandarraylist;
import java.util.Arrays;

public class ArrayOperations {

    public void traverse(int[] array) {
        System.out.println("Array Traversal: " + Arrays.toString(array));
    }

    public int linearSearch(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        return -1;
    }

    public int binarySearch(int[] array, int value) {
        Arrays.sort(array); // pastikan array terurut
        return Arrays.binarySearch(array, value);
    }

    public int[] insert(int[] array, int value, int index) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = value;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    public int[] delete(int[] array, int index) {
        int[] newArray = new int[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }
}
