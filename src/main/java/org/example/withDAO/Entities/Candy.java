package org.example.withDAO.Entities;


import org.example.withDAO.Main;

public class Candy extends Sweet {
    Main.Wrapper wrapper;;
    Main.CandyType type;
    Main.Filling filling;
    short stickLength; //длина палочки если это чупа-чупс
    Main.Filling glaze;

    public Candy(){}

    public Candy(String name, float sugarContent, float weight, Main.Shape shape, Main.Wrapper wrapper, Main.CandyType type, Main.Filling filling, Main.Filling glaze, short stickLength) {
        super(name, sugarContent, weight, shape);
        this.wrapper = wrapper;
        this.type = type;
        this.filling = filling;
        this.glaze = glaze;
        this.stickLength = stickLength;
    }

    public Candy(int id, String name, float sugarContent, float weight, Main.Shape shape, Main.Wrapper wrapper, Main.CandyType type, Main.Filling filling, Main.Filling glaze, short stickLength) {
        super(id, name, sugarContent, weight, shape);
        this.wrapper = wrapper;
        this.type = type;
        this.filling = filling;
        this.glaze = glaze;
        this.stickLength = stickLength;
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
        this.type = type;
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

    public short getStickLength() {
        return stickLength;
    }

    public void setStickLength(short stickLength) {
        this.stickLength = stickLength;
    }

    @Override
    public String toString() {
        return "Candy " + "id=" + id + " " + '\'' + name + '\'' +
                " { " + "sugarContent=" + sugarContent +
                "wrapper=" + wrapper +
                ", type=" + type +
                ", filling=" + filling +
                ", stickLength=" + stickLength +
                ", glaze=" + glaze +
                ", weight=" + weightOneItem +
                ", shape=" + shape +
                '}';
    }
}
