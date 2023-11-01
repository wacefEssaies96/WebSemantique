package tn.esprit;

import java.util.Date;
import java.util.List;

public class Commentaire {
    private int id;
    private String contenu;
    private String typeContenu;
    private Date dateCreation;
    private List<String> emojis;

    public Commentaire(int id, String contenu, String typeContenu, Date dateCreation, List<String> emojis) {
        this.id = id;
        this.contenu = contenu;
        this.typeContenu = typeContenu;
        this.dateCreation = dateCreation;
        this.emojis = emojis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getTypeContenu() {
        return typeContenu;
    }

    public void setTypeContenu(String typeContenu) {
        this.typeContenu = typeContenu;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", typeContenu='" + typeContenu + '\'' +
                ", dateCreation=" + dateCreation +
                ", emojis=" + emojis +
                '}';
    }
}
