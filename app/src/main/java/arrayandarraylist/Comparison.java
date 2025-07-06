package main.java.arrayandarraylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Comparison {
    public static void main(String[] args) {
        // Inisialisasi data kecil untuk demonstrasi
        int[] smallArray = {10, 20, 30, 40, 50};
        ArrayList<Integer> smallList = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

        // 1. Traversal
        System.out.println("Array Traversal: " + Arrays.toString(smallArray));
        System.out.println("ArrayList Traversal: " + smallList);
        System.out.println();

        // 2. Pencarian nilai 30
        int target = 30;
        int idxArr = linearSearch(smallArray, target); // menggunakan pencarian linear manual
        int idxList = smallList.indexOf(target);       // menggunakan metode indexOf dari ArrayList
        System.out.printf("Pencarian %d dalam Array: %s di indeks %d%n",
                target, (idxArr >= 0 ? "Ditemukan" : "Tidak ditemukan"), idxArr);
        System.out.printf("Pencarian %d dalam ArrayList: %s di indeks %d%n",
                target, (idxList >= 0 ? "Ditemukan" : "Tidak ditemukan"), idxList);
        System.out.println();

        // 3. Penyisipan nilai 25 pada indeks ke-2
        smallArray = insert(smallArray, 25, 2);
        smallList.add(2, 25);
        System.out.println("Array setelah penyisipan elemen 25: " + Arrays.toString(smallArray));
        System.out.println("ArrayList setelah penyisipan elemen 25: " + smallList);
        System.out.println();

        // 4. Penghapusan elemen pada indeks ke-2
        smallArray = delete(smallArray, 2);
        smallList.remove(2);
        System.out.println("Array setelah penghapusan indeks 2: " + Arrays.toString(smallArray));
        System.out.println("ArrayList setelah penghapusan indeks 2: " + smallList);
        System.out.println();

        // Uji performa dengan dataset besar
        int N = 1000;
        int[] bigArray = new int[N];
        ArrayList<Integer> bigList = new ArrayList<>(N);
        Random rnd = new Random(42);

        for (int i = 0; i < N; i++) {
            int val = rnd.nextInt(10_000);
            bigArray[i] = val;
            bigList.add(val);
        }

        int sampleVal = bigArray[N / 2];
        long[] timesArr = new long[4];
        long[] timesList = new long[4];

        // 1. Waktu pencarian
        long t0 = System.nanoTime();
        linearSearch(bigArray, sampleVal);
        timesArr[0] = System.nanoTime() - t0;

        t0 = System.nanoTime();
        bigList.indexOf(sampleVal);
        timesList[0] = System.nanoTime() - t0;

        // 2. Waktu penyisipan di tengah
        t0 = System.nanoTime();
        bigArray = insert(bigArray, -1, N / 2);
        timesArr[1] = System.nanoTime() - t0;

        t0 = System.nanoTime();
        bigList.add(N / 2, -1);
        timesList[1] = System.nanoTime() - t0;

        // 3. Waktu penghapusan di tengah
        t0 = System.nanoTime();
        bigArray = delete(bigArray, N / 2);
        timesArr[2] = System.nanoTime() - t0;

        t0 = System.nanoTime();
        bigList.remove(N / 2);
        timesList[2] = System.nanoTime() - t0;

        // 4. Waktu pengurutan (hanya ArrayList)
        timesArr[3] = 0;
        t0 = System.nanoTime();
        Collections.sort(bigList);
        timesList[3] = System.nanoTime() - t0;

        // Tampilkan hasil pengukuran performa
        printTable(timesArr, timesList);
    }

    /**
     * Melakukan pencarian linear terhadap elemen dalam array.
     * @param arr array yang akan dicari
     * @param val nilai yang dicari
     * @return indeks jika ditemukan, -1 jika tidak
     */
    private static int linearSearch(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val) return i;
        }
        return -1;
    }

    /**
     * Menyisipkan elemen pada posisi tertentu dalam array.
     * @param arr array asal
     * @param val nilai yang akan disisipkan
     * @param idx posisi indeks penyisipan
     * @return array baru dengan elemen yang disisipkan
     */
    private static int[] insert(int[] arr, int val, int idx) {
        int[] out = new int[arr.length + 1];
        System.arraycopy(arr, 0, out, 0, idx);
        out[idx] = val;
        System.arraycopy(arr, idx, out, idx + 1, arr.length - idx);
        return out;
    }

    /**
     * Menghapus elemen pada posisi tertentu dalam array.
     * @param arr array asal
     * @param idx indeks elemen yang akan dihapus
     * @return array baru dengan elemen yang telah dihapus
     */
    private static int[] delete(int[] arr, int idx) {
        int[] out = new int[arr.length - 1];
        System.arraycopy(arr, 0, out, 0, idx);
        System.arraycopy(arr, idx + 1, out, idx, arr.length - idx - 1);
        return out;
    }

    /**
     * Mencetak tabel waktu eksekusi dan bar chart sederhana.
     * @param arrTimes waktu eksekusi operasi pada array
     * @param listTimes waktu eksekusi operasi pada ArrayList
     */
    private static void printTable(long[] arrTimes, long[] listTimes) {
        String[] ops = {"Search", "Insert", "Delete", "Sort"};
        System.out.println("┌───────────┬──────────────────┬───────────────────┐");
        System.out.println("│ Operation │ Array Time (ns)  │ ArrayList Time(ns)│");
        System.out.println("├───────────┼──────────────────┼───────────────────┤");
        for (int i = 0; i < ops.length; i++) {
            System.out.printf("│ %-9s │ %-16d │ %-17d │%n", ops[i], arrTimes[i], listTimes[i]);
        }
        System.out.println("└───────────┴──────────────────┴───────────────────┘");

        System.out.println("\nBar Chart (each █ ≈100k ns):");
        for (int i = 0; i < ops.length; i++) {
            System.out.printf("%-9s Arr: ", ops[i]);
            printBar(arrTimes[i]);
            System.out.printf("%-9s Lis: ", ops[i]);
            printBar(listTimes[i]);
        }
    }

    /**
     * Mencetak bar chart berbasis karakter blok sesuai waktu.
     * @param t waktu dalam nanodetik
     */
    private static void printBar(long t) {
        int blocks = (int)(t / 100_000);
        for (int i = 0; i < blocks; i++) System.out.print("█");
        System.out.printf(" (%d)%n", t);
    }
}