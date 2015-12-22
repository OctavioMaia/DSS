package Presentation;

import Business.SGE;

public class TesteInterfaces{
	
	public static void main(String args[]){
		SGE s = new SGE();
		//GerirPR i = new GerirPR(null); --nao acabado, ver git
		//Login i = new Login(); --feito
		//MainEleitor i = new MainEleitor(null);
		//ResultadosAR i = new ResultadosAR(null);
		//ResultadosPR i = new ResultadosPR();
		//CadernoRecenseamento i = new CadernoRecenseamento(s);
		CriarEleicao i = new CriarEleicao(null);
	}
	
}