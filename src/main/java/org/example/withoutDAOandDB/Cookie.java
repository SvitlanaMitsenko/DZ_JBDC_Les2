package org.example.withoutDAOandDB;

public class Cookie extends Sweet{
    String ingredients;
    public Cookie(String name, float sugarContent, float weight, Main.Shape shape, String ingredients) {
        super(name, sugarContent, weight, shape);
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
        return "Cookie" + " " + super.toString() + " {" +
                "ingredients='" + ingredients + '\'' +
                '}';
    }
}
