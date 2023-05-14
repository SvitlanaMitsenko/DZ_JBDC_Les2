package org.example.withoutDAOandDB;

public abstract class Sweet implements Comparable<Sweet> {
    String name;

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

    @Override
    public String toString() {
        return
                name + '\'' +
                ", sugarContent=" + sugarContent +
                ", weight=" + weightOneItem +
                ", shape=" + shape ;
    }

    @Override
    public int compareTo(Sweet sweet) {
        if (sweet.sugarContent == this.sugarContent)
        return 0;
        else  return Math.round(this.sugarContent*100 - sweet.sugarContent*100);
    }
}
