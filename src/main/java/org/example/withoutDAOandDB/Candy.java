package org.example.withoutDAOandDB;

public class Candy extends Sweet {
    int id;
    Main.Wrapper wrapper;;
    Main.CandyType type;
    Main.Filling filling;
    int stickLength; //длина палочки если это чупа-чупс
    Main.Filling glaze;

    public Candy(){}
    public Candy(String name, float sugarContent, float weight, Main.Shape shape, Main.Wrapper wrapper, Main.CandyType type, Main.Filling filling, Main.Filling glaze, int stickLength) {
        super(name, sugarContent, weight, shape);
        this.wrapper = wrapper;
        this.type = type;
        this.filling = filling;
        this.glaze = glaze;
        this.stickLength = stickLength;
    }

    public void setId(int id){
        this.id = id;
    }
    public Main.Wrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(Main.Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Main.CandyType getType() {
        return type;
    }

    public void setType(Main.CandyType type) {
        this.type  = type;
    }

    public Main.Filling getFilling() {
        return filling;
    }

    public void setFilling(Main.Filling filling) {
        this.filling = filling;
    }

    public Main.Filling getGlaze() {
        return glaze;
    }

    public void setGlaze(Main.Filling glaze) {
        this.glaze = glaze;
    }

    public int getStickLength() {
        return stickLength;
    }

    public void setStickLength(int stickLength) {
        this.stickLength = stickLength;
    }

    @Override
    public String toString() {
        return "Candy " + name +
                " { " + "sugarContent=" + sugarContent +
                "wrapper=" + wrapper.rus +
                ", type=" + type +
                ", filling=" + filling +
                ", stickLength=" + stickLength +
                ", glaze=" + glaze +
                ", weight=" + weightOneItem +
                ", shape=" + shape +
                '}';
    }
}
