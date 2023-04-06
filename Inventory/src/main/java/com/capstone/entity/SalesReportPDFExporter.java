package com.capstone.entity;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportPDFExporter {

	private List<Purchase> sales;


	private void writeColumn(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setPadding(6);

		cell.setPhrase(new Phrase("Purchase ID"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Item ID"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Sale Date"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price of 1 unit"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Quantity"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Sale Price"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Profit"));
		table.addCell(cell);
	}

	private void writeData(PdfPTable table) {
		for (Purchase purchase : sales) {
			table.addCell(String.valueOf(purchase.getId()));
			table.addCell(String.valueOf(purchase.getItem().getId()));
			table.addCell(purchase.getPurchaseDate());
			table.addCell(String.valueOf(purchase.getItem().getPrice()));
			table.addCell(String.valueOf(purchase.getQuantity()));
			table.addCell(String.valueOf(purchase.getPrice()));	
			table.addCell(String.valueOf(purchase.getProfit()));	
		}
	}

	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		document.add(new Paragraph("Sales Report"));
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10);
		float cellHeight = 35f;
		writeColumn(table);
		writeData(table);
		
		for (PdfPRow row : table.getRows()) {
		    for (PdfPCell cell : row.getCells()) {
		        cell.setFixedHeight(cellHeight);
		    }
		}
		
		document.add(table);
		document.close();
	}
}
