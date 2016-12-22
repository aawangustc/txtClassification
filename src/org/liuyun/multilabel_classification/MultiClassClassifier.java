package org.liuyun.multilabel_classification;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.transform.Templates;

import org.apache.commons.lang.ObjectUtils.Null;


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
	public String MultiLabelPredict(String testDataPath,String modelPath) throws IOException, FileNotFoundException
	{
		String result = new String();
		//读取文档数据	
		
		
		String encoding = "utf-8";
		File testFile = new File(testDataPath);
		InputStreamReader read = new InputStreamReader(new FileInputStream(testFile),encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		String txt = new String();
		String t = null;
		while ((t = bufferedReader.readLine())!=null) {
			txt += t;			
		}
		
		//加载模型合集
		File modelPathInfo=new File(modelPath);
		File[] modelList = modelPathInfo.listFiles();
		
		for (File model : modelList) {
			
			SingleClassClassifer sc = new SingleClassClassifer();
			
			if(sc.predict(txt.trim(), model.getAbsolutePath()).equals("1"))
			{
				result+=model.getName();
			}
		}		
		return result;
	}
	public static void main (String args[])throws IOException, FileNotFoundException {
		MultiClassClassifier myClassifier = new MultiClassClassifier();
	//	myClassifier.MultiLabelTrain("C:\\Users\\AA\\git\\txtClassification\\testdata\\train", "C:\\Users\\AA\\git\\txtClassification\\testdata\\result");
		
		//测试结果
		File testPathInfo=new File("C:\\Users\\AA\\git\\txtClassification\\testdata\\test");
		File[] testClassList = testPathInfo.listFiles();
		int i=0;
		int j=0;
		for (File testClass : testClassList)
		{
			File[] testList = testClass.listFiles();
			String trueLabel = testClass.getName();
			for (File txt : testList)
			{
				String labels =new String();
				labels = myClassifier.MultiLabelPredict(txt.getAbsolutePath(), "C:\\Users\\AA\\git\\txtClassification\\testdata\\result");
				if (labels.equals(trueLabel))
					{i++;j++;}
			}			
			System.out.println(trueLabel+"正确个数为：");
			System.out.println(j);
			j=0;
		}
		
		//String labels =new String();
		//labels = myClassifier.MultiLabelPredict("/home/aa/文档/THUCNews/test/test/798977.txt", "/home/aa/文档/THUCNews/test/result");
		System.out.println("正确个数为：");
		System.out.println(i);
	}
}
