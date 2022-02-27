package model.services.itens;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.MainWindow;
import exception.ObjectException;
import model.entities.User;
import model.entities.utilitary.Item;

public class CreatePDFFileExchange {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
	private static final Date hoje = Calendar.getInstance(Locale.getDefault()).getTime();
	
	private final String fileServer = "\\" + "\\10.209.8.25\\tecnologia$\\04 - FIELD\\05 - TERMOS\\";
	private final String fileLocal = "C:\\Users\\853373\\Documents\\";
	private String nameFile = "";
	private String filePath = "";
	
	private final User user;
	private final Item itemOld;
	private final Item itemNew;
	
	public CreatePDFFileExchange(User user, Item itemOld, Item itemNew) {
		this.user = user;
		this.itemOld = itemOld;
		this.itemNew = itemNew;
		nameFile = user.getRegistration() + " - Exchange - " + sdf.format(hoje) + ".pdf";
		filePath = fileServer + nameFile;
		createPDF();
	}
	
	private void createPDF() {

		Document document = new Document(PageSize.A4, 36f, 52f, 40f, 20f);

		Paragraph emptyLine = new Paragraph(" ");

		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath.contains("pdf") ? filePath : filePath + ".pdf"));
			document.open();
			document.add(getHeader()); // Header
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(getDate()); // Date
			document.add(emptyLine);
			document.add(getUser()); // User
			document.add(emptyLine);
			document.add(getOld());
			document.add(emptyLine);
			document.add(getItensOld()); // Item Old
			document.add(emptyLine);
			document.add(getNew());
			document.add(emptyLine);
			document.add(getItensNew()); // Item New
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(getThird());
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(getSignature());
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(emptyLine);
			document.add(getTechnical());

			Desktop.getDesktop().open(new File(filePath));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			if (ioe.getMessage().contains("(O caminho da rede não foi encontrado)")) {
				filePath = fileLocal + nameFile;
				createPDF();	
			} else if (ioe.getMessage().contains("(O sistema não pode encontrar o caminho especificado)")) {
				throw new ObjectException(ioe.getMessage());
			}
		} finally {
			document.close();
		}
	}
	
	private Paragraph getHeader() {
		Paragraph header = new Paragraph("TERMO DE ALTERAÇÃO");
		header.setAlignment(1);
		return header;
	}
	
	private Paragraph getDate() {
		Paragraph date = new Paragraph("São Paulo, " + dfmt.format(hoje) + ".");
		date.setAlignment(0);
		return date;
	}
	
	private Paragraph getUser() {
		Paragraph first = new Paragraph("Eu, " + user.getName() + ", portador(a) do CPF: "
				+ user.getCpf() + ", matrícula: " + user.getRegistration()
				+ ", alocado(a) no projeto: " + user.getProject().getName()
				+ ", realizei nesta data, na empresa INDRA, a troca dos equipamentos e periféricos listados abaixo: \n");
		first.setAlignment(0);
		return first;
	}
	
	private Paragraph getOld() {
		Paragraph old_ = new Paragraph("DEVOLVIDOS>");
		old_.setAlignment(0);
		return old_;
	}
	
	private PdfPTable getItensOld() {
		PdfPTable table = new PdfPTable(4);
		
		PdfPCell type = new PdfPCell(new Phrase("Tipo"));
		type.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(type);
		
		PdfPCell code = new PdfPCell(new Phrase("Codigo"));
		code.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(code);
		
		PdfPCell model = new PdfPCell(new Phrase("Nome"));
		model.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(model);
		
		PdfPCell patrimonyNumber = new PdfPCell(new Phrase("Número de Patrimônio"));
		patrimonyNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(patrimonyNumber);		
		table.setHeaderRows(1);

		table.addCell(itemOld.getType()); // Type
		table.addCell(itemOld.getCode()); // Code
		table.addCell(itemOld.getName()); // Brand
		table.addCell(itemOld.getPatrimonyNumber()); // Patrimony Number
		
		return table;
	}
	
	private Paragraph getNew() {
		Paragraph new_ = new Paragraph("RETIRADOS>");
		new_.setAlignment(0);
		return new_;
	}
	
	private PdfPTable getItensNew() {
		PdfPTable table = new PdfPTable(4);
		
		PdfPCell type = new PdfPCell(new Phrase("Tipo"));
		type.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(type);
		
		PdfPCell code = new PdfPCell(new Phrase("Codigo"));
		code.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(code);
		
		PdfPCell model = new PdfPCell(new Phrase("Nome"));
		model.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(model);
		
		PdfPCell patrimonyNumber = new PdfPCell(new Phrase("Número de Patrimônio"));
		patrimonyNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(patrimonyNumber);		
		table.setHeaderRows(1);

		table.addCell(itemNew.getType()); // Type
		table.addCell(itemNew.getCode()); // Code
		table.addCell(itemNew.getName()); // Brand
		table.addCell(itemNew.getPatrimonyNumber()); // Patrimony Number
		
		return table;
	}
	
	private Paragraph getThird() {
		Paragraph third = new Paragraph("Declaro estar ciente que:\n"
				+ "• O equipamento é de uso estritamente profissional e apenas devo utilizá-lo para executar as atividades solicitadas pela Empresa. \n"
				+ "• É proibida a instalação, de forma intencional ou não, de qualquer software, hardware, periférico ou qualquer outro componente no equipamento.  \n"
				+ "• Qualquer alteração no equipamento, tanto de Software, quanto de Hardware, somente poderá ser efetuada pelo departamento de Suporte Técnico da Indra ou com uma autorização por e-mail do Gestor. \n"
				+ "• As penalidades previstas na Lei nº 9.609/98 de proteção aos Programas de Computador recairão exclusivamente sobre o colaborador responsável pelo equipamento, sob pena, também, de ser demitido por justa causa. \n");

		third.setAlignment(0);
		return third;
	}

	private Paragraph getSignature() {
		Paragraph signature = new Paragraph("______________________________\n" + "              Assinatura           ");
		signature.setAlignment(1);
		return signature;
	}

	private Paragraph getTechnical() {
		Paragraph technical = new Paragraph("Técnico Responsável: " + MainWindow.collaborator.getName());
		technical.setAlignment(0);
		return technical;
	}
}
