package org.example.withDAO.DAO;

public interface IDAOFactory {
    ICandyDAO getCandyDAO();
    ICookieDAO getCookieDAO();
    IPresentDAO getPresentDAO();

}
