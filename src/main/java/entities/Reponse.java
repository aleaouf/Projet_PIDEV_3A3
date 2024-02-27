package entities;


public class Reponse {
    private int id_reponse;
    private int id_reclamation;
    private String contenu;

    // Constructeur

    public Reponse(){}
    public Reponse(int id_reponse, int id_reclamation, String contenu) {
        this.id_reponse = id_reponse;
        this.id_reclamation = id_reclamation;
        this.contenu = contenu;
    }

    // Getters
    public int getId_reponse() {
        return id_reponse;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public String getContenu() {
        return contenu;
    }

    // Setters
    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
