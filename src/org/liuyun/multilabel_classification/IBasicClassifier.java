package org.liuyun.multilabel_classification;
import java.lang.String;
public interface IBasicClassifier {
	public String predict(String testData, String modelPath);
	public int train(String trainDataPath, String modelPath);
}
