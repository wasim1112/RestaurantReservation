package app.general.common.utils;

import java.util.List;
import java.util.Set;


public class SecurityUtils {

    public static void assertListIsSubListOfList(List<Long> userSelectedIds, Set<Long> availableIds){
        int originalListSize = userSelectedIds.size();
        userSelectedIds.retainAll(availableIds);
        if(originalListSize != userSelectedIds.size()){
            throw new RuntimeException("errors.general.notAuthorized");
        }
    }

}
