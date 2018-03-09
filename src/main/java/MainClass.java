import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        List<String> columnName = new ArrayList<String>();
        List<String> fieldName = new ArrayList<String>();
        columnName.add("Full Name");
        columnName.add("First name");
        columnName.add("Auto");
        columnName.add("Auto number");
        columnName.add("phone number");
        columnName.add("phone");
        columnName.add("annual revenue");



        fieldName.add("auto");
        fieldName.add("full name");
        fieldName.add("phne");
        fieldName.add("ph");
        fieldName.add("name");
        fieldName.add("full");
        fieldName.add("pho");
        fieldName.add("pho no.");
        fieldName.add("phone no.");
        fieldName.add("phone number");
        fieldName.add("auto number");
        fieldName.add("revenue");
        fieldName.add("annual");
        fieldName.add("annual revenue");
        fieldName.add("reven");
        fieldName.add("rev");
        fieldName.add("rvn");
        fieldName.add("fst");
        fieldName.add("aut");
        fieldName.add("aut_no.");
        FieldsMatchFinder again = new FieldsMatchFinder();
        List<String> matchedFields = again.findMatchedFields(columnName, fieldName);
        for(String s:matchedFields){
            System.out.println(s);
        }
    }
}
