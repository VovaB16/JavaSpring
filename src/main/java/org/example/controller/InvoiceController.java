package org.example.controller;

import org.example.exception.InvoiceNotFoundException;
import org.example.model.Invoice;
import org.example.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private IInvoiceService service;

    private static final String UPLOAD_DIR = "file:D:/Java/JavaSpring/src/main/resources/photo/";

    @GetMapping("/register")
    public String showRegistration() {
        return "registerInvoicePage";
    }

    @PostMapping("/save")
    public String saveInvoice(
            @ModelAttribute Invoice invoice,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes attributes
    ) {
        if (!image.isEmpty()) {
            try {
                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, image.getBytes());

                invoice.setImagePath("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                attributes.addAttribute("message", "Error saving the file!");
                return "registerInvoicePage";
            }
        }

        Long id = service.saveInvice(invoice).getId();
        attributes.addAttribute("message", "Record with id : '" + id + "' is saved successfully!");
        return "redirect:getAllInvoices";
    }

    @GetMapping("/getAllInvoices")
    public String getAllInvoices(
            @RequestParam(value = "message", required = false) String message,
            Model model
    ) {
        List<Invoice> invoices = service.getAllInvoices();
        model.addAttribute("list", invoices);
        model.addAttribute("message", message);
        return "allInvoicesPage";
    }

    @GetMapping("/edit")
    public String getEditPage(
            Model model,
            RedirectAttributes attributes,
            @RequestParam Long id
    ) {
        String page = null;
        try {
            Invoice invoice = service.getInvoiceById(id);
            model.addAttribute("invoice", invoice);
            page = "editInvoicePage";
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
            page = "redirect:getAllInvoices";
        }
        return page;
    }

    @PostMapping("/update")
    public String updateInvoice(
            @ModelAttribute Invoice invoice,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes attributes
    ) {
        if (!image.isEmpty()) {
            try {
                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, image.getBytes());

                invoice.setImagePath("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                attributes.addAttribute("message", "Error updating the file!");
                return "editInvoicePage";
            }
        }

        service.updateInvoice(invoice);
        Long id = invoice.getId();
        attributes.addAttribute("message", "Invoice with id: '" + id + "' is updated successfully!");
        return "redirect:getAllInvoices";
    }

    @GetMapping("/delete")
    public String deleteInvoice(
            @RequestParam Long id,
            RedirectAttributes attributes
    ) {
        try {
            service.deleteInvoiceById(id);
            attributes.addAttribute("message", "Invoice with Id : '" + id + "' is removed successfully!");
        } catch (InvoiceNotFoundException e) {
            e.printStackTrace();
            attributes.addAttribute("message", e.getMessage());
        }
        return "redirect:getAllInvoices";
    }



}
