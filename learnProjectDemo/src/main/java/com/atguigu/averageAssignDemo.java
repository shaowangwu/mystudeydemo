package com.atguigu;

import java.util.ArrayList;
import java.util.List;
/**
* @author:shaowangwu
* @Date: 2022/3/4 15:39
* Description:
*/
public class averageAssignDemo {

    /**
     * 平均拆分集合
     * ***/
    public static <T>List<List<T>> averageDividelist(List<T> source,int n){
        List<List<T>> lists = new ArrayList<List<T>>();
        //取余数
        int remainder = source.size()%n;
        //取商
        int number = source.size()/n;
        //偏移量
        int offset=0;

        for (int i = 0; i < n; i++) {
            List<T> val=null;
           if(remainder>0){
               val=source.subList(i*number+offset,(i+1)*number+offset+1);
               remainder--;
               offset++;
           }else{
               val=source.subList(i*number+offset,(i+1)*number+offset);
           }

           if(val!=null && val.size()>0){
               lists.add(val);
           }
        }

        return lists;
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("a");
        list.add("111");
        list.add("bbb");

        averageDividelist(list,5);
    }


}
