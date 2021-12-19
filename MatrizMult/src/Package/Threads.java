package Package;


public class Threads extends Thread {
	
    protected String name;
    protected int[][] matrizA;
    protected int[][] matrizB;
    protected int[][] matrizC;
	
	protected int linha;
	protected int dm;
	
	public Threads(String name,int dm, int linha, int[][] matrizA, int[][] matrizB ,int[][] matrizC) {
		this.name = name;
		this.linha = linha;
		this.dm = dm;
		this.matrizA = matrizA;
		this.matrizB = matrizB;
		this.matrizC = matrizC;
	}

	public void run() {
		
		for(int j = 0; j < dm; j++) {
			for(int k = 0; k < dm; k++) {
				matrizC[linha][j] += matrizA[linha][k] * matrizB[k][j];
			}
		}
			
	
//		matrizC[0][0] = (matrizA[0][0] * matrizB[0][0]) + (matrizA[0][i] * matrizB[i][0]);
//		matrizC[0][1] = (matrizA[0][0] * matrizB[0][i]) + (matrizA[0][i] * matrizB[i][i]);
//		matrizC[1][0] = (matrizA[0][0] * matrizB[0][0]) + (matrizA[0][i] * matrizB[i][0]);
//		matrizC[i][i] = (matrizA[0][0] * matrizB[0][i]) + (matrizA[0][i] * matrizB[i][i]);
		
    }
}
