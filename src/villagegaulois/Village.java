package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbetals);

	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
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

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef !");
		}

		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal - 1].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			int retour = -1;
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe() && retour == -1) {
					retour = i;
				}
			}
			return retour + 1;
		}

		private Etal[] trouverEtals(String produit) {
			int cpt = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					cpt++;
				}
			}
			Etal[] etal = new Etal[cpt];
			int j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etal[j] = etals[i];
					j++;
				}
			}
			return etal;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}

			}
			return null;
		}

		private String afficherMarche() {
			int cpt = 0;
			String s = "";
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					cpt++;
					s = etals[i].afficherEtal();
				}
			}
			if (cpt != etals.length) {
				s = "Il restre " + (etals.length - cpt) + " étals non utilisés dans le marché.\n";
			}
			return s;

		}
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le vendeur ");
		chaine.append(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append("\nLe vendeur ");
		chaine.append(vendeur.getNom());
		chaine.append(" vend des fleurs à l'étal n: ");
		chaine.append(marche.trouverEtalLibre());
		chaine.append("\n");
//		marchse.trouverEtalLibre();
		marche.utiliserEtal(marche.trouverEtalLibre(), vendeur, produit, nbProduit);
//		chaine.append("\nL'étal libre maintenet est : ");
//		chaine.append(marche.trouverEtalLibre());

		return chaine.toString();

	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etal = marche.trouverEtals(produit);
		if (etal.length > 1) {

			chaine.append("Les vendeurs qui proposent des ");
			chaine.append(produit);
			chaine.append(" sont :");
			for (int i = 0; i < etal.length; i++) {
				chaine.append("\n-");
				chaine.append((etal[i].getVendeur()).getNom());
			}

		}
		if (etal.length == 1) {
			chaine.append("Seul le vendeur ");
			chaine.append(etal[0].getVendeur().getNom());
			chaine.append(" propose des ");
			chaine.append(produit);
			chaine.append(" au marché\n");
		}
		if (etal.length <= 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des ");
			chaine.append(produit);
			chaine.append(" au marché.\n");
		}

		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etal = new Etal();
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i].getVendeur() == vendeur) {
				etal = marche.etals[i];
			}
		}

		return etal;
	}

	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i].getVendeur() == vendeur) {
				chaine.append(marche.etals[i].libererEtal());
			}
		}

		return chaine.toString();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + getNom() + "possède plusieurs étals: \n");
		for (int i = 0; i < marche.etals.length - 1; i++) {
			chaine.append("\n" + (marche.etals[i].getVendeur()).getNom());
			chaine.append(" vend");

			chaine.append(marche.etals[i].getQuantite() + " ");
			chaine.append(marche.etals[i].getProduit() + "\n");
		}
		chaine.append(marche.afficherMarche());

		return chaine.toString();
	}

}