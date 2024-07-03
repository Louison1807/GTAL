package model;

public class EnvoyerModel {
	private String idEnv;
	private String numEnvoyeur;
	private String numRecepteur;
	private int montant;
	private String date;
	private String raison; 	

	public String getIdEnv() {
		return idEnv;
	}

	public void setIdEnv(String idEnv) {
		this.idEnv = idEnv;
	}

	public String getNumEnvoyeur() {
		return numEnvoyeur;
	}

	public void setNumEnvoyeur(String numEnvoyeur) {
		this.numEnvoyeur = numEnvoyeur;
	}

	public String getNumRecepteur() {
		return numRecepteur;
	}

	public void setNumRecepteur(String numRecepteur) {
		this.numRecepteur = numRecepteur;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public EnvoyerModel() {
		// TODO Auto-generated constructor stub
	}

}
