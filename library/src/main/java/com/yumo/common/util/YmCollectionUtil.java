package com.yumo.common.util;

import java.util.Collection;

/**
 * Created by yumo on 2018/5/11.
 */

public class YmCollectionUtil {

    /**
     * 集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if (collection == null || collection.isEmpty()){
            return true;
        }
        return false;
    }
}
