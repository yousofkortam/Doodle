package com.information_retrieval.ir_project.service.indexing;

import com.information_retrieval.ir_project.Directory;
import com.information_retrieval.ir_project.algorithms.IndexesFactory;
import com.information_retrieval.ir_project.algorithms.Lucene_Indexer;
import com.information_retrieval.ir_project.preprocessing.Preprocessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import util.ReadData;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IndexingServiceImpl implements IndexingService{
    @Override
    public void index(String indexType, boolean normalization, boolean steaming, boolean lemetization, boolean stopWords, boolean tokenization) {
        log.info("indexType {}, normalization {}, steaming {}, lemetization {}, stopWords {}, tokenization {}",
                indexType, normalization,steaming, lemetization, stopWords, tokenization);

        try{
            String []data= ReadData.readDocuments(Directory.ARCHIVE_PATH + "/CISI.ALL");
//            for(int i=0;i<data.length;i++){
//                System.out.println("============ (" +(i+1) +")===========");
//                data[i] = data[i].toLowerCase();
//            }
            ArrayList<List<String>> cleanedData=new ArrayList<>();
            int z1 =0;
            for(var d:data){
                if(z1==100)
                    break;
                cleanedData.add(Preprocessing.preprocess(d,stopWords,
                        normalization,
                       steaming ,
                        lemetization));
                z1++;
            }

            if(indexType.equalsIgnoreCase("Bi-word-index")) {
                IndexesFactory.setByWordIndex(data);
                System.out.println( "i am finishing ya man" );
            }
            else if((indexType.equalsIgnoreCase("Incidence-matrix"))) {
                var matrix =  IndexesFactory.setIncidenceMatrix(cleanedData);
                matrix.forEach((x, list) -> {
                    System.out.print(x + "  ");
                    list.forEach(z -> System.out.print(z + " "));
                    System.out.println();
                });
            }else if((indexType.equalsIgnoreCase("Lucene"))){
                Lucene_Indexer.Indexer();
            }else if((indexType.equalsIgnoreCase("Inverted-matrix"))){
                var matrix= IndexesFactory.setInvertedIndex(cleanedData);
                matrix.forEach((x,list)->{
                    System.out.print(x + "  ");
                    list.forEach(z -> System.out.print(z + " "));
                    System.out.println();
                });
            }else if((indexType.equalsIgnoreCase("Positional-index"))){
                var matrix= IndexesFactory.setPositionalIndex(cleanedData);
                System.out.println(matrix);
            }
        }catch (Exception ex){
            System.out.println(ex);
        }

    }
}
