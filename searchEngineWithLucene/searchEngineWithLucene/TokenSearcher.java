package searchEngineWithLucene;//Napoleon Oikonomou

import java.io.File;
import java.util.Scanner;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TokenSearcher {
    public static void main(String[] args) throws Exception {
            File indexDir = new File("/Users/Napoleon/Desktop/IndexFolder");
            System.out.println("Give word to search for: ");
            Scanner user_input = new Scanner(System.in);
            String query;
            query = user_input.next();
            user_input.close();
            int hits = 100;
            TokenSearcher searcher = new TokenSearcher();
            searcher.searchIndex(indexDir, query, hits);
    }


    public int searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {
        Directory index = FSDirectory.open(indexDir.toPath());
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("contents", new StemmerAnalyzer(new SimpleAnalyzer()));
        Query query = parser.parse(queryStr);
        TopDocs topDocs = searcher.search(query, maxHits);
        ScoreDoc[] hits = topDocs.scoreDocs;
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            Document d = searcher.doc(docId);
            GUIForIndexingAndSearching.consoleMessages.add(d.get("filename"));
            System.out.println(d.get("filename"));
        }
        System.out.println("Found " + hits.length);
        GUIForIndexingAndSearching.consoleMessages.add("Found " + hits.length);

        return hits.length;
    }
}
