package org.liuyun.multilabel_classification;
import java.io.File;

import java.util.ArrayList;


public class MultiClassClassifier {
	private class LabelInfo
	{
	    public String labelName;
	    //public String labelModelPath;
	    public String trainDataPath;
	}
	public ArrayList<LabelInfo> LoadData(String dataPath)
	{
		ArrayList<LabelInfo> labelList = new ArrayList<LabelInfo>();
		
		File path=new File(dataPath);
		File[] fileList = path.listFiles();
		for (File f : fileList) {
			LabelInfo tmp = new LabelInfo();
			tmp.trainDataPath = f.getAbsolutePath();
			tmp.labelName = f.getName();
			labelList.add(tmp);			
		}		
		return labelList;		
	}
	public String MultiLabelTrain(String dataPath,String modelPath)
	{
		ArrayList<LabelInfo> labelList = LoadData(dataPath);
		for (LabelInfo labelInfo : labelList) {
			SingleClassClassifer sc = new SingleClassClassifer();
			sc.train(labelInfo.trainDataPath, modelPath+"/"+labelInfo.labelName);			
		}		
		return modelPath;
		
	}
	public static void main (String args[]) {
		MultiClassClassifier myClassifier = new MultiClassClassifier();
		myClassifier.MultiLabelTrain("/home/aa/文档/THUCNews/test/test", "/home/aa/文档/THUCNews/test/test");
	}
}
