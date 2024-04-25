package com.github.juaquii.Model.dao;

import com.github.juaquii.Model.Connection.ConnectionMariaDB;
import com.github.juaquii.Model.entity.Manager;
import com.github.juaquii.Model.entity.Player;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements DAO<Manager,String> {
    private final static String INSERT="INSERT INTO manager (dni,name) VALUES (?,?)";
    private final static String UPDATE="UPDATE author SET name=? WHERE dni=?";
    private final static String FINDALL="SELECT a.dni,a.name FROM author AS a";
    private final static String FINDBYDNI="SELECT a.dni,a.name FROM author AS a WHERE a.dni=?";
    private final static String DELETE="DELETE FROM author AS a WHERE a.dni=?";

    @Override
    public Manager save(Manager entity) {
        Manager result = entity;
        if(entity==null || entity.getDni()==null) return result;
        Manager a = findById(entity.getDni());  //si no está devuelve un autor por defecto
        if(a.getDni()==null){
            //INSERT
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1,entity.getDni());
                        pst.setString(2,entity.getName());
                        pst.executeUpdate();
                        //si fuera autoincremental yo tendría que leer getGeneratedKeys() -> setDNI
                        /*  ResultSet res = pst.getGeneratedKeys();
                            if(rs.next()){
                                entity.setDni(rs.getStrng(1));
                             }
                         */

                        //save cascade -> opcional
                        if(entity.getBooks()!=null) {
                            for (Player b : entity.getBooks()) {
                                BookDAO.build().save(b);
                            }
                        }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            //UPDATE
            try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1,entity.getName());
                pst.setString(2,entity.getDni());
                pst.executeUpdate();

                //update cascada --> opcional
                if(entity.getBooks()!=null){
                    List<Player> booksBefore = BookDAO.build().findByAuthor(entity);
                    List<Player> booksAfter = entity.getBooks();

                    List<Player> booksToBeRemoved = new ArrayList<>(booksBefore);
                    booksToBeRemoved.removeAll(booksAfter);

                    for(Player b:booksToBeRemoved){
                        BookDAO.build().delete(b);
                    }
                    for(Player b:booksAfter){
                        BookDAO.build().save(b);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Manager delete(Manager entity) throws SQLException {
        if(entity==null || entity.getDni()==null) return entity;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1,entity.getDni());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Manager findById(String key) {
        Manager result = new AuthorLazy();
        if(key==null) return result;

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYDNI)) {
            pst.setString(1,key);
            ResultSet res = pst.executeQuery();
            if(res.next()){
                result.setDni(res.getString("dni"));
                result.setName(res.getString("name"));
                //Lazy
                //BookDAO bDAO = new BookDAO();
                //result.setBooks(bDAO.findByAuthor(result));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                Manager a = new AuthorLazy();
                a.setDni(res.getString("dni"));
                a.setName(res.getString("name"));
                //Lazy
                // a.setBooks(BookDAO.build().findByAuthor(a));
                result.add(a);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static AuthorDAO build(){
        return new AuthorDAO();
    }
}
class AuthorLazy extends Manager {
 @Override
 public List<Player> getBooks(){
        if(super.getBooks()==null){
            setBooks(BookDAO.build().findByAuthor(this));
        }
        return super.getBooks();
 }
}
