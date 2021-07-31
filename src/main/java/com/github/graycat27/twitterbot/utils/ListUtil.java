package com.github.graycat27.twitterbot.utils;

import java.util.List;

public class ListUtil {

    private ListUtil(){ /* インスタンス化防止 */ }

    public static void printList(List<?> targetList){
        int size = targetList.size();

        System.out.println("{ List:{size: "+ size +",");
        System.out.println("       data:[");
        for(int i=0; i<size-1; i++){
            System.out.print(targetList.get(i).toString());
            System.out.println(",");
        }
        if(size>1) {
            System.out.println(targetList.get(size - 1).toString());
        }
        System.out.println("]} }");


    }
}
