package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.WorkLine;
import dao.WorkLineDao;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class WordCloud extends PApplet{
	PFont font;
	String fontFile = "ArialRoundedMTBold-96.vlw";
	int fSize = 96;
	int maxSize = 96;
	int minSize = 12;
	String wordFile = "processing.txt";
	 
	String[] words;
	int[]  count;
	int most;
	int least;
	float currentSize;
	int currentIndex;
	
	int workId;
	
	public WordCloud(int width,int height,int workId){
		this.workId = workId;
		displayWidth = width;
		displayHeight = height;
	}
	 
	public void setup(){
	  size(displayWidth, displayHeight);
	  colorMode(HSB, TWO_PI, 1, 1, 1);
	  rectMode(CORNER);
	  background(color(0, 0, 1));
	  smooth();
	  font = createFont("segoepr.ttf", 96);//loadFont(fontFile);
	  initializeWords(); 
	  noLoop();
	 
	}
	 
	public void draw() {
		//background(226,225,225);
		
	  while(currentIndex < words.length) {
	    float relsize = map(count[currentIndex],least,most,minSize,maxSize);
	    boolean drawn = false; 
	    while (!drawn) {
	      drawn = drawWord(words[currentIndex], relsize);
	      if (!drawn)
	       println("redrawing "+words[currentIndex]);
	        relsize = (float) (relsize * 0.95);
	    }
	    currentIndex++;
	  } 
	}
	 
	boolean drawWord(String word, float wordSize) {
	  int intSize = (int)wordSize;
	  textFont(font, wordSize);
	  int w = (int) (textWidth(word));
	  PGraphics g = createGraphics(w, intSize);
	  g.beginDraw();
	  g.background(color(0, 0, 1, 0));
	  g.fill(color(0,0,0));
	  g.textAlign(CENTER, CENTER);
	  g.translate(w/2, wordSize/2);
	  g.scale(wordSize / fSize);
	  g.textFont(font);
	  g.text(word, 0, 0);
	  g.endDraw();
	 
	  PGraphics gMask = createGraphics(w, intSize);
	  gMask.beginDraw();
	  //gMask.background(color(0, 0, 1, 1));
	  gMask.image(g, 0, 0);
	  gMask.filter(ERODE); 
	  gMask.filter(ERODE);
	  gMask.endDraw();
	   
	  for (int tries=50; tries>0; tries--) {
	    int x = (int)random(width-w);
	    int y = (int)random(height-intSize);
	     
	    boolean fits = true;
	    for (int dx = 0; dx< w && fits; dx++) {
	      for (int dy = 0; dy<intSize && fits; dy++) {
	        if (brightness(gMask.get(dx, dy))<0.5) {
	          if (brightness(get(x+dx, y+dy))<0.5) {
	            fits = false;
	          }
	        }
	      }
	    }
	    if (fits) {
	      image(g, x, y);
	      return true;
	    }
	  }
	  return false;
	}
	 
	/*boolean equalColor(color c1, color c2) {
	  String h1 = hex(color(c1));
	  String h2 = hex(color(c2));
	  return h1.equals(h2);
	}*/
	 
	
	String[] getLineOfWork(){
		WorkLineDao dao = new WorkLineDao();
		List<WorkLine> lines = new ArrayList<WorkLine>();
		try {
			lines = dao.getWorkLineOfAWork(workId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] returnArray = new String[lines.size()];
		for(int i=0; i<lines.size(); i++){
			returnArray[i] = lines.get(i).getLine();
		}
		return returnArray;
	}
	 
	void initializeWords() {
	  /*ArrayList ignore = new ArrayList();
	  String[] ignoreStrs  = loadStrings("ignore.txt");
	  for (int i = 0; i < ignoreStrs.length; i++) {
	    ignore.add(ignoreStrs[i].trim().toUpperCase());
	  }*/
	  HashMap wordcount = new HashMap();
	  //String[] lines = loadStrings(wordFile);
	  String[] lines = getLineOfWork();
	  for (int i = 0; i < lines.length; i++) {
	    String[] words = split(lines[i], " "); 
	    for (int j = 0; j < words.length; j++)  {
	      String word = clean(words[j]).toUpperCase();
	      if (word.length() == 0) {
	        continue;
	      }
	     /* if (ignore.contains(word)) {
	        continue;
	      }*/
	      Integer count = (Integer)wordcount.get(word);
	      if (count == null) {
	         wordcount.put(word, new Integer(1));
	       }
	       else {
	         int newCount = count.intValue() + 1;
	         wordcount.put(word, new Integer(newCount));
	       }
	    }
	  }
	  words = new String[wordcount.size()];
	  count = new int[wordcount.size()];
	  int idx = 0;
	  Iterator it = wordcount.entrySet().iterator();  // Get an iterator
	  while (it.hasNext()) {
	      Map.Entry me = (Map.Entry)it.next();
	      words[idx] = (String)me.getKey();
	      count[idx] = ((Integer)(me.getValue())).intValue();
	      idx++;
	  }
	  sortWords();
	  String[] sorted = new String[words.length];
	  for (int i = 0; i < words.length; i++) {
	    sorted[i] = count[i]+" "+words[i];
	  }
	  most = count[0];
	  least = count[count.length-1];
	  //saveStrings("totals.txt", sorted);
	 
	}
	 
	String clean(String word) {
	  word = word.trim();
	  if (word.endsWith(".") || word.endsWith(",") || word.endsWith(";"))
	    word = word.substring(0, word.length() - 1);
	  return word.trim();   
	}
	 
	 
	void sortWords() {
	  boolean changed = true;
	  while (changed) {
	    boolean madeChange = false;
	    for (int i = 0; i < count.length-1; i++) {
	      if (count[i] < count[i+1]) {
	        int temp = count[i];
	        String tempW = words[i];
	        count[i] = count[i+1];
	        words[i] = words[i+1];
	        count[i+1] = temp;
	        words[i+1] = tempW;
	        madeChange = true;
	      }
	    }
	    changed = madeChange;
	  }
	}

}
