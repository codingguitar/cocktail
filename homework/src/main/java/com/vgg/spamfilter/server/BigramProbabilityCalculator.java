package com.vgg.spamfilter.server;


import com.vgg.spamfilter.common.SpamFilterConstants;

import java.io.*;
import java.math.RoundingMode;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class BigramProbabilityCalculator implements Runnable{
    BufferedReader reader;
    Socket client;
    private Map<String, Integer> bigramsHash = new HashMap<String, Integer>();
    private int bigramCount;
    private String probabilityScore="0";

    public BigramProbabilityCalculator(){}

    public BigramProbabilityCalculator(Socket client, BufferedReader reader){
        this.client = client;
        this.reader = reader;
    }

    public void run() {
        try {
            String line;
            StringBuilder data = new StringBuilder();
            //in case client goes non responsive the thread should not block but timeout
            client.setSoTimeout(SpamFilterConstants.SOCKET_TIMEOUT);
            while ((line = reader.readLine()) != null && !line.equals(SpamFilterConstants.CLIENT_EOF)){
                data.append(line).append(" ");
            }

            //calculate probability
            calculateProbability(data.toString());

            //construct an output string for the client
            String retVal = "Bigram Probability=" + probabilityScore + ", Bigram Count=" + bigramCount;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write(retVal);
            writer.write(SpamFilterConstants.NEW_LINE);
            writer.write(SpamFilterConstants.SERVER_EOF);
            writer.write(SpamFilterConstants.NEW_LINE);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failure in reading file data from client");
        }
    }


    public int getBigramCount() {
        return bigramCount;
    }

    public String getProbabilityScore() {
        return probabilityScore;
    }

    public void calculateProbability(String data) {

        //convert the data into bigrams
        bigramCount = generateBigrams(data);

        double p =0;
        double probabilityCount = 1;
        //iterate thorugh the hash map that contains the counts and multiply them
        for (Map.Entry<String, Integer> entry : bigramsHash.entrySet()){
            //since we have to use the number of occurances of a bigram everytime it occurs
            //we will just multiply the count by the number of occurances
            // for instance
            // if bigram count is 1 the contribution will be 1, so 1 * 1 = 1^1
            // if the bigram count is 2 it will be multiplied twice, once for each occurance so 2 * 2 = 2^2
            // if the bigram count is 3 it will be multiplied thrice, once for each occurance so 3 * 3 * 3 = 3^3
            probabilityCount *= Math.pow(entry.getValue(), entry.getValue());
        }
        double power = (double)1/bigramCount;
        p = Math.pow(probabilityCount, power);

        //format to 5 decimals
        if (p>0) {
            DecimalFormat formatter = new DecimalFormat("#.#####");
            formatter.setRoundingMode(RoundingMode.HALF_UP);
            probabilityScore = formatter.format(p);
        }

    }

    private int generateBigrams(String data) {

        //clean up and retain only numbers and digits. also replace new lines with space
        data = data.replaceAll("[^A-Za-z0-9 ]", "");
        data = data.replaceAll("\n", " ");

        //cleanup extra spaces and retain only one space
        data = data.replaceAll("\\s+", " ");

        String[] bigramArr = data.toString().split(" ");
        StringBuilder bigram = new StringBuilder();
        for (int i = 0; i < bigramArr.length; i++){
            if (i+1 < bigramArr.length) {
                bigram.setLength(0);
                bigram.append(bigramArr[i]).append(" ").append(bigramArr[i+1]);
                String key = bigram.toString();
                if (bigramsHash.containsKey(key)){
                    bigramsHash.put(key, bigramsHash.get(key) + 1);
                }
                else {
                    bigramsHash.put(key, 1);
                }
            }
        }
        return bigramArr.length - 1;
    }
}
