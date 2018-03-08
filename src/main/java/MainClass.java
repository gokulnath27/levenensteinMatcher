import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        List<String> columnName = new ArrayList<String>();
        List<String> fieldName = new ArrayList<String>();
        columnName.add("Full Name");
        columnName.add("First name");
        columnName.add("Auto");
        columnName.add("phone");
        columnName.add("annual revenue");
        columnName.add("revenue");


        fieldName.add("auto");
        fieldName.add("full name");
        fieldName.add("phne");
        fieldName.add("ph");
        fieldName.add("name");
        fieldName.add("full");
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
        columnName.sort(Comparator.comparing(String::length));
        FieldsMatchFinder again = new FieldsMatchFinder();
        List<String> matchedFields = again.findMatchedFields(columnName, fieldName);
        for(String s:matchedFields){
            System.out.println(s);
        }
    }
}
