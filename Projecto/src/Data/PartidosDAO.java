/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Partido;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruifreitas
 */
public class PartidosDAO {

    private final String url = "", nome = "root", pass = "senha";

    private Connection con;

    public PartidosDAO(String url, String nome, String pass) {
        try {
			this.con = DriverManager.getConnection(url, nome, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public List<Partido> getPartidos() {
        try {
            List<Partido> lista = new ArrayList<Partido>();
            PreparedStatement s1 = con.prepareStatement("Select * from partidos");
            ResultSet rs = s1.executeQuery();
            while(rs.next()){
                Partido temp = new Partido();
                temp.setId(Integer.parseInt(rs.getString("id")));
                temp.setNome(rs.getString("nome"));
                temp.setSigla(rs.getString("sigla"));
                temp.setSimbolo(rs.getString("simbolo"));
                lista.add(temp);
            }
            con.commit();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(PartidosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Partido getPartidoId(int id) {
        try {
            Partido p = new Partido();
            PreparedStatement s1 = con.prepareStatement("Select * from partidos where partidos.id=?");
            s1.setString(1, "" + id);
            ResultSet res = s1.executeQuery();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(PartidosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
}
