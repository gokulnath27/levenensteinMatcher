import java.util.*;

public class FieldMatchLevenenstein {
    public String FieldMatchFinder(List<String> columnName, String fieldName ) {
        int totalFieldMatchedCount = 0;
        int totalRatio = 0;
        String fieldMatched = null;
        LevensteinDistanceFinder totalDistance = new LevensteinDistanceFinder();
        for (String fieldsToBeMatchedName:columnName) {
            int eachFieldRatio = totalDistance.getValue(fieldsToBeMatchedName,fieldName);
            if(eachFieldRatio>totalRatio){
                totalRatio = eachFieldRatio;
                fieldMatched = fieldsToBeMatchedName;
            }
        }
        return fieldMatched;
    }
}
