/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texttable;

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class TextFunc {
   
   public static ArrayList<ArrayList<String>> ColsLines = new ArrayList<>(); 
   public static ArrayList<Float> Cols = new ArrayList<>();   
   

   
 public static void Unhide(String fileName) throws IOException {
           File fileToBeHidden = new File(fileName);
 
           Path path = FileSystems.getDefault().getPath(fileName);

           // Hide file;
           Files.setAttribute(path, "dos:hidden", false);
  
           // Now, let's test whether file has been hidden or not
           boolean fileHidden = fileToBeHidden.isHidden();
 
           if (fileHidden)
                  System.out.println(fileName + " is hidden ");
           else
                  System.out.println(fileName + " isn't hidden ");
 
    }

 public static void Hide(String fileName) throws IOException {
           File fileToBeHidden = new File(fileName);
 
           Path path = FileSystems.getDefault().getPath(fileName);

           // Hide file;
           Files.setAttribute(path, "dos:hidden", true);
  
           // Now, let's test whether file has been hidden or not
           boolean fileHidden = fileToBeHidden.isHidden();
 
           if (fileHidden)
                  System.out.println(fileName + " is hidden ");
           else
                  System.out.println(fileName + " isn't hidden ");
 
    }

   
   public void FileRead(String pathToFile){ 

   try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(pathToFile), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // log error
        }
   }
   
   public void FileRead1(String pathToFile){
       int i=0;
       try {
        List<String> Lines = Files.readAllLines(Paths.get(pathToFile), StandardCharsets.UTF_8);
            for(String Line: Lines){
                //System.out.println(Line);
                List<String> LineList = new ArrayList<>(Arrays.asList(Line.split(",")));
                ColsLines.add( new ArrayList<>());
                ColsLines.get(i).addAll(LineList.stream().map(String::valueOf).collect(Collectors.toList()));
                ++i;
            }
       }
       catch(IOException e){
           
       }
       
   }
   
   public void SaveToFile(String selectedfile){
        int ColC = TextTableGUI.jTable1.getColumnCount(); //Определяем кол-во столбцов
        int ItemC = TextTableGUI.jTable1.getRowCount();  //и элементов (строк)
       StringBuilder sb;
       try{
       TextFunc.Unhide(selectedfile);
       }
       catch(IOException e){};
       
       FileWriter fw = null;
       File f = new File(selectedfile);
       //f.setWritable(true);
      // f.setExecutable(true);
	try { //Пытаемся открыть файл на запись
	     fw = new FileWriter(f);
           //  fw = new RandomAccessFile(selectedfile,"rw");
		}catch (IOException e1) {
			e1.printStackTrace();
			}
		for (int i = 0; i < ItemC; i++) { //проходим все строки
		sb = new StringBuilder();
		     for (int j = 0; j < ColC; j++) { //собираем одну строку из множества столбцов
			sb.append(TextTableGUI.jTable1.getValueAt(i, j));
				if (j < ColC - 1) sb.append(',');
				if (j == ColC - 1) sb.append("\r\n");
				}
				System.out.println(sb); //Контроль =)
				try { //Пытаемся писать в файл
				//fw.write(sb.toString()); //записывем собранную строку в файл
                                fw.write(sb.toString());
						//fw.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				sb = null;
			}
        try {
            fw.close();
        } catch (IOException ex) {
            
        }
         try{
       TextFunc.Hide(selectedfile);
       }
       catch(IOException e){};
    }    
   
      public void SaveToFile1(String selectedfile){
        int ColC = TextTableGUI.jTable1.getColumnCount(); //Определяем кол-во столбцов
        int ItemC = TextTableGUI.jTable1.getRowCount();  //и элементов (строк)
       File f = new File(selectedfile);
       f.setWritable(true);
       StringBuilder sb;
       RandomAccessFile fw = null;
	try { //Пытаемся открыть файл на зап 
             fw = new RandomAccessFile(f,"rw");
		}catch (IOException e1) {
			e1.printStackTrace();
			}
               // sb = new StringBuilder();
		for (int i = 0; i < ItemC; i++) { //проходим все строки
		sb = new StringBuilder();
		     for (int j = 0; j < ColC; j++) { //собираем одну строку из множества столбцов
			sb.append(TextTableGUI.jTable1.getValueAt(i, j));
				if (j < ColC - 1) sb.append(',');
				if (j == ColC - 1) sb.append("\r\n");
				}
				//System.out.println(sb); //Контроль =)
				try { //Пытаемся писать в файл
				//fw.write(sb.toString()); //записывем собранную строку в файл
                                String sb1 = sb.toString();
                                fw.write(sb1.getBytes());
                                                          
				}catch (IOException e) {
					e.printStackTrace();
				}
				sb = null;
			}
        try {
            fw.close();
            f.setWritable(false);
        } catch (IOException ex) {
            
        }
    }    
   
   public void  ArrayRead(){
       for (int i=0; i<ColsLines.size();i++ ){
           for (String el: ColsLines.get(i)){
               System.out.print(el+" ");
           }
       System.out.println("\n");
       }
   }
   
   public void InsertToTable(){

       for (int rows=0; rows<ColsLines.size();++rows ){
           for (int cols=0; cols< ColsLines.get(0).size();++cols){
              TextTableGUI.jTable1.getModel().setValueAt(ColsLines.get(rows).get(cols), rows, cols);
           }
       }
   }
   
   public void ScanFiles(){
       int DiskNums = File.listRoots().length;
       String FilePath = File.listRoots()[DiskNums-3].toString();
       FilePath = FilePath + "work";
       System.out.println(FilePath);
       String Name = "BLANKs11.txt";
       try{
           List<File> FileCollect = Files.walk(Paths.get(FilePath)).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
           System.out.println(FilePath+"\\"+Name);
           for (File file: FileCollect){
               System.out.println(file.toPath());
               if (file.toString().equals(FilePath+"\\"+Name)){
                   TextTableGUI.filename1 = file.toString();
                   System.out.println(file.toString());
                   break;
               }
           }
       }
       catch(IOException e){
           
       }
   }
    
}
