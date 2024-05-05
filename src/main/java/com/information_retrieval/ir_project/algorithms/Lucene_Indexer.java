package com.information_retrieval.ir_project.algorithms;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lucene_Indexer {

public static void Indexer() throws IOException{
    String cisiPath = com.information_retrieval.ir_project.Directory.ARCHIVE_PATH + "CISI.ALL";
    String indexDir = "indexDir";

    Directory indexDirct = FSDirectory.open(new File(indexDir));
    IndexWriterConfig analyzerConfig = new IndexWriterConfig(Version.LUCENE_42, new SimpleAnalyzer(Version.LUCENE_42));
    analyzerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    IndexWriter writer = new IndexWriter(indexDirct, analyzerConfig);

    File cisiFile = new File(cisiPath);
    BufferedReader reader = new BufferedReader(new FileReader(cisiFile));
    String line;
    Document doc = null;
    while ((line = reader.readLine()) != null) {
        System.out.println();
        if (line.startsWith(".I")) {
            if (doc != null) {
                writer.addDocument(doc);
            }
            doc = new Document();
            String id = line.substring(3).trim();
            doc.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
        } else if (line.startsWith(".T")) {
            String title = reader.readLine().trim();
            doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
        } else if (line.startsWith(".W")) {
            String content = reader.readLine().trim();
            doc.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));
        }else if (line.startsWith(".A")) {
            String author = reader.readLine().trim();
            doc.add(new Field("author", author, Field.Store.YES, Field.Index.ANALYZED));
        }
    }

    writer.addDocument(doc);
    System.out.println("# of Docs indexed = " + writer.numDocs());
    System.out.println("Lucene Index Built Successfully.");
    writer.close();
    reader.close();
}
}