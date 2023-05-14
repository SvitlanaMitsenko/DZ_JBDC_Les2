package org.example.withDAO;

import org.example.withDAO.DAO.IPresentDAO;
import org.example.withDAO.DAOImpl.DAOFactory;
import org.example.withDAO.DAO.ICandyDAO;
import org.example.withDAO.DAO.IDAOFactory;
import org.example.withDAO.Entities.Candy;
import org.example.withDAO.Entities.Present;
import org.example.withDAO.Entities.Sweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    // наполнитель конфеты (начинка)
    public enum Filling {NO, MILK_CHOCOLATE, DARK_CHOCOLATE, DRAGEE, WAFFLES, HAZELNUT, ALMOND, LIQUOR, JELLY, LOLLIPOP, CARAMEL}

    ;  //Шоколад, Драже, Вафли, Фундук, Миндаль, Желе, леденец

    // форма конфеты
    public enum Shape {CUBE, PYRAMID, OVAL, SPHERE, RECTANGLE, STAR, CIRCLE}

    ; //Куб, Пирамида, Овал

    // вид фантика
    public enum Wrapper {
        Paper(1, "Бумага"), Foil(2, "Фольга"), Transparent(3, "Прозрачная упаковка"); //Бумага, Фольга, Прозрачный
        int value;
        String rus;

        Wrapper(int v, String rus) {
            this.value = v;
            this.rus = rus;
        }
    }

    public enum CandyType {CHOCOLATE, JELLY, LOLLIPOP}

    ; //Шоколадная, Желейная, Леденец

    public static void main(String[] args) {
        IDAOFactory factory = DAOFactory.getInstance();
        ICandyDAO candyDAO = factory.getCandyDAO();
        
        //Оновимо вміст кукру у цукерки з id=3;
        candyDAO.updateSugareContent(33.33f,3);

        //Отримаємо всі цукерки
        ArrayList<Candy> candies = (ArrayList<Candy>) candyDAO.getAll();
        candies.forEach(item -> System.out.println(item));

        //Отримаємо цукерку по id
        Candy candy = candyDAO.getById(2);
        System.out.println("\nДо оновлення: " + candy);

        //Оновимо отриману цукерку
        candy.setName("New candy");
        candy.setType(CandyType.LOLLIPOP);
        candy.setSugarContent(30.23f);
        candyDAO.update(candy);
        candy = candyDAO.getById(2);
        System.out.println("Після оновлення: " + candy + "\n");


        //Додамо нову цекерку
        // int id, String name, float sugarContent, float weight, Main.Shape shape, Main.Wrapper wrapper, Main.CandyType type, Main.Filling filling, Main.Filling glaze, short stickLength
        int id = 6;
         candyDAO.add(new Candy(id, "BirdMilk", 8.5f, 15f, Shape.RECTANGLE, Wrapper.Paper, CandyType.CHOCOLATE, Filling.CARAMEL, Filling.DARK_CHOCOLATE, (short)0));

        ArrayList<Candy> candies2 = (ArrayList<Candy>) candyDAO.getAll();

        candies2.forEach(item -> System.out.println(item));
        System.out.println();

        //Видалимо нову цукерку
        candyDAO.delete(id);

        // Інформія про подарунки (кожен подарунок містить список солодощів (цукерки та/або печива)
        IPresentDAO presentDAO = factory.getPresentDAO();
        ArrayList<Present> presents =  (ArrayList)presentDAO.getFoolInfo();
        System.out.println();
        presents.forEach(item->{
            System.out.println(item.toString());
        });
    }
}
