package org.liuyun.multilabel_classification;
import java.lang.String;
public interface IBasicClassifier {
	public int train(String trainDataPath);
	public int predict(String testDataPath, String model);
}
