package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.
    private IDictionary<URI, Double> normOfAllUris;

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
        this.normOfAllUris = getNormOfDocVector(this.documentTfIdfVectors);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: feel free to change or modify these methods however you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * Return a dictionary mapping every single unique word found
     * in every single document to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Double> result = new ChainedHashDictionary<String, Double>();
        int totalDocs = pages.size();
        for (Webpage page : pages) {
            ISet<String> seen = new ChainedHashSet<String>();
            IList<String> words = page.getWords();
            for (String word : words) {
                if (!seen.contains(word)) {
                    seen.add(word);
                    if (!result.containsKey(word)) {
                        double initialValue = Math.log(totalDocs);
                        result.put(word, initialValue);
                    } else {
                        double oldIdf = result.get(word);
                        double newIdf = getNewIdf(oldIdf, totalDocs);
                        result.put(word, newIdf);
                    }
                }
            }
        }
        return result;
    }
    
    private double getNewIdf(double num, int totalDocs) {
        double result = Math.exp(num);
        result = Math.pow(result / totalDocs, -1) + 1.0;
        result = Math.log(totalDocs / result);
        return result;
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> result = new ChainedHashDictionary<String, Double>();
        int totalWords = words.size();
        for (String word : words) {
            if (!result.containsKey(word)) {
                double newTfScore = 1.0 / totalWords;
                result.put(word, newTfScore);
            } else {
                double oldTfScore = result.get(word);
                double newTfScore = oldTfScore * totalWords;
                newTfScore = (newTfScore + 1) / totalWords;
                result.put(word, newTfScore);
            }
        }
        return result;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        IDictionary<URI, IDictionary<String, Double>> result = 
                new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        for (Webpage page : pages) {
            URI pageUri = page.getUri();
            IDictionary<String, Double> tfScores = computeTfScores(page.getWords());
            IDictionary<String, Double> pageResult = new ChainedHashDictionary<String, Double>();
            for (KVPair<String, Double> wordScore : tfScores) {
                double idfScore = this.idfScores.get(wordScore.getKey());
                double tfScore = wordScore.getValue();
                pageResult.put(wordScore.getKey(), idfScore * tfScore);
            }
            result.put(pageUri, pageResult);
        }
        return result;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // Note: The pseudocode we gave you is not very efficient. When implementing,
        // this method, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        IDictionary<String, Double> documentVector = this.documentTfIdfVectors.get(pageUri);
        IDictionary<String, Double> queryVector = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> queryTfScores = computeTfScores(query);
        
        double numerator = 0.0;
        for (KVPair<String, Double> wordScore : queryTfScores) {
            double idfScore = this.idfScores.get(wordScore.getKey());
            double tfScore = wordScore.getValue();
            double queryWordScore = idfScore * tfScore;
            queryVector.put(wordScore.getKey(), queryWordScore);
            
            double docWordScore = 0.0;
            if (documentVector.containsKey(wordScore.getKey())) {
                docWordScore = documentVector.get(wordScore.getKey());
            }
            numerator += docWordScore * queryWordScore;
        }
        
        double denominator = this.normOfAllUris.get(pageUri) * norm(queryVector);
        
        if (denominator != 0.0) {
            return numerator / denominator;
        } 
        return 0.0;
    }
    
    private Double norm(IDictionary<String, Double> vector) {
        double output = 0.0;
        for (KVPair<String, Double> pair : vector) {
            double score = pair.getValue();
            output += score * score;
        }
        
        return Math.sqrt(output);
    }
    
    private IDictionary<URI, Double> getNormOfDocVector(IDictionary<URI, IDictionary<String, Double>> docVector) {
        IDictionary<URI, Double> result = new ChainedHashDictionary<URI, Double>();
        for (KVPair<URI, IDictionary<String, Double>> uriPair : docVector) {
            double score = norm(docVector.get(uriPair.getKey()));
            result.put(uriPair.getKey(), score);
        }
        return result;
    }
}
