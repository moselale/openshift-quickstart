package org.acme.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ANA2_OPERATORI
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="ANA2_OPERATORI.findAll", query="SELECT o FROM ANA2_OPERATORI o"),
	@NamedQuery(name="ANA2_OPERATORI.findByCid", query="SELECT o FROM ANA2_OPERATORI o WHERE o.CID = :cid"),
	@NamedQuery(name="ANA2_OPERATORI.findTeamLeader", query="SELECT o.TEAMLEADER FROM ANA2_OPERATORI o WHERE o.CID= :firsUserCid")
})
public class ANA2_OPERATORI implements Serializable {

	   
	@Id
	private String CID;
	private String NOME;
	private String COGNOME;
	private String EMAIL;
	private String MANSIONE;
	private boolean TEAMLEADER;
	private static final long serialVersionUID = 1L;

	public ANA2_OPERATORI() {
		super();
	}   
	public String getCID() {
		return this.CID;
	}

	public void setCID(String CID) {
		this.CID = CID;
	}   
	public String getNOME() {
		return this.NOME;
	}

	public void setNOME(String NOME) {
		this.NOME = NOME;
	}   
	public String getCOGNOME() {
		return this.COGNOME;
	}

	public void setCOGNOME(String COGNOME) {
		this.COGNOME = COGNOME;
	}   
	public String getEMAIL() {
		return this.EMAIL;
	}

	public void setEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}   
	public String getMANSIONE() {
		return this.MANSIONE;
	}

	public void setMANSIONE(String MANSIONE) {
		this.MANSIONE = MANSIONE;
	}   
	public boolean getTEAMLEADER() {
		return this.TEAMLEADER;
	}

	public void setTEAMLEADER(boolean TEAMLEADER) {
		this.TEAMLEADER = TEAMLEADER;
	}
   
}
