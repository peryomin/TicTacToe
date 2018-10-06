package main.java.com.peryomin.tictactoe.minimax;

import main.java.com.peryomin.tictactoe.Game;
import main.java.com.peryomin.tictactoe.players.AI;
import main.java.com.peryomin.tictactoe.players.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Evolution {
    private int coeffNum;
    private int populationSize;
    private int gamesPerRound;
    private List<Gen> population;
    private Random r;
    private String fileName;
    private static String coeffSeparator = "\t";

    public Evolution(int coeffNum, int populationSize, int gamesPerRound) {
        this.coeffNum = coeffNum;
        this.populationSize = populationSize;
        this.gamesPerRound = gamesPerRound;
        population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            population.add(new Gen(coeffNum));
        }
        r = new Random();
        fileName = "gens.txt";
    }

    public void initCoeffs() {
        for (Gen gen: population) {
            for (int i = 0; i < coeffNum; i++) {
                gen.randomInit();
            }
        }
    }

    private void mutate(int size) {
        int n = population.size();
        for (int i = 0; i < size; i++) {
            Gen from = population.get(i);
            Gen to = population.get(n - i - 1);
            to.setCoeffs(from.getMutatedCoeffs());
            to.generation = 1;
        }
    }

    private void playRound(EvaluationState crossEvalFunc, VladsEval zeroEvalFunc) {
        Player crossPlayer = new AI(crossEvalFunc);
        Player zeroPlayer = new AI(zeroEvalFunc);

        for (int i = 0; i < population.size() - 1; i++) {
            for (int j = i + 1; j < population.size(); j++) {
                Gen crossGen = population.get(i);
                Gen zeroGen = population.get(j);
                crossEvalFunc.setCoeffs(crossGen.coeffs);
                //zeroEvalFunc.setCoeffs(zeroGen.coeffs);
                Game game = new Game(crossPlayer, zeroPlayer);

                int result = game.playGame(100, false);

                if (result == 3) {
                    crossGen.score += 1;
                    zeroGen.score += 1;
                } else if (result == 1) {
                    crossGen.score += 3;
                } else if (result == 2) {
                    zeroGen.score += 3;
                }
            }
        }

        for (Gen gen : population) {
            gen.generation++;
        }
    }

    public void start() {
        // random init / read from file
        initDataSource();
        EvaluationState crossEvalFunc = new EvaluationState();
        VladsEval zeroEvalFunc = new VladsEval();
        int roundCounter = 0;

        while(true) {
            roundCounter++;

            copyFile("round" + roundCounter + "gen.txt");

            // play round
            long beforeRoundTime = System.currentTimeMillis();
            playRound(crossEvalFunc, zeroEvalFunc);
            long afterRoundTime = System.currentTimeMillis();

            System.out.println("The round " + roundCounter + " is over.");
            System.out.println("Time for round: " + ((afterRoundTime - beforeRoundTime) / 1000.0));
            writeToLog("Time for round " + roundCounter + ": "
                    + ((afterRoundTime - beforeRoundTime) / 1000.0) + " sec");

            // sort gens
            population.sort((x, y) -> y.score - x.score);

            // mutate (get rid of 20% of the population)
            mutate((int) (0.2f * population.size()));

            // write to file
            updateDataSource();

            // reset score
            for (Gen gen : population) {
                gen.score = 0;
            }
        }
    }

    private void initDataSource() {
        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                initCoeffs();

                writeToFile(population);
            } else {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufRead = new BufferedReader(fileReader);
                String line;

                population.clear();
                while ((line = bufRead.readLine()) != null) {
                    String[] fileCoeffs = line.split(coeffSeparator);
                    int[] coeffs = new int[coeffNum];

                    for (int i = 0; i < fileCoeffs.length - 2; i++) {
                        coeffs[i] = Integer.parseInt(fileCoeffs[i]);
                    }
                    Gen gen = new Gen(coeffNum);
                    gen.setCoeffs(coeffs);
                    gen.generation = Integer.parseInt(fileCoeffs[fileCoeffs.length - 2]);
                    gen.score = Integer.parseInt(fileCoeffs[fileCoeffs.length - 1]);
                    population.add(gen);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDataSource() {
        File file = new File(fileName);

        file.delete();
        try {
            file.createNewFile();
            writeToFile(population);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(List<Gen> population) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(fileName));
        for (Gen gen : population) {
            fileWriter.write(gen.toString());
            fileWriter.write(System.getProperty("line.separator"));
        }
        fileWriter.flush();
        fileWriter.close();
    }

    private void writeToLog(String line) {
        try {
            File log = new File("log.txt");
            log.createNewFile();
            Files.write(log.toPath(), (line + System.getProperty("line.separator")).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(String copyFileName) {
        try {
            Files.copy(new File(fileName).toPath(),
                    (new File(copyFileName).toPath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Gen {
        int[] coeffs;
        int score;
        int generation;

        Gen(int coeffNum) {
            coeffs = new int[coeffNum];
            score = 0;
            generation = 1;
        }

        void randomInit() {
            Random r = new Random();
            for (int i = 0; i < coeffs.length; i++) {
                coeffs[i] = r.nextInt(200) - 100;
            }
        }

        void setCoeffs(int[] coeffs) {
            this.coeffs = coeffs;
        }

        public int[] getMutatedCoeffs() {
            int[] coeffsCopy = new int[coeffs.length];
            for (int i = 0; i < coeffs.length; i++) {
                coeffsCopy[i] = coeffs[i];
            }

            Random r = new Random();
            for (int i = 0; i < coeffs.length; i++) {
                coeffsCopy[i] += r.nextInt(20) - 10;
            }

            return coeffsCopy;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();

            for (int coeff: coeffs) {
                result.append(coeff).append(coeffSeparator);
            }
            result.append(generation).append(coeffSeparator);
            result.append(score);

            return result.toString();
        }
    }
}