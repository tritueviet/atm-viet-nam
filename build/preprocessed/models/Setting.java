package models;
//Lien 21/05/2013
public class Setting {
	private int setting_id;
	private int bank_id;
	private String isSettingBank;

	public int getSetting_id() {
		return setting_id;
	}

	public void setSetting_id(int setting_id) {
		this.setting_id = setting_id;
	}

	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public String getIsSettingBank() {
		return isSettingBank;
	}

	public void setIsSettingBank(String isSettingBank) {
		this.isSettingBank = isSettingBank;
	}

}
