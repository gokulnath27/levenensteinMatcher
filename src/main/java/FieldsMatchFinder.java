import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FieldsMatchFinder {

    public static String escapeString(String escapeCharacterSequence){
        StringBuffer stringBuffer = new StringBuffer(escapeCharacterSequence.length());
        CharacterIterator characterIterator = new StringCharacterIterator(escapeCharacterSequence);
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
                case '_':
                    stringBuffer.append(" ");
                    break;
                default:
                    stringBuffer.append(eachCharacter);
                    break;
            }
        }
        escapeCharacterSequence = stringBuffer.toString();
        return escapeCharacterSequence;
    }


    public List<String> findMatchedFields(List<String> fieldsToBeMatched, List<String> allFields){
        fieldsToBeMatched.sort(Comparator.comparing(String::length));
        int flag = 0;
        String fieldLocalName;
        String fieldsToBeMatchedName;
        List<String> matchedFields = new ArrayList<String>();
        List<String> fieldsToBeMatchedTemporary = new ArrayList<String>();
        List<String> allFieldsTemporary = new ArrayList<String>();
        fieldsToBeMatched.sort(Comparator.comparing(String::length));
        Iterator<String> fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();
        Iterator<String> allFieldsIterator = allFields.iterator();



        //escaping special characters from the list
        while(fieldsToBeMatchedIterator.hasNext()){
            fieldsToBeMatchedTemporary.add(escapeString(fieldsToBeMatchedIterator.next()));
        }
        fieldsToBeMatched = fieldsToBeMatchedTemporary;

        while(allFieldsIterator.hasNext()){
            allFieldsTemporary.add(escapeString(allFieldsIterator.next()));
        }
        allFields = allFieldsTemporary;
        allFieldsIterator = allFields.iterator();
        while(allFieldsIterator.hasNext()) {
            fieldLocalName = allFieldsIterator.next();
            flag = 0;

            //Case1: exact match
            if(flag == 0) {
                while (fieldsToBeMatchedIterator.hasNext()) {
                    fieldsToBeMatchedName = fieldsToBeMatchedIterator.next();
                    if (fieldLocalName.toLowerCase().equals(fieldsToBeMatchedName.toLowerCase()) && (flag == 0)) {
                        matchedFields.add(fieldsToBeMatchedName);
                        allFieldsIterator.remove();
                        flag = 1;
                    }
                }

            }
            fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();

            //Case2 substring of field matches with column
            if(flag == 0){
                while(fieldsToBeMatchedIterator.hasNext()){
                    fieldsToBeMatchedName = fieldsToBeMatchedIterator.next();
                    if(fieldsToBeMatchedName.toLowerCase().contains(fieldLocalName.toLowerCase()) && (flag == 0)){
                        matchedFields.add(fieldsToBeMatchedName);
                        allFieldsIterator.remove();
                        flag = 1;
                    }
                }
            }
            fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();

            //Case3 substring of field matches with substring of column(where substring is seperated by space and not all substrings are taken) and the field name must be less than column name
            if(flag == 0 ){
                String[] split = fieldLocalName.split(" ");
                while (fieldsToBeMatchedIterator.hasNext()) {
                    fieldsToBeMatchedName = fieldsToBeMatchedIterator.next();
                    if (fieldsToBeMatchedName.toLowerCase().contains(split[0]) && (flag == 0) && fieldLocalName.length() <= fieldsToBeMatchedName.length()) {
                        matchedFields.add(fieldsToBeMatchedName);
                        allFieldsIterator.remove();
                        flag = 1;
                    }
                }
            }
            fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();

            //Case4 substring of field matches with substring of column(where substring is seperated by space and not all substrings are taken)
            if(flag == 0 ){
                String[] split = fieldLocalName.split(" ");
                while (fieldsToBeMatchedIterator.hasNext()) {
                    fieldsToBeMatchedName = fieldsToBeMatchedIterator.next();
                    if (fieldsToBeMatchedName.toLowerCase().contains(split[0]) && (flag == 0)) {
                        matchedFields.add(fieldsToBeMatchedName);
                        allFieldsIterator.remove();
                        flag = 1;
                    }
                }
            }
            fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();

            //Case5 field matches with substring of column
            if (flag == 0) {
                while (fieldsToBeMatchedIterator.hasNext()) {
                    fieldsToBeMatchedName = fieldsToBeMatchedIterator.next();
                    if (fieldLocalName.toLowerCase().contains(fieldsToBeMatchedName.toLowerCase()) && (flag == 0)) {
                        matchedFields.add(fieldsToBeMatchedName);
                        allFieldsIterator.remove();
                        flag = 1;
                    }
                }
            }

            // if no cases get matched execute Levenenstein Distance
            if(flag == 0){
                FieldMatchLevenenstein fieldMatchLevenenstein = new FieldMatchLevenenstein();
                String fieldMatched = fieldMatchLevenenstein.FieldMatchFinder(fieldsToBeMatched, fieldLocalName);
                matchedFields.add(fieldMatched);
            }
        }
        return matchedFields;
    }
}
