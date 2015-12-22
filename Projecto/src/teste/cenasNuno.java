package teste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import Business.*;
import Data.CirculoDAO;
import Data.EleitoresDAO;

public class cenasNuno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EleitoresDAO eleitores = new EleitoresDAO();
		CirculoDAO circulos = new CirculoDAO();
		for (int i=1;i<23;i++){
			Circulo c = new Circulo(i,"nuno gay",0);
			Circulo s = circulos.put(i, c);
			if(s!=null){
				System.out.println(s.toString());
			}else{
				System.out.println("novo");
			}
		}
		System.out.println("Fim");
		System.out.println(circulos.size());
		
			//System.out.println(eleitores.values());
		List<Eleitor> l = lerCadernoRecenciamento(File.separator+"Users"+File.separator+"joaosilva"+File.separator+"Desktop"+File.separator+"nomes.csv");
		Iterator<Eleitor> i = l.iterator();
		int i1=0;
		while(i.hasNext()){
			//System.out.println(i1++);
			Eleitor e = i.next();
			//System.out.println(i.next().toString());
			Eleitor e1 = eleitores.put(e.getnIdent(), e);
			if(e1!=null){
				System.out.println(e1.toString());
			}else{
				System.out.println("novo");
			}
			
		}
		System.out.println("Tamanho:" + eleitores.size());
		System.out.println("ok");
	
	}
	
	public static List<Eleitor> lerCadernoRecenciamento(String path) {

		ArrayList<Eleitor> listaEleitores = new ArrayList<Eleitor>();
		String flin = "sep=";
		String line = "";
		String csvSplit = ",";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				if (line.contains(flin)){
					csvSplit=line.split("=")[1];
					System.out.println(csvSplit);
				}else{
					String[] eleitores = line.split(csvSplit);
					
					int a = Integer.parseInt(eleitores[1]);
					int b = Integer.parseInt(eleitores[0]);
					Eleitor e = new Eleitor(eleitores[2], a, b, eleitores[3]);
					listaEleitores.add(e);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listaEleitores;
	}
}
