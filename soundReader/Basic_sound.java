package soundReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import sun.audio.*;

public class Basic_sound {

	//runs the handpan mapped from the text file
	public static void main(String[] args) throws Exception {
		playFile("maryLamb.txt");
	}

	/** Precondition: text in filename must not contain digits */
	private static void playFile(String filename)
	{
	  String path= "/Users/josephnechleba/Desktop/GenFold/Eclipse/Compsci/Java/sound/src/";
	  try
	  {
	    BufferedReader readIn = new BufferedReader(new FileReader(path + filename));
	    String line;
	    Integer[] arr= new Integer[26];//keeps track of letter frequency, init with 0's
	    Arrays.fill(arr, 0);
	    int offset= ThreadLocalRandom.current().nextInt(0, 25);//offset to create random sound mapping to letters
	    while ((line = readIn.readLine()) != null) {
	      int len= line.length();
	      //go through all the characters in this line, playing handpan sound (or silence)
	      for (int i=0; i<len; i++) {
	    	 char c= Character.toUpperCase(line.charAt(i));
	    	 int toNum= Character.getNumericValue(c);
	    	 //if the char read was a letter
	    	 if (toNum != -1) {
	    		 int fileIndex= (Character.getNumericValue(c) + offset) % 26;//maps letters to different sounds
	    		 arr[toNum - 10]++;//increments letter counter
		    	 try  {
		    		 //play handpan wave file letter is mapped to
		    		 String t_file= path + "chanced-" + fileIndex + ".wav";
		    		 InputStream in= new FileInputStream(t_file);
		    		 AudioStream audioStream= new AudioStream(in);
		    		 AudioPlayer.player.start(audioStream);
		    		 Thread.sleep(150);
		    	 }
		    	 catch (FileNotFoundException e) {
		    		
		    	 }	 
	    	 }
	    	//the char read was a symbol (i.e. comma, asterisk, period, question mark)
	    	//use probability to calculate if there should be silence
	    	 else {
	    		 double prob = ThreadLocalRandom.current().nextDouble(0.0, 1.00);
	    		 if (prob < .70) {
	 
	    		 }
	    		 else if (prob < .95) {
	    			 Thread.sleep(140); 
	    		 }
	    		 else {
	    			 Thread.sleep(420);
	    		 }
	    	 }
	      }
	    }
	    //print the frequency count of each of the letters in the text file
	    for (int j=0; j<26; j++) {
	    	System.out.println("Number of "+(char) (j + 97)+"'s is " + arr[j]);
	    }
	    readIn.close();
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	  }
	}
}
