package org.docksidestage.bizfw.debug.searcher;

import java.util.Iterator;
import java.util.List;

import org.docksidestage.bizfw.debug.Word;
import org.docksidestage.bizfw.debug.WordPool;

/**
 * @author zaya
 */
public class IteratorSearcher implements Searcher {
    public List<Word> words;

    public IteratorSearcher() {
        words = new WordPool().getWords();
    }

    @Override
    public Word search(String searchingFor) {
        Iterator<Word> iterator = words.iterator();
        while (iterator.hasNext()) {
            Word target = iterator.next();
            if (target.getWord().equals(searchingFor)) {
                System.out.println("Found! " + searchingFor + " | " + target.getWord());
                return target;
            }
        }
        throw new IllegalArgumentException("the word you are looking for is not here, word:" + searchingFor);
    }
}
