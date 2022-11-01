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
        if(terms.length != weights.length) {
            throw new IllegalArgumentException("terms and weights are not the same length");
        }
        initialize(terms, weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if(prefix.length()>MAX_PREFIX){
            prefix = prefix.substring(0,MAX_PREFIX);
        }
        List<Term> list = new ArrayList<>();
        if (myMap.containsKey(prefix)){
            List<Term> all = myMap.get(prefix);
            list = all.subList(0, Math.min(k, all.size()));
        }
        

        return list;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
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
            }
        }
        for (String key : myMap.keySet()) {
            Collections.sort(myMap.get(key),Comparator.comparing(Term::getWeight).reversed());       
        }
    }

    // if(mySize==0) {
    //     for (String s : myMap.keySet()) {
    //         mySize = mySize + (BYTES_PER_CHAR * s.length());
    //         List<Term> termList = myMap.get(s);
    //         for (int i = 0; i < termList.size(); i++) {
    //             Term t = termList.get(i);
    //             mySize = mySize + BYTES_PER_DOUBLE + BYTES_PER_CHAR * t.getWord().length();
    //         }
    //     }
    // }


    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            for (String key : myMap.keySet()) {
                mySize += BYTES_PER_CHAR * key.length(); 
            }
            for (List<Term> list : myMap.values()) {
                for (Term term : list) {
                    mySize += (BYTES_PER_CHAR * term.getWord().length()) + BYTES_PER_DOUBLE;
                }
            }
        }

        return mySize;
    }
    
}

