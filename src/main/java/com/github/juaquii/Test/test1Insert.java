package com.github.juaquii.Test;

import com.github.juaquii.Model.dao.ManagerDAO;
import com.github.juaquii.Model.entity.Enum.Calidad;
import com.github.juaquii.Model.entity.Manager;
import com.github.juaquii.Model.dao.PlayerDAO;
import com.github.juaquii.Model.entity.Player;
import com.github.juaquii.Model.entity.Team;
import java.util.ArrayList;
public class test1Insert {
    public static void main(String[] args) {
        Manager m = new Manager(1,"España","Carlo Ancelotti", Calidad.bronze,new ArrayList<>());
        ManagerDAO mDAO = new ManagerDAO();
        mDAO.save(m);
    }
}
