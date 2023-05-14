package org.example.withDAO.DAO;



import org.example.withDAO.Entities.Candy;

import java.util.List;

public interface ICandyDAO {
    void add(Candy candy);

    List<Candy> getAll();

    Candy getById(int id);

    void update(Candy candy);

    void updateSugareContent(float sugareContent, int candyId);

    void delete(int id);
}
