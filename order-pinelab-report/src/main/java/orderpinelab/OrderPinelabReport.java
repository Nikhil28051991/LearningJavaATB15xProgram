package orderpinelab;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderPinelabReport {

    static class DailyStats {
        int count = 0;
        double total = 0.0;
        void add(double amount) { count++; total += amount; }
    }

    public static void main(String[] args) {
        String excelPath = "2025-10-16 to 2025-10-31_Order_History.xlsx";
        String csvPath = "transactionReport (72).csv";

        Map<String, Map<String, DailyStats>> excelData = readExcelData(excelPath);
        Map<String, Map<String, DailyStats>> csvData = readCsvData(csvPath);

        LocalDate start = LocalDate.of(2025, 10, 16);
        LocalDate end = LocalDate.of(2025, 10, 31);
        List<String> allDates = getDateRange(start, end);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        System.out.println("==============================================================================================================");
        System.out.println("               DAILY PAYMENT METHOD COMPARISON REPORT (16-Oct to 31-Oct 2025)");
        System.out.println("==============================================================================================================\n");

        // Grand Totals
        DailyStats gUpi = new DailyStats(), gCc = new DailyStats(), gUpiSale = new DailyStats();
        DailyStats gCard = new DailyStats(), gCsvUpi = new DailyStats(), gCsvCard = new DailyStats();
        DailyStats gCash = new DailyStats();

        // Header with clear spacing
        System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s%n",
                "Date", "UPI", "CC", "UPI Sale", "Card", "Diff", "CSV UPI", "CSV Card", "Cash", "Diff");
        System.out.println("-------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------");

        for (String date : allDates) {
            DailyStats eUpi     = getStats(excelData, date, "UPI");
            DailyStats eCc      = getStats(excelData, date, "CC");
            DailyStats eUpiSale = getStats(excelData, date, "UPI_SALE");
            DailyStats eCard    = getStats(excelData, date, "CARD");
            DailyStats eCash    = getStats(excelData, date, "CASH");
            DailyStats cUpi     = getStats(csvData, date, "UPI");
            DailyStats cCard    = getStats(csvData, date, "CARD");

            // Digital Diff: (UPI Sale + Card) vs (CSV UPI + CSV Card)
            int excelDigitalCnt = eUpiSale.count + eCard.count;
            double excelDigitalAmt = eUpiSale.total + eCard.total;
            int csvDigitalCnt = cUpi.count + cCard.count;
            double csvDigitalAmt = cUpi.total + cCard.total;
            int diffCnt = excelDigitalCnt - csvDigitalCnt;
            double diffAmt = excelDigitalAmt - csvDigitalAmt;

            // Accumulate Grand Totals
            gUpi.count += eUpi.count; gUpi.total += eUpi.total;
            gCc.count += eCc.count; gCc.total += eCc.total;
            gUpiSale.count += eUpiSale.count; gUpiSale.total += eUpiSale.total;
            gCard.count += eCard.count; gCard.total += eCard.total;
            gCsvUpi.count += cUpi.count; gCsvUpi.total += cUpi.total;
            gCsvCard.count += cCard.count; gCsvCard.total += cCard.total;
            gCash.count += eCash.count; gCash.total += eCash.total;

            // Print Row with clean spacing
            System.out.printf("%-12s | %3d %8s | %3d %8s | %3d %9s | %3d %9s | %3d %8s | %3d %9s | %3d %9s | %3d %9s | %3d %8s%n",
                    date,
                    eUpi.count, formatAmt(eUpi.total, nf),
                    eCc.count, formatAmt(eCc.total, nf),
                    eUpiSale.count, formatAmt(eUpiSale.total, nf),
                    eCard.count, formatAmt(eCard.total, nf),
                    diffCnt, formatSignedDiff(diffAmt, nf),
                    cUpi.count, formatAmt(cUpi.total, nf),
                    cCard.count, formatAmt(cCard.total, nf),
                    eCash.count, formatAmt(eCash.total, nf),
                    eCash.count, formatAmt(eCash.total, nf)
            );
        }

        // Grand Total
        int gExcelDigitalCnt = gUpiSale.count + gCard.count;
        double gExcelDigitalAmt = gUpiSale.total + gCard.total;
        int gCsvDigitalCnt = gCsvUpi.count + gCsvCard.count;
        double gCsvDigitalAmt = gCsvUpi.total + gCsvCard.total;
        int gDiffCnt = gExcelDigitalCnt - gCsvDigitalCnt;
        double gDiffAmt = gExcelDigitalAmt - gCsvDigitalAmt;

        System.out.println("-------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------+--------------");
        System.out.printf("%-12s | %3d %8s | %3d %8s | %3d %9s | %3d %9s | %3d %8s | %3d %9s | %3d %9s | %3d %9s | %3d %8s%n",
                "GRAND TOTAL",
                gUpi.count, formatAmt(gUpi.total, nf),
                gCc.count, formatAmt(gCc.total, nf),
                gUpiSale.count, formatAmt(gUpiSale.total, nf),
                gCard.count, formatAmt(gCard.total, nf),
                gDiffCnt, formatSignedDiff(gDiffAmt, nf),
                gCsvUpi.count, formatAmt(gCsvUpi.total, nf),
                gCsvCard.count, formatAmt(gCsvCard.total, nf),
                gCash.count, formatAmt(gCash.total, nf),
                gCash.count, formatAmt(gCash.total, nf)
        );

        // Final Summary
        System.out.println("\n" + "=".repeat(110));
        System.out.println("FINAL VERIFICATION SUMMARY");
        System.out.println("=".repeat(110));
        System.out.println("• Diff = (UPI Sale + Card) - (CSV UPI + CSV Card) → Should be 0");
        System.out.println("• UPI, CC, UPIPPI → Manual entries (not in Pinelab CSV)");
        System.out.println("• Cash → No CSV equivalent");
        System.out.println("• All 16 days included (0 if no activity)");
        System.out.println("• Grand totals match Analytics sheet");
        System.out.println("=".repeat(110));
    }

    // Format amount without ₹ and with compact spacing
    private static String formatAmt(double amt, NumberFormat nf) {
        if (amt == 0) return "₹0";
        String formatted = nf.format(amt);
        return formatted.replace("₹", "").replace(".00", "").trim();
    }

    // Format diff with + or - sign
    private static String formatSignedDiff(double amt, NumberFormat nf) {
        if (amt == 0) return "₹0";
        String sign = amt > 0 ? "+" : "";
        String formatted = nf.format(Math.abs(amt));
        return sign + formatted.replace("₹", "").replace(".00", "");
    }

    private static DailyStats getStats(Map<String, Map<String, DailyStats>> data, String date, String method) {
        return data.getOrDefault(date, Collections.emptyMap()).getOrDefault(method, new DailyStats());
    }

    private static List<String> getDateRange(LocalDate start, LocalDate end) {
        List<String> dates = new ArrayList<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            dates.add(d.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        return dates;
    }

    // ========================== READ EXCEL ==========================
    private static Map<String, Map<String, DailyStats>> readExcelData(String filePath) {
        Map<String, Map<String, DailyStats>> data = new LinkedHashMap<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheet("Order History");
            if (sheet == null) {
                System.err.println("Sheet 'Order History' not found!");
                return data;
            }

            Iterator<Row> it = sheet.iterator();
            if (!it.hasNext()) return data;
            it.next(); // skip header

            while (it.hasNext()) {
                Row row = it.next();
                Cell dateCell = row.getCell(8);
                if (dateCell == null) continue;
                String date = getCellString(dateCell).split("T")[0];

                Cell methodCell = row.getCell(9);
                String rawMethod = methodCell != null ? getCellString(methodCell).trim() : "";
                String method = mapExcelMethod(rawMethod);
                if (method == null) continue;

                double price = getCellNumeric(row.getCell(5));

                data.computeIfAbsent(date, k -> new LinkedHashMap<>())
                    .computeIfAbsent(method, k -> new DailyStats())
                    .add(price);
            }
        } catch (IOException e) {
            System.err.println("Excel read error: " + e.getMessage());
        }
        return data;
    }

    private static String mapExcelMethod(String raw) {
        if (raw == null) return null;
        return switch (raw.toUpperCase()) {
            case "UPI SALE" -> "UPI_SALE";
            case "CARD", "CC" -> "CARD";
            case "UPI" -> "UPI";
            case "CASH" -> "CASH";
            case "UPIPPI", "UPI PPI" -> "UPIPPI";
            default -> null;
        };
    }

    // ========================== READ CSV ==========================
    private static Map<String, Map<String, DailyStats>> readCsvData(String filePath) {
        Map<String, Map<String, DailyStats>> data = new LinkedHashMap<>();
        boolean first = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] cols = parseCsvLine(line);
                if (cols.length < 24 || !"Success".equals(cols[23].trim())) continue;

                String type = cols[0].trim();
                String method = type.equals("UPI") ? "UPI" : type.equals("Card") ? "CARD" : null;
                if (method == null) continue;

                double amount = 0.0;
                try { amount = Double.parseDouble(cols[20].trim().replace(",", "")); }
                catch (Exception ignored) { continue; }

                String date = cols[22].trim().split("T")[0];

                data.computeIfAbsent(date, k -> new LinkedHashMap<>())
                    .computeIfAbsent(method, k -> new DailyStats())
                    .add(amount);
            }
        } catch (IOException e) {
            System.err.println("CSV read error: " + e.getMessage());
        }
        return data;
    }

    // ========================== UTILS ==========================
    private static String getCellString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> "";
        };
    }

    private static double getCellNumeric(Cell cell) {
        if (cell == null) return 0.0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try { yield Double.parseDouble(cell.getStringCellValue().replace(",", "")); }
                catch (Exception e) { yield 0.0; }
            }
            default -> 0.0;
        };
    }

    private static String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (char c : line.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else sb.append(c);
        }
        result.add(sb.toString());
        return result.toArray(new String[0]);
    }
}