package Jeu;

import Data.Message;
import Data.Observateur;
import Ui.*;
import java.math.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;

public class Controleur {
	private IHM ihm;
	private Monopoly monopoly;
        private int de1;
        private int de2;
        private Observateur observateur;
        
        
        public Controleur(IHM ihm, Monopoly monopoly) {
            this.ihm = ihm;
            this.monopoly = monopoly;
            this.de1 = 0;
            this.de2 = 0;
            ihm.afficher();
        }
        

	public void jouerUnCoup(Joueur aJ) {
            
            Message message = new Message();
            String choix;
            Carreau c = this.lancerDesAvancer(aJ);
            
            ihm.infoJoueur(aJ, de1, de2); // position, cash, somme lancés etc...
            
            if (c instanceof Gare){
               Joueur jProprio = ((Gare) c).getProprietaire();
               if (jProprio == null){
                   choix = ihm.choixPayer( aJ.getCash(), ((Gare) c).getPrixAchat() ); //payer ? (y/n)
                   message = ((Gare) c).action(aJ,(this.de1 + this.de2), choix); //si y , message.type = ACHAT, sinon message.type = PASSER
               }
               if (jProprio != null && jProprio != aJ) {
                   int l = ((Gare) c).calculLoyer(0);
                   aJ.payerLoyer(l);
                   jProprio.recevoirLoyer(l);
               }
            }   
            
            if (c instanceof Compagnie){
               Joueur jProprio = ((Compagnie) c).getProprietaire();
               if (jProprio == null){
                   choix = ihm.choixPayer( aJ.getCash(), ((Compagnie) c).getPrixAchat() );
                   ihm.choixPayer( aJ.getCash(), ((Compagnie) c).getPrixAchat() );
                   message = ((Compagnie) c).action(aJ,(this.de1 + this.de2), choix);
               }
               if (jProprio != null && jProprio != aJ) {
                   int l = ((Compagnie) c).calculLoyer(0);
                   aJ.payerLoyer(l);
                   jProprio.recevoirLoyer(l);
               }
            }
 
            if (c instanceof ProprieteAConstruire){
               Joueur jProprio = ((ProprieteAConstruire) c).getProprietaire();
               if (jProprio == null){
                   choix = ihm.choixPayer( aJ.getCash(), ((ProprieteAConstruire) c).getPrixAchat() );
                   ihm.choixPayer( aJ.getCash(), ((ProprieteAConstruire) c).getPrixAchat() );
                   message = ((ProprieteAConstruire) c).action(aJ,(this.de1 + this.de2), choix);
               }
               if (jProprio != null && jProprio != aJ) {
                   int l = ((ProprieteAConstruire) c).calculLoyer(0);
                   aJ.payerLoyer(l);
                   jProprio.recevoirLoyer(l);
               }
               
            }
               
            if (message.type == Message.Types.ACHAT_PROPRIETE) {
                ihm.achatEffectue(aJ.getCash());
            }
            else if (message.type == Message.Types.PASSER) {
                ihm.passer();
            }

      
            

	}
        
        public void jouerPlusieursTours(HashMap<Integer, Joueur> joueurs){
            
            int numJoueurGagnant=0;
            int nbJoueurs = joueurs.size();
            while (joueurs.size()>1) {
                Iterator<Map.Entry<Integer,Joueur>> iter = joueurs.entrySet().iterator();
                while (iter.hasNext()){          
                    Map.Entry<Integer, Joueur> entry = iter.next();
                   
                    if (nbJoueurs == 1){
                        numJoueurGagnant = entry.getKey();
                        break;
                    }
                    Integer i = entry.getKey();
                    Joueur j = entry.getValue();
                    jouerUnCoup(j);
                    if (entry.getValue().getCash() <= 0) {
                        entry.getValue().reinitProprietes();
                        iter.remove();
                        nbJoueurs = nbJoueurs - 1;
                        ihm.joueurSupprime(entry.getValue().getNom());
                    }



                    while (de1 == de2){
                        jouerUnCoup(j);
                    }
                    
                }
            }
            
            ihm.joueurAGagne(joueurs.get(numJoueurGagnant).getNom());
        }

	private Carreau lancerDesAvancer(Joueur aJ) {
		int d = this.lancerDes();
                Carreau cCour = aJ.getPosCourante();
                cCour = setNouveauCarreau(d, cCour);
                aJ.setCarreau(cCour);
                return cCour;
                
	}
        

	private int lancerDes() {
                int min = 1;
                int max = 7;
                this.setDe1((int)(Math.random() * (max-min)) + min);
                this.setDe2((int)(Math.random() * (max-min)) + min);
                return getDe1() + getDe2();
	}

	private Carreau setNouveauCarreau(int aD, Carreau cCour) {
		int num = cCour.getNumero();
                if( (num+aD)>40 ) {
                    num = num+aD-40;
                } else {
                    num = num+aD;
                }
                return getMonopoly().getCarreau(num);
                
	}

    /**
     * @return the de1
     */
    public int getDe1() {
        return de1;
    }

    /**
     * @param de1 the de1 to set
     */
    public void setDe1(int de1) {
        this.de1 = de1;
    }

    /**
     * @return the de2
     */
    public int getDe2() {
        return de2;
    }

    /**
     * @param de2 the de2 to set
     */
    public void setDe2(int de2) {
        this.de2 = de2;
    }

    /**
     * @return the monopoly
     */
    public Monopoly getMonopoly() {
        return monopoly;
    }

    /**
     * @return the observateur
     */
    public Observateur getObservateur() {
        return observateur;
    }

    /**
     * @param observateur the observateur to set
     */
    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }


}
