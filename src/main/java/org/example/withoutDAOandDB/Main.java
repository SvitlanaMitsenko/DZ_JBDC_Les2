package org.example.withoutDAOandDB;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Main
{
    // наполнитель конфеты (начинка)
    public enum Filling{MILK_CHOCOLATE, DARK_CHOCOLATE, DRAGEE, WAFFLES, HAZELNUT, ALMOND, LIQUOR, JELLY, LOLLIPOP, CARAMEL };  //Шоколад, Драже, Вафли, Фундук, Миндаль, Желе, леденец

    // форма конфеты
    public enum Shape{CUBE, PYRAMID, OVAL, SPHERE, RECTANGLE, STAR, CIRCLE}; //Куб, Пирамида, Овал
    // вид фантика
    public enum Wrapper{
        Paper(1, "Бумага"), Foil(2, "Фольга"), Transparent(3, "Прозрачная упаковка"); //Бумага, Фольга, Прозрачный
        int value;
        public String rus;
        Wrapper(int v, String rus){
            this.value = v;
            this.rus = rus;
        }
    };
    // вид конфеты
    public enum CandyType{CHOCOLATE, JELLY, LOLLIPOP }; //Шоколадная, Желейная, Леденец

    // Новогодний подарок. Определить иерархию конфет и прочих сладостей.
// Создать несколько объектов-конфет. Собрать детский подарок с определением его веса.
// Провести сортировку конфет в подарке на основе одного из параметров.
// Найти конфету в подарке, соответствующую заданному диапазону содержания сахара
    public static void main(String[] args) {
        String s = "TEST";
        System.out.println(s);

        Candy candy = new Candy();

        Candy candy_bee = new Candy("Bee", 30, 10, Shape.CUBE, Wrapper.Transparent, CandyType.JELLY, Filling.JELLY, null, 0);
        Candy candy_AVK = new Candy("Chocolate night", 50, 20, Shape.CUBE, Wrapper.Foil, CandyType.CHOCOLATE, Filling.ALMOND, null, 0);
        Candy candy_chupik = new Candy("Chupa-Chups", 20, 30, Shape.SPHERE, Wrapper.Paper, CandyType.LOLLIPOP, Filling.LOLLIPOP, null, 15 );
        Candy candy_barbaris = new Candy("Barbaris", 20.5f, 8, Shape.OVAL, Wrapper.Paper, CandyType.LOLLIPOP, Filling.LOLLIPOP, null, 0);
        Candy candy_egypt = new Candy("Egypt", 23.5F, 27, Shape.PYRAMID, Wrapper.Foil, CandyType.CHOCOLATE, Filling.LIQUOR, Filling.MILK_CHOCOLATE, 0);
        Cookie cookie_biscuit = new Cookie("Biscuit", 10, 16, Shape.RECTANGLE, "Butter, nuts" );
        Cookie cookie_star = new Cookie("Star", 10, 23, Shape.STAR, "Butter, palm oil" );
        Cookie cookie_honey = new Cookie("Honey", 25, 30, Shape.RECTANGLE, "Butter, nuts, honey" );
        Cookie cookie_nuts = new Cookie("Nuts", 40, 40, Shape.RECTANGLE, "Butter, nuts, milk" );
        Cookie cookie_napoleon = new Cookie("Napoleon", 45, 20, Shape.CIRCLE, "Butter, sour cream" );

        HashMap<Sweet, Float> present1 = new HashMap<>();
        present1.put(candy_bee, 0.5f);
        present1.put(candy_AVK, 0.2f);
        present1.put(candy_egypt, 0.5f);
        present1.put(cookie_biscuit, 0.4f);
        present1.put(cookie_nuts, 0.6f);

        HashMap<Sweet, Float> present2 = new HashMap<>();
        present2.put(candy_chupik, 0.5f);
        present2.put(candy_barbaris, 0.4f);
        present2.put(cookie_star, 0.3f);
        present2.put(cookie_honey, 0.4f);
        present2.put(cookie_napoleon, 0.3f);


        Present present1_ = new Present("Present1", present1);
        Present present2_ = new Present("Present2", present2);

        ArrayList<Present> presents = new ArrayList<>();
        presents.add(present1_);
        presents.add(present2_);
        Collections.sort(presents, new Comparator<Present>() {
            @Override
            public int compare(Present o1, Present o2) {
                if (o1.getTotalWeight() == o2.getTotalWeight()){
                    return 0;
                }else{
                    return Math.round(o1.getTotalWeight()*100 - o2.getTotalWeight()*100);
                }
            }
        });
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        presents.forEach(item-> System.out.println(item.name + " - " + df.format(item.getTotalWeight()) + " kg"));

        System.out.println(present1_);
        System.out.println(present2_);

        System.out.println("Продукты в подарке с заданным содержанием сахара");
        List<Sweet> filteredList = present2_.findBySugare(20f,40f);
        filteredList.forEach(System.out::println);
    }

    public static int factorial(int num) {
        int res = 1;
        if (num == 1 || num == 0 ) {
            res = 1;
        } else {
            res = num*factorial(--num);
        }
        return res;
    }

}
