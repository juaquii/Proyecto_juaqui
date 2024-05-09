package com.github.juaquii.Model.dao;
import com.github.juaquii.Model.Connection.ConnectionMariaDB;
import com.github.juaquii.Model.entity.Enum.Calidad;
import com.github.juaquii.Model.entity.Manager;
import com.github.juaquii.Model.entity.Player;
import org.mariadb.jdbc.Statement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO implements DAO<Manager> {
    private final static String INSERT = "INSERT INTO entrenador (ID,Pais,Nombre,Calidad) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE entrenador SET Pais=?, Nombre=?, Calidad=? WHERE ID=?";
    //private final static String FINDALL = "SELECT m.ID, m.Pais, m.Nombre, m.Calidad FROM entrenador AS m";
    private final static String FINDBYID = "SELECT m.ID, m.Pais, m.Nombre, m.Calidad FROM entrenador AS m WHERE m.ID=?";
    private final static String DELETE = "DELETE FROM entrenador WHERE ID=?";

    private Connection conn;
    public ManagerDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Manager save(Manager entity) {
        Manager result = entity;
        if (entity == null || entity.getID() == 0) return result;
        Manager m = findById(entity.getID());
        if (m == null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getID());
                pst.setString(2, entity.getPais());
                pst.setString(3, entity.getNombre());
                pst.setString(4, entity.getCalidad().getRarety());
                pst.executeUpdate();
                //pst.getGeneratedKeys() -> setID
                ResultSet res = pst.getGeneratedKeys();
                if(res.next()){
                    entity.setID(res.getInt(1));
                }

                //save cascade -> opcional
                if(entity.getPlayers()!=null) {
                    for (Player p : entity.getPlayers()) {
                        PlayerDAO.build().save(p);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Manager update(Manager entity) {
        Manager result = entity;
        if (entity == null || entity.getID() == 0) return result;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getPais());
            pst.setString(2, entity.getNombre());
            pst.setString(3, entity.getCalidad().getRarety());
            pst.setInt(4, entity.getID());
            pst.executeUpdate();


            if (entity.getPlayers() != null) {
                List<Player> playersBefore = PlayerDAO.build().findByManager(entity);
                List<Player> playersAfter = entity.getPlayers();

                List<Player> playersToBeRemoved = new ArrayList<>(playersBefore);
                playersToBeRemoved.removeAll(playersAfter);

                for (Player p : playersToBeRemoved) {
                    ManagerDAO.build().delete(p);
                }
                for (Player p : playersAfter) {
                    ManagerDAO.build().save(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Manager delete(Manager entity) throws SQLException {
        if (entity == null || entity.getID() == 0) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, String.valueOf(entity.getID()));
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Manager findById(int key) {
        Manager result = new Manager();
        if (key == 0) return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, String.valueOf(key));
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setID(res.getInt("ID"));
                result.setPais(res.getString("pais"));
                result.setNombre(res.getString("nombre"));
                result.setCalidad(Calidad.valueOf(res.getString("Calidad")));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**public List<Manager> findAll() {
        List<Manager> result = new ArrayList<>();

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Manager a = new ManagerLazy();
                a.setID(res.getString("ID"));
                a.setPais(res.getString("pais"));
                a.setNombre(res.getString("nombre"));
                a.setCalidad(res.getString("calidad"));
                result.add(a);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }**/

    @Override
    public void close() throws IOException {

    }

    public static ManagerDAO build() {
        return new ManagerDAO();
    }
}
 

