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
public class InventoryPDFExporter {

	private List<Item> inventory;

	// Method to write the column headings of the PDF table
	private void writeColumn(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setPadding(6);

		cell.setPhrase(new Phrase("Item ID"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Item Name"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Category"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Sale Price"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cost Price"));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Units"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Units Sold"));
		table.addCell(cell);
	}

	// Method to write the data of the PDF table
	private void writeData(PdfPTable table) {
		for (Item item : inventory) {
			table.addCell(String.valueOf(item.getId()));
			table.addCell(item.getName());
			table.addCell(item.getCategory());
			table.addCell(String.valueOf(item.getPrice()));
			table.addCell(String.valueOf(item.getCostPrice()));
			table.addCell(String.valueOf(item.getQuantity()));
			table.addCell(String.valueOf(item.getQuantitySold()));
		}
	}

	// Method to export the inventory to a PDF file and write it to the response
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		document.add(new Paragraph("Inventory"));
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
