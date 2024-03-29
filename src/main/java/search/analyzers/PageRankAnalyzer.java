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
 * This class is responsible for computing the 'page rank' of all available webpages.
 * If a webpage has many different links to it, it should have a higher page rank.
 * See the spec for more details.
 */
public class PageRankAnalyzer {
    private IDictionary<URI, Double> pageRanks;

    /**
     * Computes a graph representing the internet and computes the page rank of all
     * available webpages.
     *
     * @param webpages  A set of all webpages we have parsed.
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    public PageRankAnalyzer(ISet<Webpage> webpages, double decay, double epsilon, int limit) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        // Step 1: Make a graph representing the 'internet'
        IDictionary<URI, ISet<URI>> graph = this.makeGraph(webpages);

        // Step 2: Use this graph to compute the page rank for each webpage
        this.pageRanks = this.makePageRanks(graph, decay, limit, epsilon);

        // Note: we don't store the graph as a field: once we've computed the
        // page ranks, we no longer need it!
    }

    /**
     * This method converts a set of webpages into an unweighted, directed graph,
     * in adjacency list form.
     *
     * You may assume that each webpage can be uniquely identified by its URI.
     *
     * Note that a webpage may contain links to other webpages that are *not*
     * included within set of webpages you were given. You should omit these
     * links from your graph: we want the final graph we build to be
     * entirely "self-contained".
     */
    private IDictionary<URI, ISet<URI>> makeGraph(ISet<Webpage> webpages) {
        IDictionary<URI, ISet<URI>> result = new ChainedHashDictionary<URI, ISet<URI>>();
        ISet<URI> possiblePages = new ChainedHashSet<URI>();
        
        for (Webpage page : webpages) {
            possiblePages.add(page.getUri());
        }
        
        for (Webpage page : webpages) {
            ISet<URI> links = new ChainedHashSet<URI>();
            IList<URI> inPageLinks = page.getLinks();
            for (URI uri : inPageLinks) {
                if (uri != page.getUri() && possiblePages.contains(uri)) {
                    links.add(uri);
                }
            }
            result.put(page.getUri(), links);
        }
        
        return result;
    }

    /**
     * Computes the page ranks for all webpages in the graph.
     *
     * Precondition: assumes 'this.graphs' has previously been initialized.
     *
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    private IDictionary<URI, Double> makePageRanks(IDictionary<URI, ISet<URI>> graph,
                                                   double decay,
                                                   int limit,
                                                   double epsilon) {
        // Step 1: The initialize step should go here
        double totalPages = graph.size();
        IDictionary<URI, Double> oldScores = new ChainedHashDictionary<URI, Double>();
        IDictionary<URI, Double> newScores = new ChainedHashDictionary<URI, Double>();
        
        for (KVPair<URI, ISet<URI>> pair : graph) {
            URI uri = pair.getKey();
            oldScores.put(uri, 1.0 / totalPages);
        }
        
        for (int i = 0; i < limit; i++) {
            // Step 2: The update step should go here
            boolean converged = true;
            for (KVPair<URI, ISet<URI>> pair : graph) {
                URI uri = pair.getKey();
                double sum = 0.0;
                for (KVPair<URI, ISet<URI>> insidePair : graph) {
                    URI insideUri = insidePair.getKey();
                    if (graph.get(insideUri).isEmpty()) {
                        sum += decay * oldScores.get(insideUri) / totalPages;
                    } 
                    if (graph.get(insideUri).contains(uri)) {
                        sum += decay * oldScores.get(insideUri) / graph.get(insideUri).size();
                    }
                }
                sum += (1.0 - decay) / totalPages;
                newScores.put(uri, sum);
                
                double diff = Math.abs(oldScores.get(uri) - newScores.get(uri));
                if (diff >= epsilon) {
                    converged = false;
                }
            }
            if (converged) {
                return newScores;
            }
            oldScores = newScores;
            newScores = new ChainedHashDictionary<URI, Double>();
        }
        return newScores;
    }

    /**
     * Returns the page rank of the given URI.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public double computePageRank(URI pageUri) {
        // Implementation note: this method should be very simple: just one line!
        return this.pageRanks.get(pageUri);
    }
}
