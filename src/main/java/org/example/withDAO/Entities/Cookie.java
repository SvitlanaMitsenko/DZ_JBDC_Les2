package org.example.withDAO.Entities;

import org.example.withDAO.Main;

public class Cookie extends Sweet {
    String ingredients;
    public Cookie(){}
    public Cookie(int id, String name, float sugarContent, float weight, Main.Shape shape, String ingredients) {
        super(id, name, sugarContent, weight, shape);
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Cookie " + "id=" + id + " " + super.toString() + " {" +
                "ingredients='" + ingredients + '\'' +
                '}';
    }
}
