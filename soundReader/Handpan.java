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

public class Handpan {

	public static void main(String[] args) throws Exception {
		Integer[] freqCounter = playFile("maryLamb.txt");
		//print the frequency count of each of the letters in the text file
	    for (int j=0; j<26; j++) {
	    	System.out.println("Number of "+(char) (j + 97)+"'s is " + freqCounter[j]);
	    }
	}

	/** PlayFile assigns each letter of the alphabet a unique handpan sound. It then takes
	 *  in a text file (Precondition: text may not contain digits; 
	 *  must be made of letters and punctuation marks) and, as the text file
	 *  is scanned, plays the corresponding sounds. The result is a handpan "solo" with each sounds
	 *  frequency determined by the number of its mapped letter within the text file. I 
	 *  was curious to see if the letter frequency within the English language gave the Handpan sounds
	 *  a coherent structure. The output of playFile is played through the 
	 *  computers speakers using Java's soundReader library. */
	
	@SuppressWarnings("restriction")
	private static Integer[] playFile(String filename) {
	  //absolute path
	  String path= "/Users/josephnechleba/Desktop/GenFold/Eclipse/Compsci/Java/sound/src/";
	  Integer[] freqCounter= new Integer[26];//keeps track of letter frequency, init with 0's
	  Arrays.fill(freqCounter, 0);
	  try {
	    BufferedReader readIn = new BufferedReader(new FileReader(path + filename));
	    /*this offset is used to have varying sound mappings to letters. 
	    For example, if the offset is different in two different runs of the
	    program, then the letter 'e' will have two different handpan sounds assigned to it */
	    int offset= ThreadLocalRandom.current().nextInt(0, 25);
	    String line; //current line being read in the text file
	    //loop through entire text file
	    while ((line = readIn.readLine()) != null) {
	      int len= line.length();
	      //for each character in this line, play its corresponding handpan sound (or silence)
	      for (int i=0; i<len; i++) {
	    	 char c= Character.toUpperCase(line.charAt(i));
	    	 int toNum= Character.getNumericValue(c);
	    	 //if the char read was a letter
	    	 if (toNum != -1) {
	    		 //use offset so that letter's have a new arrangement of sound mappings
	    		 int fileIndex= (Character.getNumericValue(c) + offset) % 26;
	    		//this letter has been seen
	    		 freqCounter[toNum - 10]++;
		    	 try {
		    		 //play handpan wave file letter is mapped to
		    		 String t_file= path + "chanced-" + fileIndex + ".wav";
		    		 InputStream in= new FileInputStream(t_file);
		    		 AudioStream audioStream= new AudioStream(in);
		    		 AudioPlayer.player.start(audioStream);
		    		 //wait before playing the next sound
		    		 Thread.sleep(130);
		    	 }
		    	 catch (FileNotFoundException e) {
		    		System.out.println("Error: file not found");
		    	 }	 
	    	 }
	    	//the char read was a symbol (i.e. comma, asterisk, period, question mark)
	    	//"throw dice" to calculate if there should be silence
	    	 else {
	    		 double prob = ThreadLocalRandom.current().nextDouble(0.0, 1.00);
	    		 if  (prob > .70 && prob < .95) {
	    			 Thread.sleep(140); 
	    		 }
	    		 else if (prob < .20) {
	    			 Thread.sleep(420);
	    		 }
	    	 }
	      }
	    }
	    readIn.close();
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	  }
	return freqCounter;
	}
}
