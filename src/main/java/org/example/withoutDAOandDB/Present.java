package org.example.withoutDAOandDB;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Present implements Comparator<Map.Entry<Sweet,Float>>{
    String name;
    HashMap<Sweet, Float> present;

    public Float getTotalWeight() {
        return totalWeight;
    }

    private Float totalWeight = 0f;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String str;

    public Present(String name, HashMap<Sweet, Float> present) {
        this.name = name;
        //List<withoutDAO.Sweet> list = new ArrayList<>(present.keySet());
       // Collections.sort(list);
        this.present = present;
        for (Object o : present.entrySet()) {
            Map.Entry<Sweet, Float> entry = (Map.Entry) o;
            totalWeight = totalWeight + entry.getValue();
        }
    }

    @Override
    public String toString() {
        str = name +  ", total weight = " + df.format(totalWeight) + " kg " + "\n";
        present.entrySet().stream().sorted(this::compare).forEach((entry)-> { str += entry.getKey().toString() + " - " + entry.getValue().toString()+ " kg" + "\n";});
        return  str;
       // Set<Map.Entry<withoutDAO.Sweet,Float>> set = present.entrySet();
       // for (Map.Entry<withoutDAO.Sweet,Float> entry : set) {
       //    // Map.Entry entry = (Map.Entry) o;
       //     res_str += entry.getKey().toString() + " - " + entry.getValue().toString()+ " kg" + "\n";
       // }
    }

    @Override
    public int compare(Map.Entry<Sweet, Float> o1, Map.Entry<Sweet, Float> o2) {
        if (o1.getKey().sugarContent == o2.getKey().sugarContent)
            return 0;
        else  return Math.round(o1.getKey().sugarContent*100 - o2.getKey().sugarContent*100);
    }

    public List<Sweet> findBySugare(float down, float up){
       // List<withoutDAO.Sweet> list = new ArrayList<>();
        List<Sweet> list = this.present.keySet().stream().filter(item->{
         return item.sugarContent >= down && item.sugarContent <= up ? true : false;
        }).collect(Collectors.toList());
        return list;
    }
}
