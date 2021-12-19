package Package;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrizmulti {
	

	private static Scanner menu;

	public static void main(String[] args) throws IOException {
		String tamanho = "4x4";
		String pathA = "C:\\Users\\davic\\Downloads\\Matrizes\\A"+tamanho+".txt";
		String pathB = "C:\\Users\\davic\\Downloads\\Matrizes\\B"+tamanho+".txt"; 
		String pathC = "C:\\Users\\davic\\Downloads\\Matrizes\\C"+tamanho+".txt";
		
		int aux = 2;
		int auxMatriz = 0;
		
		int linha = Matrizmulti.lerValores(pathA).get(0);
		int coluna = Matrizmulti.lerValores(pathA).get(1);
		
		int linha2 = Matrizmulti.lerValores(pathB).get(0);
		int coluna2 = Matrizmulti.lerValores(pathB).get(1);
		
		int[][] matrizA = new int[linha][coluna];
		int[][] matrizB = new int[linha2][coluna2];
		
		
		int dm = matrizB.length;
		int[][] matrizC = new int[dm][dm];	
		
		Threads[] tm = new Threads[dm];
		
		menu = new Scanner (System.in);
		List<Integer> valoresPathA = Matrizmulti.lerValores(pathA);
		List<Integer> valoresPathB = Matrizmulti.lerValores(pathB);
		
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				matrizA[i][j] = valoresPathA.get(aux);
				matrizB[i][j] = valoresPathB.get(aux);
				aux++;
			}
		}
		
		System.out.print("##---------- Menu ----------##\n\n");
		System.out.print("|-----------------------------|\n");
		System.out.print("| Opção S - Sequencial        |\n");
		System.out.print("| Opção C - Concorrente       |\n");
		System.out.print("| Opção X - Sair              |\n");
		System.out.print("|-----------------------------|\n");
		System.out.print("Digite uma opção: ");
			
		String opcao = menu.nextLine();
			
			
		switch (opcao.toUpperCase()) {
			case "S":
				long startTimeParalela = System.currentTimeMillis();
				System.out.println("Opção Sequencial Selecionado \n");
				//MatrizA X MatrizB
				if(linha == coluna2) {
					for (int i = 0; i < linha; i++) {
						for (int j = 0; j < coluna; j++) {
							for (int k = 0; k < linha2; k++) {
								matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
							}
						}
					}
					} else {
						System.out.println("Não é possivel realizar a multiplicação");
					}
					imprimirMatriz(matrizC);
					System.out.println("");
					Matrizmulti.escreverValores(pathC, matrizC);
			
					long endTimeParalela  = System.currentTimeMillis();
					long resultTimeParalela  = endTimeParalela - startTimeParalela;
					System.out.print("Sequencial C" + tamanho + " Time: " + resultTimeParalela  + "ms");
					break;
					
				case "C":
					long startTimeConcorrente = System.currentTimeMillis();
					System.out.println("Opção Concorrente Selecionado\n");
						for (int i = 0; i < dm; i++) {
							tm[auxMatriz] = new Threads("Thread" + i, dm, i, matrizA, matrizB, matrizC);
							tm[auxMatriz].start();
							auxMatriz++;
						}
						
						for (int i = 0; i < dm ; i++) {		
							try {
								tm[i].join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}	
						}
						
				
						Matrizmulti.escreverValores(pathC, matrizC);
						imprimirMatriz(matrizC);
					
					long endTimeConcorrente = System.currentTimeMillis();
					long resultTimeConcorrente = endTimeConcorrente - startTimeConcorrente;
					System.out.println("\nConcorrente C" + tamanho + " Time: " + resultTimeConcorrente  + "ms");
					break;
				
				default:
					System.out.print("\nOpção Inválida!");
					main(args);
					break;
					
				case "X":
					System.out.print("\nPrograma finalizado!!!");	
					return;		
			}
		
	}
	
	// imprimir matriz
	public static void imprimirMatriz(int matriz[][]) {
		 for (int l = 0; l < matriz.length; l++)  {  
		       for (int c = 0; c < matriz[0].length; c++) {
		           System.out.print(matriz[l][c] + " "); //imprime caracter a caracter
		       }  
		     System.out.println(" "); //muda de linha
		  } 
	}
	
	public static Stream<Integer> separarValores(String s) {
		return Stream.of(s.split(" ")).map(Integer::parseInt);
	}
	
	public static List<Integer> lerValores(String path) throws IOException {
		Stream<String> linhasArquivo = Files.readAllLines(new File(path).toPath()).stream();
		return linhasArquivo.flatMap(Matrizmulti::separarValores).collect(Collectors.toList());
	}
	
	
	public static void escreverValores(String path, int[][] matriz) throws IOException {
		File arquivo = new File(path);
		boolean existe = arquivo.exists();
		
		if(existe != true) {
			arquivo.createNewFile();
			PrintWriter pw = new PrintWriter(arquivo);
			for (int l = 0; l < matriz.length; l++)  {  
			       for (int c = 0; c < matriz[0].length; c++) { 
			    	   pw.write(matriz[l][c] + " "); 
			       }  
			    pw.println(" ");
			 }
			pw.close();
		 } else {
			arquivo.delete();
			escreverValores(path, matriz);
		}
	}
	
}
