package Jeu;



import java.util.ArrayList;
import javax.swing.text.Position;

public class Joueur {
	private String nomJoueur;
	private int cash;
	public ArrayList<Gare> gares;
        public ArrayList<Compagnie> compagnies;
        private ArrayList<ProprieteAConstruire> propietes_a_construire;
	private Carreau positionCourante;
        
        
        public Joueur(String nom, Carreau depart) {
            nomJoueur = nom;
            this.cash = 1500;
            gares = new ArrayList<Gare>();
            compagnies = new ArrayList<Compagnie>();
            propietes_a_construire = new ArrayList<ProprieteAConstruire>();
            this.setCarreau(depart);
        }
        
        public String getNom(){
            return this.nomJoueur;
        }
        

	public void payerLoyer(int aL) {
            this.cash =- aL;
	}

	public void recevoirLoyer(int aL) {
            this.cash =+ aL;
	}

	public int getCash() {
		return this.cash;
	}
        
        public void diminuerCash(int val){
            this.cash =- val;
        }

	public Carreau getPosCourante() {
		return positionCourante;
	}

	public void setCarreau(Carreau aCCour) {
		this.positionCourante = aCCour;
	}
        
        public void addGare(Gare g){
            this.gares.add(g);
        }
        
        public void addCompagnie(Compagnie c){
            this.compagnies.add(c);
        }
        
        public void addProprieteAConstruire(ProprieteAConstruire p){
            this.getPropietes_a_construire().add(p);
        }
        
        public int getNbGare(){
            return gares.size();
        }
        public int getNbCompagnies(){
            return compagnies.size();
        }
        public int getNbProprietes(){
            return getPropietes_a_construire().size();
        }

    /**
     * @return the propietes_a_construire
     */
    public ArrayList<ProprieteAConstruire> getPropietes_a_construire() {
        return propietes_a_construire;
    }
        
        
}