package services;

import entities.Personne;
import interfaces.IServices;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneServices implements IServices<Personne> {
    Connection cnx;
    public PersonneServices() {
        cnx = MyConnection.getInstance().getCnx();
    }
    @Override
    public void Ajouter(Personne personne) {
        String sql = "INSERT INTO user(Nom,Prenom,Email,Password,Datedenaissance)" + " VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"','"+personne.getEmail()+"','"+personne.getPassword()+"','"+personne.getDatedenaissance()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(sql);
            System.out.println("Personne ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean existemail(String Email) {
        boolean exist = false;
        String sql = "SELECT * FROM user WHERE Email = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, Email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            // Handle or log the exception appropriately
            System.err.println("An error occurred while executing SQL query: " + ex.getMessage());
        }
        return exist;
    }
    public void addEntity2(Personne personne) {
        String requete = "INSERT INTO user(Nom,Prenom,Email,Password,Datedenaissance) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, personne.getNom());
            pst.setString(2, personne.getPrenom());
            pst.setString(3, personne.getEmail());
            pst.setString(4, personne.getPassword());
            pst.setString(5, personne.getDatedenaissance());
            pst.executeUpdate();
            System.out.println("Personne added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(Personne personne) {

    }

    @Override
    public void deleteEntity(Personne personne) {

    }
    public Personne getPersonneById(int id) throws SQLException {

        Personne personne = null;
        String query = "SELECT * FROM user WHERE Id_user = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    personne = new Personne();
                    personne.setId(resultSet.getInt("Id_user"));
                    personne.setNom(resultSet.getString("Nom"));
                    personne.setPrenom(resultSet.getString("Prenom"));
                    personne.setEmail(resultSet.getString("Email"));
                    personne.setPassword(resultSet.getString("Password"));
                    personne.setDatedenaissance(resultSet.getString("Datedenaissance"));
                }
            }
        }
        return personne;
    }
    public Personne recuperer2(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE Id_user = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Personne personne = new Personne();
                    personne.setId(resultSet.getInt("Id_user"));
                    personne.setNom(resultSet.getString("Nom"));
                    personne.setPrenom(resultSet.getString("Prenom"));
                    personne.setEmail(resultSet.getString("Email"));
                    personne.setPassword(resultSet.getString("Password"));
                    personne.setDatedenaissance(resultSet.getString("Datedenaissance"));
                    return personne;
                }
            }
        }
        return null; // Retourne null si aucune personne n'est trouvée avec cet ID
    }



    public List<Personne> recuperer() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = MyConnection.getInstance().getCnx(); // Utilisation de MyConnection.getConnection()
            String query = "SELECT Id_user, Nom, Prenom, Email, Password FROM user"; // Assurez-vous de spécifier le nom de votre table
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Création d'un objet Personne à partir des données récupérées de la base de données
                Personne personne = new Personne();
                personne.setId(resultSet.getInt("Id_user"));
                personne.setNom(resultSet.getString("Nom"));
                personne.setPrenom(resultSet.getString("Prenom"));
                personne.setEmail(resultSet.getString("Email"));
                personne.setPassword(resultSet.getString("Password"));

                // Ajout de la personne à la liste
                personnes.add(personne);
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            // Notez que nous n'avons pas besoin de fermer explicitement la connexion ici car elle est obtenue à partir de MyConnection.getInstance().getCnx(),
            // et sa gestion de la connexion est gérée ailleurs (probablement dans MyConnection)
        }

        return personnes;
    }
    public void modifier(Personne t) throws SQLException {
        String req = "Update user set Nom=?, Prenom=?, Email=?, Password=? where Id_user=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, t.getNom());
        stmt.setString(2, t.getPrenom());
        stmt.setString(3, t.getEmail());
        stmt.setInt(4, t.getId());

        stmt.executeUpdate();

        System.out.println(" modification etablie!");
    }
    public void supprimer(Personne t) throws SQLException {
        String req = "Delete from user where Id_user=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setInt(1, t.getId());
        stmt.executeUpdate();
        System.out.println(" suppression etablie!");


    }
    public List<Personne> rechercherParNom(String nom) throws SQLException {
        List<Personne> users = new ArrayList<>();
        String req = "SELECT * FROM user WHERE (Nom LIKE '%" + nom + "%' OR Email LIKE '%" + nom + "%' OR Prenom LIKE '%" + nom + "%')";
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(req);

        while (rs.next()) {
            Personne p = new Personne();
            p.setId(rs.getInt("Id_user"));
            p.setEmail(rs.getString("Email"));
            p.setNom(rs.getString("Nom"));
            p.setPrenom(rs.getString("Prenom"));
            p.setPassword(rs.getString("Password"));

            users.add(p);


        }
        return users;
    }
    public List<Personne> recupererParPrenom(String prenom) throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        String query = "SELECT Id_user, Nom, Prenom, Email, Password FROM user WHERE Prenom = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, prenom);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Personne personne = new Personne();
                    personne.setId(resultSet.getInt("Id_user"));
                    personne.setNom(resultSet.getString("Nom"));
                    personne.setPrenom(resultSet.getString("Prenom"));
                    personne.setEmail(resultSet.getString("Email"));
                    personne.setPassword(resultSet.getString("Password"));
                    personnes.add(personne);
                }
            }
        }
        return personnes;
    }



    @Override
    public List<Personne> getAllData() {
        List<Personne> data=new ArrayList<>();
        String requete = "SELECT * FROM  user ";
        try {
            Statement st =  MyConnection.getInstance().getCnx().createStatement();
           ResultSet rs= st.executeQuery(requete);
           while (rs.next()){
               Personne p = new Personne("Rebai", "Saber", "saberweldzakeya@gmail.com", "aloulou123","20/20/2020");
               p.setId(rs.getInt(1));
               p.setNom(rs.getString("Nom"));
               p.setPrenom(rs.getString("Prenom"));
               p.setEmail(rs.getString("Email"));
               p.setPassword(rs.getString("Password"));
               p.setDatedenaissance(rs.getString("Datedenaissance"));
               data.add(p);
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return data;
    }
}
