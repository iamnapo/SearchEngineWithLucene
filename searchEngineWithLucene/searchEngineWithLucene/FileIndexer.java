//Napoleon Oikonomou

package searchEngineWithLucene;

import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

public class FileIndexer {

	private int index(File indexDir, File dataDir, String suffix) throws Exception {
		SimpleAnalyzer baseAnalyzer = new SimpleAnalyzer();
		StemmerAnalyzer analyzer = new StemmerAnalyzer(baseAnalyzer);
		IndexWriterConfig iWC = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir.toPath()), iWC);
		indexWriter.getConfig().setUseCompoundFile(false);
		indexDirectory(indexWriter, dataDir, suffix);
		int numIndexed = indexWriter.numRamDocs();
		indexWriter.close();
		return numIndexed;
	}

	private void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws Exception {
		File[] files = dataDir.listFiles();
		assert files != null;
		for (File arxeio : files) {
			if (arxeio.isDirectory()) {
				indexDirectory(indexWriter, arxeio, suffix);
			} else {
				indexFileWithIndexWriter(indexWriter, arxeio, suffix);
			}
		}
	}

	private void indexFileWithIndexWriter(IndexWriter indexWriter, File arxeio, String suffix) throws Exception {
		if (arxeio.isHidden() || arxeio.isDirectory() || !arxeio.canRead() || !arxeio.exists()) {
			return;
		}
		if (suffix != null && !arxeio.getName().endsWith(suffix)) {
			return;
		}
		Document doc = new Document();
		doc.add(new StringField("filename", arxeio.getCanonicalPath(), Field.Store.YES));
		doc.add(new TextField("contents", new FileReader(arxeio)));
		String hashResult = HashWithCheckSum.getCheckSum(arxeio.getCanonicalPath());
		doc.add(new StringField("hash", hashResult, Field.Store.YES));
		Term term = new Term("hash", hashResult);
		Term term1 = new Term("filename", arxeio.getCanonicalPath());
		indexWriter.deleteDocuments(term);
		indexWriter.deleteDocuments(term1);
		indexWriter.addDocument(doc);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Indexing...");
		GUIForIndexingAndSearching.consoleMessages.add("Indexing...");
		File indexDir = new File(args[0] + "/IndexDir");
		File dataDir = new File(args[0]);
		String suffix = "txt";
		FileIndexer indexer = new FileIndexer();
		int numIndex = indexer.index(indexDir, dataDir, suffix);
		System.out.println("Done!!");
		GUIForIndexingAndSearching.consoleMessages.add("Done!!");
		System.out.println("Indexed " + numIndex + " files.");
		GUIForIndexingAndSearching.consoleMessages.add("Indexed " + numIndex + " files.");

	}
}
