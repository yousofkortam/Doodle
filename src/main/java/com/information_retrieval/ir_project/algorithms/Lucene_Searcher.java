package com.information_retrieval.ir_project.algorithms;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
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
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Lucene_Searcher {

    public static ArrayList<Integer> searcher(String str_query) throws IOException, ParseException {
        Directory indexDirct = FSDirectory.open(new File("indexDir"));
        IndexReader reader = DirectoryReader.open(indexDirct);
        IndexSearcher is = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(Version.LUCENE_42, "content", new SimpleAnalyzer(Version.LUCENE_42));

        Query query = parser.parse(str_query);

        TopDocs hits = is.search(query, 100);

        System.out.println("Found " + hits.totalHits +" document(s) that matched query '" + str_query + "':");
        ArrayList<Integer> indexIds = new ArrayList<>();
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            indexIds.add(Integer.valueOf(doc.get("id")));
            System.out.println(doc.get("id")+" By Author : "+doc.get("author"));
        }

        reader.close();
        return  indexIds;
    }
   /* public static void main(String[] args) throws IOException, ParseException {
        searcher("Give");
    }*/
}
