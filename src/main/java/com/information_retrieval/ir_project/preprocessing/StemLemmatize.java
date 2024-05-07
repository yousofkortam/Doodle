
package com.information_retrieval.ir_project.preprocessing;




import com.information_retrieval.ir_project.Directory;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class StemLemmatize {

    public static ArrayList<String> lemmatizeTokens(ArrayList<String> inputTokens) throws Exception {

        String[] tokens=new String[inputTokens.size()];
        int count=0;
        for (String inputToken : inputTokens) {
            tokens[count++]=inputToken;
        }

        InputStream inputStreamPOSTagger = new FileInputStream(Directory.ARCHIVE_PATH + "/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        InputStream dictLemmatizer = new FileInputStream(Directory.ARCHIVE_PATH + "/en-lemmatizer.dict");
        DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(
                dictLemmatizer);
        String[] lemmas = lemmatizer.lemmatize(tokens, tags);
        for(int i=0;i<lemmas.length;i++){
            if(lemmas[i].equals("O")){
                lemmas[i] = inputTokens.get(i);
            }
        }
        return new ArrayList<>(Arrays.asList(lemmas));
    }

}
