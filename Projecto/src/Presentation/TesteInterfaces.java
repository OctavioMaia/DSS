package Presentation;

import Business.SGE;

public class TesteInterfaces{
	
	public static void main(String args[]){
		SGE s = new SGE();
		//GerirPR i = new GerirPR(null,null); //--nao acabado, ver git
		Login i = new Login(s); //--feito
		//MainEleitor i = new MainEleitor(null);
		//ResultadosAR i = new ResultadosAR(null);
		//ResultadosPR i = new ResultadosPR();
		//CadernoRecenseamento i = new CadernoRecenseamento(s);
		//CriarEleicao i = new CriarEleicao(null);
		//GerirAR i = new GerirAR(null,null);
		//MainAdmin i = new MainAdmin(null);
		//Votar i = new Votar(null);
	}
	
}