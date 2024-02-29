package services;

import entities.Reclamation;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReclamationServices implements IServices<Reclamation> {
    @Override
    public void addEntity(Reclamation reclamation) throws SQLException {
        String requete = "INSERT INTO reclamation (id_user, type, contenu) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, reclamation.getId_user());
            pst.setString(2, reclamation.getType());
            pst.setString(3, reclamation.getContenu());

            pst.executeUpdate();
            System.out.println("Reclamation ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Reclamation reclamation) throws SQLException {
        String requete = "UPDATE reclamation SET id_user=?, type=?, contenu=? WHERE id_reclamation=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, reclamation.getId_user());
            pst.setString(2, reclamation.getType());
            pst.setString(3, reclamation.getContenu());
            pst.setInt(4, reclamation.getId_reclamation());
            pst.executeUpdate();
            System.out.println("Reclamation mise à jour");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Reclamation reclamation) throws SQLException{
        String requete = "DELETE FROM reclamation WHERE id_reclamation=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, reclamation.getId_reclamation());
            pst.executeUpdate();
            System.out.println("Reclamation supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reclamation> getAllData() throws SQLException{
        List<Reclamation> reclamations = new ArrayList<>();
        String requete = "SELECT * FROM reclamation";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId_reclamation(rs.getInt("id_reclamation"));
                reclamation.setId_user(rs.getInt("id_user"));
                reclamation.setType(rs.getString("type"));
                reclamation.setContenu(rs.getString("contenu"));
                reclamation.setDate_env(rs.getDate("date_env"));
                reclamation.setStatus(rs.getString("status"));

                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    public void updateStatus(Reclamation reclamation) throws SQLException {
        String requete = "UPDATE reclamation SET status=? WHERE id_reclamation=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, "traitée");
            pst.setInt(2, reclamation.getId_reclamation());
            pst.executeUpdate();
            System.out.println("status mise à jour");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
