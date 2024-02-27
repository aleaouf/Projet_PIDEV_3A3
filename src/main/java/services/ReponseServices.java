package services;

import entities.Reclamation;
import entities.Reponse;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReponseServices implements IServices<Reponse> {

        @Override
        public void addEntity(Reponse reponse) throws SQLException {
            String requete = "INSERT INTO reponse (id_reclamation, contenu) VALUES (?, ?)";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, reponse.getId_reclamation());
                pst.setString(2, reponse.getContenu());
                pst.executeUpdate();
                System.out.println("Réponse ajoutée avec succès");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void updateEntity(Reponse reponse) throws SQLException {
            String requete = "UPDATE reponse SET id_reclamation=?, contenu=? WHERE id_reponse=?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, reponse.getId_reclamation());
                pst.setString(2, reponse.getContenu());
                pst.setInt(3, reponse.getId_reponse());
                pst.executeUpdate();
                System.out.println("Réponse mise à jour avec succès");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void deleteEntity(Reponse reponse) throws SQLException {
            String requete = "DELETE FROM reponse WHERE id_reponse=?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, reponse.getId_reponse());
                pst.executeUpdate();
                System.out.println("Réponse supprimée avec succès");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public List<Reponse> getAllData() throws SQLException{
            List<Reponse> reponses = new ArrayList<>();
            String requete = "SELECT * FROM reponse";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                while (rs.next()) {
                    Reponse reponse = new Reponse(rs.getInt("id_reponse"),
                            rs.getInt("id_reclamation"),
                            rs.getString("contenu"));
                    reponses.add(reponse);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return reponses;
        }

    public List<Reponse> getAllDataId(int id_reclam) {
        List<Reponse> reponses = new ArrayList<>();
        String requete = "SELECT * FROM reponse WHERE id_reclamation="+id_reclam;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reponse reponse = new Reponse(rs.getInt("id_reponse"),
                        rs.getInt("id_reclamation"),
                        rs.getString("contenu"));
                reponses.add(reponse);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponses;
    }
    }

