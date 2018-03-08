import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import org.apache.commons.lang3.StringUtils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class LevensteinDistanceFinder {

    public static int getRatio(String fieldsToBeMatched, String allFields, boolean debug) {

        if (fieldsToBeMatched.length() >= allFields.length()) {
            String temp = allFields;
            allFields = fieldsToBeMatched;
            fieldsToBeMatched = temp;
        }
        fieldsToBeMatched = escapeString(fieldsToBeMatched);
        allFields = escapeString(allFields);

        fieldsToBeMatched = fieldsToBeMatched.toLowerCase();
        allFields = allFields.toLowerCase();


        Set<String> temporarySetOne = new HashSet<String>();
        Set<String> temporoarySetTwo = new HashSet<String>();


        StringTokenizer stringTokenizer = new StringTokenizer(fieldsToBeMatched);
        while (stringTokenizer.hasMoreTokens()) {
            temporarySetOne.add(stringTokenizer.nextToken());
        }

        StringTokenizer stringTokenizer1 = new StringTokenizer(allFields);
        while (stringTokenizer1.hasMoreTokens()) {
            temporoarySetTwo.add(stringTokenizer1.nextToken());
        }
        SetView<String> intersection = Sets.intersection(temporarySetOne, temporoarySetTwo);
        TreeSet<String> sortedIntersection = Sets.newTreeSet(intersection);
        SetView<String> restOfSetOne = Sets.symmetricDifference(temporarySetOne, intersection);
        TreeSet<String> sortedRestOfSetOne = Sets.newTreeSet(restOfSetOne);
        SetView<String> restOfSetTwo = Sets.symmetricDifference(temporoarySetTwo, intersection);
        TreeSet<String> sortedRestOfSetTwo = Sets.newTreeSet(restOfSetTwo);
        String temporaryStringOne = "";
        String temporaryStringTwo = "";
        String temporaryStringThree = "";
        for (String eachWord : sortedIntersection) {
            temporaryStringOne = temporaryStringOne + " " + eachWord;
        }
        temporaryStringOne = temporaryStringOne.trim();

        Set<String> stringSetViewOne = Sets.union(sortedIntersection, sortedRestOfSetOne);
        for (String eachStringSetView : stringSetViewOne) {
            temporaryStringTwo = temporaryStringTwo + " " + eachStringSetView;
        }
        temporaryStringTwo = temporaryStringTwo.trim();

        Set<String> stringSetViewTwo = Sets.union(intersection, sortedRestOfSetTwo);
        for (String eachStringSetView : stringSetViewTwo) {
            temporaryStringThree = temporaryStringThree + " " + eachStringSetView;
        }
        temporaryStringThree = temporaryStringThree.trim();
        int amountOne = calculateLevensteinDistance(temporaryStringOne, temporaryStringTwo);
        int amountTwo = calculateLevensteinDistance(temporaryStringOne, temporaryStringThree);
        int amountThree = calculateLevensteinDistance(temporaryStringTwo, temporaryStringThree);
        int finalRatio = Math.max(Math.max(amountOne, amountTwo), amountThree);
        return finalRatio;
    }

    public static int calculateLevensteinDistance(String fieldToBeMatched, String allFields) {
        int distance = StringUtils.getLevenshteinDistance(fieldToBeMatched, allFields);
        double ratio = ((double) distance) / (Math.max(fieldToBeMatched.length(), allFields.length()));
        return 100 - new Double(ratio * 100).intValue();
    }

    public static String escapeString(String token) {

        StringBuffer stringBuffer = new StringBuffer(token.length());
        CharacterIterator characterIterator = new StringCharacterIterator(token);
        for (char eachCharacter = characterIterator.first(); eachCharacter != CharacterIterator.DONE; eachCharacter = characterIterator.next()) {
            switch (eachCharacter) {
                // '-,)(!`\":/][?;~><
                case '\'':
                case '/':
                case '\\':
                case '-':
                case ',':
                case ')':
                case '(':
                case '!':
                case '`':
                case '\"':
                case ':':
                case ']':
                case '[':
                case '?':
                case ';':
                case '~':
                case '<':
                case '>':
                    stringBuffer.append(" ");
                    break;
                default:
                    stringBuffer.append(eachCharacter);
                    break;
            }
        }
        token = stringBuffer.toString();
        return token;
    }

    public int getValue(String a, String b) {
        return getRatio(a, b, false);
    }
}