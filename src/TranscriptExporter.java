import javax.swing.*;
import java.io.FileOutputStream; // Changed from FileWriter
import java.io.IOException;
import java.util.ArrayList;

// Imports for OpenPDF
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

public class TranscriptExporter {

    // Define fonts for the PDF
    private static final Font FONT_TITLE = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD);
    private static final Font FONT_HEADER = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);
    private static final Font FONT_BODY = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL);
    private static final Font FONT_BODY_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.BOLD);

    public static void exportTranscript(Student student) {
        
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Error: No student data to export.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Get all grade data
        StudentService ss = new StudentService();
        ArrayList<String[]> grades = ss.getGrades(student.getUsername());

        // 2. Show a JFileChooser to get the save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Transcript as PDF");
        
        // Suggest a .pdf filename
        fileChooser.setSelectedFile(new java.io.File(student.getUsername() + "_Transcript.pdf"));
        
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            // Ensure the file has a .pdf extension
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new java.io.File(fileToSave.getParentFile(), fileToSave.getName() + ".pdf");
            }

            // 3. Create the PDF Document
            Document document = new Document(PageSize.A4);
            
            try {
                // 4. Get a PdfWriter instance
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));

                // 5. Open the document
                document.open();

                // 6. Add content
                
                // --- Title ---
                Paragraph title = new Paragraph("OFFICIAL TRANSCRIPT", FONT_TITLE);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE); // Add a blank line

                // --- Student Info (in a borderless table) ---
                PdfPTable infoTable = new PdfPTable(2); // 2 columns
                infoTable.setWidthPercentage(100);
                infoTable.addCell(createCell("Student Username:", FONT_BODY_BOLD, Element.ALIGN_RIGHT));
                infoTable.addCell(createCell(student.getUsername(), FONT_BODY, Element.ALIGN_LEFT));
                infoTable.addCell(createCell("Roll Number:", FONT_BODY_BOLD, Element.ALIGN_RIGHT));
                infoTable.addCell(createCell(String.valueOf(student.getRollno()), FONT_BODY, Element.ALIGN_LEFT));
                infoTable.addCell(createCell("Program:", FONT_BODY_BOLD, Element.ALIGN_RIGHT));
                infoTable.addCell(createCell(student.getprogram(), FONT_BODY, Element.ALIGN_LEFT));
                infoTable.addCell(createCell("Year:", FONT_BODY_BOLD, Element.ALIGN_RIGHT));
                infoTable.addCell(createCell(String.valueOf(student.getYear()), FONT_BODY, Element.ALIGN_LEFT));
                document.add(infoTable);
                document.add(Chunk.NEWLINE);

                // --- Grades Header ---
                Paragraph gradesHeader = new Paragraph("COURSES & GRADES", FONT_HEADER);
                gradesHeader.setAlignment(Element.ALIGN_CENTER);
                document.add(gradesHeader);
                
                // Add a divider line
                Paragraph divider = new Paragraph("________________________________________________________");
                divider.setAlignment(Element.ALIGN_CENTER);
                document.add(divider);
                document.add(Chunk.NEWLINE);


                if (grades.isEmpty()) {
                    document.add(new Paragraph("No grades available.", FONT_BODY));
                } else {
                    // Columns from getGrades:
                    // [0] subject, [1] title, [2] quiz, [3] assignment, 
                    // [4] midsem, [5] endsem, [6] group_project, [7] grad
                    for (String[] row : grades) {
                        
                        // Course Header
                        String titleStr = (row[1] != null && !row[1].isEmpty()) ? " - " + row[1] : "";
                        Paragraph courseHeader = new Paragraph(row[0] + titleStr, FONT_BODY_BOLD);
                        document.add(courseHeader);

                        // Final Grade
                        Paragraph finalGrade = new Paragraph("  Final Grade: " + row[7], FONT_BODY);
                        document.add(finalGrade);

                        // Breakdown Table
                        PdfPTable breakdownTable = new PdfPTable(2);
                        breakdownTable.setWidthPercentage(90); // Indent a bit
                        breakdownTable.setSpacingBefore(5f);
                        breakdownTable.setSpacingAfter(10f);
                        
                        breakdownTable.addCell(createCell("  Quiz:", FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell(row[2], FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell("  Assignment:", FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell(row[3], FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell("  Mid-Sem:", FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell(row[4], FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell("  End-Sem:", FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell(row[5], FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell("  Project:", FONT_BODY, Element.ALIGN_LEFT));
                        breakdownTable.addCell(createCell(row[6], FONT_BODY, Element.ALIGN_LEFT));
                        
                        document.add(breakdownTable);
                    }
                }
                
                document.add(Chunk.NEWLINE);
                Paragraph end = new Paragraph("========== END OF TRANSCRIPT ==========", FONT_BODY_BOLD);
                end.setAlignment(Element.ALIGN_CENTER);
                document.add(end);

                // 7. Close the document
                document.close();
                
                JOptionPane.showMessageDialog(null, "Transcript PDF saved successfully to:\n" + fileToSave.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving transcript PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Helper method to create styled, borderless cells for the info table
    private static PdfPCell createCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(4);
        return cell;
    }
}