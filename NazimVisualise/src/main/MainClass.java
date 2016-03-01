package main;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import dao.WordDao;
import model.Word;
import processing.core.PApplet;
import zemberek.morphology.apps.TurkishMorphParser;
import com.google.common.base.Joiner;
import org.antlr.v4.runtime.Token;
import zemberek.tokenizer.ZemberekLexer;

public class MainClass {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TurkishMorphParser parser;
		parser = TurkishMorphParser.createWithDefaults();
		Tokenize tokenize = new Tokenize(parser);
		
		ArrayList<String> kelimeListesi; 
		String cumle = "Düşmesin bizimle yola : evinde ağlayanların göz yaşlarını boynunda ağır bir zincir gibi taşıyanlar";
		String cumle2 = "Sen de çıkar göğsünün ağaçlıklı kafesinden yüreğini; şu güneşten düşen ateşe fırlat; yüreğini yüreklerimizin yanına at!";	
		String[] strArr = cumle2.split(" ");
		kelimeListesi = new ArrayList<String>( Arrays.asList(strArr));
		
		WordDao wordDao = new WordDao();
		ArrayList<Word> poemWords = new ArrayList<Word>(wordDao.getWordsOfAWork(6175));
		
		for(int i = 0; i<kelimeListesi.size(); i++){
			//System.out.println(tokenize.parse(kelimeListesi.get(i)));
			tokenize.parse(kelimeListesi.get(i));
		}
		
		for(int i = 0; i<poemWords.size(); i++){
			//System.out.println(tokenize.parse(kelimeListesi.get(i)));
			//tokenize.parse(poemWords.get(i).getText());
		}
		
		/*tokenIterator();
        System.out.println();
        simpleTokenization();*/
		
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationUI frame = new ApplicationUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});*/
		
		
	}
	
    public static void tokenIterator() {
        System.out.println("Low level tokenization iterator using Ant-lr Lexer.");
        ZemberekLexer lexer = new ZemberekLexer();
        String input = "İstanbul'a, merhaba!";
        System.out.println("Input = " + input);
        Iterator<Token> tokenIterator = lexer.getTokenIterator(input);
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            System.out.println("Token= " + token.getText() + " Type=" + token.getType());
        }
    }

    public static void simpleTokenization() {
        System.out.println("Simple tokenization returns a list of token strings.");
        ZemberekLexer lexer = new ZemberekLexer();
        String input = "İstanbul'a, merhaba!";
        System.out.println("Input = " + input);
        System.out.println("Tokenization list = " +
                Joiner.on("|").join(lexer.tokenStrings("İstanbul'a, merhaba!")));
    }

}
