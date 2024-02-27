package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.PersonneServices;
public class PersonneInfo {

    private int loggedInPersonId;

    public void setLoggedInPersonId(int loggedInPersonId) {
        this.loggedInPersonId = loggedInPersonId;
        // Vous pouvez utiliser cet ID pour charger les informations de la personne connectée
        // depuis la base de données ou d'où vous les récupérez.
    }

    public void setDatedenaissancetextfield(TextField datedenaissancetextfield3) {
        this.datedenaissancetextfield3 = datedenaissancetextfield3;
    }

    public void setEmailtextfield(TextField emailtextfield3) {
        this.emailtextfield3 = emailtextfield3;
    }

    @FXML
    private TextField datedenaissancetextfield3;

    @FXML
    private TextField emailtextfield3;

    public void setNomtextfield(TextField nomtextfield3) {
        this.nomtextfield3 = nomtextfield3;
    }

    @FXML
    private TextField nomtextfield3;

    public void setPasswordtextfield(TextField passwordtextfield3) {
        this.passwordtextfield3 = passwordtextfield3;
    }

    @FXML
    private TextField passwordtextfield3;

    public void setPrenomtextfield(TextField prenomtextfield3) {
        this.prenomtextfield3 = prenomtextfield3;
    }

    @FXML
    private TextField prenomtextfield3;

    private PersonneServices personneServices;

    // Méthode pour initialiser le contrôleur après que tous les champs aient été injectés par FXML.
    @FXML
    void initialize() {
        this.personneServices = new PersonneServices();

    }





    // Méthode pour modifier le nom de la personne
    @FXML
    void ModifierNom() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour modifier le prénom de la personne
    @FXML
    void Modifierprenom() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour modifier l'email de la personne
    @FXML
    void Modifieremail() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour modifier le mot de passe de la personne
    @FXML
    void Modifierpassword() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour modifier la date de naissance de la personne
    @FXML
    void Modifierdate() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour supprimer le compte de la personne
    @FXML
    void supprimercompte() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }

    // Méthode pour retourner à la page précédente
    @FXML
    void retour() {
        // La méthode est laissée vide car l'objectif est seulement d'afficher les informations
    }


}
