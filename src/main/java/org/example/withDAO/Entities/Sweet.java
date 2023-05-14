package org.example.withDAO.Entities;


import org.example.withDAO.Main;

public abstract class Sweet implements Comparable<Sweet> {
    int id;
    String name;
    float sugarContent;
    float weightOneItem;
    Main.Shape shape;

    public Sweet(){}
    public Sweet(String name, float sugarContent, float weight, Main.Shape shape) {
        this.name = name;
        this.sugarContent = sugarContent;
        this.weightOneItem = weight;
        this.shape = shape;
    }
    public Sweet(int id, String name, float sugarContent, float weight, Main.Shape shape) {
        this.id = id;
        this.name = name;
        this.sugarContent = sugarContent;
        this.weightOneItem = weight;
        this.shape = shape;
    }

    @Override
    public String toString() {
        return
                '\'' + name + '\'' +
                ", sugarContent=" + sugarContent +
                ", weight=" + weightOneItem +
                ", shape=" + shape ;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Sweet sweet) {
        if (sweet.sugarContent == this.sugarContent)
        return 0;
        else  return Math.round(this.sugarContent*100 - sweet.sugarContent*100);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSugarContent() {
        return sugarContent;
    }

    public void setSugarContent(float sugarContent) {
        this.sugarContent = sugarContent;
    }

    public float getWeightOneItem() {
        return weightOneItem;
    }

    public void setWeightOneItem(float weightOneItem) {
        this.weightOneItem = weightOneItem;
    }

    public Main.Shape getShape() {
        return shape;
    }

    public void setShape(Main.Shape shape) {
        this.shape = shape;
    }
}
