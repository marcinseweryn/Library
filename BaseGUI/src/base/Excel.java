package base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;


public class Excel {

	public void createExelFile(ArrayList<Book> list,String tableName) throws IOException{
		
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("sheet");
		
		Row heading=sheet.createRow(0);
		heading.createCell(0).setCellValue("Marka");
		heading.createCell(1).setCellValue("Moc");
		heading.createCell(2).setCellValue("Cena");
		
		CellStyle headingStyle=workbook.createCellStyle();
		Font font=workbook.createFont();
		font.setBold(true);
		headingStyle.setFont(font);
		for(int i=0;i<3;i++){
			heading.getCell(i).setCellStyle(headingStyle);
			sheet.autoSizeColumn(i);
		}
		
		int index=1;
		for(Book car:list){
			Row row=sheet.createRow(index);
			
			Cell cellMark=row.createCell(0);
			cellMark.setCellValue(car.getTitle());
			Cell cellPower=row.createCell(1);
			cellPower.setCellValue(car.getAuthor());
			Cell cellPrice=row.createCell(2);
			cellPrice.setCellValue(car.getISBN());
			
			index++;
		}
		
		File file=new File(tableName+".xls");
		FileOutputStream fos=new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();		
	}
	
	
	
}
