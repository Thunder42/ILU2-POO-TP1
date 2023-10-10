package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	private static class Marche { 
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i=0; i<etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal,Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int i = 0;
			boolean trouve = false;
			while (!trouve && i<etals.length) {
				Etal tal = etals[i];
				if (!tal.isEtalOccupe()) { 
					trouve = true;
				} else {
					i++;
				}
			}
			if (trouve) {
				return i;
			} else {
				return -1;
			}
		}
		
		public Etal[] trouverEtals(String produit) {
			int avecP = 0;
			for (int i=0; i<etals.length; i++) {
				Etal etal = etals[i];
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					avecP++;
				}
			}
			Etal[] etalsP = new Etal[avecP];
			int ind = 0;
			for (int j=0; j<etals.length; j++) {
				Etal etalp = etals[j];
				if (etalp.isEtalOccupe() && etalp.contientProduit(produit)) {
					etalsP[ind] = etals[j];
					ind++;
				}
			}
			return etalsP;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder message = new StringBuilder();
			int nbEtals = etals.length;
			boolean fini = false;
			int i = 0;
			while (!fini && i < nbEtals) {
				Etal etal = etals[i];
				if (etal.isEtalOccupe()) {
					message.append(etal.afficherEtal());
					i++;
				} else {
					fini = true;
				}
			}
			if (nbEtals-i > 0) {
				message.append("Il reste "+(nbEtals-i)+" étals non utilisés dans le marché.\n");
			}
			return message.toString();
		}
		
	}
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder message = new StringBuilder(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		int etalL = marche.trouverEtalLibre();
		if (etalL != -1) {
			message.append("Le vendeur "+vendeur.getNom()+" vend des "+ produit +" à l'étal n° "+(etalL+1)+".\n");
			marche.utiliserEtal(etalL, vendeur, produit, nbProduit);
		}
		return message.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder message = new StringBuilder("Les vendeurs qui proposent des "+ produit + " sont :\n");
		Etal[] etalsv = marche.trouverEtals(produit); //problème
		for (int i=0; i<etalsv.length; i++) {
			message.append("- "+((etalsv[i].getVendeur()).getNom())+"\n");
		}
		return message.toString();
	}
	
	public  Etal rechercherEtal(Gaulois vendeur) {
		Etal etalv = marche.trouverVendeur(vendeur);
		return etalv;
	}
	
	 public String partirVendeur(Gaulois vendeur) {
		 StringBuilder message = new StringBuilder();
		 Etal etalv = rechercherEtal(vendeur);
		 message.append(etalv.libererEtal());
		 return message.toString();
	 }
	 
	 public String afficherMarche() {
		 StringBuilder message = new StringBuilder();
		 message.append(marche.afficherMarche());
		 return message.toString();
	 }
}