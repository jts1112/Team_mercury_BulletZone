package edu.unh.cs.cs619.bulletzone.util;
public class LongWrapper {
    private long result;
    private long id1;
    private long id2;

    public LongWrapper() { }

    public LongWrapper(long result) {
        this.result = result;
    }

    // Constructor with three long variables
    public LongWrapper(long result, long id1, long id2) {
        this.result = result;
        this.id1 = id1;
        this.id2 = id2;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public long getId1() {
        return id1;
    }

    public void setId1(long id1) {
        this.id1 = id1;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }
}
