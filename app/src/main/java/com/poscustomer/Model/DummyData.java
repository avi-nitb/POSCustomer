package com.poscustomer.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Abhishek on 29-03-2017.
 */

public class DummyData {
    private static final String[] titles = {"Merchant Name1","Merchant Name2", "Merchant Name3", "Merchant Name4",
    "Merchant Name5"};

    private static final String[] subTitles = {"450.0m Mehrauli Archeological park New Delhi", "450.0m Mehrauli Archeological park New Delhi",
            "450.0m Mehrauli Archeological park New Delhi","450.0m Mehrauli Archeological park New Delhi","450.0m Mehrauli Archeological park New Delhi"};

    public static List<ListItem> getListData(){
        List<ListItem> data = new ArrayList<>();

        for (int i=0;i< 4;i++){
            for (int x =0; x<titles.length; x++){
                ListItem item = new ListItem();
                item.setTitle(titles[x]);
                item.setSubTitle(subTitles[x]);
                data.add(item);
            }
        }
        return (data);
    }
    public static ListItem getRandomListItem(){
        int rand = new Random().nextInt(5);

        ListItem item = new ListItem();

        item.setTitle(titles[rand]);
        item.setSubTitle(subTitles[rand]);

        return item;
    }
}
