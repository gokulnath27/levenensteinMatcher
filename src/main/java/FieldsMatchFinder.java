import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FieldsMatchFinder {
    public List<String> findMatchedFields(List<String> fieldsToBeMatched, List<String> fields){
        fieldsToBeMatched.sort(Comparator.comparing(String::length));
        int flag = 0;
        String fieldLocalName;
        String fieldsToBeMatchedName;
        List<String> matchedFields = new ArrayList<String>();
        Iterator<String> fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();
        Iterator<String> allFieldsIterator = fields.iterator();

        //Case1: exact match

        while(allFieldsIterator.hasNext()) {
            fieldLocalName = allFieldsIterator.next();
            flag = 0;
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


            //Case2 field matches with substring of column


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
            fieldsToBeMatchedIterator = fieldsToBeMatched.iterator();



            //Case3 substring of field matches with column

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





            //Case4 substring of field matches with substring of column(where substring is seperated by space and not all substrings are taken)
            if(flag == 0){
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

            // if no cases get matched
            if(flag == 0){
                FieldMatchLevenenstein fieldMatchLevenenstein = new FieldMatchLevenenstein();
                String fieldMatched = fieldMatchLevenenstein.FieldMatchFinder(fieldsToBeMatched, fieldLocalName);
                matchedFields.add(fieldMatched);
            }
        }
        return matchedFields;
    }
}
