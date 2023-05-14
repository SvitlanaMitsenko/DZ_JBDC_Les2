package org.example.withDAO.Entities;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Present implements Comparator<Map.Entry<Sweet,Float>>{
    private String name;
    private HashMap<Sweet, Float> presentItems;

      private Float totalWeight = 0f;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String str;
    public Present(){}
    public Present(String name, HashMap<Sweet, Float> present) {
        this.name = name;
        //List<withoutDAO.Sweet> list = new ArrayList<>(present.keySet());
       // Collections.sort(list);
        this.presentItems = present;
        for (Object o : present.entrySet()) {
            Map.Entry<Sweet, Float> entry = (Map.Entry) o;
            totalWeight = totalWeight + entry.getValue();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Sweet, Float> getPresentItems() {
        return presentItems;
    }

    public void setPresentItems(HashMap<Sweet, Float> present) {
        this.presentItems = present;
    }

    public Float getTotalWeight() {
        for (Object o : presentItems.entrySet()) {
            Map.Entry<Sweet, Float> entry = (Map.Entry) o;
            totalWeight = totalWeight + entry.getValue();
        }
        return(totalWeight);
    }



    @Override
    public String toString() {
        str = name +  ", total weight = " + df.format(getTotalWeight()) + " kg " + "\n";
        presentItems.entrySet().stream().sorted(this::compare).forEach((entry)-> { str += entry.getKey().toString() + " - " + entry.getValue().toString()+ " kg" + "\n";});
        return  str;
    }

    @Override
    public int compare(Map.Entry<Sweet, Float> o1, Map.Entry<Sweet, Float> o2) {
        if (o1.getKey().sugarContent == o2.getKey().sugarContent)
            return 0;
        else  return Math.round(o1.getKey().sugarContent*100 - o2.getKey().sugarContent*100);
    }

    public List<Sweet> findBySugare(float down, float up){
       // List<withoutDAO.Sweet> list = new ArrayList<>();
        List<Sweet> list = this.presentItems.keySet().stream().filter(item->{
         return item.sugarContent >= down && item.sugarContent <= up ? true : false;
        }).collect(Collectors.toList());
        return list;
    }
}
