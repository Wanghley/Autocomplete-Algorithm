import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;


    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
        initialize(terms, weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        // TODO Auto-generated method stub
        myMap = new HashMap<>();
        myMap.put("", new ArrayList<Term>());
        mySize=0;
        for (int i = 0; i < weights.length; i++) {
            String termString = terms[i];
            double weight = weights[i];
            myMap.get("").add(new Term(termString, weight));
            for(int j=0;j<termString.length();j++){
                String prefix = termString.substring(0, j+1);
                myMap.putIfAbsent(prefix, new ArrayList<Term>());
                myMap.get(prefix).add(new Term(termString, weight));
                // Calculating the size of the map adding each term, prefix, and weight
                mySize+=(BYTES_PER_CHAR*prefix.length())+(BYTES_PER_CHAR*termString.length())+(BYTES_PER_DOUBLE);
            }
        }
        for (String key : myMap.keySet()) {
            Collections.sort(myMap.get(key),Comparator.comparing(Term::getWeight).reversed());       
        }
    }

    @Override
    public int sizeInBytes() {
        // TODO Auto-generated method stub
        return mySize;
    }
    
}

