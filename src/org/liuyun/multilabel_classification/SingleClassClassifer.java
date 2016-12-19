package org.liuyun.multilabel_classification;

import org.thunlp.text.classifiers.BasicTextClassifier;
import org.thunlp.text.classifiers.ClassifyResult;
import org.thunlp.text.classifiers.LinearBigramChineseTextClassifier;

public class SingleClassClassifer implements IBasicClassifier {

	@Override
	public int train(String trainDataPath,String modelPath) {
		// TODO Auto-generated method stub
		// 新建分类器对象
		BasicTextClassifier classifier = new BasicTextClassifier();
		
		// 设置参数
		String defaultArguments = ""
			 + "-train " +trainDataPath   // 设置您的训练路径，这里的路径只是给出样例
			 + " -d1 0.5 "  // 前50%用于训练
			 + "-f 35000 " // 设置保留特征数，可以自行调节以优化性能
			+  "-s " +modelPath // 将训练好的模型保存在硬盘上，便于以后测试或部署时直接读取模型，无需训练
			 ;		
		// 初始化
		classifier.Init(defaultArguments.split(" "));
		classifier.runAsBigramChineseTextClassifier();		
		return 0;
	}

	@Override
	public String predict(String testData, String modelPath) {
		// TODO Auto-generated method stub
		BasicTextClassifier classifier = new BasicTextClassifier();
		
		// 设置分类种类，并读取模型
		classifier.loadCategoryListFromFile(modelPath+"/category");
		classifier.setTextClassifier(new LinearBigramChineseTextClassifier(classifier.getCategorySize()));
		classifier.getTextClassifier().loadModel("modelPath");
		
		//对字符串进行分类
		ClassifyResult[] result = classifier.classifyText(testData,1);
		return classifier.getCategoryName(result[0].label);
	}
	

}
