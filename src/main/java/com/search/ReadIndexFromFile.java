package com.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class ReadIndexFromFile {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ReadIndexFromFile.class);

    public static void searchIndex(String searchTerm) throws IOException, ParseException {
        IndexSearcher searcher = createSearcher();
        TopDocs docs = searchInContent(searchTerm, searcher);

        Logger.info("Total Results ::{} ", docs.totalHits);

        for (ScoreDoc sd : docs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            Logger.info("Path : {}, Score: {}", d.get("path"), sd.score);
        }
    }

    private static TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws ParseException, IOException {
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);

        return searcher.search(query, 10);
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get("indexedFiles"));
        IndexReader reader = DirectoryReader.open(dir);
        return new IndexSearcher(reader);
    }
}