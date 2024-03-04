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

    MailService mailService = new MailService();

        @Override
        public void addEntity(Reponse reponse) throws SQLException {
            String requete = "INSERT INTO reponse (id_reclamation, contenu) VALUES (?, ?)";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, reponse.getId_reclamation());
                pst.setString(2, reponse.getContenu());
                pst.executeUpdate();
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                mailService.sendEmail("azizghest@gmail.com","Traitement Reclamations","Votre reponse a été envoyé a votre reclamation." +
=======
                mailService.sendEmail("azizghest@gmail.com","Traitement Reclamations","Une reponse a été envoyé a votre reclamation." +
>>>>>>> Stashed changes
=======
                mailService.sendEmail("azizghest@gmail.com","Traitement Reclamations","Une reponse a été envoyé a votre reclamation." +
>>>>>>> Stashed changes
=======
                mailService.sendEmail("azizghest@gmail.com","Traitement Reclamations","Une reponse a été envoyé a votre reclamation." +
>>>>>>> Stashed changes
                        "\nMerci pour votre patience.");
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
        public List<Reponse> getAllData() throws SQLException {
            List<Reponse> reponses = new ArrayList<>();
            String requete = "SELECT * FROM reponse";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                while (rs.next()) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                    Reponse reponse = new Reponse(rs.getInt("id_reponse"),
                            rs.getInt("id_reclamation"),
                            rs.getString("contenu"),
                            rs.getDate("date_rep"));
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                    Reponse reponse = new Reponse();
                    reponse.setId_reponse(rs.getInt("id_reponse"));
                    reponse.setId_reclamation(rs.getInt("id_reclamation"));
                    reponse.setContenu(rs.getString("contenu"));

<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                    reponses.add(reponse);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(reponses);
            return reponses;
        }

    public List<Reponse> getAllDataId(int id_reclam) {
        List<Reponse> reponses = new ArrayList<>();
        String requete = "SELECT * FROM reponse WHERE id_reclamation="+id_reclam;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reponse reponse = new Reponse();
                reponse.setId_reponse(rs.getInt("id_reponse"));
                reponse.setId_reclamation(rs.getInt("id_reclamation"));
                reponse.setContenu(rs.getString("contenu"));
                reponse.setDate_rep(rs.getDate("date_rep"));

                reponses.add(reponse);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponses;
    }
    }

