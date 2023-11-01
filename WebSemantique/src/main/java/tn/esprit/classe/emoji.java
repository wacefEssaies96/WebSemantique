package tn.esprit.classe;

public class emoji {
	  private int id;
	    private String symbole;
	    private String categorie;
	    
	    
	    
	    
		public emoji(int id, String symbole, String categorie) {
			super();
			this.id = id;
			this.symbole = symbole;
			this.categorie = categorie;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSymbole() {
			return symbole;
		}
		public void setSymbole(String symbole) {
			this.symbole = symbole;
		}
		public String getCategorie() {
			return categorie;
		}
		public void setCategorie(String categorie) {
			this.categorie = categorie;
		}
}
