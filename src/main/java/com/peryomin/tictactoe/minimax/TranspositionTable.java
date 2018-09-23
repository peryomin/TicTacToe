package main.java.com.peryomin.tictactoe.minimax;

public class TranspositionTable {
    private static int MB = 128;
    private static int ENTRY_SIZE_BIT = 256;
    private Entry[] entries;

    TranspositionTable() {
        int capacity = MB * 1024 * 1024 * 8 / ENTRY_SIZE_BIT;
        entries = new Entry[capacity];
    }

    public void put(long stateHash, int score, int depth) {
        entries[(int) (stateHash & (entries.length - 1))] = new Entry(score, depth, stateHash);
    }

    public Entry get(long stateHash, int depth) {
        Entry entry = entries[(int) (stateHash & (entries.length - 1))];
        if (entry == null) {
            return null;
        }
        if (entry.hash == stateHash && entry.depth >= depth) {
            return entry;
        }
        return null;
    }

    static class Entry {
        int score;
        int depth;
        long hash;

        Entry(int score, int depth, long hash) {
            this.score = score;
            this.depth = depth;
            this.hash = hash;
        }
    }
}
