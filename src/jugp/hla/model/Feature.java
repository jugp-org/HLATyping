package jugp.hla.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Feature {

/*	
	private String featureName;
	private String featureStatus;
	private int seqCoordinatesStart;
	private int seqCoordinatesEnd;
	private int seqLength;
	private String featureSequence;
	
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getFeatureStatus() {
		return featureStatus;
	}
	public void setFeatureStatus(String featureStatus) {
		this.featureStatus = featureStatus;
	}
	public int getSeqCoordinatesStart() {
		return seqCoordinatesStart;
	}
	public void setSeqCoordinatesStart(int seqCoordinatesStart) {
		this.seqCoordinatesStart = seqCoordinatesStart;
	}
	public int getSeqCoordinatesEnd() {
		return seqCoordinatesEnd;
	}
	public void setSeqCoordinatesEnd(int seqCoordinatesEnd) {
		this.seqCoordinatesEnd = seqCoordinatesEnd;
	}
	public int getSeqLength() {
		return seqLength;
	}
	public void setSeqLength(int seqLength) {
		this.seqLength = seqLength;
	}
	public String getFeatureSequence() {
		return featureSequence;
	}
	public void setFeatureSequence(String featureSequence) {
		this.featureSequence = featureSequence;
	}
*/
	
	private StringProperty featureName;
	private StringProperty featureStatus;
	private StringProperty featureSequence;
	private StringProperty featureType;
	
	/*
	private IntegerProperty seqCoordinatesStart;
	private IntegerProperty seqCoordinatesEnd;
	private IntegerProperty seqLength;
*/

    public Feature(String featureName, String featureStatus, String featureSequence, String featureType) {
        this.featureName = new SimpleStringProperty(featureName);
        this.featureStatus = new SimpleStringProperty(featureStatus);
        this.featureSequence = new SimpleStringProperty(featureSequence);
        this.featureType = new SimpleStringProperty(featureType);
    }
	
	public StringProperty getFeatureName() {
		return featureName;
	}
	public void setFeatureName(StringProperty featureName) {
		this.featureName = featureName;
	}
	public StringProperty getFeatureStatus() {
		return featureStatus;
	}
	public void setFeatureStatus(StringProperty featureStatus) {
		this.featureStatus = featureStatus;
	}
	public StringProperty getFeatureSequence() {
		return featureSequence;
	}
	public void setFeatureSequence(StringProperty featureSequence) {
		this.featureSequence = featureSequence;
	}

	public boolean getActive() {
		if (this.featureType.get() == "Exon") {
			return true;
		}else {
			return true;
		}
	}

	public StringProperty getFeatureType() {
		return featureType;
	}

	public void setFeatureType(StringProperty featureType) {
		this.featureType = featureType;
	}
	
/*	
	public int getSeqCoordinatesStart() {
		return seqCoordinatesStart.get();
	}
	public void setSeqCoordinatesStart(int seqCoordinatesStart) {
		this.seqCoordinatesStart.set(seqCoordinatesStart);
	}
	public int getSeqCoordinatesEnd() {
		return seqCoordinatesEnd.get();
	}
	public void setSeqCoordinatesEnd(int seqCoordinatesEnd) {
		this.seqCoordinatesEnd.set(seqCoordinatesEnd);
	}
	public int getSeqLength() {
		return seqLength.get();
	}
	public void setSeqLength(int seqLength) {
		this.seqLength.set(seqLength);
	}
*/	

	
}
