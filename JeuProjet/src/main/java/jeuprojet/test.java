package jeuprojet;

import java.util.ArrayList;
import java.util.Arrays;


public class test {
 
    	
    	
	static int size = 4;
	  static int maxDigit = 9;

            public static void main(String[] args)  {

         	   
            	
                ArrayList <String> allCombi = allPossibleCombinaisons();
                    System.out.println(allCombi);
                }


            private static int[] generateTab() {
                int[] ret = new int[size];
                for (int i = 0; i < size; i++) {
                    ret[i] = 0;
                }
                return ret;
            }

            private static int[] copyTab(int[] sol) {
                int[] ret = new int[size];
                for (int i = 0; i < size; i++) {
                    ret[i] = sol[i];
                }
                return ret;
            }

            private static boolean isCompletedTab(int[] sol) {
                for (int i = 0; i < sol.length; i++) {
                    if (sol[i] != maxDigit) {
                        return true;
                    }
                }
                return false;

            }

            private static int[] reaffect(int[] sol) {
                for (int i = sol.length - 1; i > 0; i--) {
                    if (sol[i] == maxDigit + 1) {
                        sol[i] = 0;
                        sol[i - 1]++;
                    }
                }
                return sol;
            }


            private static ArrayList <String> allPossibleCombinaisons() {
                ArrayList<String> possibleCombi = new ArrayList<>();
                int[] sol = generateTab();
                copyTab(sol);
                while (isCompletedTab(sol)) { // tant qu'on a pas des 9 a chaque element de la combi on continue la boucle
                    sol[sol.length - 1]++;
					if (sol[sol.length - 1] == maxDigit + 1) {
                        sol = reaffect(sol);
                    }
                    copyTab(sol);
                }
                for (int i = 0; i < possibleCombi.size(); i++) {
                    possibleCombi.add(Arrays.toString(sol));
                }
                return possibleCombi;
            }




        }