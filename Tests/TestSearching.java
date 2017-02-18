//Napoleon Oikonomou

import com.sun.tools.javac.util.Assert;
import org.junit.Test;
import searchEngineWithLucene.TokenSearcher;

import java.io.File;

public class TestSearching {

    @Test
    public void test1() {
        File indexDir = new File("/Users/Napoleon/Desktop/IndexFolder");
        TokenSearcher tokenSearcher = new TokenSearcher();
        int found = 0;
        try {
            found = tokenSearcher.searchIndex(indexDir,"name",100);
        }catch ( Exception ignored) {}
        int expected = 3;
        Assert.check(found == expected,"Found " + found + " while expected " + expected);
    }

    @Test
     public void test2() {
        File indexDir = new File("/Users/Napoleon/Desktop/IndexFolder");
        TokenSearcher tokenSearcher = new TokenSearcher();
        int found = 0;
        try {
            found = tokenSearcher.searchIndex(indexDir,"names",100);
        }catch ( Exception ignored) {}
        int expected = 3;
        Assert.check(found == expected,"Found " + found + " while expected " + expected);
    }

    @Test
    public void test3() {
        File indexDir = new File("/Users/Napoleon/Desktop/IndexFolder");
        TokenSearcher tokenSearcher = new TokenSearcher();
        int found = 0;
        try {
            found = tokenSearcher.searchIndex(indexDir,"napoleon",100);
        }catch ( Exception ignored) {}
        int expected = 1;
        Assert.check(found == expected,"Found " + found + " while expected " + expected);
    }

    @Test
    public void test4() {
        File indexDir = new File("/Users/Napoleon/Desktop/IndexFolder");
        TokenSearcher tokenSearcher = new TokenSearcher();
        int found = 0;
        try {
            found = tokenSearcher.searchIndex(indexDir,"nonExist",100);
        }catch ( Exception ignored) {}
        int expected = 0;
        Assert.check(found == expected,"Found " + found + " while expected " + expected);
    }
}
