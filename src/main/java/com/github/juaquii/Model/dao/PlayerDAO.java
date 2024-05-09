package com.github.juaquii.Model.dao;

import com.github.juaquii.Model.Connection.ConnectionMariaDB;
import com.github.juaquii.Model.entity.Manager;
import com.github.juaquii.Model.entity.Player;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements DAO<Player> {
    private static final String FINDALL = "SELECT p.idj, p.media, p.posicion, p.habilidadEspecial FROM player AS p";
    private static final String FINDBYID = "SELECT p.idj, p.media, p.posicion, p.habilidadEspecial FROM player AS p WHERE p.idj=?";
    private static final String INSERT = "INSERT INTO player (idj, media, posicion, habilidadEspecial) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE player SET media=?, posicion=?, habilidadEspecial=? WHERE idj=?";
    private static final String DELETE = "DELETE FROM player WHERE idj=?";
    private static final String FINDBYMANAGER = "SELECT p.idj, p.media, p.posicion, p.habilidadEspecial FROM player AS p WHERE p.manager=?";

    private static final String FINDBYAUTHOR ="SELECT b.isbn,b.title,b.id_author FROM book AS b WHERE b.id_author=?";

    private Connection conn;
    public PlayerDAO(){
        conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public Player save(Player entity) {
        Player result=entity;
        if(entity!=null){
            String idj = entity.getIdj();
            if(idj!=null){
                Player isInDataBase = findById(Integer.parseInt(idj));
                if(isInDataBase==null){
                    //INSERT
                    try(PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getIdj());
                        pst.setString(2, entity.getMedia());
                        pst.setString(3, entity.getPosicion());
                        pst.setString(4, entity.getHabilidadEspecial());
                        pst.executeUpdate();

                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Player update(Player entity) {
        Player result = entity;
        if (entity != null) {
            String idj = entity.getIdj();
            if (idj != null) {
                Player isInDataBase = findById(Integer.parseInt(idj));
                if (isInDataBase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getIdj());
                        pst.setString(2, entity.getMedia());
                        pst.setString(3, entity.getPosicion());
                        pst.setString(4, entity.getHabilidadEspecial());
                        pst.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }



        if(entity!=null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getIdj());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

        public List<Player> findAll() {
       List<Player> result = new ArrayList<>();
        try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Player b = new Player();
                    b.setIdj(res.getString("isbn"));
                    b.setPosicion(res.getString("title"));
                    result.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Player> findByManager(Manager m) {
        List<Player> result = new ArrayList<>();
        if(m==null || m.getID()==0) return result;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYMANAGER)){
            pst.setString(1, String.valueOf(m.getID()));
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Player p = new Player();
                    p.setIdj(res.getString("idj"));
                    p.setMedia(res.getString("media"));
                    p.setPosicion(res.getString("posicion"));
                    p.setHabilidadEspecial(res.getString("habilidadEspecial"));
                    result.add(p);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }



    @Override
    public Player delete(Player entity) throws SQLException {
        return null;
    }

    @Override
    public Player findById(int key) {
            Player result = null;
            try(PreparedStatement pst = conn.prepareStatement(FINDBYID)){
                pst.setString(1, String.valueOf(key));
                try(ResultSet res = pst.executeQuery()){
                    if(res.next()){
                        Player p = new Player();
                        p.setIdj(res.getString("idj"));
                        p.setMedia(res.getString("media"));
                        p.setPosicion(res.getString("posicion"));
                        p.setHabilidadEspecial(res.getString("habilidadEspecial"));
                        result = p;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }

    @Override
        public void close() throws IOException {
    }
    public static PlayerDAO build(){
        return new PlayerDAO();
    }
}
