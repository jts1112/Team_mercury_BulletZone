package edu.unh.cs.cs619.bulletzone.util;

/**
 * Created by simon on 10/1/14.
 */
public class LongWrapper {
    private long result;
    private long result2;
    private long result3;

    public LongWrapper(long result) {
        this.result = result;
    }

    // Constructor with three long variables
    public LongWrapper(long result, long id1, long id2) {
        this.result = result;
        this.result2 = id1;
        this.result3 = id2;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public long getResult2() {
        return result2;
    }

    public void setResult2(long result2) {
        this.result2 = result2;
    }

    public long getResult3() {
        return result3;
    }

    public void setResult3(long result3) {
        this.result3 = result3;
    }
}
