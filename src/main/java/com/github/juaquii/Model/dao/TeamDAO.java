package com.github.juaquii.Model.dao;
import com.github.juaquii.Model.Connection.ConnectionMariaDB;
import com.github.juaquii.Model.entity.Manager;
import com.github.juaquii.Model.entity.Player;
import com.github.juaquii.Model.entity.Team;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TeamDAO implements DAO<Team> {
    private final static String INSERT = "INSERT INTO manager (Idequipo, Nombreequipo, Mediaequipo) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE manager SET Nombreequipo=?, Mediaequipo=? WHERE Idequipo=?";
    private final static String FINDALL = "SELECT m.Idequipo, m.Nombreequipo, m.Mediaequipo FROM manager AS m";
    private final static String FINDBYID = "SELECT m.Idequipo, m.Nombreequipo, m.Mediaequipo FROM manager AS m WHERE m.Idequipo=?";
    private final static String DELETE = "DELETE FROM manager WHERE Idequipo=?";

    @Override
    public Team save(Team entity) {
        Team result = entity;
        if (entity == null || entity.getIdequipo() == null) return result;
        Team t = findById(Integer.parseInt(entity.getIdequipo()));
        if (t.getIdequipo() == null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                pst.setString(1, entity.getIdequipo());
                pst.setString(2, entity.getNombreequipo());
                pst.setString(3, entity.getMediaequipo());
                pst.executeUpdate();
                if (entity.getIdequipo() != null) {
                    for (Manager m : entity.getManager()) {
                        ManagerDAO.build().save(m);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Team update(Team entity) {
        Team result = entity;
        if (entity == null || entity.getIdequipo() == null) return result;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            pst.setString(1, entity.getIdequipo());
            pst.setString(2, entity.getNombreequipo());
            pst.setString(3, entity.getMediaequipo());
            pst.executeUpdate();


            /**if (entity.getPlayers() != null) {
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
            }**/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team delete(Team entity) throws SQLException {
        if (entity == null || entity.getIdequipo() == null) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1, entity.getIdequipo());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Team findById(int key) {
        Team result = new Team();
        if (key == 0) return result;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setString(1, String.valueOf(key));
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setIdequipo(res.getString("idequipo"));
                result.setNombreequipo(res.getString("nombreequipo"));
                result.setMediaequipo(res.getString("mediaequipo"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public List<Team> findAll() {
        List<Team> result = new ArrayList<>();

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Team a = new Team();
                a.setIdequipo(res.getString("idequipo"));
                a.setNombreequipo(res.getString("nombreequipo"));
                a.setMediaequipo(res.getString("mediaequipo"));
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

    public static TeamDAO build() {
        return new TeamDAO();
    }

}
