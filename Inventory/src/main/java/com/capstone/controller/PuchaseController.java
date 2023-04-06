package com.capstone.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.entity.Item;
import com.capstone.entity.Purchase;
import com.capstone.entity.SalesReportPDFExporter;
import com.capstone.service.PurchaseSeviceImpl;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PuchaseController {

	@Autowired
	private PurchaseSeviceImpl purchaseService;

	@RequestMapping("/purchase")
	public ModelAndView addPurchasePageController() {
		return new ModelAndView("CompletePurchase", "command1", new Purchase());
	}

	/*
	 * This method provides a simple way for users to complete a purchase and
	 * receive feedback on the success of the operation.
	 */
	@RequestMapping("/purchaseComplete")
	public ModelAndView completePurchaseController(@ModelAttribute("command1") Item item) {
		ModelAndView modelAndView = new ModelAndView();
		int purchaseItem = purchaseService.addPurchase(item.getId(), item.getQuantity());
		String message = null;
		if (purchaseItem == 2) {
			message = "Purchase complete";
		} else if (purchaseItem == 1) {
			message = "Not enough stock in inventory for purchase";
		} else {
			message = "Item not found";
		}

		modelAndView.addObject("message", message);
		modelAndView.setViewName("Purchased");
		return modelAndView;
	}

	/*
	 * This method exports the sales report to a PDF file and returns it as a response to
	 * the client. This method is useful for generating reports or exporting data in
	 * a format that can be easily shared and viewed by others. It retrieves the
	 * sales data from the purchase service and passes it to the
	 * SalesReportPDFExporter class to generate the PDF report.
	 */
	@RequestMapping("/exportSales")
	public void exportInventoryToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		String headerKey = "SalesContent";
		String headerValue = "download; filename = sales.pdf";

		response.setHeader(headerKey, headerValue);
		List<Purchase> salesReport = purchaseService.getSalesReport();
		SalesReportPDFExporter salesReportExporter = new SalesReportPDFExporter(salesReport);
		salesReportExporter.exportToPDF(response);
	}
}
