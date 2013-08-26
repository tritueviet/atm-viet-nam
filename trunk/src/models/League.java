package models;
//Lien 21/05/2013
public class League {
	private int league_id;
	private int bank_id;
	private int co_bank_id;

	public int getLeague_id() {
		return league_id;
	}

	public void setLeague_id(int league_id) {
		this.league_id = league_id;
	}

	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public int getCo_bank_id() {
		return co_bank_id;
	}

	public void setCo_bank_id(int co_bank_id) {
		this.co_bank_id = co_bank_id;
	}

}
