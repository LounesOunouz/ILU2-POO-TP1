package histoire;

import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			etal.libererEtal();
		} catch (IllegalStateException e) {
			System.out.println("Gestion d'erreur faite ! ");
		}
		System.out.println(etal.acheterProduit(5, null));
		System.out.println("Fin du test");
	}
}
//reste b+C +2+village 