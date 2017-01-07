package chapter4.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 例如map分片原理提高速度,的解决词梯
 * @author Administrator
 *
 */
public class MapAdjacentWords {
 public static Map<String,List<String>>computeAdjacentWords(List<String>words){
	 //存结果
	 Map<String,List<String>>adjWords=new TreeMap<String,List<String>>();
	 //先按长度分片
	 Map<Integer,List<String>>wordsByLength=new TreeMap<Integer,List<String>>();
	 
	 for(String word:words){
		 update(wordsByLength,word.length(),word);
	 }
	 //
	 for(Map.Entry<Integer, List<String>>entry:wordsByLength.entrySet()){
		 List<String>groupsWords=entry.getValue();
		 int groupNum=entry.getKey();
		 for(int i=0;i<groupNum;i++){
			 //再按照缺少一个字母 在临时分片 例如：word  ：ord，wrd,wod,wor来分
			 Map<String,List<String>>repToWord=new TreeMap<String,List<String>>();
			 for(String word:groupsWords){
				 String key=word.substring(0,i)+word.substring(i+1, word.length());
				 update(repToWord,key,word);
			 }
			 //更新结果
			 for(List<String>repWords:repToWord.values()){
				 if(repWords.size()>1){
					 for(String repWord:repWords){
						 for(String repWord2:repWords){
							 if(!repWord.equals(repWord2))
							 update(adjWords,repWord,repWord2);
						 }
					 }
				 }
			 }
		 }
	 }
	 return adjWords;
 }

private static<KeyType> void update(Map<KeyType, List<String>> map, KeyType key, String word) {
	List<String>list=	map.get(key);
	if(list==null){
		list=new ArrayList<String>();
		map.put(key, list);
	}
	list.add(word);
}
public static void main(String[] args) {
	String word="123";
	int i=0;
	System.out.println(word.substring(0,i)+":"+word.substring(i+1, word.length()));
}
}
