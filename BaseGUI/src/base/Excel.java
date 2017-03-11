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

import javafx.stage.FileChooser;


public class Excel {
	
	public void createExcelStandardReport(ArrayList<StandardReport> standardReportList) throws IOException{
		
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("sheet");
		
		Row heading=sheet.createRow(0);
		heading.createCell(0).setCellValue("Date");
		heading.createCell(1).setCellValue("Borrowed books");
		heading.createCell(2).setCellValue("Booked Books");
		heading.createCell(3).setCellValue("Returned Books");
		heading.createCell(4).setCellValue("New Users");
		
		CellStyle headingStyle=workbook.createCellStyle();
		Font font=workbook.createFont();
		font.setBold(true);
		headingStyle.setFont(font);
		for(int i=0;i<5;i++){
			heading.getCell(i).setCellStyle(headingStyle);
			sheet.autoSizeColumn(i);
		}
		
		int index=1;
		for(StandardReport report:standardReportList){
			Row row=sheet.createRow(index);
			
			Cell cellDate=row.createCell(0);
			cellDate.setCellValue(report.getDate());
			Cell cellBorrowedBooks=row.createCell(1);
			cellBorrowedBooks.setCellValue(report.getBorrowedBooks());
			Cell cellBookedBooks=row.createCell(2);
			cellBookedBooks.setCellValue(report.getBookedBooks());
			Cell cellReturnedBooks=row.createCell(3);
			cellReturnedBooks.setCellValue(report.getReturnedBooks());
			Cell cellNewUsers=row.createCell(4);
			cellNewUsers.setCellValue(report.getNewUsers());
			
			index++;
		}
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save standard report");
    	fileChooser.setInitialFileName("Standard report");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
 
		File file=fileChooser.showSaveDialog(null);
		FileOutputStream fos=new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();		
	}
	
	
	public void createExcelMostPopularBooks(ArrayList<MostPopularBooks> mostPopularBooksList) throws IOException{
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("sheet");
		
		Row heading=sheet.createRow(0);
		heading.createCell(0).setCellValue("Title");
		heading.createCell(1).setCellValue("Author");
		heading.createCell(2).setCellValue("Borrows");
		
		CellStyle headingStyle=workbook.createCellStyle();
		Font font=workbook.createFont();
		font.setBold(true);
		headingStyle.setFont(font);
		for(int i=0;i<3;i++){
			heading.getCell(i).setCellStyle(headingStyle);
			sheet.autoSizeColumn(i);
		}
				
		int index=1;
		for(MostPopularBooks report:mostPopularBooksList){
			Row row=sheet.createRow(index);
			
			Cell cellTitle=row.createCell(0);
			cellTitle.setCellValue(report.getTitle());
			Cell cellAuthor=row.createCell(1);
			cellAuthor.setCellValue(report.getAuthor());
			Cell cellBorrows=row.createCell(2);
			cellBorrows.setCellValue(report.getBorrows());
			
			index++;
		}
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save most popular books");
    	fileChooser.setInitialFileName("Most popular books");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
 
		File file=fileChooser.showSaveDialog(null);
		FileOutputStream fos=new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();		
		
	}
	
	
}
