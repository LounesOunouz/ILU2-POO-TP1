package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois g = new Gaulois("Obélix", 5);

//		Etal etal = new Etal();
//		Gaulois g = new Gaulois("Obélix", 5);

		try {
			String resultat = etal.acheterProduit(5, g); // étal vide
			System.out.println(resultat);
		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException capturée => " + e.getMessage());
		}

		System.out.println("Fin du test");
	}
}