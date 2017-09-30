package jugp.hla.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Allele {
	
    private StringProperty alleleId;
    private StringProperty alleleName;
    private StringProperty nucSequence;
    private StringProperty releaseConfirmed;

    public Allele(String alleleId, String alleleName, String nucSequence, String releaseConfirmed) {
        this.setAlleleId(new SimpleStringProperty(alleleId));
        this.setAlleleName(new SimpleStringProperty(alleleName));
        this.setNucSequence(new SimpleStringProperty(nucSequence));
        this.setReleaseConfirmed(new SimpleStringProperty(releaseConfirmed));
    }

	public StringProperty getAlleleId() {
		return alleleId;
	}

	public void setAlleleId(StringProperty alleleId) {
		this.alleleId = alleleId;
	}

	public StringProperty getAlleleName() {
		return alleleName;
	}

	public void setAlleleName(StringProperty alleleName) {
		this.alleleName = alleleName;
	}

	public StringProperty getNucSequence() {
		return nucSequence;
	}

	public void setNucSequence(StringProperty nucSequence) {
		this.nucSequence = nucSequence;
	}

	public StringProperty getReleaseConfirmed() {
		return releaseConfirmed;
	}

	public void setReleaseConfirmed(StringProperty releaseConfirmed) {
		this.releaseConfirmed = releaseConfirmed;
	}
    



}
